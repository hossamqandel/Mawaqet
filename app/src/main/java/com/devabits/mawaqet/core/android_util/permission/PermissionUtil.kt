package com.devabits.mawaqet.core.android_util.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class PermissionUtil(
    private val context: Context?,
    private var requestPermissionLauncher: ActivityResultLauncher<String>?
) {

   fun AppCompatActivity.onPermissionActionState(onGranted: () -> Unit, onDenied: () -> Unit){
       requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
           if (isGranted) onGranted.invoke()
           else onDenied.invoke()
       }
   }

    fun checkPermission(permission: String, onPermissionAccepted: () -> Unit){
        context?.let {
            if (ContextCompat.checkSelfPermission(it, permission)
                == PackageManager.PERMISSION_GRANTED
                )
                onPermissionAccepted.invoke()
            else requestPermissionLauncher?.launch(permission)
        }
    }
}
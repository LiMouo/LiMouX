package com.limoux.limou

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

class InvisibleFragment : Fragment() {
    private var callback: ((Boolean, List<String>) -> Unit)? = null
    fun requestNow(cd: (Boolean, List<String>) -> Unit, vararg permissions: String) {
        callback = cd;
        requestPermissions(permissions, 1);
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            var deniedList = ArrayList<String>()
            for ((index,result) in grantResults.withIndex()){
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            val allGranted = deniedList.isEmpty()
            callback?.let { it(allGranted,deniedList) }
        }
    }
}
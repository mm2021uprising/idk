package org.mm2021uprising.idk

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.mm2021uprising.idk.util.Constants

class MainActivity : AppCompatActivity() {

  private val dpm: DevicePolicyManager by lazy {
    getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
  }

  private val deviceAdminPromptDialog: AlertDialog by lazy {
    val builder = AlertDialog.Builder(this)
    builder.setCancelable(false)
    builder.create()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    //init device policy manager

  }

  private fun proceedDeviceAdministration() {
    val isActiveDeviceAdmin = dpm.isAdminActive(
        ComponentName(Constants.IDK_DEVICE_ADMIN_PACKAGE, Constants.IDK_DEVICE_ADMIN_CLASS)
    )

    if (!isActiveDeviceAdmin) {
      //enable device admin first
      AlertDialog.Builder(this@MainActivity)
          .setTitle(R.string.notice)
          .setMessage(R.string.device_admin_notice_msg)
          .setCancelable(false)
          .setPositiveButton(R.string.ok) { dialog, _ ->
            dialog.dismiss()
            //open device admin setting
            val deviceAdminIntent = Intent()

            deviceAdminIntent.component =
              ComponentName("com.android.settings", "com.android.settings.DeviceAdminSettings")

            startActivity(deviceAdminIntent)
          }
          .setNegativeButton(R.string.cancel) { dialog, _ ->
            dialog.dismiss()
            finishAffinity()
          }
          .create()
          .also { deviceAdminProceedDialog ->
          }
          .show()
    }
  }

  override fun onResume() {
    super.onResume()
    proceedDeviceAdministration()
  }
}
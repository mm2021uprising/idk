package org.mm2021uprising.idk.feature.home

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.mm2021uprising.idk.R
import org.mm2021uprising.idk.util.Constants
import timber.log.Timber

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

      Timber.e("aunk Htoo")
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
            deviceAdminProceedDialog.setOnShowListener {
              deviceAdminProceedDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                  .setTextColor(ContextCompat.getColor(this@MainActivity, R.color.text_secondary))
            }

          }
          .show()
    }
  }

  override fun onResume() {
    super.onResume()
    proceedDeviceAdministration()
  }
}
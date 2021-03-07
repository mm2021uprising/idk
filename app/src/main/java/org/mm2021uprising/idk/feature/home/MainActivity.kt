package org.mm2021uprising.idk.feature.home

import android.Manifest
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import org.mm2021uprising.idk.R
import org.mm2021uprising.idk.util.Constants
import timber.log.Timber

class MainActivity : AppCompatActivity() {

  companion object {

    private const val OUTGOING_CALL_REQUEST_CODE = 10
  }

  private val dpm: DevicePolicyManager by lazy {
    getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    ActivityCompat.requestPermissions(
        this, arrayOf(Manifest.permission.PROCESS_OUTGOING_CALLS), OUTGOING_CALL_REQUEST_CODE)

    //requestRuntimePermission()

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

  private fun requestRuntimePermission() {
    Dexter.withContext(this)
        .withPermission(Manifest.permission.PROCESS_OUTGOING_CALLS)
        .withListener(object : PermissionListener {
          override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
            Toast.makeText(
                this@MainActivity, "You need to allow this permission", Toast.LENGTH_SHORT
            )
                .show()
          }

          override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
            Toast.makeText(this@MainActivity, "Permission granted", Toast.LENGTH_SHORT)
                .show()
          }

          override fun onPermissionRationaleShouldBeShown(
            p0: PermissionRequest?,
            p1: PermissionToken?
          ) {
            p1?.continuePermissionRequest()
          }
        })
        .check()
  }
}
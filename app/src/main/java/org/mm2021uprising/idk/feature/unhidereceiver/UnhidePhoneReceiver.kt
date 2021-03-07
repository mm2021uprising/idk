package org.mm2021uprising.idk.feature.unhidereceiver

import android.app.admin.DevicePolicyManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.mm2021uprising.idk.PasswordFailedReceiver
import org.mm2021uprising.idk.prefmanager.PrefManager
import org.mm2021uprising.idk.util.Constants

class UnhidePhoneReceiver : BroadcastReceiver() {

  override fun onReceive(
    context: Context?,
    intent: Intent?
  ) {

    val dpm = context!!.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

    val prefManager = PrefManager(context!!)

    GlobalScope.launch {

      val incomingPhone = intent?.getStringExtra(Intent.EXTRA_PHONE_NUMBER)

      incomingPhone?.let { phone ->

        withContext(Dispatchers.Main)
        {
          Toast.makeText(context, "out goding $phone", Toast.LENGTH_LONG)
              .show()
        }

        if (phone == prefManager.getPredefinePhoneForUnhide()) {
          resultData = null

          if (prefManager.isHideApps()) {

            val ownerComponentName = ComponentName(context, PasswordFailedReceiver::class.java)
            dpm.setApplicationHidden(ownerComponentName, Constants.FACEBOOK_PKG_NAME, false)
            dpm.setApplicationHidden(ownerComponentName, Constants.MESSENGER_PKG_NAME, false)

            prefManager.hideApps(false)
          }
        }
      }
    }
  }
}
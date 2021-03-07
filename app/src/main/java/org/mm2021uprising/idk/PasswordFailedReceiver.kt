package org.mm2021uprising.idk

import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.UserHandle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.mm2021uprising.idk.prefmanager.PrefManager
import org.mm2021uprising.idk.util.Constants
import timber.log.Timber
import java.time.LocalDateTime
import java.time.ZoneOffset

class PasswordFailedReceiver : DeviceAdminReceiver() {

  companion object {
    private const val INCORRECT_PASSWORD_FINAL_COUNT = 3
    private const val TIME_INTERVAL = 30000

  }

  override fun onEnabled(
    context: Context,
    intent: Intent
  ) {

    super.onEnabled(context, intent)
  }

  override fun onPasswordFailed(
    context: Context,
    intent: Intent,
    user: UserHandle
  ) {
    super.onPasswordFailed(context, intent, user)

    val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

    val prefManager = PrefManager(context)

    GlobalScope.launch {

      val localDateTime = prefManager.getPasswordLocalDate()
      val now = LocalDateTime.now()

      Timber.e("Password failed")
      if (localDateTime == null) {
        prefManager.updatePasswordIncorrectCount(1)
        prefManager.updatePasswordLocalDate(now)

      } else if (now.toInstant(ZoneOffset.UTC)
              .toEpochMilli() - localDateTime.toInstant(ZoneOffset.UTC)
              .toEpochMilli() <= TIME_INTERVAL
      ) {

        var count = prefManager.getPasswordIncorrectCount() + 1

        if (count == INCORRECT_PASSWORD_FINAL_COUNT) {
          //hide them now
          Timber.e("hi $count")
          if (!prefManager.isHideApps()) {
            val ownerComponentName = ComponentName(context, PasswordFailedReceiver::class.java)
            dpm.setApplicationHidden(ownerComponentName, Constants.FACEBOOK_PKG_NAME, true)
            dpm.setApplicationHidden(ownerComponentName, Constants.MESSENGER_PKG_NAME, true)

            prefManager.hideApps(true)
          }

          prefManager.updatePasswordIncorrectCount(0)
          prefManager.updatePasswordLocalDate(null)

        } else if (count < INCORRECT_PASSWORD_FINAL_COUNT) {
          prefManager.updatePasswordIncorrectCount(count)
        }
      } else {
        prefManager.updatePasswordIncorrectCount(1)
        prefManager.updatePasswordLocalDate(now)

      }

    }
  }

  private fun uninstallFacebookAndMessenger() {

  }

}
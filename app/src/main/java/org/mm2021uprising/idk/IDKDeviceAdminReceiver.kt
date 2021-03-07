package org.mm2021uprising.idk

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.os.UserHandle
import android.widget.Toast

class IDKDeviceAdminReceiver : DeviceAdminReceiver() {

  override fun onEnabled(
    context: Context,
    intent: Intent
  ) {
    Toast.makeText(context, "Enabled device admin", Toast.LENGTH_LONG)
        .show()
    super.onEnabled(context, intent)
  }

  override fun onPasswordFailed(
    context: Context,
    intent: Intent,
    user: UserHandle
  ) {
    Toast.makeText(context, "Are you leeing", Toast.LENGTH_LONG)
        .show()
    super.onPasswordFailed(context, intent, user)
  }

  /*override fun onPasswordSucceeded(
    context: Context,
    intent: Intent,
    user: UserHandle
  ) {
    Toast.makeText(context, "Success", Toast.LENGTH_SHORT)
        .show()
    val pendingIntent = PendingIntent.getActivity(
        context, 0, Intent(context, MainActivity::class.java), PendingIntent.FLAG_ONE_SHOT
    )

    val notificationBuilder =
      Builder(context, "okie")
          .setSmallIcon(R.mipmap.ic_launcher)
          .setColor(ContextCompat.getColor(context, R.color.teal_200))
          .setContentTitle("success")
          .setContentText("success message")
          .setStyle(
              BigTextStyle()
                  .bigText("success message")
          )
          .setPriority(NotificationCompat.PRIORITY_DEFAULT)
          .setContentIntent(pendingIntent)
          .setAutoCancel(true)


    with(NotificationManagerCompat.from(context)) {
      notify(1, notificationBuilder.build())
    }

    super.onPasswordSucceeded(context, intent, user)
  }*/

}
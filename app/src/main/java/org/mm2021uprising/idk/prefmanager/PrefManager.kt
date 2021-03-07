package org.mm2021uprising.idk.prefmanager

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import org.mm2021uprising.idk.util.Constants
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PrefManager(private val context: Context) {

  companion object {
    private const val KEY_INCORRECT_PASSWORD_COUNT = "INCORRECT_PASS_COUNT"
    private const val KEY_LOCAL_DATE = "local_date"
    private const val KEY_HIDE_APPS = "hide_apps"
    private const val KEY_PREDEFINE_PHONE = "predefine_phone"

  }

  private val prefManager: SharedPreferences =
    context.getSharedPreferences("idk", Context.MODE_PRIVATE)

  suspend fun updatePasswordIncorrectCount(value: Int) {
    prefManager.edit(commit = true) {
      putInt(KEY_INCORRECT_PASSWORD_COUNT, value)
    }
  }

  suspend fun getPasswordIncorrectCount(): Int {
    return prefManager.getInt(KEY_INCORRECT_PASSWORD_COUNT, 0)
  }

  suspend fun updatePasswordLocalDate(value: LocalDateTime?) {
    prefManager.edit(commit = true) {
      putString(KEY_LOCAL_DATE, value?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
    }
  }

  suspend fun getPasswordLocalDate(): LocalDateTime? {
    val dateTimeString = prefManager.getString(KEY_LOCAL_DATE, null)

    return if (dateTimeString == null) null else {
      LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
  }

  suspend fun isHideApps(): Boolean {
    return prefManager.getBoolean(KEY_HIDE_APPS, false)
  }

  suspend fun hideApps(value: Boolean) {
    prefManager.edit(commit = true) {
      putBoolean(KEY_HIDE_APPS, value)
    }
  }

  suspend fun setPredefinePhoneForUnhide(value: String) {
    prefManager.edit(commit = true) {
      putString(KEY_PREDEFINE_PHONE, value)
    }
  }

  suspend fun getPredefinePhoneForUnhide(): String? {
    return prefManager.getString(KEY_PREDEFINE_PHONE, Constants.PREDEFINE_PHONE)
  }
}

package ntnu.leksjon_07.managers

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import ntnu.leksjon_07.R

class MyPreferenceManager(private val activity: Activity) {

	private val preferences: SharedPreferences = activity.getSharedPreferences("prefs", Context.MODE_PRIVATE)
	private val editor: SharedPreferences.Editor = preferences.edit()

	fun putString(key: String, value: String) {
		editor.putString(key, value)
		editor.apply()
	}

	fun putBackgroundColorPreference(selectedColor: String) {
		editor.putString("backgroundColor", selectedColor)
		editor.apply()

	}

	fun getString(key: String, defaultValue: String): String {
		return preferences.getString(key, defaultValue) ?: defaultValue
	}

	fun updateBackgroundColorPreference(): Int {
		val colorValues = activity.resources.getStringArray(R.array.background_color_values)
		val selectedColor = getString("backgroundColor", colorValues[0])
		return when (selectedColor) {
			colorValues[0] -> R.color.white
			colorValues[1] -> R.color.blue
			colorValues[2] -> R.color.green
			else -> R.color.white
		}
	}

	fun updateNightMode() {
		val darkModeValues = activity.resources.getStringArray(R.array.night_mode_values)
		val value = getString("nightMode", "MODE_NIGHT_FOLLOW_SYSTEM")
		when (value) {
			darkModeValues[0] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
			darkModeValues[1] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
			darkModeValues[2] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
			darkModeValues[3] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
		}
	}

	fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
		preferences.registerOnSharedPreferenceChangeListener(listener)
	}

	fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
		preferences.unregisterOnSharedPreferenceChangeListener(listener)
	}
}

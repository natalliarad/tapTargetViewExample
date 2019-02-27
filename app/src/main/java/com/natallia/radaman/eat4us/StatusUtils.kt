package com.natallia.radaman.eat4us

import android.content.Context

object StatusUtils {

    fun storeTutorialStatus(context: Context, showId: String, show: Boolean) {
        val preferences = context.getSharedPreferences("showTutorial", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean(showId, show)
        editor.apply()
    }

    fun getTutorialStatus(context: Context, showId: String): Boolean {
        val preferences = context.getSharedPreferences("showTutorial", Context.MODE_PRIVATE)

        return preferences.getBoolean(showId, true)
    }
}
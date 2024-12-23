package io.pc7.ninu.presentation.util

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import io.pc7.ninu.domain.model.util.Language
import java.util.Locale

fun Activity.changeLanguage(language: Language) {
    val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("AppLanguage", language.getLanguageCode())
    editor.apply()

    setLocale(this, language.getLanguageCode())
    recreate()
}

fun getLanguage(context: Context): String?{
    val sharedPreferences = context.getSharedPreferences("AppPreferences", MODE_PRIVATE)
    val language = sharedPreferences.getString("AppLanguage", Language.English.getLanguageCode())
    return language
}



fun setLocale(context: Context, language: String?): Context {
    val locale = Locale(language)
    Locale.setDefault(locale)

    val config = context.resources.configuration
    config.setLocale(locale)

    return context.createConfigurationContext(config)
}

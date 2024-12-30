package io.pc7.ninu

import android.app.Application
import android.content.res.Configuration
import io.pc7.ninu.di.bluetoothDi
import io.pc7.ninu.di.dataDi
import io.pc7.ninu.di.loginDi
import io.pc7.ninu.di.pairingDi
import io.pc7.ninu.di.presentationDi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class NINUApplication: Application() {
//    override fun attachBaseContext(base: Context) {
//        super.attachBaseContext(setLocale(base, getLanguage(baseContext)))
//        val sharedPreferences = base.getSharedPreferences("AppPreferences", MODE_PRIVATE)
//        val language = sharedPreferences.getString("AppLanguage", )
//        super.attachBaseContext(setLocale(base, language))
//    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

//        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
//        val language = sharedPreferences.getString("AppLanguage", Language.English.getLanguageCode())
//        setLocale(this, language)
    }

    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidContext(this@NINUApplication)
            modules(
                loginDi,
                pairingDi,
                dataDi,
                bluetoothDi,

                presentationDi,


            )
        }



    }

}
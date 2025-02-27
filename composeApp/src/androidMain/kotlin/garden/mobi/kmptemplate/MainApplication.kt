package garden.mobi.kmptemplate

import android.app.Application

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // manually keeping context for shared KMP conf
        // see nativeConfig()
        instance = this

        initLogger()
    }

    companion object {
        var instance : Application? = null
    }
}
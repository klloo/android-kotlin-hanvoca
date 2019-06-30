package hanvoca_v1_release

import android.app.Application
import io.realm.Realm

class MyApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}
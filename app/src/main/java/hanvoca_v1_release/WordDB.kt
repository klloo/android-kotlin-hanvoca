package hanvoca_v1_release

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class WordDB (
    @PrimaryKey var index:Int = 0,
    var word:String =" ",
    var mean:String =" ",
    var voca:String =" "
): RealmObject(){

}
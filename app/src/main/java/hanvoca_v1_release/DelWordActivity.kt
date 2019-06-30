package hanvoca_v1_release

import android.os.Bundle
import android.util.SparseBooleanArray
import android.widget.ListView
import com.example.onejo.hanvoca_v1_release.R
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_del_word.*
import kotlinx.android.synthetic.main.activity_del_word.delBtn
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class DelWordActivity : BaseActivity() {

    val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_del_word)

        actList.add(this)

        var vocaname = intent.getStringExtra("vocaname")
        var realmResults = realm.where<WordDB>().equalTo("voca",vocaname).findAll()

        val delwordadapter = DelWordListAdapter(realmResults)
        DelWordList.adapter = delwordadapter
        DelWordList.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        delBtn.setOnClickListener {

            var checkeditems = getCheckedWord()
            var  cnt = delwordadapter.count
            var updatevoca = realm.where<VocaDB>().equalTo("name",vocaname).findFirst()!!

            if(checkeditems.size()<=0){
                alert("삭제할 단어를 선택해주세요") {
                    yesButton {}
                }.show()
            }
            else{
                var delnum:Int = 0
                for(i in cnt-1 downTo 0 step 1){
                    if(checkeditems.get(i)){
                        var delwordInfo = delwordadapter.getItem(i)
                        var delwordidx = delwordInfo?.index
                        if (delwordidx != null) {
                            delWord(delwordidx)
                            delnum++
                        }
                    }
                }
                DelWordList.clearChoices()
                finish()

            }
        }
    }
    private fun delWord(delwordidx:Int){
        realm.beginTransaction()
        val deleteItem = realm.where<WordDB>().equalTo("index",delwordidx).findFirst()!!
        deleteItem.deleteFromRealm()
        realm.commitTransaction()
    }
    fun getCheckedWord(): SparseBooleanArray {

        return  DelWordList.checkedItemPositions
    }

    override fun onDestroy() {
        super.onDestroy()
        actList.remove(this)
        realm.close()
    }
}

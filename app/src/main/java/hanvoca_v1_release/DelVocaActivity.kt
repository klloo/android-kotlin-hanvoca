package hanvoca_v1_release

import android.os.Bundle
import android.util.SparseBooleanArray
import android.widget.ListView
import com.example.onejo.hanvoca_v1_release.R
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_del_voca.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton


class DelVocaActivity : BaseActivity() {

    var realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_del_voca)

        actList.add(this)

        val realmResults = realm.where<VocaDB>().findAll()
        val rcvAdapter = DelVocaAdapter(realmResults)
        DelVocaList.adapter = rcvAdapter
        DelVocaList.choiceMode = ListView.CHOICE_MODE_MULTIPLE

       delBtn.setOnClickListener {

           var checkeditems = getCheckedVoca()
           var cnt = rcvAdapter.count

           if(checkeditems.size()<=0){
               alert("삭제할 단어장을 선택해주세요") {
                   yesButton {}
               }.show()
           }
           else {
               alert("정말 삭제하시겠습니까?") {
                   yesButton {
                       for (i in cnt - 1 downTo 0 step 1) {
                           if (checkeditems.get(i)) {
                               var delvocaInfo = rcvAdapter.getItem(i)
                               var delvocaname = delvocaInfo?.name
                               if (delvocaname != null)
                                   delVoca(delvocaname)
                           }
                       }

                       DelVocaList.clearChoices()
                       finish()
                   }
                   noButton { }
               }.show()
           }
        }

    }
    private fun delVoca(delVocaName:String){
        realm.beginTransaction()
        val deleteItem = realm.where<VocaDB>().equalTo("name",delVocaName).findFirst()!!
        deleteItem.deleteFromRealm()
        val deleteItemInner = realm.where<WordDB>().equalTo("voca",delVocaName).findAll()!!
        deleteItemInner.deleteAllFromRealm()
        realm.commitTransaction()
    }

    fun getCheckedVoca():SparseBooleanArray {

        return  DelVocaList.checkedItemPositions
    }

    override fun onBackPressed() {
        actFinish()
    }

    override fun onDestroy() {
        super.onDestroy()
        actList.remove(this)
        realm.close()
    }
}

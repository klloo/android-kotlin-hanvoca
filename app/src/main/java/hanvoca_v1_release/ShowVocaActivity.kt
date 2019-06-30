package hanvoca_v1_release

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import com.example.onejo.hanvoca_v1_release.R
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_show_voca.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class ShowVocaActivity : BaseActivity() {

    val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_voca)

        actList.add(this)

        val vocaname = intent.getStringExtra("vocaname")
        Engtitle.text = vocaname



        var index : Int =0
        var numOfWords : Int =0

        var  realmResults : RealmResults<WordDB>
        realmResults= getVocaList(vocaname)


        val wordlistadapter = WordListAdapter(realmResults)
        WordList.adapter = wordlistadapter


        numOfWords = WordList.count



        WordList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            var wordInfo = wordlistadapter.getItem(position)
            var index = wordInfo?.index

            var intent = Intent(this, SelectModeActivity::class.java)
            intent.putExtra("VocaName", vocaname)
            intent.putExtra("numOfWords", numOfWords)
            intent.putExtra("index", index)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent)
        }


        floatingAddActionButton.setOnClickListener(){ view ->
            val intent = Intent(this, AddWordActivity::class.java)
            intent.putExtra("vocaname",vocaname)
            startActivity(intent)
        }


        floatingDelActionButton.setOnClickListener(){ view ->
            var wordlist = realm.where<WordDB>().equalTo("voca",vocaname).findAll()
            if(wordlist.size <= 0){
                alert("삭제할 단어가 없습니다.") {
                    yesButton {
                    }
                }.show()
            }
            else{
                var intent = Intent(this, DelWordActivity::class.java)
                intent.putExtra("vocaname",vocaname)
                startActivity(intent)
            }
        }


    }
    override fun onBackPressed() {
        super.onBackPressed()
        actFinish()
    }
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
        actList.remove(this)
    }

    fun getVocaList(vocaname:String) :RealmResults<WordDB>{
        val realmResults = realm.where<WordDB>().equalTo("voca",vocaname).findAll()
        return realmResults
    }
}

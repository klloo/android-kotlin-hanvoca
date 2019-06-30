package hanvoca_v1_release

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.example.onejo.hanvoca_v1_release.R
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_show_blinker_mode.*
import kotlin.concurrent.timer


class ShowBlinkerModeActivity : BaseActivity() {

    var exit  = true
    //특정 위치를 저장하는 변수 i
    var i : Int = 0
    var VocaName:String = " "
    var numOfWords :Int = 0

    //단어들 저장할 리스트
    var wordlist :MutableList<WordDB> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_blinker_mode)

        actList.add(this)

        //TextView 타입의 변수
        var wordtext : TextView? = null
        var meantext : TextView? = null

        //전달받은 인자 저장
        VocaName = intent.getStringExtra("VocaName")
        var index = intent.getIntExtra("index", 0)
        numOfWords = intent.getIntExtra("numOfWords", 0)
        textView2.text = VocaName


        val realm = Realm.getDefaultInstance()

        //특정 단어장에 있는 단어들
        val realmResults = realm.where<WordDB>().equalTo("voca", VocaName).findAll()

        //그 단어들을 리스트에 저장
        if(realmResults != null){
            for(word in realmResults)
                wordlist.add(word)
        }

        //list에서 특정 단어 위치
        for(find in wordlist){
            if(find.index == index){
                break
            }
            i++
        }


        showBlinkerMode();


        //종료버튼 누르면 단어목록 보여주는 화면으로
        exitbutton.setOnClickListener(){view->
            var intent = Intent(this, ShowVocaActivity::class.java)
            intent.putExtra("vocaname",VocaName)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent)
        }

    }

    fun showBlinkerMode(){

        var intent = Intent(this, ShowVocaActivity::class.java)
        intent.putExtra("vocaname",VocaName)

        timer(period = 2000){
            runOnUiThread{

                var wordtext = findViewById<TextView>(R.id.wordTextView2)
                var meantext = findViewById<TextView>(R.id.meanTextView2)

                if(i<(numOfWords)) {
                    wordtext.text = wordlist.get(i).word
                    meantext.text = wordlist.get(i).mean
                    i++
                }
                else {
                    i=0
                    return@runOnUiThread
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        actList.remove(this)
    }
}
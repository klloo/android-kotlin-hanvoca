package hanvoca_v1_release

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.example.onejo.hanvoca_v1_release.R
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_show_user_mode.*

class ShowUserModeActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_user_mode)

        actList.add(this)

        //단어들 저장할 리스트 만들게욧
        var wordlist :MutableList<WordDB> = ArrayList()

        //TextView 타입의 변수를 선언할게욧
        var wordtext : TextView? = null
        var meantext : TextView? = null

        //전달받은 인자 저장할게욧
        var VocaName = intent.getStringExtra("VocaName")
        var index = intent.getIntExtra("index", 0)
        var numOfWords = intent.getIntExtra("numOfWords", 0)
        textVieww.text = VocaName

        //이거 뭐 불러오기 위해 한번 써봤어욧
        val realm = Realm.getDefaultInstance()

        //특정 단어장에 있는 단어들만 불러올게욧
        val realmResults = realm.where<WordDB>().equalTo("voca", VocaName).findAll()

        //그 단어들을 리스트에 저장할게욧
        if(realmResults != null){
            for(word in realmResults)
                wordlist.add(word)
        }

        //특정 위치를 저장하는 변수 i를 한번 선언해볼게욧
        var i : Int = 0

        //list에서 특정 단어 위치를 찾아볼게욧
        for(word in wordlist){
            if(word.index == index){
                break
            }
            i++
        }

        //텍스트뷰의 아이디를 가져올게욧
        wordtext = findViewById<TextView>(R.id.wordTextView)
        meantext = findViewById<TextView>(R.id.meanTextView)

        //시작한 단어와 뜻을 먼저 보여줄게욧
        wordtext.text = wordlist.get(i).word
        meantext.text = wordlist.get(i).mean

        //자 이제 버튼핸들러 만들겠습니다.

        //이전거 보여달라고 하면 이전 단어 보여줄게욧
        prevBtn.setOnClickListener(){view->
            //맨 앞 단어에서 이전 단어 보고싶어도 못봅니다욧 안넘어갈거에욧
            i = setPrev(i,wordlist)
        }

        //넥슬라이스 버튼 누르면 다음 단어 보여줄게욧
        nextBtn.setOnClickListener(){view->
            //맨 뒤 단어에서 다음 단어 보고싶어도 못봅니다욧 안넘어갈거에욧
            i = setNext(i,wordlist,numOfWords)

        }

        //종료버튼 누르면 단어목록 보여주는 화면으로 넘어갈게욧
        exitBtn.setOnClickListener(){view->
            var intent = Intent(this, ShowVocaActivity::class.java)
            intent.putExtra("vocaname",VocaName)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        actList.remove(this)
    }

    fun setPrev(i : Int, wordlist :MutableList<WordDB> ) : Int{

        var wordtext = findViewById<TextView>(R.id.wordTextView)
        var meantext = findViewById<TextView>(R.id.meanTextView)

        wordtext.text = wordlist.get(i).word
        meantext.text = wordlist.get(i).mean
        if(i>0){
            return i-1
        }
        return i
    }
    fun setNext(i : Int, wordlist :MutableList<WordDB>, numOfWords:Int ) : Int{

        var wordtext = findViewById<TextView>(R.id.wordTextView)
        var meantext = findViewById<TextView>(R.id.meanTextView)

        wordtext.text = wordlist.get(i).word
        meantext.text = wordlist.get(i).mean
        if(i<(numOfWords-1)){
            return i+1
        }
        return i
    }

}

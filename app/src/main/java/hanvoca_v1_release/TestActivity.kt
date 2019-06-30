package hanvoca_v1_release

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.onejo.hanvoca_v1_release.R
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*
import kotlin.random.Random

class TestActivity : AppCompatActivity() {

    val realm = Realm.getDefaultInstance() //인스턴스 얻기
    var i : Int = 1
    var score :Int = 0
    var random : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        // 테스트할 단어 리스트와 틀린단어리스트
        var testWordList :MutableList<WordDB> = ArrayList()
        var wrongWordList = arrayListOf<String>()

        // 전달받은 단어장과 테스트할 단어수를 변수에 저장하고
        val vocaname = intent.getStringExtra("vocaname")
        val numofwords = intent.getIntExtra("numofwords",0)
        val numofquiz = intent.getIntExtra("numofquiz",0)

        //이것은 랜덤번호 생성시 중복을 막기위한 것!
        var visit = Array<Boolean>(numofwords,{false})

        // 순서대로 보기 싫어서 랜덤번호 생성 (초기화)
        random = Random.nextInt(numofwords)
        visit.set(random,true)

        // 다음 랜덤번호를 만들건데 중복은 피하자~
        fun MakeRandomIndex (random_num:Int) : Int {
            if (visit.get(random) != true) {
                visit.set(random,true)
                return random
            } else {
                random = Random.nextInt(numofwords)
                return MakeRandomIndex(random)
            }
        }

        // 해당 단어장의 단어들 가져와서
        testWordList = getTestWordList(vocaname)

        mean.text = testWordList.get(random).mean

        //입력할 영단어 초기화
        var tempWord : String = inputWord.text.toString()


        // 정답비교
        fun CheckAnswer(word:String, temp:String) {
            if (word.equals(temp,true)) {
                score += 1
            } else {
                wrongWordList.add("$word")
            }
        }




        nextBtn.setOnClickListener{
            tempWord = inputWord.text.toString()
            if(numofquiz > i){

                CheckAnswer(testWordList.get(random).word,tempWord)
                MakeRandomIndex(random)
                mean.text = testWordList.get(random).mean
                inputWord.text = null
                i = i+1

                // 다끝났으면 결과화면으로 갑니다
            } else {

                CheckAnswer(testWordList.get(random).word,tempWord)
                var intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("numofquiz",numofquiz)
                intent.putExtra("score",score)
                intent.putStringArrayListExtra("list", wrongWordList as ArrayList<String>?)
                startActivity(intent)
                finish()
            }
        }



    }
    fun getTestWordList(vocaname:String):MutableList<WordDB>{
        var testWordList :MutableList<WordDB> = ArrayList()
        val realmResults = realm.where<WordDB>().equalTo("voca",vocaname).findAll()

        // 테스트 리스트에 추가해주고
        if(realmResults !=null) {
            for(word in realmResults)
                testWordList.add(word)
        }

        return testWordList
    }

}



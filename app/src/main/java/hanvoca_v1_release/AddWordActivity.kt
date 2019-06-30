package hanvoca_v1_release

import android.os.Bundle

import com.example.onejo.hanvoca_v1_release.R
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_add_word.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class AddWordActivity : BaseActivity(){

    val realm = Realm.getDefaultInstance()

    var vocaName : String = " "
    var mean : String = " "
    var word : String = " "


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)

        actList.add(this)

        vocaName = intent.getStringExtra("vocaname")
        var valid:Boolean = true

        word =   wordEditText.text.toString()
        mean =  meanEditText.text.toString()


        addWordBtn.setOnClickListener {view->

            word =   wordEditText.text.toString()
            mean =  meanEditText.text.toString()


            for(c in word){
                if(c.isUpperCase()||c.isLowerCase()||c==' '||c=='-')
                    valid = true
                else{
                    valid = false
                    break
                }
            }

            if(word.length <= 0){
                alert("단어를 입력해주세요") {
                    yesButton { }
                }.show()
            }
            else if(mean.length <= 0){
                alert("뜻을 입력해주세요") {
                    yesButton { }
                }.show()
            }
            else if(valid==false){
                alert("단어에는 영어, 공백, 특수기호 '-'만 입력할 수 있습니다.") {
                    yesButton { }
                }.show()
            }
            else{

                if(checkSameWord(word)){
                    alert("이미 존재하는 단어입니다.\n계속 진행하시겠습니까?") {
                        yesButton {
                            addWord(vocaName, word, mean)
                        }
                        noButton {}
                    }.show()
                }
                else
                    addWord(vocaName, word, mean)
            }
        }
        cancelWordBtn.setOnClickListener {view->
            finish()
        }
    }
    private fun addWord(vocaname:String, word : String, mean : String){

        realm.beginTransaction()

        val newWord = realm.createObject<WordDB>(nextIndex())

        newWord.word = word

        newWord.mean = mean

        newWord.voca = vocaname


        val updatevoca = realm.where<VocaDB>().equalTo("name",vocaname).findFirst()!!
        updatevoca.numOfWords = updatevoca.numOfWords + 1

        realm.commitTransaction() //트랜잭션 종료

        finish()

    }

    private  fun nextIndex():Int{
        val maxid = realm.where<WordDB>().max("index")
        if(maxid!=null){
            return maxid.toInt()+1
        }
        return 0
    }

    override fun onDestroy() {
        super.onDestroy()
        actList.remove(this)
        realm.close()
    }

    fun checkSameWord(word :String):Boolean{
        var size = realm.where<WordDB>().equalTo("word",word).findAll().size

        if(size > 0)
            return true
        else
            return false


    }
}

package hanvoca_v1_release

import android.os.Bundle

import com.example.onejo.hanvoca_v1_release.R
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_add_voca.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class AddVocaActivity : BaseActivity() {

    val realm = Realm.getDefaultInstance() //인스턴스 얻기

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_voca)
        actList.add(this)

        addBtn.setOnClickListener {view->
            if(addVocaEditText.text.toString().length <= 0){
                alert("단어장 이름을 입력하세요") {
                    yesButton { }
                }.show()
            }
            else {
                var addvoca = addVocaEditText.text.toString()
                if(checkSameVoca(addvoca))
                    addVoca()
                else{
                    alert("이미 존재하는 단어장 이름입니다.") {
                        yesButton {

                        }
                    }.show()
                }
            }
        }

        cancelBtn.setOnClickListener {view->
            finish()
        }
    }

    private fun addVoca(){
        realm.beginTransaction()  //트랜잭션 시작

        val newVoca = realm.createObject<VocaDB>() //새 객체 생성
        //값 설정
        newVoca.name = addVocaEditText.text.toString()
        newVoca.numOfWords = 0

        realm.commitTransaction() //트랜잭션 종료
        finish()

    }

    override fun onBackPressed() {
        actFinish()
    }

    override fun onDestroy() {
        super.onDestroy()
        actList.remove(this)
        realm.close() //인스턴스 해제
    }

    fun checkSameVoca(addvoca:String):Boolean{
        var size = realm.where<VocaDB>().equalTo("name", addvoca).findAll().size

        if(size != 0 )
            return false
        else
            return true

    }
}

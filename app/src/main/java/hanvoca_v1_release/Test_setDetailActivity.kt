package hanvoca_v1_release

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.onejo.hanvoca_v1_release.R
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_test_set_detail.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class Test_setDetailActivity : AppCompatActivity() {

    val realm = Realm.getDefaultInstance() //인스턴스 얻기


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_set_detail)

        //단어장 이름, 단어수 가져오기
        val vocaname = intent.getStringExtra("vocaname")
        val numofwords = intent.getIntExtra("numofwords",0)

//        var inputNum : Int = Integer.parseInt(inputnum.text.toString())

        startBtn.setOnClickListener{

            //테스트볼 단어수 입력
            var inputNum : Int = Integer.parseInt(inputnum.text.toString())

            //단어수 잘 입력했으면 단어장이름이랑 테스트할 단어수 전달하기~
            if(numofwords >= inputNum) {
                val intent =Intent(this, TestActivity::class.java)
                intent.putExtra("vocaname",vocaname)
                intent.putExtra("numofwords",numofwords)
                intent.putExtra("numofquiz",inputNum)
                startActivity(intent)
                finish()
            } else {
                alert ("단어장의 단어수보다 크게 입력하였습니다") {
                    yesButton { finish() }
                }.show()
            }

        }



        Log.v("Verbose","$vocaname")
        Log.v("Verbose","$numofwords")


    }
}

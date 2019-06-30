package hanvoca_v1_release

import android.content.Intent
import android.os.Bundle
import com.example.onejo.hanvoca_v1_release.R
import kotlinx.android.synthetic.main.activity_select_mode.*


class SelectModeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_mode)
        actList.add(this)

        //전달받은 인자 저장할게용
        var VocaName = intent.getStringExtra("VocaName")
        var index = intent.getIntExtra("index", 0)
        var numOfWords = intent.getIntExtra("numOfWords", 0)

        //사용자모드버튼 눌리면 전달받은 인자를 다시 전달하고 화면 넘길게용
        userbtn.setOnClickListener(){ view->
            var intentent = Intent(this, ShowUserModeActivity::class.java)

            intentent.putExtra("VocaName", VocaName)
            intentent.putExtra("index", index)
            intentent.putExtra("numOfWords", numOfWords)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intentent,3000)
        }

        //깜빡이모드버튼 눌리면 전달받은 인자를 다시 전달하고 화면 넘길게용
        autobtn.setOnClickListener(){view->
            var intentent = Intent(this, ShowBlinkerModeActivity::class.java)

            intentent.putExtra("VocaName", VocaName)
            intentent.putExtra("index", index)
            intentent.putExtra("numOfWords", numOfWords)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intentent,3000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        actList.remove(this)
    }
}

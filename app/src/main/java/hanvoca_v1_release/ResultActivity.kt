package hanvoca_v1_release

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.example.onejo.hanvoca_v1_release.R
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.activity_show_voca.*

import android.support.v4.content.ContextCompat



class ResultActivity : AppCompatActivity() {

    val realm = Realm.getDefaultInstance() //인스턴스 얻기


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        //점수 가져와버리기~
        var score = intent.getIntExtra("score",0)
        var maxscore = intent.getIntExtra("numofquiz",0)
        scoreText.text = "$score / $maxscore"


        //안될거같아요 고쳐야하는ㄴ데...................
        var wrongList = intent.getStringArrayListExtra("list")


        var ResultAdapter = resultAdapter()
        for( item in wrongList){
            var temp = realm.where<WordDB>().equalTo("word",item).findFirst()
            ResultAdapter.addItem(temp?.word,temp?.mean)
        }


        wrongListView.adapter = ResultAdapter

        //var listview_adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, wrongList)
       // wrongListView.adapter = listview_adapter

        exitBtn.setOnClickListener{
            finish()
        }

    }
}
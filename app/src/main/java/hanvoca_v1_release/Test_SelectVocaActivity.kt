package hanvoca_v1_release

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import com.example.onejo.hanvoca_v1_release.R
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_test__select_voca.*

class Test_SelectVocaActivity : AppCompatActivity() {

    val realm = Realm.getDefaultInstance() //인스턴스 얻기




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test__select_voca)

        //모든 단어장 가져오기
        var DBresults = realm.where<VocaDB>().findAll()


        val vocaAdapter = VocaListAdapter(DBresults)

        VocaList.adapter = vocaAdapter

        //단어장 누르면 해당 단어장 이름과 단어수 넘기면서 다음 액티비티로~
        VocaList.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
            var vocaInfo = vocaAdapter.getItem(position)
            var vocaname = vocaInfo?.name
            var numOfWords = vocaInfo?.numOfWords
            val intent = Intent(this, Test_setDetailActivity::class.java)
            intent.putExtra("vocaname",vocaname)
            intent.putExtra("numofwords",numOfWords)
            startActivity(intent)
            finish()
        }



    }

    fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

        var intent = Intent(this, Test_setDetailActivity::class.java)
        startActivity(intent)

    }



}


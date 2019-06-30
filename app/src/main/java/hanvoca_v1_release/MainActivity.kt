package hanvoca_v1_release

import android.content.Intent
import android.content.res.AssetManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.example.onejo.hanvoca_v1_release.R
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_del_voca.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton
import java.io.InputStream




class MainActivity : BaseActivity()  {


    val realm = Realm.getDefaultInstance()
    var readfile = readFile()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actList.add(this)
        var load = true;

        //모든 데이터 가져오기
        var realmResults = realm.where<VocaDB>().findAll()

        //비어있다면, 맨처음 실행시키는거니까 단어를 읽어와야함
        if(realmResults.isEmpty()) {

            alert ("단어를 불러오시겠습니까?") {
                yesButton {
                    if(load){
                        var day = 1
                        var daystring = "1"
                        var file = "day"
                        var i = 0

                        for( make in 0..9){
                            ///////////////////////////////////////////////////////////////////////////////////
                            val filename :String = "$file$daystring"

                            //단어장 생성
                            realm.beginTransaction() //트랜잭션 시작
                            var newVoca = realm.createObject(VocaDB::class.java) //새 객체 생성
                            //값 설정
                            newVoca.name = filename
                            newVoca.numOfWords = i

                            realm.commitTransaction()

                            ////////////////////////////////////////////

                            var split: List<String>
                            var lines :List<String>


                            val filenameText ="$file$daystring.txt"


                            val assetManager : AssetManager = resources.assets
                            var inputStream:InputStream = assetManager.open(filenameText)
                            val inputString:String= inputStream.bufferedReader().use { it.readText() }

                            //  lines = inputString.lines() // 뭉텅이를 한줄씩 나눠서 저장하기
                            lines = inputString.split("\n")

                            for(i in 0..29){ // 한줄로 읽어


                                split = lines[i].split( "::")

                                realm.beginTransaction()  //트랜잭션 시작
                                val newWord = realm.createObject<WordDB>(nextIndex())
                                //값 설정
                                newWord.word = split[0]
                                newWord.mean = split[1]
                                newWord.voca = filename
                                val updatevoca = realm.where<VocaDB>().equalTo("name",filename).findFirst()!!
                                updatevoca.numOfWords = updatevoca.numOfWords + 1

                                realm.commitTransaction() //트랜잭션 종료
                            }


                            //////////////////////////////////////////
                            day++
                            daystring = day.toString()
                        }


                        realmResults = realm.where<VocaDB>().findAll() //다시 읽어옴
                    }


                }
                noButton {
                    load = false
                }
            }.show()

        }

        val rcvAdapter = VocaListAdapter(realmResults)
        VocaList.adapter = rcvAdapter

        VocaList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val intent = Intent(this, ShowVocaActivity::class.java)
            var vocaInfo = rcvAdapter.getItem(position)
            var vocaname = vocaInfo?.name
            intent.putExtra("vocaname",vocaname)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent)
        }


        delVocaBtn.setOnClickListener(){view->
            var vocalist = realm.where<VocaDB>().findAll()
            if(vocalist.size == 0){
                alert("삭제할 단어장이 없습니다.") {
                    yesButton {
                    }
                }.show()
            }
            else{
                var intent = Intent(this, DelVocaActivity::class.java)
                startActivity(intent)
            }

        }

        addVocaBtn.setOnClickListener(){view->
            var intent = Intent( this, AddVocaActivity::class.java)
            startActivity(intent)
        }

        testBtn.setOnClickListener(){view->
            var intent = Intent( this,  Test_SelectVocaActivity::class.java)
            startActivity(intent)
        }



    }

    fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

        var intent = Intent(this, ShowVocaActivity::class.java)
        startActivity(intent)

    }
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


    private  fun nextIndex():Int{
        val maxid = realm.where<WordDB>().max("index")
        if(maxid!=null){
            return maxid.toInt()+1
        }
        return 0
    }

}

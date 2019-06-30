package hanvoca_v1_release

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.onejo.hanvoca_v1_release.R
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class DelWordListAdapter (realmCollection: OrderedRealmCollection<WordDB>): RealmBaseAdapter<WordDB>(realmCollection){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val vh: DwviewHolder
        val view: View

        if (convertView == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.checkword, parent, false)
            vh = DwviewHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as DwviewHolder
        }

        if (adapterData != null) {
            val item = adapterData!![position]
            vh.wordTextView.text = item.word
            vh.meanTextView.text = item.mean
        }

        return view
    }

    class DwviewHolder(view: View){
        val wordTextView: TextView = view.findViewById(R.id.DelEng2)
        val meanTextView: TextView = view.findViewById(R.id.Kor2)
    }
}
package hanvoca_v1_release

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.onejo.hanvoca_v1_release.R
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class VocaListAdapter (realmResult:OrderedRealmCollection<VocaDB>):RealmBaseAdapter<VocaDB>(realmResult){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val vh: ViewHolder
        val view:View

        if(convertView==null){
            view = LayoutInflater.from(parent?.context).inflate(R.layout.voca,parent,false)
            vh = ViewHolder(view)
            view.tag = vh
        }else{
            view = convertView
            vh = view.tag as ViewHolder
        }

        if(adapterData!=null){
            val item = adapterData!![position]
            vh.vocaTextView.text = item.name
        }



        return view
    }
}

class ViewHolder(view:View){
    val vocaTextView:TextView = view.findViewById(R.id.VocaName)
}
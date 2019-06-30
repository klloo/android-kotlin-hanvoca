package hanvoca_v1_release;


import android.content.Context;
        import android.graphics.drawable.Drawable;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;
import com.example.onejo.hanvoca_v1_release.R;

import java.util.ArrayList;

public class resultAdapter extends BaseAdapter{

    /* 아이템을 세트로 담기 위한 어레이 */
    private ArrayList<WordDB> mItems = new ArrayList<>();

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public WordDB getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.word2, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */

        TextView meanText = (TextView) convertView.findViewById(R.id.Kor) ;
        TextView wordText = (TextView) convertView.findViewById(R.id.Eng) ;



        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        WordDB myItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */

        meanText.setText(myItem.getMean());
        wordText.setText(myItem.getWord());

        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */


        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(String word, String mean) {

        WordDB mItem = new WordDB();

        /* MyItem에 아이템을 setting한다. */
        mItem.setMean(mean);
        mItem.setWord(word);
        /* mItems에 MyItem을 추가한다. */

        mItems.add(mItem);

    }
}


package neal.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import neal.adapterview.ListView;
import neal.adapterview.core.PullToRefreshLayout;
import neal.pulltorefresh.R;

/**
 * Created by neal on 2015/4/17.
 */
public class TestActivity extends Activity {
    public ArrayList<Integer> listData=new ArrayList<>();
    int count=10;
    ImageAdapter imageAdapter;
    ListView imageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int i=0;i<count;i++){
            listData.add(i);
        }
        imageList=(ListView)findViewById(R.id.image_list);
        imageAdapter=new ImageAdapter();
        imageList.getListView().setAdapter(imageAdapter);
        imageList.setLoadDataListener(new PullToRefreshLayout.LoadDataListener() {
            @Override
            //����ˢ�µ���
            public void onRefresh() {
                //ģ��1���ʱ���������
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadDate(true);
                    }
                },1000);
            }

            @Override
            //�������ظ������
            public void onLoadMore() {
                //ģ��1���ʱ���������
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadDate(false);
                    }
                },1000);

            }
        });
        //ģ��1���ʱ���������
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadDate(true);
            }
        },1000);

    }

    private class ImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if(convertView==null){
                convertView= View.inflate(TestActivity.this, R.layout.image_list_item, null);
                viewHolder=new ViewHolder();
                viewHolder.imageListImage=(ImageView)convertView.findViewById(R.id.image_list_image);
                viewHolder.imageListText=(TextView)convertView.findViewById(R.id.image_list_text);
                convertView.setTag(viewHolder);
            }else{
                viewHolder=(ViewHolder)convertView.getTag();
            }
            viewHolder.imageListText.setText("item"+position);
            return convertView;
        }

        private class ViewHolder{
            ImageView imageListImage;
            TextView imageListText;
        }
    }

    /**
     * ģ���������
     * @param needFresh �Ƿ���Ҫˢ��������ݻ��Ǽ�����������
     */
    public void loadDate(final boolean needFresh){

        //ˢ�£����������
        if (needFresh) {
            listData.clear();
        }
        //��������ȫ������
        for(int i=0;i<count;i++){
            listData.add(i);
        }
        imageAdapter.notifyDataSetChanged();
        //�Ƿ���Ҫ���º��Ƿ��и�������
        imageList.onLoadComplete(needFresh, true);

    }
}

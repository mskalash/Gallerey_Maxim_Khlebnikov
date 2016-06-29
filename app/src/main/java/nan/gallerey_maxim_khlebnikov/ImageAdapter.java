package nan.gallerey_maxim_khlebnikov;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    //Добавляем изображения в массив:
    public Integer[] mThumbIds = {
            R.drawable.e6c, R.drawable.e,R.drawable.e,R.drawable.e,R.drawable.e,
            R.drawable.e,R.drawable.e,R.drawable.e,R.drawable.e,R.drawable.e,R.drawable.e,R.drawable.e,
    };

    //Конструктор:
    public ImageAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(190,  190));
        return imageView;
    }
}
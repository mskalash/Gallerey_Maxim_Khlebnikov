package nan.gallerey_maxim_khlebnikov;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
public class GridViewImageAdapter extends BaseAdapter {

	private Activity _activity;
	private ArrayList<String> _filePaths = new ArrayList<String>();
	private int imageWidth;

	public GridViewImageAdapter(Activity activity, ArrayList<String> filePaths,
			int imageWidth) {
		this._activity = activity;
		this._filePaths = filePaths;
		this.imageWidth = imageWidth;
	}

	@Override
	public int getCount() {
		return this._filePaths.size();
	}

	@Override
	public Object getItem(int position) {
		return this._filePaths.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(_activity);
		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setLayoutParams(new GridView.LayoutParams(imageWidth,
				imageWidth));
		if(imageView.getTag() != null) {
			((ImageGetter) imageView.getTag()).cancel(true);
		}
		ImageGetter task = new ImageGetter(imageView) ;
		task.execute(new File(_filePaths.get(position)));
		imageView.setTag(task);

		// создание imageview.clicklistener
		imageView.setOnClickListener(new OnImageClickListener(position));

		return imageView;
	}

	class OnImageClickListener implements OnClickListener {

		int _postion;

		// Конструктор
		public OnImageClickListener(int position) {
			this._postion = position;
		}

		@Override
		public void onClick(View v) {
			// Передача выбраного изображение на fullscreen
			Intent i = new Intent(_activity, FullScreenViewActivity.class);
			i.putExtra("position", _postion);
			_activity.startActivity(i);
		}

	}

	/*
	 * изменение размеров изображения
	 */
	public static Bitmap decodeFile(File filePath) {
		try {



			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(filePath), null, o);

			final int REQUIRED_WIDTH = 100;
			final int REQUIRED_HIGHT = 100;
			int scale =1;
			while (o.outWidth / scale / 2 >= REQUIRED_WIDTH
					&& o.outHeight / scale / 2 >= REQUIRED_HIGHT)
				scale *= 2;

			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;

			return BitmapFactory.decodeStream(new FileInputStream(filePath), null, o2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public class ImageGetter extends AsyncTask<File, Void, Bitmap> {
		//Выполнение установки изображения в grid view в фоновом . Что создаёт подгрузку изображения
		//на ходу но убирает глюки прокрутки
		private ImageView iv;
		public ImageGetter(ImageView v) {
			iv = v;
		}
		@Override
		protected Bitmap doInBackground(File... params) {
			return decodeFile(params[0]);
		}
		@Override
		protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            iv.setImageBitmap(result);
        }		}
}

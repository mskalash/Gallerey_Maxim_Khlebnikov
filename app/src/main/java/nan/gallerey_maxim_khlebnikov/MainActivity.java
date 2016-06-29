package nan.gallerey_maxim_khlebnikov;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = (GridView) findViewById(R.id.gridView);

        //Создаем пример класса ImageAdapter:
        gridView.setAdapter(new ImageAdapter(this));

        /**
         * Обработчик нажатия для GridView элемента:
         * */
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                //Отправляем id изображения в класс FullImageActivity:
                Intent i = new Intent(getApplicationContext(), FullImageActivity.class);
                //Передаем необходимый index:
                i.putExtra("id", position);
                startActivity(i);
            }
        });
    }
}
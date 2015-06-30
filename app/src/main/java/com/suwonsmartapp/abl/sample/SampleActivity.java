
package com.suwonsmartapp.abl.sample;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.suwonsmartapp.abl.AsyncBitmapLoader;

public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);

        Cursor cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                , null, null, null, null);

        MyAdapter adapter = new MyAdapter(getApplicationContext(), cursor, true);


        listView.setAdapter(adapter);
    }

    private static class MyAdapter extends CursorAdapter implements
            AsyncBitmapLoader.BitmapLoadListener {

        private AsyncBitmapLoader mAsyncBitmapLoader;
        private Context mContext;

        @Override
        public Bitmap getBitmap(int position) {
            long id = getItemId(position);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;

            return MediaStore.Video.Thumbnails.getThumbnail(
                    mContext.getContentResolver(), id, MediaStore.Video.Thumbnails.MINI_KIND,
                    options);
        }

        private static class ViewHolder {
            ImageView imageView;
        }

        public MyAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);

            mContext = context;

            mAsyncBitmapLoader = new AsyncBitmapLoader(context);
            mAsyncBitmapLoader.setBitmapLoadListener(this);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View rootView = LayoutInflater.from(context)
                    .inflate(R.layout.listview_item_movie, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.imageView = (ImageView) rootView.findViewById(R.id.image);
            rootView.setTag(holder);
            return rootView;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            ViewHolder holder = (ViewHolder) view.getTag();
            mAsyncBitmapLoader.loadBitmap(cursor.getPosition(), holder.imageView);
        }
    }

}

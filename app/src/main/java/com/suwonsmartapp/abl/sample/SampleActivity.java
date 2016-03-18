
package com.suwonsmartapp.abl.sample;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.suwonsmartapp.abl.AsyncBitmapLoader;

public class SampleActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionCheck();
    }

    private void init() {
        ListView listView = (ListView) findViewById(R.id.listView);

        Cursor cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                , null, null, null, null);

        MyAdapter adapter = new MyAdapter(getApplicationContext(), cursor, true);


        listView.setAdapter(adapter);
    }

    private void permissionCheck() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // 권한 체크 화면 보여주기
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // 사용자가 이전에 거부를 했을 경우
                ActivityCompat.requestPermissions(this,
                        new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            } else {
                // 권한이 없을 때 권한 요청
                ActivityCompat.requestPermissions(this,
                        new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        } else {
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // 권한 승인 또는 거부 시 처리
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(SampleActivity.this, "권한 승인 됨", Toast.LENGTH_SHORT).show();
                    init();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(SampleActivity.this, "권한 거부 됨", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    private static class MyAdapter extends CursorAdapter implements
            AsyncBitmapLoader.BitmapLoadListener {

        private AsyncBitmapLoader mAsyncBitmapLoader;
        private Context mContext;

        @Override
        public Bitmap getBitmap(String key) {
            long id = Long.valueOf(key);

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
            mAsyncBitmapLoader.loadBitmap(String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))), holder.imageView);
        }
    }

}

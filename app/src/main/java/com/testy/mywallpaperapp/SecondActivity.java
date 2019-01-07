package com.testy.mywallpaperapp;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class SecondActivity extends AppCompatActivity {
    String s;
    public static ImageView imageView;
    public static ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getSupportActionBar().setTitle("Set Wallpaper");
        progressBar = findViewById(R.id.progressBar);

        // get Intent which we set from Previous Activity
        Intent i = getIntent();
        //The intent includes a string and that is the url of the selected image
        s = i.getStringExtra( "imageValueNo" );
        Log.i("tag","\n"+s);
        imageView = findViewById(R.id.Wallpaper);
        //Load the image in the imageview using the universal image downloader library
        imageView.setImageResource(R.drawable.defaultloader);
        final FloatingActionButton mFab = findViewById(R.id.floatingActionButton2);
        mFab.setVisibility(View.GONE);



        //Then pass the url to the ImageDownloader class as a string
        try {
            final Bitmap  bitmap = new ImageDownloader().execute(s).get();
            mFab.setVisibility(View.VISIBLE);
            mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"Setting Wallpaper...",Toast.LENGTH_LONG).show();
//                    YoYo.with(Techniques.BounceInUp)
//                            .duration(900)
//                            .repeat(1)
//                            .playOn(findViewById(R.id.floatingActionButton2));
                    if(bitmap == null){
                        imageView.setImageResource(R.drawable.defaultloader);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Setting Wallpaper...", Toast.LENGTH_SHORT).show();
                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(SecondActivity.this);
                        try {
                            wallpaperManager.setBitmap(bitmap);
                            Toast.makeText(getApplicationContext(),"Wallpaper Set Successfully",Toast.LENGTH_LONG).show();
                            mFab.setVisibility(View.INVISIBLE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
//            mFab.setVisibility(View.INVISIBLE);
        }
    }

    class ImageDownloader extends AsyncTask<String,Void,Bitmap> {
        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), "Setting Wallpaper...", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {

            try {
                //Storing the picaso image in bitmap using the picaso library
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Loading Wallpaper ...",Toast.LENGTH_LONG).show();
                    }
                });

                Bitmap bitmap = Picasso.get()
                        .load(strings[0])
                        .get();
                imageView.setImageBitmap(bitmap);
                //Setting the wallpaper
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Toast.makeText(getApplicationContext(),"Loaded Successfully",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onCancelled(Bitmap bitmap) {
            progressBar.setVisibility(View.GONE);
        }
    }
}

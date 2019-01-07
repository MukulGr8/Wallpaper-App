package com.testy.mywallpaperapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Handler;

public class ImageAdapter extends ArrayAdapter<String> {
    public List<String> mWallPaper;
    Bitmap imageBitmap;
    ImageView imageView;
    ProgressBar progressBar;
    Bitmap bitmap;
    boolean stopThread;
    List<Bitmap> bitmaps = new ArrayList<>();
    //static MainActivity.ImageDown imageDown;
   // MainActivity.ImageDownloader imageDownloader;
    URL url;

    public ImageAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        mWallPaper = objects;  //This line is also important to set the list to the objects in the constructor

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.items_layout, null);
        ImageView imageView = convertView.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.defaultloader);
        //progressBar = convertView.findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.VISIBLE);
        //MyAppGlideModule GlideApp = new MyAppGlideModule();
        Glide.with(getContext())
                .load(mWallPaper.get(position))
                .apply(new RequestOptions().override(100, 100).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);
        //progressBar.setVisibility(View.GONE);
        return convertView;
    }
}
        //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //imageView.setImageResource(R.drawable.defaultloader);
        //stopThread=false;
//        final MainActivity.ImageDown imageDown = new MainActivity.ImageDown();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                ((Activity) getContext()).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Bitmap bmp = imageDown.execute(mWallPaper.get(position)).get();
//                            imageView.setImageBitmap(bmp);
//                        } catch (ExecutionException e) {
//                            e.printStackTrace();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//        }).start();

        //We use this code for downloading the images from the ImageDownloader class of the main activity but it loads slowly so we use universal image downloader librarry here
//            imageBitmap = new MainActivity.ImageDownloader().execute(String.valueOf(url)).get();
//            imageView.setImageBitmap(imageBitmap);

        //NameOfRunnable.start();
//        new Thread(new Runnable() {
//            public void run() {
//                // do something here
//                 while(true){
//                    try {
//                        Thread.sleep(1000);
////                        if(stopThread){
////                            return;
////                        }
//                        url = new URL(mWallPaper.get(position));
//                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//                        // Connect the http url connection
//                        connection.connect();
//
//                        // Get the input stream from http url connection
//                        InputStream inputStream = connection.getInputStream();
//
//                        // Initialize a new BufferedInputStream from InputStream
//                        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
//
//                        // Convert BufferedInputStream to Bitmap object
//                        Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
//                        bitmap = Bitmap.createScaledBitmap(bmp, 200, 300, true);
//                        bitmaps.add(bitmap);
//                        //Thread.sleep(2000);
//                        // to get the current selected image url
//                        //Log.i("tag", String.valueOf(stopThread));
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                 }    // Log.i("tag", "this is your url "+String.valueOf(url));
//                //Now the url is loaded in the universal image donwloader in the imageView
//            }
//        }).start();
//
//        ((Activity)getContext()).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                for(int i=0;i<bitmaps.size();i++){
//                    imageView.setImageBitmap(bitmaps.get(i));
//                }
//            }
//        });
        //Picasso.get().load(String.valueOf(url)).into(imageView);


//        new AsyncTask<String,Void,String>(){
//
//            @Override
//            protected String doInBackground(String... strings) {
//                Picasso.get().load(String.valueOf(url)).into(imageView);
//                return null;
//            }
//        };
//        ImageLoader.getInstance().displayImage(String.valueOf(url), imageView, new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//                progressBar.setVisibility(View.VISIBLE);
//                imageView.setImageResource(R.drawable.defaultloader);
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                progressBar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                progressBar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//                progressBar.setVisibility(View.GONE);
//                imageView.setImageResource(R.drawable.defaultloader);
//            }
//        });


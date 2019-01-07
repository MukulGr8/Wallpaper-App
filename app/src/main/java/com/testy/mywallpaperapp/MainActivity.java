package com.testy.mywallpaperapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    String result;
    //This is the list for storing the url in the form of a string
    ArrayList<String> mWallPaper = new ArrayList<String>();
    ArrayList<String> mWallPaper2 = new ArrayList<String>();
    List<String> urlList = new ArrayList<>();
    public static ImageView imageView;
    //ImageDownloader imageDownloader;
    //public static ImageAdapter imageAdapter;
    //public static GridView gridView;
    public static int itemWall = 1;
    public static ProgressBar progressBar;
    public LinearLayout mLLayout;
    //ImageDown imageDown;
    BitmapAdapter bitmapAdapter;
    List<Bitmap> bitmaps = new ArrayList<>();
    int check = 0;
    GridView gridView;
    int doubleCheck = 1;
    private static final int DISK_CACHE_SIZE_FOR_SMALL_INTERNAL_STORAGE_MIB = 50*1024*1024;
    //Toolbar toolbar;
    //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.items_layout);
        GridView gridView = findViewById(R.id.gridView);
        imageView = findViewById(R.id.imageView);
        mLLayout = findViewById(R.id.llsecond);
        progressBar = findViewById(R.id.progressBar);
        //toolbar = findViewById(R.id.toolBar);
        //toolbar.setTitle("HD Wallpapers");
        getSupportActionBar().setTitle("HD Wallpapers");
        //Checks if a item in the gridView is clicked or if a image in a gridView is clicked
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("imageValueNo", mWallPaper.get(i)); // put image data in Intent that is the url here of the image
                //pass that URL to the secondActivity.class using the intent
                startActivity(intent); // start Intent
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(MainActivity.this).clearDiskCache();
            }
        }).start();

        nextImageDownloader(imageView);
    }

    public void prevImageDownloader(View view) {
        getSupportActionBar().setTitle("HD Wallpapers");
        itemWall = itemWall - 1;
        Toast.makeText(getApplicationContext(),"Images Are Loading...",Toast.LENGTH_LONG).show();
        Log.i("tag", String.valueOf(itemWall));
        BackgroundTask task = new BackgroundTask();
        String result = null;
        mWallPaper.clear();
        try {
            String pageUrl = "https://www.pexels.com/search/android%20wallpaper/?page=" + itemWall;
            result = task.execute(pageUrl).get();
            Pattern p = Pattern.compile("<img srcset=\"(.*?) "); //Reglar expression for seraching the img srcset tag
            Matcher m = p.matcher(result);
            while (m.find()) {
                // Log.i("tag",m.group(1));
                mWallPaper.add(m.group(1));
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//
        gridView = findViewById(R.id.gridView);
        ImageAdapter imageAdapter =  new ImageAdapter(this,R.layout.items_layout,mWallPaper);
        //BitmapAdapter bitmapAdapter = new BitmapAdapter(this, R.layout.items_layout, bitmaps);
        imageAdapter.notifyDataSetChanged();
        gridView.setAdapter(imageAdapter);
//
    }

    public void nextImageDownloader(final View view) {
        getSupportActionBar().setTitle("HD Wallpapers");
        Toast.makeText(getApplicationContext(),"Images Are Loading...",Toast.LENGTH_LONG).show();
        Log.i("tag", String.valueOf(itemWall));
        BackgroundTask task = new BackgroundTask();
        String result = null;
        //bitmaps.clear();
        mWallPaper.clear();
        try {
            String pageUrl = "https://www.pexels.com/search/android%20wallpaper/?page=" + itemWall;
            result = task.execute(pageUrl).get();
            Pattern p = Pattern.compile("<img srcset=\"(.*?) "); //Reglar expression for seraching the img srcset tag
            Matcher m = p.matcher(result);
            while (m.find()) {
                // Log.i("tag",m.group(1));
                mWallPaper.add(m.group(1));
                urlList.add(m.group(1));
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        ImageDownloadTask imageDownloadTask = new ImageDownloadTask();
//        try {
//            imageDownloadTask.execute(urlList).get();
//            //imageView.setImageBitmap(bm);
//            //bitmapAdapter.setBitmapName(bm,view,i);
//            //bitmapAdapter.notifyDataSetChanged();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        gridView = findViewById(R.id.gridView);
        ImageAdapter imageAdapter = new ImageAdapter(this, R.layout.items_layout, mWallPaper);
        //BitmapAdapter bitmapAdapter = new BitmapAdapter(this, R.layout.items_layout, bitmaps);
//        gridView.setVisibility(View.VISIBLE);
        imageAdapter.notifyDataSetChanged();
        gridView.setAdapter(imageAdapter);
//
        //Checks if a item in the gridView is clicked or if a image in a gridView is clicked
        itemWall+=1;
    }

//        ImageDownloadTask imageDownloadTask = new ImageDownloadTask();
//        try {
//            imageDownloadTask.execute(urlList).get();
//            //imageView.setImageBitmap(bm);
//            //bitmapAdapter.setBitmapName(bm,view,i);
//            //bitmapAdapter.notifyDataSetChanged();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        //Setting gridview and passing the imageAdapter in it
//        GridView gridView = findViewById(R.id.gridView);
//        BitmapAdapter bitmapAdapter = new BitmapAdapter(this, R.layout.items_layout, bitmaps);
//        gridView.setVisibility(View.VISIBLE);
//        bitmapAdapter.notifyDataSetChanged();
//        gridView.setAdapter(bitmapAdapter);
//        bitmapAdapter.notifyDataSetChanged();

//        Checks if a item in the gridView is clicked or if a image in a gridView is clicked
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                intent.putExtra("imageValueNo", mWallPaper.get(i)); // put image data in Intent that is the url here of the image
//                //pass that URL to the secondActivity.class using the intent
//                startActivity(intent); // start Intent
//            }
//        });
//        itemWall += 1;
        // Determine the number of cores on the device
//        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
//// Construct thread pool passing in configuration options
//// int minPoolSize, int maxPoolSize, long keepAliveTime, TimeUnit unit,
//// BlockingQueue<Runnable> workQueue
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(
//                NUMBER_OF_CORES * 2,
//                NUMBER_OF_CORES * 2,
//                60L,
//                TimeUnit.SECONDS,
//                new LinkedBlockingQueue<Runnable>()
//        );

//        executor.execute(new Runnable() {
//            public void run() {
//                Timer timer = new Timer();
//                TimerTask doAsynchronousTask = new TimerTask() {
//                    @Override
//                    public void run() {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if(doubleCheck < mWallPaper.size()) {
//                                    ImageDown imageDown = new ImageDown();
//                                    // PerformBackgroundTask this class is the class that extends AsynchTask
//                                    Bitmap bmp = null;
//                                    try {
//                                        bmp = imageDown.execute(mWallPaper.get(doubleCheck)).get();
//                                    } catch (ExecutionException e) {
//                                        e.printStackTrace();
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
//                                    bitmapAdapter.setBitmapName(bmp, view, doubleCheck);
//                                    bitmapAdapter.notifyDataSetChanged();
//                                    doubleCheck++;
//                                }
//                                else {return;}
//                            }
//                        });
//                    }
//                };
//                timer.schedule(doAsynchronousTask, 0, 1000); //execute in every 50000 ms
//            }
//        });

    //This is used to download the html text from the given url and then store in the result string
    class BackgroundTask extends AsyncTask<String, Void, String> {
        String line = "";

        @Override
        protected String doInBackground(String... string) {
            try {
                URL url = new URL(string[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream stream = httpURLConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                //Using while loop retreving the data from the buffer reader object
                while ((line = br.readLine()) != null) {
                    //Appending the string buffer everytime we found any data from the url
                    buffer.append(line);
                }

                result = buffer.toString();
                //closing all the connections
                br.close();
                stream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), "Loading....", Toast.LENGTH_LONG).show();
        }
    }
//     class ImageDown extends AsyncTask<String,Void,Bitmap>{
//
//        @Override
//        protected Bitmap doInBackground(String... strings) {
//            try {
//                URL url = new URL(strings[0]);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setDoInput(true);
//                connection.connect();
//                InputStream input = connection.getInputStream();
//                BufferedInputStream bufferedInputStream = new BufferedInputStream(input);
//                // Convert BufferedInputStream to Bitmap object
//                Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
//                bmp = Bitmap.createScaledBitmap(bmp, 200, 300, true);
//                Log.i("tag","done");
//                return bmp;
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//     }

    class ImageDownloadTask extends AsyncTask<List<String>,String, List<Bitmap>> {
        // Before the tasks execution
        // Do the task in background/non UI thread
        private View view;
        private  ViewGroup viewGroup;

        @Override
        protected void onPreExecute() {
             GridView gridView = findViewById(R.id.gridView);
             BitmapAdapter bitmapAdapter = new BitmapAdapter(getApplicationContext(), R.layout.items_layout, bitmaps);
            gridView.setAdapter(bitmapAdapter);
        }

        @Override
        protected List<Bitmap> doInBackground(List<String>... strings) {
            int count = urlList.size();
            int i = 0;
            Log.i("tag", String.valueOf(count));
            for (String file : urlList) {
                //URL url = urls[0];
                Log.i("tag" + i, file);
                HttpURLConnection connection = null;
                // Loop through the urls
                try {
                    URL url = new URL(file);
                    // Initialize a new http url connection
                    connection = (HttpURLConnection) url.openConnection();

                    // Connect the http url connection
                    connection.connect();

                    // Get the input stream from http url connection
                    InputStream inputStream = connection.getInputStream();

                    // Initialize a new BufferedInputStream from InputStream
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                    // Convert BufferedInputStream to Bitmap object
                    Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
                    Bitmap bitmap = Bitmap.createScaledBitmap(bmp, 200, 300, true);
                    //here
                    gridView = findViewById(R.id.gridView);
                    bitmapAdapter = new BitmapAdapter(getApplicationContext(), R.layout.items_layout, bitmaps);
                    gridView.setAdapter(bitmapAdapter);
                    bitmaps.add(bitmap);
                    //bitmapAdapter.notifyDataSetChanged();
                    //publishProgress(bitmaps);
                    publishProgress("1");
                    bitmapAdapter.notifyDataSetChanged();
                    Log.i("tag","progressing");
                    Log.i("tag", "done");
                    check++;
                    i++;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    // Disconnect the http url connection
                    connection.disconnect();
                }
            }
            // Return bitmap list
            return bitmaps;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Log.i("tag","values ok done");
        }

        @Override
        protected void onPostExecute(List<Bitmap> bitmaps) {
            Log.i("tag","size of bitmpas is "+bitmaps.size());
            super.onPostExecute(bitmaps);
        }

        @Override
        protected void onCancelled() {
            Log.i("tag","cancelled the task");
            super.onCancelled();
        }

        // @Override
//        protected void onProgressUpdate(List<Bitmap>... values) {
//            Log.i("tag","setting the images to the gridviwe");
//            super.onProgressUpdate(values);
//            Log.i("tag","setting the images to the gridviwe");
////            GridView gridView = findViewById(R.id.gridView);
////            BitmapAdapter bitmapAdapter = new BitmapAdapter(getApplicationContext(), R.layout.items_layout, values[0]);
////            bitmapAdapter.notifyDataSetChanged();
////            gridView.setAdapter(bitmapAdapter);
////            Log.i("tag","Image set in the gridview");
//        }

        //        @Override
//        protected void onProgressUpdate(Integer... values) {
//            Log.i("tag","setting the images to the gridviwe");
//            BitmapAdapter bitmapAdapter = new BitmapAdapter(getApplicationContext(), R.layout.items_layout, bitmaps);
//            bitmapAdapter.notifyDataSetChanged();
//            GridView gridView = findViewById(R.id.gridView);
//            gridView.requestLayout();
//            Log.i("tag","Image Set to the gridview");
//            Log.i("tag", values[0]);
//            super.onProgressUpdate(values);
//        }

        //         @Override
//         protected void onPostExecute(List<Bitmap> result) {
//             // mLLayout.removeAllViews();
//             for (int i = 0; i < result.size(); i++) {
//
//                 final Bitmap bitmap = result.get(i);
//                 Log.i("tag", "setting image" + "  " + i);
//                 // Save the bitmap to internal storage
//                 //Uri imageInternalUri = saveImageToInternalStorage(bitmap,i);
//                 // Display the bitmap from memory
//                 addNewImageViewToLayout(bitmap,i);
//
//                 // Display bitmap from internal storage
//             }
//         }
//     }
//
//    public void addNewImageViewToLayout(final Bitmap bitmap,int i){
//
//        // Initialize a new ImageView widget
//        ImageView iv = new ImageView(getApplicationContext());
//
//        //Set an id for the iamgeView
//        iv.setId(i);
//        // Set an image for ImageView
//        iv.setImageBitmap(bitmap);
//
//        // Create layout parameters for ImageView
//        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(350, 350);
//
//        // Add layout parameters to ImageView
//        iv.setLayoutParams(lp);
//
//        // Finally, add the ImageView to layout
//        mLLayout.addView(iv);
//        Log.i("tag","added the image to the layout");
//
//
//    }

    }

    @Override
    protected void onDestroy() {
        Glide.get(this).clearDiskCache();
        Glide.get(this).clearMemory();
        super.onDestroy();
    }
}


//



//
//    public static class ImageDownloader extends AsyncTask<String,Void,String>{
//
//        @Override
//        protected String doInBackground(String... strings) {
//            Picasso.get().load(String.valueOf(strings[0])).into(imageView);
//            return null;
//        }
//    }

//ByteArrayOutputStream stream = new ByteArrayOutputStream();
//bmp.compress(Bitmap.CompressFormat.JPEG, 80, stream);


// byte[] byteArray = stream.toByteArray();
// Bitmap compressedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

// Add the bitmap to list
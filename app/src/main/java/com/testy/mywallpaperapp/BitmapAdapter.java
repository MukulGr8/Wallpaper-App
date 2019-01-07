package com.testy.mywallpaperapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class BitmapAdapter extends ArrayAdapter<Bitmap> {
    List<Bitmap> bitmaps;
    //ImageView imageView;

    public BitmapAdapter(Context context, int resource,List<Bitmap> objects) {
        super(context, resource, objects);
        bitmaps = objects;

    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.items_layout, null);
        ImageView imageView = convertView.findViewById(R.id.imageView);
        //imageView.setImageResource(R.drawable.defaultloader);
//        for(int i=0;i<bitmaps.size();i++){
            imageView.setImageBitmap(bitmaps.get(position));
//        }
//        imageView.setImageBitmap(b);
        return convertView;
    }



//    public void setBitmapName(Bitmap b,View convertView,int position){
//        ImageView imageView = convertView.findViewById(R.id.imageView);
//        imageView.setImageBitmap(b);
//        Log.i("tag","done bhai kati");
//    }
//    public View getBitmapLIst(List<Bitmap> bitmaps,int position,View convertView){
////        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////        convertView = inflater.inflate(R.layout.items_layout, null);
////        ImageView imageView = convertView.findViewById(R.id.imageView);
////        imageView.setImageBitmap(bitmaps.get(position));
//        return  convertView;
//    }
}

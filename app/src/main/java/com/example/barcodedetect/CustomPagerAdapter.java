package com.example.barcodedetect;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import firebase.storage.helper.GetImgFromStorage;
import model.Product;


public class CustomPagerAdapter extends PagerAdapter {
    private Activity activity;
    private String id;
    private int count;
    private GetImgFromStorage getImg;

    public CustomPagerAdapter(Activity activity, String id,int numOfImg){
        this.activity = activity;
        this.id = id;
        this.count = numOfImg;
        this.getImg = new GetImgFromStorage(id);
    }
    @Override
    public int getCount() {
        return this.count;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (activity).getLayoutInflater();
        //creating  xml file for custom viewpager
        View viewItem = inflater.inflate(R.layout.content_custom, container, false);
        //finding id
        ImageView imageView =  viewItem.findViewById(R.id.item_imageView);
        //setting data
        Glide.with(activity)
                .load("link here")
                .centerCrop()
                .into(imageView);
        //imageView.setImageResource(imagesArray[position]);
        container.addView(viewItem);

        return viewItem;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        // TODO Auto-generated method stub
        return view == object;    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        container.removeView((View) object);
    }
}

package com.example.barcodedetect;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class CustomPagerAdapter extends PagerAdapter {
    private Activity activity;
    private Integer[] imagesArray;
    private String[] namesArray;

    public CustomPagerAdapter(Activity activity,Integer[] imagesArray,String[] namesArray){

        this.activity = activity;
        this.imagesArray = imagesArray;
        this.namesArray = namesArray;
    }
    @Override
    public int getCount() {
        return imagesArray.length;
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
        imageView.setBackgroundResource(imagesArray[position]);

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

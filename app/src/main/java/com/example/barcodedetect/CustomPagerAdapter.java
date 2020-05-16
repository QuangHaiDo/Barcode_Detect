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

public class CustomPagerAdapter extends PagerAdapter {
    private Activity activity;
    private String[] imagesArray;
    private GetImgFromStorage imgReference;
    public CustomPagerAdapter(Activity activity,String[] imagesArray){

        this.activity = activity;
        this.imagesArray = imagesArray;
        this.imagesArray = imagesArray;
        imgReference = new GetImgFromStorage();
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
//        imageView.setBackgroundResource(imagesArray[position]);
        Glide.with(activity)
                .load(imgReference.getReference().child(imagesArray[position]))
                .into(imageView);
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

package com.example.barcodedetect;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import firebase.database.helper.ProductDataAccess;
import firebase.storage.helper.GetImgFromStorage;
import model.Product;

public class ProductResultActivity extends Activity {

    TextView productInfo;
    ProductDataAccess productDataAccess;
    Product pResult;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    GetImgFromStorage imgReference;
    private int dotscount;
    private ImageView[] dots;

    ArrayList<String> imageId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_preview);
        sliderDotspanel = findViewById(R.id.sliderDots);
        viewPager = findViewById(R.id.images_scrolling);
        productInfo = findViewById(R.id.productInfo);

        /**
         * Read data from database and pass result to @TextView productInfo
         */
        imageId = new ArrayList<>();
        imgReference = new GetImgFromStorage();
        PagerAdapter adapter = new CustomPagerAdapter(ProductResultActivity.this,imageId,imgReference);
        viewPager.setAdapter(adapter);
        productDataAccess = new ProductDataAccess();
        productDataAccess.findProductByCode(getIntent().getStringExtra("textResult"),productInfo,adapter,imageId,imgReference);

        /** pass img_src to imageId[] and imagesName[]
        */

        // fixed img per product preview
        dotscount = 3;
        dots = new ImageView[dotscount];
        /** insert image to ViewPager
         * */
        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}

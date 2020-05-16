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

import firebase.database.helper.ProductDataAccess;
import model.Product;

public class ProductResultActivity extends Activity {

    TextView productInfo;
    ProductDataAccess productDataAccess;
    Product pResult;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;

    private int dotscount;
    private ImageView[] dots;

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

        productDataAccess = new ProductDataAccess();
        productDataAccess.findProductByCode(getIntent().getStringExtra("textResult"),productInfo);
        /* //read img_src
        sau productDataAcess lay duoc thogn tin anh, src anh roi pass vao duoi
        */
        /** pass img_src to imageId[] and imagesName[]
        */
//        Integer[] imageId = {R.raw.img1,R.raw.img2,R.raw.img3,R.raw.img4,R.raw.img5};
        String[] imageId = {"8935001800286/IMG_20200507_153714.jpg",
                "8935001800286/but-bi-thien-long-027.jpg",
                "8935001800286/but_bi_tl_027_xanh_hop_20_3.jpg",
                "8935246908662/IMG_20200507_153732.jpg",
                "8935246908662/IMG_20200507_153741.jpg"};

        PagerAdapter adapter = new CustomPagerAdapter(ProductResultActivity.this,imageId);
        viewPager.setAdapter(adapter);

        dotscount = adapter.getCount();
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

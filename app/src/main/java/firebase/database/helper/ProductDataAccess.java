package firebase.database.helper;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.barcodedetect.CustomPagerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import firebase.storage.helper.GetImgFromStorage;
import model.Product;

public class ProductDataAccess {
    private DatabaseReference databaseReference;
    Product dataResult;

    public ProductDataAccess(){
        databaseReference = FirebaseDatabase.getInstance().getReference("PRODUCT");
        dataResult = new Product();
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    /*public void getAllProducts(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot eachSnapshot:dataSnapshot.getChildren()){
                    Product product = eachSnapshot.getValue(Product.class);
                    Log.d("HAIDD",product.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("HAIDD", "Failed to read value.", databaseError.toException());
            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                Log.d("HAIDD", "Value is: " + map);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("HAIDD", "Failed to read value.", databaseError.toException());
            }
        });
    }*/

    public void findProductByCode(String code, TextView tView, PagerAdapter adapter, ArrayList<String> imageId, GetImgFromStorage imgReference){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(code)){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.child(code).getValue();
                    dataResult.setpID(code);
                    dataResult.setpName(map.get("NAME").toString());
                    dataResult.setpCategory(map.get("CATEGORY").toString());
                    dataResult.setpVendor(map.get("VENDOR").toString());
                    dataResult.setpPrice(map.get("PRICE").toString());

                    if (map.get("IMG_SRC") != null) {
                        dataResult.setpImg_src(map.get("IMG_SRC").toString().replaceAll(" ",""));
                        imageId.addAll(Arrays.asList(dataResult.getpImg_src()));
                    }

                    imgReference.setReference(dataResult.getpID());
                    adapter.notifyDataSetChanged();
                    tView.setText(dataResult.toString());

                } else {
                    tView.setText("Không tìm thấy kết quả");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("HAIDD", "Failed to read value.", databaseError.toException());
            }
        });
    }

    public Product getDataResult() {
        return dataResult;
    }
}

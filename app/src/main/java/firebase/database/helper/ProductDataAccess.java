package firebase.database.helper;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import model.Product;

public class ProductDataAccess {
    private DatabaseReference databaseReference;
    Product dataResult;

    public ProductDataAccess(){
        databaseReference = FirebaseDatabase.getInstance().getReference("PRODUCT");
        dataResult = new Product();
    }

    public Product getDataResult() {
        return this.dataResult;
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

    public void findProductByCode(String code, TextView tView){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(code)){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.child(code).getValue();
                    Log.d("HAIDD",map.toString());
                    dataResult.setpID(code);
                    dataResult.setpName(map.get("NAME").toString());
                    dataResult.setpCategory(map.get("CATEGORY").toString());
                    dataResult.setpVendor(map.get("VENDOR").toString());
                    dataResult.setpPrice(map.get("PRICE").toString());
                    dataResult.setpNumOfImg(
                            Integer.parseInt(
                                map.get("NUM_OF_IMG").toString()
                            )
                    );

                    Log.d("HAIDD map",map.toString());
                    Log.d("HAIDD dataResult",dataResult.toString());

                    tView.setText(dataResult.toString());
//                    for (Map.Entry<String,Object> entry:map.entrySet()){
//                        Log.d("HAIDD",entry.getKey()+":"+entry.getValue());
//                    }
                } else {
                    tView.setText("Không tìm thấy kết quả");
                    Log.d("HAIDD"+this.getClass(),"Khong co du lieu");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("HAIDD", "Failed to read value.", databaseError.toException());
            }
        });
    }
}

package firebase.storage.helper;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class GetImgFromStorage {
    private StorageReference reference;

    public GetImgFromStorage() {
        this.reference = FirebaseStorage.getInstance().getReference("PRODUCT_IMG_SRC");
    }


    public StorageReference getReference() {
        return reference;
    }

    public void setReference(String path) {
        this.reference = FirebaseStorage.getInstance().getReference("PRODUCT_IMG_SRC").child(path);
    }
}

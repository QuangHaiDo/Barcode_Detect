package firebase.storage.helper;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class GetImgFromStorage {
    private StorageReference reference;

    public GetImgFromStorage() {
        this.reference = FirebaseStorage.getInstance().getReference("PRODUCT_IMG_SRC");
    }
    public GetImgFromStorage(String child) {
        this.reference = FirebaseStorage.getInstance().getReference("PRODUCT_IMG_SRC").child(child);
    }

    public StorageReference getReference() {
        return reference;
    }
}

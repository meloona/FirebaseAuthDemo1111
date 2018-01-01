package sas.ita.com.firebaseauthdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

        Bitmap bmp1,bmp2;
        ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference("server/saving-data/fireblog");
        DatabaseReference userRef=ref.child("users");

        Map<String,User> users=new HashMap<>();
        users.put("abc",new User("June 23,1998","Maiyah Ahmed","momo"));
        userRef.setValue(users);

        final FirebaseDatabase database1=FirebaseDatabase.getInstance();
        DatabaseReference ref1=database.getReference("HCTDATA");
        DatabaseReference userRef1=ref.child("Faculties");
        imageView=(ImageView)findViewById(R.id.image);
        Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,1);

//        Map<String,User> users1=new HashMap<>();
//        String key1=userRef1.push().getKey();
//        String key2=userRef1.push().getKey();
//        users1.put(key1,new User("June 23,1998","Maiyah Ahmed","momo"));
//        userRef.setValue(users1);

    }
protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK)
                {
                    Bundle extras=data.getExtras();
                    bmp1=(Bitmap)extras.get("data");
                    bmp2=Bitmap.createScaledBitmap(bmp1,512,512,true);
                    imageView.setImageBitmap(bmp2);
                    uploadeFile(bmp1);
                }
                break;
        }
}

    private void uploadeFile(Bitmap bmp1) {
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageRef=storage.getReference();
        StorageReference ImageRef=storageRef.child("customer/98234023");
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bmp1.compress(Bitmap.CompressFormat.PNG,20,baos);
        byte[] data=baos.toByteArray();
        UploadTask uploadTask=ImageRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("dowenload url",taskSnapshot.getDownloadUrl().toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    public void Signout(View view) {
       FirebaseAuth.getInstance().signOut();
        finish();
    }
}

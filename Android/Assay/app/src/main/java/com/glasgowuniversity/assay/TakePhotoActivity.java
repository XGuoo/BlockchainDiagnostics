package com.glasgowuniversity.assay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class TakePhotoActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 123;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    static String currentPhotoPath;
    static String currentPhotoName;
    public static boolean isPhotoTaken;
    Button save;
    Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        setIsPhotoTaken(false);
        dispatchTakePictureIntent();

        save = findViewById(R.id.photo_button_upload);
        exit = findViewById(R.id.photo_button_exit);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadLastPhoto();
                save.setVisibility(View.GONE);
                exit.setVisibility(View.GONE);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssayDevice.resetDevice();
                finish();
            }
        });

        if(isPhotoTaken){
            save.setVisibility(View.VISIBLE);
            exit.setVisibility(View.VISIBLE);
        } else{
            save.setVisibility(View.GONE);
            exit.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setIsPhotoTaken(false);
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null) signInAnonymously();
    }

    private void signInAnonymously() {
        mAuth.signInAnonymously().addOnSuccessListener(this, new  OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // do your stuff
            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("signIn", "signInAnonymously:FAILURE", exception);
                    }
                });
    }

    public void setIsPhotoTaken(boolean value){
        isPhotoTaken = value;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.glasgowuniversity.assay.fileprovider",
                        photoFile);
                takePictureIntent.setDataAndType(photoURI, "image/jpg");
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
            setIsPhotoTaken(true);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new Date());
        String imageFileName = "assay_image_" + timeStamp + "_" + AssayDevice.getDeviceId();
        String storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        File image = new File(storageDir + File.separator + imageFileName + ".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        currentPhotoName = imageFileName;
        return image;
    }

    public void uploadPhoto(final String name, String path){

        Toast.makeText(this,"UPLOADING . . .",Toast.LENGTH_LONG).show();

        Uri file = Uri.fromFile(new File(path));

        final StorageReference fileRef = mStorageRef.child("images/"+name+".jpg");

        fileRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                AssayDevice.setImageUri(uri);
                                Log.d("FireBase", "onSuccess: uri= "+ uri.toString());
                                startActivity(new Intent(TakePhotoActivity.this,PatientInfoActivity.class));
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    public void uploadLastPhoto(){
        uploadPhoto(currentPhotoName,currentPhotoPath);
    }

    @Override
    public void onBackPressed() {}

}

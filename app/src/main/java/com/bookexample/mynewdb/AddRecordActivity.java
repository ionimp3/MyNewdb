package com.bookexample.mynewdb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class AddRecordActivity extends AppCompatActivity {

    private ImageView pImageView;
    private EditText pNameEt, pAgeEt, pPhoneEt;
    Button saveInfoBt;
    ActionBar actionBar;

    //카메라,퍼미션관려 변수선언
    private static final int  CAMERA_REQUEST_CODE = 100;
    private static final int  STORAGE_REQUEST_CODE = 101;

    private static final int  IMAGE_PICK_CAMERA_CODE = 102;
    private static final int  IMAGE_PICK_GALLERY_CODE = 103;

    private String[] cameraPermissions;
    private String[] storagePermissions;

    private Uri imageUri;

    private String name, age, phone, timeStamp;
    private DatabaseHelper dbhelper;

    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Add Information");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        pImageView = findViewById(R.id.personImage);
        pNameEt = findViewById(R.id.personName);
        pAgeEt = findViewById(R.id.personAge);
        pPhoneEt = findViewById(R.id.personPhone);

        saveInfoBt = findViewById(R.id.addButton);

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //메인함수에서 데이터베이스 오브젝트 초기화
        dbhelper = new DatabaseHelper(this);


        pImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                imagePickDialog();

            }
        });
        saveInfoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //세이브버튼 누르면 db에 데이터 insert
                getData();

            }
        });

    }

    private void getData() {

        name = ""+pNameEt.getText().toString().trim();
        age = ""+pAgeEt.getText().toString().trim();
        phone = ""+pPhoneEt.getText().toString().trim();

        timeStamp = ""+System.currentTimeMillis();

        long id = dbhelper.insertInfo(
                ""+name,
                ""+age,
                ""+phone,
                ""+imageUri,
                ""+timeStamp,
                ""+timeStamp

        );

        //Toast.makeText(this, "id 에 데이터 래코드 추가"+id, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(AddRecordActivity.this, MainActivity.class));

    }

    private void imagePickDialog() {

        String[] options = {"Camera", "Gallery"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select or Image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    // 0 이면 카메라열고 퍼미션체크
                    if (!checkCameraPermission()) {
                        // 퍼미션 허가가 아닌 경우 허가요청
                        requestCameraPermission();
                    }
                    else {
                        pickFromCamera();

                    }
                }

                else if (which == 1) {

                    if (!checkStoragePermission()) {
                        requestStoragePermissions();
                    }
                    else {
                        pickFromStorage();
                    }

                }

            }
        });
        builder.create().show();
    }

    private void pickFromStorage() {

        //갤러리에서 이미지 가져오기
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);

    }

    private void pickFromCamera() {

        //카메라에서 이미지 가져오기
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Image title");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);


    }

    private boolean checkStoragePermission() {

        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermissions() {

        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){

        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);

        boolean resultl = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);

        return result && resultl;

    }

    private void requestCameraPermission() {

        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case CAMERA_REQUEST_CODE: {

                if (grantResults.length>0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted) {
                        pickFromCamera();
                    }

                    else {
                        Toast.makeText(this, "카메라퍼미션허용이 필요함!!", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            break;

            case STORAGE_REQUEST_CODE: {

                if (grantResults.length>0) {

                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (storageAccepted) {
                        pickFromStorage();
                    }

                    else {
                        Toast.makeText(this,"외장sd카드 퍼미션허용이 필요함!!",Toast.LENGTH_SHORT).show();
                    }
                }

            }

        }

    }


    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {

        //이미지 크롭 라이브러기 기능
        if (resultCode == RESULT_OK) {

            if (requestCode == IMAGE_PICK_GALLERY_CODE) {

                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if (requestCode == IMAGE_PICK_CAMERA_CODE) {

                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                if (resultCode == RESULT_OK) {

                    Uri resultUri = result.getUri();
                    imageUri = resultUri;
                    pImageView.setImageURI(resultUri);
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                    Exception error = result.getError();
                    Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
                }
            }

        }


        super.onActivityResult(requestCode, resultCode, data);
    }




    @Override
    public  boolean onSupportNavigateUp(){
        // 백버튼 누르면 메인엑티비티로 돌아감
        onBackPressed();
        return super.onSupportNavigateUp();

    }

}

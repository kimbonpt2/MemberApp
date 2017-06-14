package com.maiatech.member;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DetailActivity extends AppCompatActivity {
    private ImageView imgSend, imgCall, imgAvatar;
    private ImageView imgBack;
    private TextView tvName, tvRegency, tvAddress, tvAge, tvPhone, tvEmail, tvSkype, tvFacebook;

    private Context context;
    private static String phone;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private DatabaseReference mData;
    private final StorageReference storageRef = storage.getReference();
    private int REQUEST_CODE = 1;
    private Uri imgUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initWidget();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(MainAdapter.DATA);
        if (bundle != null) {
            String name = bundle.getString("name");
            String regency = bundle.getString("regency");
            String address = bundle.getString("address");
            String age = bundle.getString("age");
            String email = bundle.getString("email");
            String skype = bundle.getString("skype");
            String facebook = bundle.getString("facebook");
            String avatar = bundle.getString("avatar");
            phone = bundle.getString("phone");

            tvName.setText(name);
            tvRegency.setText(regency);
            tvAddress.setText(address);
            tvAge.setText(age);
            tvPhone.setText(phone);
            tvEmail.setText(email);
            tvSkype.setText(skype);
            tvFacebook.setText(facebook);
            if (avatar.equals("")) {
                imgAvatar.setImageResource(R.drawable.user);
            } else {
                Picasso.with(context).load(avatar).into(imgAvatar);
            }
            imgSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SENDTO);
                    intent.putExtra("sms_body", "");
                    intent.setData(Uri.parse("sms:" + phone));
                    startActivity(intent);
                }
            });

            imgCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent();
//                    intent.setAction(Intent.ACTION_CALL);
//                    intent.setData(Uri.parse("tel:" + phone));
//                    startActivity(intent);

                    ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);

                }
            });

            imgAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    capturePicture();
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE);
                    ///ham luu
//                    saveData();


                }
            });

            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

        }
    }

    private void saveData() {
//        Calendar calendar = Calendar.getInstance();
        StorageReference mRef = storageRef.child("IMG" + System.currentTimeMillis() + ".png");
        imgAvatar.setDrawingCacheEnabled(true);
        imgAvatar.buildDrawingCache();

        Bitmap bitmap = imgAvatar.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(DetailActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @SuppressWarnings("VisibleForTests")
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Toast.makeText(DetailActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                Log.d("AAA", downloadUrl + "");
                String mHinhAnh = String.valueOf(downloadUrl);
                mData.child("HinhAnh").push().setValue(mHinhAnh, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            Toast.makeText(DetailActivity.this, "Lưu dữ liệu thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DetailActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imgAvatar.setImageURI(imgUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            imgUri = data.getData();
//            imgAvatar.setImageURI(imgUri);

//            saveData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void capturePicture() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 100);
        } else {
            Toast.makeText(getApplication(), "Camera không được hỗ trợ", Toast.LENGTH_LONG).show();
        }
    }

    public void initWidget() {
        imgSend = (ImageView) findViewById(R.id.img_send);
        imgCall = (ImageView) findViewById(R.id.img_call);
        imgAvatar = (ImageView) findViewById(R.id.img_avatar);
        imgBack = (ImageView) findViewById(R.id.img_back);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvRegency = (TextView) findViewById(R.id.tv_regency);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvAge = (TextView) findViewById(R.id.tv_age);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        tvEmail = (TextView) findViewById(R.id.tv_email);
        tvSkype = (TextView) findViewById(R.id.tv_skype);
        tvFacebook = (TextView) findViewById(R.id.tv_facebook);

    }

    //phone
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phone));//
            startActivity(intent);//
//            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Toast.makeText(DetailActivity.this, "Bạn không cho phép sử dụng tính năng gọi", Toast.LENGTH_LONG).show();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

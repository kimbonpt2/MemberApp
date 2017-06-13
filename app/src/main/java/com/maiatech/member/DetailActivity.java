package com.maiatech.member;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    private ImageView imgSend, imgCall, imgAvatar;
    private ImageView imgBack;
    private TextView tvName, tvRegency, tvAddress, tvAge, tvPhone, tvEmail, tvSkype, tvFacebook;
    int REQUEST_CODE_IMAGE = 1;

    public static  String phone;

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
//            int Age = Integer.parseInt(age);
            String email = bundle.getString("email");
            String skype = bundle.getString("skype");
            String facebook = bundle.getString("facebook");
            phone = bundle.getString("phone");

            tvName.setText(name);
            tvRegency.setText(regency);
            tvAddress.setText(address);
            tvAge.setText(age);
            tvPhone.setText(phone);
            tvEmail.setText(email);
            tvSkype.setText(skype);
            tvFacebook.setText(facebook);
//            imgAvatar.setImageResource(member.getIcon());


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

                    ActivityCompat.requestPermissions(DetailActivity.this, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CODE_IMAGE);

                }
            });

            imgAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    capturePicture();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE_IMAGE);
                    ///ham luu

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAvatar.setImageBitmap(bitmap);
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

        if (requestCode == REQUEST_CODE_IMAGE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phone));//
            startActivity(intent);//
//            startActivityForResult(intent, REQUEST_CODE_IMAGE);
        } else {
            Toast.makeText(DetailActivity.this, "Bạn không cho phép sử dụng tính năng gọi", Toast.LENGTH_LONG).show();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

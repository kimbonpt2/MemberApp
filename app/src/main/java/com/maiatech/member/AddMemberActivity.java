package com.maiatech.member;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMemberActivity extends AppCompatActivity {
    EditText edtName, edtRegency, edtPhone, edtAge, edtAddress, edtEmail, edtFacebook, edtSkype;
    Button btnAdd;

    DatabaseReference mData;
    FirebaseDatabase database;
    String memberId;
    String mName, mRegency, mPhone, mAge, mAddress, mEmail, mFacebok, mSkype, mAvatar;
    Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        database = FirebaseDatabase.getInstance();
        mData = database.getReference();

        memberId = mData.push().getKey();

        edtName = (EditText) findViewById(R.id.edtName);
        edtRegency = (EditText) findViewById(R.id.edtRegency);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtAge = (EditText) findViewById(R.id.edtAge);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtFacebook = (EditText) findViewById(R.id.edtFacebook);
        edtSkype = (EditText) findViewById(R.id.edtSkype);

        btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName = edtName.getText().toString();
                mRegency = edtRegency.getText().toString();
                mPhone = edtPhone.getText().toString();
                mAge = edtAge.getText().toString();
                mAddress = edtAddress.getText().toString();
                mEmail = edtEmail.getText().toString();
                mFacebok = edtFacebook.getText().toString();
                mSkype = edtSkype.getText().toString();
                addNewMember();
                finish();
            }
        });
    }

    public void addNewMember() {
        member = new Member(mName, mRegency, mAge, mPhone, mAddress, mEmail, mFacebok, mSkype, mAvatar);
        mData.child(memberId).setValue(member);
    }
}

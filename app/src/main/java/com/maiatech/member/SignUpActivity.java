package com.maiatech.member;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText edtEmail_SignUp, edtPassword_Sigup;
    Button btnSignUp2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        anhXa();
        Control();
    }

    private void anhXa() {
        edtEmail_SignUp = (EditText) findViewById(R.id.edtEmail_Sigup);
        edtPassword_Sigup = (EditText) findViewById(R.id.edtPassword_Sigup);
        btnSignUp2 = (Button) findViewById(R.id.btnSignUp2);
    }

    private void Control() {
        btnSignUp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });
    }

    private void SignUp() {
        String email = edtEmail_SignUp.getText().toString();
        String pass = edtPassword_Sigup.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Dang ky thanh cong", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Dang ky that bai", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }
}

package com.example.mrvijay.loginfirebase;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;




public class LoginActivity extends AppCompatActivity {

    EditText phone,code;
    Button login,otp;

    String verification;
    String code1;
    String phone1;


    FirebaseAuth auth;

    boolean loginstatus=false;

     PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        phone = findViewById(R.id.emaillogin);
        code=findViewById(R.id.editText2);
        login = findViewById(R.id.login);
        auth = FirebaseAuth.getInstance();
        otp=findViewById(R.id.button);


       final  ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("signing in");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);





        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phone1=phone.getText().toString();

                if(phone1.length()!=0 & phone1.contains("@")){

                    auth.sendPasswordResetEmail(phone1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(LoginActivity.this, "check email for password change", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                else {
                    Toast.makeText(LoginActivity.this, "first enter email", Toast.LENGTH_SHORT).show();
                }

            }
        });









        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                code1=code.getText().toString();
                phone1=phone.getText().toString();



                if (phone1.contains("@") && code1.length()!=0) {

                    auth.signInWithEmailAndPassword(phone1,code1).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful())
                            {
                                Intent intent=new Intent(LoginActivity.this,HomeActivity.class);

                                startActivity(intent);
                                finish();

                                SharedPreferences sharedPreferences=getSharedPreferences("status",MODE_PRIVATE);

                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putBoolean("onlineStatus",true);
                                editor.putString("emailid",phone1);

                                editor.commit();




                            }
                            else {
                                Toast.makeText(LoginActivity.this, "not a user", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }

                        }
                    });






                }
                else {
                    progressDialog.cancel();
                    Toast.makeText(LoginActivity.this, "Invalid Details...", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }
}

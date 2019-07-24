package com.example.bsstt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


//Login activity, connects with FIREBASE to see if user exists in the DB

public class MainActivity extends AppCompatActivity {

    //variable declaration
    private TextView signup;
    private EditText email;
    private EditText password;
    private Button btnLogin;
    private ProgressDialog mDialog;

    //FIREBASE connection
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mAuth cross check with firebase
        mAuth=FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        if(mAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }
        //GUI linking by id
        signup = findViewById(R.id.signup_txt);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        btnLogin = findViewById(R.id.login_btn);



        // on click listener for login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get text and turn to string and remove trailing spaces
                String mEmail= email.getText().toString().trim();
                String mPassword = password.getText().toString().trim();

                //cheack for empty fields
                if(TextUtils.isEmpty(mEmail)){
                    email.setError("Required Field");
                    return;
                }

                if(TextUtils.isEmpty(mPassword)){
                    password.setError("Required Field");
                    return;
                }

                mDialog.setMessage("Processing");
                mDialog.show();


                //Firebase authentication
                mAuth.signInWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Login Successful", Toast.LENGTH_SHORT);
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            mDialog.show();
                            mDialog.dismiss();

                        } else{
                            Toast.makeText(getApplicationContext(), "Incorreect Login Info", Toast.LENGTH_SHORT).show();
                            mDialog.show();
                            mDialog.dismiss();
                        }
                    }
                });


            }
        });


        //redirect to signup activity
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });
    }
}

package com.example.wbcc.firebasedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!= null){
            //if you are already logedin
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));


        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword =(EditText)findViewById(R.id.editTextPassword);
        buttonSignIn =(Button)findViewById(R.id.buttonSignin);
        textViewSignUp = (TextView)findViewById(R.id.textViewSignup);
        progressDialog = new ProgressDialog(this);

        buttonSignIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);


    }

    public void userLongin(){
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            //email is Empty
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("loggin...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"loggin Sucessfully",Toast.LENGTH_SHORT).show();
                            finish();

                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

                        }
                    }
                });
    }
    @Override
    public void onClick(View view){
        if(view == buttonSignIn){
            userLongin();
        }
        if(view == textViewSignUp){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}

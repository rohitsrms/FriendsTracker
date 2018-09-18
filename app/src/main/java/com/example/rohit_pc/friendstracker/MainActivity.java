package com.example.rohit_pc.friendstracker;

import android.app.ProgressDialog;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
private Button  button;
private TextView autoCompleteTextView;
private EditText pass;
private TextView Signin;
private ProgressDialog progressDialog;
private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,MapsActivity.class));
        }
       progressDialog=new ProgressDialog(this);
        button=(Button)findViewById(R.id.button);
        autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        pass=(EditText)findViewById(R.id.pass);
        Signin=(TextView)findViewById(R.id.textView3);
    button.setOnClickListener(this);
    Signin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==button){
            registerUser();
        }
        if (view==Signin)
        {
            Intent in =new Intent(MainActivity.this,Login.class);
            startActivity(in);
        }
    }

    private void registerUser() {
        String email=autoCompleteTextView.getText().toString().trim();
        String password=pass.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();            //empty email
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();//empty password
            return;
        }

        //if validation are ok
        //progress bar
        progressDialog.setMessage("Registering User");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    finish();
                    startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                }
                else {
                    Toast.makeText(MainActivity.this, "User Alredy Exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }
}

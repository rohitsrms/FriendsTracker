package com.example.rohit_pc.friendstracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity implements View.OnClickListener{
private Button btn;
private AutoCompleteTextView tvemail;
private EditText pass1;
private TextView gotoregister;
private Button logout;
private ProgressDialog prodig;
private FirebaseAuth firebaseAuth;
private DatabaseReference databaseReference;
private DatabaseReference mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),MapsActivity.class));
        }
        databaseReference=FirebaseDatabase.getInstance().getReference("FriendsData");

        prodig=new ProgressDialog(this);
        setContentView(R.layout.activity_login);
        btn=(Button)findViewById(R.id.btn);
        logout=(Button)findViewById(R.id.Logout);
        tvemail=(AutoCompleteTextView)findViewById(R.id.tvemail);
        pass1=(EditText)findViewById(R.id.pass1);
        gotoregister=(TextView)findViewById(R.id.gotoregister);
    btn.setOnClickListener(this);
    logout.setOnClickListener(this);
    gotoregister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==btn)
        {
            loginuser();
        }
        if(view==gotoregister)
        {
            Intent in=new Intent(Login.this,MainActivity.class);//go to Register
            startActivity(in);
        }

        if(view==logout)
        {
            firebaseAuth.signOut();
            finish();
        }
    }

    private void loginuser() {
        final String email=  tvemail.getText().toString().trim();
        String password=pass1.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        prodig.setMessage("Signing In..");
        prodig.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

           if(task.isSuccessful())
           {
               Toast.makeText(getApplicationContext(), "Logged In Successfully", Toast.LENGTH_SHORT).show();
               prodig.dismiss();
                startActivity(new Intent(getApplicationContext(),MapsActivity.class));
           }

           else
           {
               Toast.makeText(Login.this, "Enter Correct Email/Password", Toast.LENGTH_SHORT).show();
               prodig.dismiss();
           }
            }
        });
    }
}

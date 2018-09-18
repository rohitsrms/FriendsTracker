package com.example.rohit_pc.friendstracker;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.Attributes;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView textViewp;
    private EditText editfullname;
    private EditText editAddress;
    private Button submitbtn;


    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;

    private FirebaseUser mCurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth=FirebaseAuth.getInstance();
        mCurrentUser=firebaseAuth.getCurrentUser();

        databaseReference=FirebaseDatabase.getInstance().getReference().child("FriendsData");

    submitbtn=(Button)findViewById(R.id.submitbtn);
    editAddress=(EditText)findViewById(R.id.editAddress);
    editfullname=(EditText)findViewById(R.id.editfullname);
    textViewp.setText(""+mCurrentUser.getEmail());
    }

    @Override
    public void onClick(View view) {
    if(view==submitbtn){
        saveuserinformation();//saving data
    }
    }

    private void saveuserinformation() {
        String name=editfullname.getText().toString().trim();
        String address=editAddress.getText().toString().trim();
        double lat=-34;
        double lang=151;
        String uid=mCurrentUser.getUid();
        String email=mCurrentUser.getEmail();
        if (TextUtils.isEmpty(name)) {

          Toast.makeText(this, "Enter Name ", Toast.LENGTH_SHORT).show();
          return;
      }
        if(TextUtils.isEmpty(address))
        {
            Toast.makeText(this, "Enter Address", Toast.LENGTH_SHORT).show();//empty password
            return;
        }
UserInformation u1=new UserInformation(name,address,lat,lang,uid,email);
        databaseReference.child(uid).setValue(u1);

          editfullname.setText("");
          editAddress.setText("");
          Toast.makeText(this, name + " Information Saved", Toast.LENGTH_SHORT).show();
        finish();
          startActivity(new Intent(this,Login.class));

    }
}

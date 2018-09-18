package com.example.rohit_pc.friendstracker;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.internal.firebase_auth.zzao;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.jar.Attributes;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {
    LatLng ll;
    private GoogleMap mMap;
    ImageButton currentloc;
    ImageButton lgout;
    double lat;
    ImageButton search;
    double lang;
    EditText tvfemail;
    FirebaseUser mCurrentUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        firebaseAuth=FirebaseAuth.getInstance();
        mCurrentUser=firebaseAuth.getCurrentUser();

        databaseReference=FirebaseDatabase.getInstance().getReference("FriendsData");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        currentloc = (ImageButton) findViewById(R.id.currentloc);
        tvfemail=(EditText) findViewById(R.id.tvfemail);
        lgout=(ImageButton)findViewById(R.id.lgout);
        search=(ImageButton)findViewById(R.id.search);
      //  tvemailn.setText(""+mCurrentUser.getEmail());
        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 100, this);
lgout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
});
          currentloc.setOnClickListener(new View.OnClickListener() {

         @Override
    public void onClick(View view) {
             CameraUpdate cu=CameraUpdateFactory.newLatLngZoom(ll,15);
             MarkerOptions m=new MarkerOptions();
             mMap.animateCamera(cu);
             mMap.clear();
             MarkerOptions mw=new MarkerOptions();
             mw.position(ll);
             mw.title("My Location");
             mMap.addMarker(mw);

    }
});
          search.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  final String femail=tvfemail.getText().toString();
                  DatabaseReference ref=FirebaseDatabase.getInstance().getReference("FriendsData");
                  ref.addValueEventListener(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          for (DataSnapshot datas: dataSnapshot.getChildren()){
                              String flat =datas.child("lat").getValue().toString();
                             String flang =datas.child("lang").getValue().toString();
                              String email=datas.child("email").getValue().toString();
                              String name=datas.child("name").getValue().toString();
                              if (email.equals(femail)){
                                  LatLng friend = new LatLng(Double.parseDouble(flat),Double.parseDouble(flang));
                                  CameraUpdate cu=CameraUpdateFactory.newLatLngZoom(friend,15);
                                  mMap.animateCamera(cu);
                                  mMap.addMarker(new MarkerOptions().position(friend).title(name)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                                  break;
                              }
                          }

                      }


                      @Override
                      public void onCancelled(@NonNull DatabaseError databaseError) {
                          Toast.makeText(MapsActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                      }
                  });

              }
          });


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(28.343803, 79.427530);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Gandhi Udhyan")).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    @Override
    public void onLocationChanged(Location location) {
     ll=new LatLng(location.getLatitude(),location.getLongitude());
     lat=location.getLatitude();
     lang=location.getLongitude();
        String uid=mCurrentUser.getUid();
    //write code here
        DatabaseReference ref=databaseReference.child(uid);
        ref.child("lat").setValue(lat);
        ref.child("lang").setValue(lang);

        Toast.makeText(this, "LatLang Saved", Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"GPS STARTED", Toast.LENGTH_SHORT).show();
        currentloc.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}

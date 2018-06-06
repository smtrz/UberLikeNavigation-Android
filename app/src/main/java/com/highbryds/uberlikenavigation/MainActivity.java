package com.highbryds.uberlikenavigation;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;/*
import com.highbryds.navigation_uber.Interfaces.NaviInterfaces;
import com.highbryds.navigation_uber.NavigationUtils;*/

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback  /*,  NaviInterfaces*/ {
    private GoogleMap mMap;
    private Button stop;
 //   NavigationUtils nu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       // nu = new NavigationUtils();
      //  nu.delay = 3000;
      //  nu.MapZoom = 17f;

        stop = findViewById(R.id.stop);
        SupportMapFragment SMF = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        SMF.getMapAsync(this);

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   nu.StopAnimation();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
              //  nu.CreatePath(mMap, MainActivity.this);


               /* LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (LatLng latLng : MainActivity.this.Get_LatLng()) {
                    builder.include(latLng);
                }
                LatLngBounds bounds = builder.build();
                CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 2);
                mMap.animateCamera(mCameraUpdate);*/

               // nu.StartAnimation(MainActivity.this.Get_LatLng(), mMap);


            }
        });


    }

   /* @Override
    public List<LatLng> Get_LatLng() {
        ArrayList<LatLng> newlist = new ArrayList<>();
        newlist.add(new LatLng(24.925911, 67.141012));
        newlist.add(new LatLng(24.925989, 67.140824));
        newlist.add(new LatLng(24.927434, 67.140516));
        newlist.add(new LatLng(24.928479, 67.140229));
        newlist.add(new LatLng(24.929853, 67.140364));
        newlist.add(new LatLng(24.930234, 67.139632));
        newlist.add(new LatLng(24.931441, 67.139416));


        return newlist;
    }*/
}

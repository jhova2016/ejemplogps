package com.example.ejemplogps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private GoogleMap mapa;
    private LatLng UPV;
    private LocationManager locManager;
    double longitude, latitude;
    private Location loc;
    TextView llugar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        llugar=findViewById(R.id.lugar);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        mapa = googleMap;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mapa.getUiSettings().setZoomControlsEnabled(false);
        UPV = new LatLng(latitude=loc.getLatitude(),longitude=loc.getLongitude());
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(UPV, 15));

        mapa.setOnMapClickListener( this);
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mapa.setMyLocationEnabled(true);
            mapa.getUiSettings().setCompassEnabled(true);
        }

        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation( loc.getLatitude(),  loc.getLongitude(), 1);
            if (addresses.size() > 0) {
                llugar.setText(addresses.get(0).getAddressLine(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }





    }

    public void moveCamera(View view) {
        mapa.moveCamera(CameraUpdateFactory.newLatLng(UPV));
    }

    public void animateCamera(View view) {
        mapa.animateCamera(CameraUpdateFactory.newLatLng(UPV));
    }

    public void addMarker(View view) {

        mapa.addMarker(new MarkerOptions().position(mapa.getCameraPosition().target));




    }

     public void onMapClick(LatLng puntoPulsado) {
       /* mapa.addMarker(new MarkerOptions().position(puntoPulsado)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
         Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
         List<Address> addresses;
         try {
             addresses = gcd.getFromLocation( puntoPulsado.latitude,  puntoPulsado.longitude, 1);
             if (addresses.size() > 0) {
                 llugar.setText(addresses.get(0).getAddressLine(0));
             }
         } catch (IOException e) {
             e.printStackTrace();
         }*/
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

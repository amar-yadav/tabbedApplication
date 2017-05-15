package com.blogspot.skooltchdev.tabbedapplicationmaster;

import android.*;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private Location loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            Log.i("MapsActivity", "onCreate: LOL");
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager = (LocationManager) this
                .getSystemService(LOCATION_SERVICE);

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0, this);
        Log.d("GPS Enabled", "GPS Enabled");
        if (locationManager != null) {
            Log.i("MapsActivity", "onCreate: ");
            loc = locationManager
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            Log.i("MapsActivity", "onCreate: " + loc.getLatitude()+", "+loc.getLongitude());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng myLatLng;
//        myLatLng = new LatLng(-37, 71.1);
        if(loc!=null){
            Log.i("MapsActivity", "onMapReady: "+loc.getLatitude()+", "+loc.getLongitude());
            myLatLng = new LatLng(loc.getLatitude(), loc.getLongitude());
            mMap.addMarker(new MarkerOptions().position(myLatLng).title("You're here"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));
        }else{
            myLatLng = new LatLng(-37, 71.1);
            mMap.addMarker(new MarkerOptions().position(myLatLng).title("You're here"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));
        }


    }

    @Override
    public void onLocationChanged(Location location) {
//        LatLng myLoc =
        Log.i("MapsActivity", "onLocationChanged: "+location.getLatitude() + ", " + location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}


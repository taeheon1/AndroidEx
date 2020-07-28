package co.kr.reo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MapSearchActivity extends AppCompatActivity {

    private int MY_REQUEST_LOCATION = 10;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_search);

//        status = findViewById(R.id.status);
//
//        ActivityCompat.requestPermissions(this,new String[]{
//                Manifest.permission.ACCESS_FINE_LOCATION }, MY_REQUEST_LOCATION);
//        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//
//        LocationListener locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                status.setText("위도: " + location.getLatitude() + "\n경도: " + location.getLongitude() + "\n고도: " + location.getAltitude());
//            }
//
//            @Override
//            public void onStatusChanged(String s, int i, Bundle bundle) { }
//
//            @Override
//            public void onProviderEnabled(String s) { }
//
//            @Override
//            public void onProviderDisabled(String s) { }
//        };
//
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//            Toast.makeText(MapSearchActivity.this,"First enable LOCATION ACCESS in settings.",Toast.LENGTH_LONG).show();
//            return;
//        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,100,locationListener);
        Uri uri = Uri.parse(String.format("geo:%f,$f?z=10",37.30,127.2));
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }
}

package ms_br.appriuso;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.Marker;
import android.location.Location;
import com.google.android.gms.location.LocationListener;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import android.util.Log;


public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener/*, OnMapClickListener/*, OnMapLongClickListener */{

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    LatLng latLng;
    GoogleMap mGoogleMap;
    SupportMapFragment mFragment;
    Marker currLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mFragment.getMapAsync(this);


    }




    @Override
    public void onMapReady(GoogleMap gMap) {
        mGoogleMap = gMap;
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);


        /*mGoogleMap.setOnMapClickListener(new mGoogleMap.OnMapClickListener() {
            @Override
            public void onClick(LatLng point) {

                //what you need to do when that button is clicked
            }
        });*/

        buildGoogleApiClient();
        mGoogleApiClient.connect();

    }

    /*public void onMapClick (LatLng point) {
        // Do Something
        Log.d("arg0", point.latitude + "-" + point.longitude);
    }*/

    protected synchronized void buildGoogleApiClient() {

        Toast.makeText(this,"buildGoogleApiClient",Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {

        Toast.makeText(this,"onConnected",Toast.LENGTH_SHORT).show();
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            //place marker at current position
            //mGoogleMap.clear();
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            currLocationMarker = mGoogleMap.addMarker(markerOptions);
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this,"onConnectionSuspended",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,"onConnectionFailed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        //place marker at current position
        //mGoogleMap.clear();
        if (currLocationMarker != null) {
            currLocationMarker.remove();
        }
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");

        //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        currLocationMarker = mGoogleMap.addMarker(markerOptions);

        Toast.makeText(this,"Location Changed",Toast.LENGTH_SHORT).show();

        //zoom to current position:
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));

        // Zoom in, animating the camera.
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(19));

        //If you only need one location, unregister the listener
        //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }



}
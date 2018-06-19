package peraride.ce.pdn.edu.peraride.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import peraride.ce.pdn.edu.peraride.R;
import peraride.ce.pdn.edu.peraride.api.APIHelper;
import peraride.ce.pdn.edu.peraride.api.request.helper.impl.DockStationHelperImpl;
import peraride.ce.pdn.edu.peraride.api.request.helper.impl.LogoutRequestHelperImpl;
import peraride.ce.pdn.edu.peraride.api.request.helper.impl.ProfileRequestHelperImpl;
import peraride.ce.pdn.edu.peraride.api.request.helper.impl.StationDataRequestHelperImpl;
import peraride.ce.pdn.edu.peraride.api.request.helper.impl.UnlockRequestHelperImpl;
import peraride.ce.pdn.edu.peraride.api.response.Ancestor;
import peraride.ce.pdn.edu.peraride.api.response.Error;
import peraride.ce.pdn.edu.peraride.api.response.factory.DockStationResponse;
import peraride.ce.pdn.edu.peraride.api.response.factory.LogoutResponse;
import peraride.ce.pdn.edu.peraride.api.response.factory.ProfileResponse;
import peraride.ce.pdn.edu.peraride.api.response.factory.StationDataResponse;
import peraride.ce.pdn.edu.peraride.api.response.factory.UnlockResponse;
import peraride.ce.pdn.edu.peraride.data.model.DockLocation;
import peraride.ce.pdn.edu.peraride.data.model.DockStation;
import peraride.ce.pdn.edu.peraride.data.model.Logout;
import peraride.ce.pdn.edu.peraride.data.model.Profile;
import peraride.ce.pdn.edu.peraride.data.model.StationData;
import peraride.ce.pdn.edu.peraride.data.model.Unlock;
import peraride.ce.pdn.edu.peraride.data.model.response.DockStationResponseSerializer;
import peraride.ce.pdn.edu.peraride.data.model.response.DockStationSerializer;
import peraride.ce.pdn.edu.peraride.data.model.response.LogoutSerializer;
import peraride.ce.pdn.edu.peraride.data.model.response.ProfileSerializer;
import peraride.ce.pdn.edu.peraride.data.model.response.StationDataSerializer;
import peraride.ce.pdn.edu.peraride.data.model.response.UnlockSerializer;
import peraride.ce.pdn.edu.peraride.util.Constants;
import peraride.ce.pdn.edu.peraride.util.SessionManager;
import peraride.ce.pdn.edu.peraride.util.UtilityManager;

public class MapActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,GoogleMap.OnMarkerClickListener {
    private static final String TAG = MapActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 33;
    private static final int REQUEST_CODE_QR_SCAN = 101;
    private final int LOCATION_ENABLE = 4910;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private CoordinatorLayout mCoordinatorLayout;
    private boolean mCamAnimated;
    private Location mLastLocation;
    private ImageView mBtnScan;
    private TextView mScanRes;
    private List<DockLocation> lls;
    private boolean markersAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeViews();
        setListeners();
        ssl();

       addLocations();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void addLocations() {
        try {
            new DockStationHelperImpl().getStations(
                    new DockStation(SessionManager.getInstance().getJWTToken()),
                    new APIHelper.PostManResponseListener() {
                        @Override

                        public void onResponse(Ancestor ancestor) {
                            if (ancestor instanceof DockStationResponse) {
                               DockStationResponseSerializer data = ((DockStationResponse) ancestor).getData();
                               if (data!= null) {
                                   for (DockStationSerializer station : data.getResponse()) {
                                       lls.add(new DockLocation(Double.parseDouble(station.getLocation().getLat()), Double.parseDouble(station.getLocation().getLon()), station.getName()));

                                   }
                                   if (!markersAdded) {
                                       addMarkers();
                                   }
                               }
                            }
                        }

                        @Override
                        public void onError(Error error) {
                            if (error != null && !TextUtils.isEmpty(error.getData())) {
                                //mScanRes.setText(error.toString());
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void ssl() {
        try {
            TrustManager[] victimizedManager = new TrustManager[]{

                    new X509TrustManager() {

                        public X509Certificate[] getAcceptedIssuers() {

                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];

                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, victimizedManager, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void logout() {
        try {
            new LogoutRequestHelperImpl().logoutUser(
                    new Logout(SessionManager.getInstance().getJWTToken()),
                    new APIHelper.PostManResponseListener() {
                        @Override
                        public void onResponse(Ancestor ancestor) {
                            if (ancestor instanceof LogoutResponse) {
                                LogoutSerializer data = ((LogoutResponse) ancestor).getData();
                                logoutMsg(data);
                            }
                        }

                        @Override
                        public void onError(Error error) {
                            if (error != null && !TextUtils.isEmpty(error.getData())) {

                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void logoutMsg(LogoutSerializer data) {
        if(data.isRes()){
            Intent i= new Intent(MapActivity.this, LoginActivity.class);
            startActivity(i);
        }else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("PeraRide");
            alertDialogBuilder.setMessage("Something went wrong! Try again");
            alertDialogBuilder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }



    private void setListeners() {
        mBtnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openScanActivity();
            }
        });
    }

    private void initializeViews() {
        lls = new ArrayList<>();
        mCoordinatorLayout = findViewById(R.id.main_layout);
        mBtnScan = findViewById(R.id.btn_scan);
        mScanRes = findViewById(R.id.txt_scan_res);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        enableLocation();
    }

    private void enableLocation() {
        final LocationManager manager =
                (LocationManager) MapActivity.this.getSystemService(Context.LOCATION_SERVICE);

        if (!UtilityManager.hasGPSDevice(MapActivity.this)) {
            UtilityManager.showSnackBar(mCoordinatorLayout, "No GPS !", Snackbar.LENGTH_INDEFINITE);
            return;
        }

        if (manager != null && UtilityManager.hasGPSDevice(MapActivity.this)) {
            enableLocationServices();
        }
    }

    private void enableLocationServices() {

        if (mGoogleApiClient == null) {
            buildGoogleApiClient();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);
            Task<LocationSettingsResponse> result =
                    LocationServices.getSettingsClient(this)
                            .checkLocationSettings(builder.build());
            result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
                @Override
                public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                    try {
                        task.getResult(ApiException.class);
                    } catch (ApiException exception) {
                        switch (exception.getStatusCode()) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    ResolvableApiException resolvable =
                                            (ResolvableApiException) exception;
                                    resolvable.startResolutionForResult(MapActivity.this,
                                            LOCATION_ENABLE);
                                } catch (IntentSender.SendIntentException e) {
                                    Log.d(TAG, e.getMessage());
                                } catch (ClassCastException e) {
                                    Log.d(TAG, e.getMessage());
                                }
                                break;
                        }
                    }
                }
            });
        }
    }

    private void openScanActivity() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            showRequestPermissionAlert();
            return;
        }
        Intent i = new Intent(MapActivity.this, QrCodeActivity.class);
        startActivityForResult(i, REQUEST_CODE_QR_SCAN);
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
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        addMarkers();

    }

    private void addMarkers() {
        /*if (lls != null && !lls.isEmpty() && mMap!= null){
            markersAdded = true;
            for (DockLocation ll : lls) {
                LatLng coordinate = new LatLng(ll.getLatitude(), ll.getLongitude());
                Marker marker = mMap.addMarker(new MarkerOptions().position(coordinate).title(ll.getPlace()));
                mMap.setOnMarkerClickListener(this);
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.bike5));
                //marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                //Toast.makeText(this, "lat " + String.valueOf(ll.getLatitude()) + " ;; " + String.valueOf(ll.getLongitude())+" ;; ", Toast.LENGTH_SHORT).show();

            }
        }*/
        LatLng coordinate = new LatLng(7.2541, 80.5916);
        Marker marker = mMap.addMarker(new MarkerOptions().position(coordinate).title("Faculty of Engineering"));
        mMap.setOnMarkerClickListener(this);
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.bike5));

        LatLng coordinate1 = new LatLng(7.2592, 80.5976);
        Marker marker1 = mMap.addMarker(new MarkerOptions().position(coordinate).title("Faculty of Medcine"));
        mMap.setOnMarkerClickListener(this);
        marker1.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.bike5));

        LatLng coordinate2 = new LatLng(7.2638, 80.5988);
        Marker marker2 = mMap.addMarker(new MarkerOptions().position(coordinate).title("Faculty of Science"));
        mMap.setOnMarkerClickListener(this);
        marker2.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.bike5));


    }
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (!mCamAnimated) { // did map animated for 1st time
            moveCameraToMarker(new LatLng(location.getLatitude(), location.getLongitude()));
        }
    }

    private void moveCameraToMarker(LatLng latLng) {
        if (mMap == null) return;
        CameraPosition camPos = CameraPosition
                .builder(mMap.getCameraPosition())  // current Camera
                .bearing(0)                         // heading
                .zoom(15)                           // zoom level
                .target(latLng)                     // target position
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPos), 1500,
                new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        mCamAnimated = true;
                    }

                    @Override
                    public void onCancel() {
                        mCamAnimated = true;
                    }
                });
    }


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d(TAG, "onStatusChanged:" + s);
        Log.d(TAG, "onStatusChanged i:" + i);
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d(TAG, "onProviderEnabled:" + s);
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d(TAG, "onProviderDisabled:" + s);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationServices.getFusedLocationProviderClient(this);
        LocationRequest currentLocationRequest = new LocationRequest();
        currentLocationRequest.setInterval(5000)
                .setFastestInterval(0)
                .setMaxWaitTime(0)
                .setSmallestDisplacement(0)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mGoogleApiClient.isConnected())
                LocationServices.getFusedLocationProviderClient(this)
                        .requestLocationUpdates(currentLocationRequest, new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                onLocationChanged(locationResult.getLastLocation());
                            }
                        }, Looper.myLooper());
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                showRequestPermissionAlert();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                    }
                } else {
                    showRequestPermissionAlert();
                }
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_QR_SCAN) {
            if (data == null)
                return;
            //Getting the passed result
            String result = data.getStringExtra(Constants.QR_SCAN_RESULT);
            Log.d(TAG, "Have scan result in your app activity :" + result);
            if (!TextUtils.isEmpty(result)) {
                //mScanRes.setText(result);
                sendScannedInfo(result); // suppose that result is bike_id
            } else {
                mScanRes.setText("");
            }
        }
    }

    private void sendScannedInfo(String result) {
        final ProgressDialog progressDialog = UtilityManager
                .showProgress(this, getString(R.string.waiting));
        progressDialog.show();
        try {
            new UnlockRequestHelperImpl().unlockBike(
                    new Unlock(SessionManager.getInstance().getJWTToken(), result),
                    new APIHelper.PostManResponseListener() {
                        @Override
                        public void onResponse(Ancestor ancestor) {
                            progressDialog.dismiss();
                            if (ancestor instanceof UnlockResponse) {
                                UnlockSerializer data = ((UnlockResponse) ancestor).getData();
                                unlockBike(data);
                            }
                        }

                        @Override
                        public void onError(Error error) {
                            progressDialog.dismiss();
                            if (error != null && !TextUtils.isEmpty(error.getData())) {
                                mScanRes.setText(error.getData());
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
            progressDialog.dismiss();
        }
    }

    private void unlockBike(UnlockSerializer data) {

        // Toast.makeText(this, "response: " + data.getResponse(),
        //Toast.LENGTH_SHORT).show();

        // mScanRes.setText("response: " + data.getResponse());
        if(data.getRes()){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("PeraRide");
            alertDialogBuilder.setMessage("Bike was released. Have a nice ride!");
            alertDialogBuilder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }else{
            if(data.getResponse().equals("unsuccess not found rider")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("PeraRide");
                alertDialogBuilder.setMessage("Sorry! Your session has been expired. Please login again");
                alertDialogBuilder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                startActivity(new Intent(MapActivity.this, MapActivity.class));
                                finish();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }else if(data.getResponse().equals("You already have a bike")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("PeraRide");
                alertDialogBuilder.setMessage("You already have a bike. Please return it!");
                alertDialogBuilder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                startActivity(new Intent(MapActivity.this, MapActivity.class));
                                finish();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }else if(data.getResponse().equals("unsuccess lock is already empty")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("PeraRide");
                alertDialogBuilder.setMessage("Lock is already empty!");
                alertDialogBuilder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                startActivity(new Intent(MapActivity.this, MapActivity.class));
                                finish();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("PeraRide");
                alertDialogBuilder.setMessage("Something went wrong! Try again");
                alertDialogBuilder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                startActivity(new Intent(MapActivity.this, MapActivity.class));

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        }
    }

    private void showRequestPermissionAlert() {
        UtilityManager.showSnackBarWithAction(getString(R.string.on_permission_deny),
                Snackbar.LENGTH_LONG, getString(R.string.app_settings),
                mCoordinatorLayout, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                });
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent i=null;

        if (id == R.id.map) {
            i= new Intent(MapActivity.this, MapActivity.class);
            startActivity(i);
        } else if (id == R.id.profile) {
            i = new Intent(MapActivity.this,ProfileActivity.class);
            startActivity(i);

        } else if (id == R.id.logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        /*try {
            new StationDataRequestHelperImpl().getStationData(
                    new StationData(SessionManager.getInstance().getJWTToken(),marker.getTitle()),
                    new APIHelper.PostManResponseListener() {
                        @Override
                        public void onResponse(Ancestor ancestor) {
                            if (ancestor instanceof StationDataResponse) {
                                StationDataSerializer data = ((StationDataResponse) ancestor).getData();
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MapActivity.this);
                                alertDialogBuilder.setTitle("PeraRide - "+marker.getTitle()+" Bike Station");
                                alertDialogBuilder.setMessage("Available Bicycles : "+data.getNoOfBikes()+"\n\nEmpty Locks : "+data.getNoOfEmpty());
                                alertDialogBuilder.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                            }
                                        });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
                        }

                        @Override
                        public void onError(Error error) {
                            if (error != null && !TextUtils.isEmpty(error.getData())) {
                                Toast.makeText(MapActivity.this, error.getData(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MapActivity.this);
        alertDialogBuilder.setTitle("PeraRide - "+marker.getTitle()+" Bike Station");
        alertDialogBuilder.setMessage("Available Bicycles : "+10+"\n\nEmpty Locks : "+1);
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        return true;


    }
}

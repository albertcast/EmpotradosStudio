package com.example.empotradosstudio;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    String lat, lon, username;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFusedLocationClient
                = LocationServices
                .getFusedLocationProviderClient(this);

        // method to get the location
        getLastLocation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        username = getIntent().getStringExtra("usuario");
        bottomNav = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNav.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

    }

    @SuppressLint("MissingPermission")
    private void getLastLocation()
    {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient
                        .getLastLocation()
                        .addOnCompleteListener(
                                new OnCompleteListener<Location>() {

                                    @Override
                                    public void onComplete(
                                            @NonNull Task<Location> task)
                                    {
                                        Location location = task.getResult();
                                        if (location == null) {
                                            requestNewLocationData();
                                            System.out.println("algo mal");
                                        }
                                        else {
                                            lat = location.getLatitude()+ "";
                                            lon = location.getLongitude()+ "";
                                            LatLng currentPosition = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                                            mMap.addMarker(new MarkerOptions().position(currentPosition).title("You are here"));
                                            float zoomLevel = 7.0f; //This goes up to 21
                                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, zoomLevel));
                                        }
                                    }
                                });
            }

            else {
                Toast
                        .makeText(
                                this,
                                "Please turn on"
                                        + " your location...",
                                Toast.LENGTH_LONG)
                        .show();

                Intent intent
                        = new Intent(
                        Settings
                                .ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
        else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData()
    {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest
                = new LocationRequest();
        mLocationRequest.setPriority(
                LocationRequest
                        .PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient
                = LocationServices
                .getFusedLocationProviderClient(this);

        mFusedLocationClient
                .requestLocationUpdates(
                        mLocationRequest,
                        mLocationCallback,
                        Looper.myLooper());
    }

    private LocationCallback
            mLocationCallback
            = new LocationCallback() {

        @Override
        public void onLocationResult(
                LocationResult locationResult)
        {
            Location mLastLocation
                    = locationResult
                    .getLastLocation();
            lat = mLastLocation.getLatitude()+ "";
            lon = mLastLocation.getLongitude()+ "";
            LatLng currentPosition = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
            mMap.addMarker(new MarkerOptions().position(currentPosition).title("You are here"));
            float zoomLevel = 7.0f; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, zoomLevel));
        }
    };

    // method to check for permissions
    private boolean checkPermissions()
    {
        return ActivityCompat
                .checkSelfPermission(
                        this,
                        Manifest.permission
                                .ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED

                && ActivityCompat
                .checkSelfPermission(
                        this,
                        Manifest.permission
                                .ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        /* ActivityCompat
                .checkSelfPermission(
                    this,
                    Manifest.permission
                        .ACCESS_BACKGROUND_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        */
    }

    // method to requestfor permissions
    private void requestPermissions()
    {
        ActivityCompat.requestPermissions(
                this,
                new String[] {
                        Manifest.permission
                                .ACCESS_COARSE_LOCATION,
                        Manifest.permission
                                .ACCESS_FINE_LOCATION },
                PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled()
    {
        LocationManager
                locationManager
                = (LocationManager)getSystemService(
                Context.LOCATION_SERVICE);

        return locationManager
                .isProviderEnabled(
                        LocationManager.GPS_PROVIDER)
                || locationManager
                .isProviderEnabled(
                        LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults)
    {
        super
                .onRequestPermissionsResult(
                        requestCode,
                        permissions,
                        grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0
                    && grantResults[0]
                    == PackageManager
                    .PERMISSION_GRANTED) {

                getLastLocation();
            }
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
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

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MapsActivity.this, InicioAplicacion.class);
                startActivity(intent);
            }
        });

        LatLng campingCabopino = new LatLng(36.490062617714415, -4.743357006152879);
        mMap.addMarker(new MarkerOptions().position(campingCabopino).title("Camping Cabopino")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng campingLosEscullos = new LatLng(36.80353101302459, -2.078768082807563);
        mMap.addMarker(new MarkerOptions().position(campingLosEscullos).title("Camping Los Escullos")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng campingGiralda = new LatLng(37.20009743404518, -7.301330646463092);
        mMap.addMarker(new MarkerOptions().position(campingGiralda).title("Camping Giralda")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng campingCañosDelMeca = new LatLng( 36.201705449863795, -6.035827088816103);
        mMap.addMarker(new MarkerOptions().position(campingCañosDelMeca).title("Camping Caños Del Meca")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng campingLaRosaleda = new LatLng(36.29325383590065, -6.095138894717429);
        mMap.addMarker(new MarkerOptions().position(campingLaRosaleda).title("Camping La Rosaleda")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng campingAlmayateCosta = new LatLng(36.72530616381238, -4.135005567911514);
        mMap.addMarker(new MarkerOptions().position(campingAlmayateCosta).title("Camping Almayate Costa")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng campingPinarSanJosé = new LatLng(36.201124159765804, -6.034519086966395);
        mMap.addMarker(new MarkerOptions().position(campingPinarSanJosé).title("Camping Pinar San José")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng campingLasLomas = new LatLng(37.15961094041243, -3.4547383581073423);
        mMap.addMarker(new MarkerOptions().position(campingLasLomas).title("Camping Las Lomas")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng campingPlayaAguadulce = new LatLng(36.671172297785475, -6.405771323734489);
        mMap.addMarker(new MarkerOptions().position(campingPlayaAguadulce).title("Camping Playa Aguadulce")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng CampingAlmanat = new LatLng(36.726861865016076, -4.113274177160128);
        mMap.addMarker(new MarkerOptions().position(CampingAlmanat).title("Camping Almanat")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng campingValdevaqueros = new LatLng(36.069383890547876, -5.679946416350117);
        mMap.addMarker(new MarkerOptions().position(campingValdevaqueros).title("Camping Valdevaqueros")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng campingMarAzulBalerma = new LatLng(36.72204697996905, -2.8782751716110466);
        mMap.addMarker(new MarkerOptions().position(campingMarAzulBalerma).title("Camping Mar Azul Balerma")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng campingLaAldea = new LatLng(37.14143113234951, -6.490598459957499);
        mMap.addMarker(new MarkerOptions().position(campingLaAldea).title("Camping La Aldea")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng CampingTarifa = new LatLng(36.05488418183475, -5.64954075258505);
        mMap.addMarker(new MarkerOptions().position(CampingTarifa).title("Camping Tarifa")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng CampingLuz = new LatLng(37.20830019007481, -7.252444631120175);
        mMap.addMarker(new MarkerOptions().position(CampingLuz).title("Camping Luz")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        // Add a marker in Malaga and move the camera
        // LatLng currentPosition = new LatLng(36.71784014808168, -4.42045255993546);
        // float zoomLevel = 7.0f; //This goes up to 21
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, zoomLevel));
        mMap.setMapType(mMap.MAP_TYPE_SATELLITE);
    }


    public void Perfil(){
        Intent intento = new Intent(MapsActivity.this, Perfil.class);
        intento.putExtra("usuario", username);
        startActivity(intento);
    }

    public void Ubicacion() {
        Intent intento = new Intent(this, MapsActivity.class);
        intento.putExtra("usuario", username);
        startActivity(intento);
    }

    public void Ranking(){
        Intent intento = new Intent(MapsActivity.this, RankingPersonas.class);
        intento.putExtra("usuario",username);
        startActivity(intento);
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        bottomNav.postDelayed(() -> {
            int itemId = item.getItemId();
            if (itemId == R.id.map) {
                Ubicacion();
            } else if (itemId == R.id.perfil) {
                Perfil();
            } else if (itemId == R.id.rank) {
                Ranking();
            }
            finish();
        }, 300);
        return true;
    }

}
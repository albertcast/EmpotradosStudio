package com.example.empotradosstudio;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        LatLng campingCabopino = new LatLng(36.490062617714415, -4.743357006152879);
        mMap.addMarker(new MarkerOptions().position(campingCabopino).title("Camping Cabopino")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MapsActivity.this, InicioAplicacion.class);
                startActivity(intent);
            }
        });
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
        LatLng malaga = new LatLng(36.717992000725886, -4.420179482633101);
        mMap.addMarker(new MarkerOptions().position(malaga).title("You are here"));
        float zoomLevel = 7.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(malaga, zoomLevel));
        mMap.setMapType(mMap.MAP_TYPE_SATELLITE);
    }
}
package com.example.reto2;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.reto2.casos_uso.CasoUsoSucursal;
import com.example.reto2.datos.ApiOracleBranches;
import com.example.reto2.modelos.Sucursal;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.reto2.databinding.ActivityMapsShowBinding;

import java.util.ArrayList;

public class MapsActivityShow extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsShowBinding binding;

    private CasoUsoSucursal casoUsoSucursal = new CasoUsoSucursal();
    private ArrayList<Sucursal> sucursales = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Intent intent = getIntent();
        Integer type = Integer.parseInt(intent.getStringExtra("type"));
        if(type == 1){
            try {
                //DBHelper dbHelper = new DBHelper(getApplicationContext());
                //Cursor cursor = dbHelper.getSucursales();

                //sucursales = casoUsoSucursal.llenarListaSucursales(cursor);
                //int tamSucursales = sucursales.size();
                LatLng init = new LatLng(3.7993, -73.34);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(init,6));
                ApiOracleBranches apiOracleBranches = new ApiOracleBranches(getApplicationContext());
                apiOracleBranches.getAllSucursalMaps(googleMap);
//                for (int i=0; i<tamSucursales; i++){
//                    MarkerOptions markerOptions = new MarkerOptions();
//                    markerOptions.position(sucursales.get(i).stringToLatLong());
//                    markerOptions.title(sucursales.get(i).getName());
//                    googleMap.addMarker(markerOptions);
//                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(type == 0){
            String name = intent.getStringExtra("name");
            String coordenadas = intent.getStringExtra("coordenadas");
            Sucursal sucursal = new Sucursal(name, coordenadas);

            LatLng init = sucursal.stringToLatLong();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(init);
            markerOptions.title(sucursal.getName());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(init,7));
            googleMap.clear();
            googleMap.addMarker(markerOptions);
        }
    }
}
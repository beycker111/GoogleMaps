package com.example.clase5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMapLongClickListener, VentanaNombreM.FragmentListener {

    private GoogleMap mMap;
    private Polygon icesiArea;
    private Marker miUbicacion;

    private TextView sitioTV;
    private ArrayList<Marker> markers;

    private double distancia;

    private Button btnMarcadores;

    private VentanaNombreM ventana;
    private String respuesta;
    private LatLng var;
    private boolean opcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        opcion=false;
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        }, 11);

        btnMarcadores= findViewById(R.id.marcador_btn);
        btnMarcadores.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  opcion=true;
              }
          }
        );

        sitioTV = findViewById(R.id.sitioTV);
        markers = new ArrayList<>();
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
    //Este metodo se ejecuta cuando el mapa ha sido cargado
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(this);

        //3.466365,-76.501685

        //Esto creo que se puede borrar
        /*
        icesiArea = mMap.addPolygon(new PolygonOptions().add(
                new LatLng(3.417376,-76.512493),
                new LatLng(3.415941,-76.509426),
                new LatLng(3.413135,-76.512322),
                new LatLng(3.415448,-76.514788)
        ));
        */
        // Add a marker in Sydney and move the camera
        LatLng icesi = new LatLng(3.341201,-76.529200);
        miUbicacion = mMap.addMarker(new MarkerOptions().position(icesi).title("Yo"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(icesi, 15));

        //Solicitud de ubicación
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //https://developers.google.com/maps/documentation/android-sdk/utility/ pagina para hacer cosas cheveres con poligonos
        //Este codigo me da mi ubicacion actual
        //manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //Esto mandaba un error, le podemos dar suprech suponiendo que manejamos bien Android
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
    }

    //Se ejecuta cada vez que se mueva uno
    @Override
    public void onLocationChanged(Location location) {

        LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
        miUbicacion.setPosition(pos);
        miUbicacion.setSnippet(location(pos));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));


        //Para saber si estamos dentro de un area delimitada por puntos
        //Recordar que mis puntos estan en el SENA
        //boolean isInIcesi = PolyUtil.containsLocation(pos, icesiArea.getPoints(), true);


        /*
        if(isInIcesi){
            sitioTV.setVisibility(View.VISIBLE);
        }else{
            sitioTV.setVisibility(View.GONE);
        }
*/
    }

    public String location (LatLng location){

        String direccM = "";

        Geocoder geoLocation = new Geocoder(MapsActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geoLocation.getFromLocation(location.latitude, location.longitude, 1);
            for (int i=0; i<addresses.size();i++) {
                String adreses = addresses.get(0).getAddressLine(i);
                direccM = adreses;
            }
        }catch (Exception e){

        }
        return direccM;
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


    @Override
    public void onMapLongClick(LatLng latLng){
        /*
        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
        markers.add(marker);


        if(markers.size() >= 2){
            Marker a = markers.get(markers.size() - 1);
            Marker b = markers.get(markers.size() - 2);
            double distance = Math.sqrt(Math.pow(a.getPosition().latitude - b.getPosition().latitude, 2) + Math.pow(a.getPosition().longitude - b.getPosition().longitude,2));
            distance = distance * 111.12 * 1000;
            sitioTV.setText("Distancia = " + distance);
        }
        */
        /*
        * Obtengo mi posicion actual y la almaceno en un marcador
        *
        * */

        //Esto es para obtener la distancia mas corta
        //distance debe ser una variable global
        /*
        Marker markerNuevo = mMap.addMarker(new MarkerOptions().position(latLng));
        markers.add(markerNuevo);

        if(markers.size() == 1){
            Marker a = markers.get(markers.size() - 1);
            distancia = Math.sqrt(Math.pow(a.getPosition().latitude - miUbicacion.getPosition().latitude, 2) + Math.pow(a.getPosition().longitude - miUbicacion.getPosition().longitude,2));
            distancia = distancia * 111.12 * 1000;
            sitioTV.setText("Distancia = " + distancia);
        }else if(markers.size() > 1){
            Marker a = markers.get(markers.size() - 1);
            double nuevaDistancia = Math.sqrt(Math.pow(a.getPosition().latitude - miUbicacion.getPosition().latitude, 2) + Math.pow(a.getPosition().longitude - miUbicacion.getPosition().longitude,2));
            nuevaDistancia = nuevaDistancia * 111.12 * 1000;
            if(nuevaDistancia < distancia){
                distancia = nuevaDistancia;
                sitioTV.setText("Distancia = " + distancia);
            }
        }
        */

        if(opcion == true) {
            var = latLng;
            VentanaNombreM m = new VentanaNombreM();
            m.show(getSupportFragmentManager(),"notice");
            opcion=false;
        }

    }

    @Override
    public void ApplyText(String name) {

        respuesta=name;
        Marker marker = mMap.addMarker(new MarkerOptions().position(var));
        markers.add(marker);
        double dis=0;

        dis = Math.sqrt(Math.pow(marker.getPosition().latitude - miUbicacion.getPosition().latitude, 2) + Math.pow(marker.getPosition().longitude - miUbicacion.getPosition().longitude,2));

        dis = dis * 111.12 * 1000;
        marker.setSnippet("Distancia: "+dis);

        marker.setTitle("Nombre : "+ respuesta);

        if(markers.size() == 1){
            Marker a = markers.get(markers.size() - 1);
            distancia = Math.sqrt(Math.pow(a.getPosition().latitude - miUbicacion.getPosition().latitude, 2) + Math.pow(a.getPosition().longitude - miUbicacion.getPosition().longitude,2));
            distancia = distancia * 111.12 * 1000;
            sitioTV.setText("Marcador mas cercano " + a.getTitle());

        }else if(markers.size() > 1){
            Marker a = markers.get(markers.size() - 1);
            double nuevo = Math.sqrt(Math.pow(a.getPosition().latitude - miUbicacion.getPosition().latitude, 2) + Math.pow(a.getPosition().longitude - miUbicacion.getPosition().longitude,2));
            nuevo = nuevo * 111.12 * 1000;

            if(nuevo < distancia){
                distancia = nuevo;
                sitioTV.setText("Marcador más cerca " + a.getTitle());
            }
            if(distancia<50){
                sitioTV.setText("El usuario se encuentra en este lugar");
            }

        }

    }
}

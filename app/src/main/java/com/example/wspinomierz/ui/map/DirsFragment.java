package com.example.wspinomierz.ui.map;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.wspinomierz.R;
import com.example.wspinomierz.ui.map.directionhelpers.FetchURL;
import com.example.wspinomierz.ui.map.directionhelpers.TaskLoadedCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import com.example.wspinomierz.ui.map.Modules.DirectionFinder;
import com.example.wspinomierz.ui.map.Modules.DirectionFinderListener;
import com.example.wspinomierz.ui.map.Modules.Route;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

//TaskLoadedCallback
public class DirsFragment extends Fragment implements OnMapReadyCallback, DirectionFinderListener {

    private GoogleMap map;
    private MarkerOptions place1, place2;
    private MapViewModel mapViewModel;
    private Polyline currentPolyline;
    private Location location;
    private double latitudeFrom;
    private double longitudeFrom;
    private LocationManager locationManager;
    private LocationListener locationListener;


    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int INITIAL_REQUEST = 1337;
    private static final int LOCATION_REQUEST = INITIAL_REQUEST + 1;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapViewModel =
                ViewModelProviders.of(this).get(MapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dirs, container, false);
//        final TextView textView = root.findViewById(R.id.text_map);
//        mapViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);
        if (mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.mapFrag, mapFragment).commit();
        }

        //TODO: tu trzeba dodać właściwe miesjca
        place1 = new MarkerOptions()
                .position(new LatLng(52.248212, 20.972353))
                .title("Start");
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_start_location)

        place2 = new MarkerOptions()
                .position(new LatLng(52.248808, 20.992594))
                .title("Skała"); //TODO: finalnie tu wartoby dać nazwę skały
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_end_location));

//        new FetchURL(getActivity()).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
//        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.mapFrag);
        requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);

        locationManager = (LocationManager)
                getActivity().getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

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
        };
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        map.addMarker(place1);
        map.addMarker(place2);
        map.setMyLocationEnabled(true);

        requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);




        Criteria criteria = new Criteria();
//        if (ContextCompat.checkSelfPermission( this,android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
//        {
//            ActivityCompat.requestPermissions(
//                    this,
//                    new String [] { android.Manifest.permission.ACCESS_COARSE_LOCATION },
//                    LocationService.MY_PERMISSION_ACCESS_COURSE_LOCATION
//            );
//        }
//        try {
//            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            double latitude = location.getLatitude();
//            double longitude = location.getLongitude();
//        } catch (SecurityException e) {
//            e.printStackTrace();
//            double latitude = 52.248212;
//            double longitude = 20.972353;
//        }


        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                latitudeFrom = 51.248212;
                longitudeFrom = 19.972353;
            }
            return;
        }
//            locationManager.requestSingleUpdate("gps", locationListener);
        locationManager.requestLocationUpdates("gps", 5000, 20, locationListener);
//        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER))
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (locationManager.isProviderEnabled( LocationManager.NETWORK_PROVIDER))
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        latitudeFrom = location.getLatitude();
        longitudeFrom = location.getLongitude();
        locationManager.removeUpdates(locationListener);


        map.addPolyline(new PolylineOptions()
                .add(place1.getPosition(), place2.getPosition())
                .width(10)
                .color(Color.RED)
        );

        String origin = Double.toString(latitudeFrom) + ',' + Double.toString(longitudeFrom);
//        String origin = "Warsaw";
        String destination = "Berlin";
        try {
            new DirectionFinder(this, origin, destination, getString(R.string.google_maps_secret_key)).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

//    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
//        // Origin of route
//        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
//        // Destination of route
//        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
//        // Mode
//        String mode = "mode=" + directionMode;
//        // Building the parameters to the web service
//        String parameters = str_origin + "&" + str_dest + "&" + mode;
//        // Output format
//        String output = "json";
//        // Building the url to the web service
//        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
//        return url;
//    }

//    @Override
//    public void onTaskDone(Object... values) {
//        if (currentPolyline != null)
//            currentPolyline.remove();
//        currentPolyline = map.addPolyline((PolylineOptions) values[0]);
//    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(getContext(), "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            originMarkers.add(map.addMarker(new MarkerOptions()
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(map.addMarker(new MarkerOptions()
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(map.addPolyline(polylineOptions));
        }
    }


}
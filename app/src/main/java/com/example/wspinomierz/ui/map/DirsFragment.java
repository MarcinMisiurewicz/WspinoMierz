package com.example.wspinomierz.ui.map;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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

//TaskLoadedCallback
public class DirsFragment extends Fragment implements OnMapReadyCallback, DirectionFinderListener {

    private GoogleMap map;
    private MarkerOptions place1, place2;
    private MapViewModel mapViewModel;
    private Polyline currentPolyline;

    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;

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


        place1 = new MarkerOptions()
                .position(new LatLng(27.658143, 85.3199503))
                .title("Start");
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_start_location)

        place2 = new MarkerOptions()
                .position(new LatLng(27.667491, 85.3208583))
                .title("Skała"); //TODO: finalnie tu wartoby dać nazwę skały
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_end_location));

//        new FetchURL(getActivity()).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
//        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.mapFrag);
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        map.addMarker(place1);
        map.addMarker(place2);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(place1.getPosition(), 18));
//        map.setMyLocationEnabled(true);
        map.addPolyline(new PolylineOptions()
                .add(place1.getPosition(), place2.getPosition())
                .width(10)
                .color(Color.RED)
        );

        String origin = "Warsaw";
        String destination = "Berlin";
        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

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
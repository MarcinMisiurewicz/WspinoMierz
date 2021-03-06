package com.example.wspinomierz;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth mFirebaseAuth;
    private TextView email;
    private ProgressBar prBar;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private View header;
    public static final String FILE_NAME_LIST = "routesList.txt";
    public static final String FILE_NAME_PAST_LIST = "pastRoutesList.txt";

    private LocationManager locationManager;
    private LocationListener locationListener;
    public Location lastLocation;

    public ArrayList<Route> getRouteList() {
        return routeList;
    }

    public ArrayList<Route> routeList;
    public ArrayList<Route> pastRouteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Location testLocation = new Location("gps");
        testLocation.setLatitude(52.011074);
        testLocation.setLongitude(22.804611);

        String dir = getFilesDir().getAbsolutePath();
        File fileList = new File(dir + "/" + FILE_NAME_LIST);
        if(fileList.exists()) {
            routeList = loadFromFile(FILE_NAME_LIST);
        } else {
            routeList = new ArrayList<Route>(
//                    Arrays.asList(
//                            new Route("a", 0, testLocation, 0),
//                            new Route("b", 1, testLocation, 2),
//                            new Route("c", 2, testLocation, 4)
//                    )
            );
        }
        File filePastList = new File(dir + "/" + FILE_NAME_LIST);
        if(filePastList.exists()) {
            pastRouteList = loadFromFile(FILE_NAME_PAST_LIST);
        } else {
            pastRouteList = new ArrayList<Route>();
//            Route r1 = new Route("a", 0, testLocation, 0);
//            r1.setRouteTime(1);
//            r1.setUserGrade(2);
//            Route r2 = new Route("a", 0, testLocation, 0);
//            r2.setRouteTime(1);
//            r2.setUserGrade(2);
//            pastRouteList.add(r1);
//            pastRouteList.add(r2);
        }


//        String json = new Gson().toJson(pastRouteList);

        lastLocation = new Location("gps");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lastLocation.setLongitude(location.getLongitude());
                lastLocation.setLatitude(location.getLatitude());
                Log.v("MAIN", "IN ON LOCATION CHANGE, lat=" + location.getLatitude() + ", lon=" + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        startLocationUpdates();

        mFirebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        InitializeUI();
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
////        FloatingActionButton fab = findViewById(R.id.fab);
////        fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////            }
////        });
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_list, R.id.nav_statistics, R.id.nav_map, R.id.nav_calc,
                R.id.nav_timer)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.logOut:
                logOut();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logOut() {
        //CurrentUser is assured to not be null at this point
        prBar.setVisibility(View.VISIBLE);
        mFirebaseAuth.signOut();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void InitializeUI(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        header = navigationView.getHeaderView(0);
        email = header.findViewById(R.id.textViewMail);
        email.setText(mFirebaseAuth.getCurrentUser().getEmail());
        prBar = findViewById(R.id.progressBar);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                startLocationUpdates();
                break;
            default:
                break;
        }
    }

    public void startLocationUpdates() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    public ArrayList<Route> loadFromFile(String filename) {
        ArrayList<Route> routeListToLoad = new ArrayList<Route>();
        FileInputStream fis = null;
        try {
            fis = openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            routeListToLoad = deserializeRouteList(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return routeListToLoad;
    }

    public ArrayList<Route> deserializeRouteList(String s) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Route>>(){}.getType();
        return gson.fromJson(s, listType);
    }
}

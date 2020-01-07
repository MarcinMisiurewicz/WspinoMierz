package com.example.wspinomierz.ui.addRoute;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.wspinomierz.MainActivity;
import com.example.wspinomierz.R;
import com.example.wspinomierz.Route;
import com.example.wspinomierz.ScaleConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

//TODO: zrobić pole z dodawaniem location dla dróg planowanych
public class addRouteFragment extends Fragment {

    private com.example.wspinomierz.ui.addRoute.addRouteViewModel addRouteViewModel;
    private Route routeToAdd;
    private MainActivity context;
    private boolean ifPast;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        addRouteViewModel =
                ViewModelProviders.of(this).get(com.example.wspinomierz.ui.addRoute.addRouteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_addroute, container, false);

        final ScaleConverter scaleConverter = new ScaleConverter();
        final EditText routeNameEditText = root.findViewById(R.id.routeName);
        final Spinner gradeSpinner = root.findViewById(R.id.grade);
        final Spinner userGradeSpinner = root.findViewById(R.id.userGrade);
        final EditText routeTimeEditText = root.findViewById(R.id.routeTime);
        final EditText pitchNumberEditText = root.findViewById(R.id.pitchNumber);
        final EditText routeLocationEditText = root.findViewById(R.id.routeLocation);
        final Button addButton = root.findViewById(R.id.addButton);
        final Switch pastSwitch = root.findViewById(R.id.switchPast);

        final ArrayList<String> kurtykiGrade = new ArrayList<>(Arrays.asList("Wycena", "I", "II", "II+", "III", "IV", "IV+", "V-", "V", "V+", "VI", "VI+", "VI.1"));
        final ArrayList<String> kurtykiUserGrade = new ArrayList<>(Arrays.asList("Twoja wycena", "I", "II", "II+", "III", "IV", "IV+", "V-", "V", "V+", "VI", "VI+", "VI.1"));


        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, kurtykiGrade);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        gradeSpinner.setAdapter(gradeAdapter);

        ArrayAdapter<String> userGradeAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, kurtykiUserGrade);
        userGradeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        userGradeSpinner.setAdapter(userGradeAdapter);



//        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(new String[] {
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.INTERNET
//                }, 10);
//            }
//            return root;
//        }

        pastSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (pastSwitch.isChecked()) {
                    routeTimeEditText.setVisibility(View.VISIBLE);
                    userGradeSpinner.setVisibility(View.VISIBLE);
                    routeLocationEditText.setVisibility(View.INVISIBLE);

                } else {
                    routeTimeEditText.setVisibility(View.INVISIBLE);
                    userGradeSpinner.setVisibility(View.INVISIBLE);
                    routeLocationEditText.setVisibility(View.VISIBLE);
                }
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View root) {
                context = (MainActivity) getActivity();

                if(routeNameEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Dodaj nazwę!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = routeNameEditText.getText().toString();
                if(gradeSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(), "Dodaj wycenę!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Integer grade = scaleConverter.String2Int("Kurtyki", gradeSpinner.getSelectedItem().toString());

                if(pitchNumberEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Dodaj liczbę wyciągów!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Integer pitchNumber = Integer.parseInt(pitchNumberEditText.getText().toString());

                if (pastSwitch.isChecked()) {
                    if(userGradeSpinner.getSelectedItemPosition() == 0) {
                        Toast.makeText(getActivity(), "Dodaj swoją wycenę!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Integer userGrade = scaleConverter.String2Int("Kurtyki", userGradeSpinner.getSelectedItem().toString());
                    if(routeTimeEditText.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Dodaj czas przejścia!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Integer routeTime = toSeconds(routeTimeEditText.getText().toString());
                    routeToAdd = new Route(name, grade, context.lastLocation, pitchNumber);
                    routeToAdd.setUserGrade(userGrade);
                    routeToAdd.setRouteTime(routeTime);
                    context.pastRouteList.add(routeToAdd);
                    saveToFile(root, context.FILE_NAME_PAST_LIST, context.pastRouteList);
                } else {
                    if(routeLocationEditText.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Dodaj lokalizację!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!validateLocation(routeLocationEditText.getText().toString())) {
                        Toast.makeText(getActivity(), "lokalizacja nieprawidłowa", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String routeLocation = routeLocationEditText.getText().toString();
                    routeLocation.replace(" ", "");
                    String[] parts = routeLocation.split(",");
                    Location futureLocation = new Location("gps");
                    futureLocation.setLatitude(Double.parseDouble(parts[0]));
                    futureLocation.setLongitude(Double.parseDouble(parts[1]));
                    routeToAdd = new Route(name, grade, futureLocation, pitchNumber);
                    context.routeList.add(routeToAdd);
                    saveToFile(root, context.FILE_NAME_LIST, context.routeList);

                }
//                Toast.makeText(getActivity(), routeToAdd.pprint(), Toast.LENGTH_SHORT).show();
            }
        });
//        addRouteViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                routeName.setText(s);
//            }
//        });
        return root;
    }

    private static int toSeconds(String s) {
        if(s.indexOf(":") != -1) {
            String[] minSec = s.split(":");
            int mins = Integer.parseInt(minSec[0]);
            int secs = Integer.parseInt(minSec[1]);
            int secsInMins = mins * 60;
            return secsInMins + mins;
        }
        return Integer.parseInt(s);
    }

    private static boolean validateLocation(String s) {
        char[] okChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ',', '.', ' '};
        outer : for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            for (int j = 0; j < okChars.length; j++) {
                if (okChars[j] == ch) {
                    continue outer;
                }
            }
            return false;
        }
        if (s.length() < 3) {
            return false;
        }
        return true;
    }

    private void saveToFile(View v, String filename, ArrayList<Route> routeList) {
        String serializedRouteList = new Gson().toJson(routeList);
        FileOutputStream fos = null;

        try {
            fos = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(serializedRouteList.getBytes());
            Toast.makeText(getActivity(), "Dodano Drogę", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

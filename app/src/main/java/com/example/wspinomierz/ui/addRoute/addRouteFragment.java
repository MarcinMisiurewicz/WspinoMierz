package com.example.wspinomierz.ui.addRoute;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.wspinomierz.R;
import com.example.wspinomierz.ui.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class addRouteFragment extends Fragment {

    private com.example.wspinomierz.ui.addRoute.addRouteViewModel addRouteViewModel;
    private Route routeToAdd;

    private HashMap<String, Integer> kurtyki2int = new HashMap<String, Integer>() {{
        put("I", 0);
        put("II", 1);
        put("II+", 2);
        put("III", 3);
        put("IV", 4);
        put("IV+", 6);
        put("V-", 7);
        put("V", 8);
        put("V+", 9);
        put("VI-", 10);
        put("VI", 11);
        put("VI+", 12);
        put("VI.1", 13);
    }};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addRouteViewModel =
                ViewModelProviders.of(this).get(com.example.wspinomierz.ui.addRoute.addRouteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_addroute, container, false);

        final EditText routeNameEditText = root.findViewById(R.id.routeName);
        final Spinner gradeSpinner = root.findViewById(R.id.grade);
        final Spinner userGradeSpinner = root.findViewById(R.id.userGrade);
        final EditText routeTimeEditText = root.findViewById(R.id.routeTime);
        final EditText pitchNumberEditText = root.findViewById(R.id.pitchNumber);
        final Button addButton = root.findViewById(R.id.addButton);

        final ArrayList<String> kurtykiGrade = new ArrayList<>(Arrays.asList("Wycena", "I", "II", "II+", "III", "IV", "IV", "IV+", "V-", "V", "V+", "VI", "VI+", "VI.1"));
        final ArrayList<String> kurtykiUserGrade = new ArrayList<>(Arrays.asList("Twoja wycena", "I", "II", "II+", "III", "IV", "IV", "IV+", "V-", "V", "V+", "VI", "VI+", "VI.1"));

        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, kurtykiGrade);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        gradeSpinner.setAdapter(gradeAdapter);

        ArrayAdapter<String> userGradeAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, kurtykiUserGrade);
        userGradeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        userGradeSpinner.setAdapter(userGradeAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View root) {
                if(routeNameEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Dodaj nazwę!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = routeNameEditText.getText().toString();
                if(gradeSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(), "Dodaj wycenę!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Integer grade = kurtyki2int.get(gradeSpinner.getSelectedItem().toString());
                if(userGradeSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(), "Dodaj swoją wycenę!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Integer userGrade = kurtyki2int.get(userGradeSpinner.getSelectedItem().toString());
                Location location = new Location("test");
                location.setLongitude(0);
                location.setLatitude(0);
                if(routeTimeEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Dodaj czas przejścia!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Integer routeTime = Integer.parseInt(routeTimeEditText.getText().toString());
                if(pitchNumberEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Dodaj liczbę wyciągów!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Integer pitchNumber = Integer.parseInt(pitchNumberEditText.getText().toString());
                routeToAdd = new Route(name, grade, location, userGrade, routeTime, pitchNumber);
                Toast.makeText(getActivity(), routeToAdd.pprint(), Toast.LENGTH_SHORT).show();
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
}
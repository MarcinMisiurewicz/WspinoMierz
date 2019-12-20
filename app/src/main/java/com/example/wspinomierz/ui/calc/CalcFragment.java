package com.example.wspinomierz.ui.calc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.wspinomierz.R;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

public class CalcFragment extends Fragment implements View.OnClickListener {
    private CalcViewModel calcViewModel;

    private String choosen_scale_from;
    private String choosen_scale_to;
    private String choosen_grade_from;
    private ArrayList<String> current_grades1;
    private ArrayList<String> current_grades2;
    private ArrayAdapter<String> gradesAdapter1;
    private ArrayAdapter<String> gradesAdapter2;

    //TODO: przenieść to do resources Strings i rozszerzyć
    ArrayList<String> uiaa = new ArrayList<>(Arrays.asList("I", "II", "II+", "III", "IV", "IV+", "V-", "V", "V+", "VI", "VI+", "VII-"));
    ArrayList<String> kurtyki = new ArrayList<>(Arrays.asList("I", "II", "II+", "III", "IV", "IV", "IV+", "V-", "V", "V+", "VI", "VI+", "V.1"));
    ArrayList<String> francuska  = new ArrayList<>(Arrays.asList("1", "2", "2+", "3", "4a", "4b", "4c", "5a", "5b", "5c", "6a", "6a+", "6b"));
    ArrayList<String> usa  = new ArrayList<>(Arrays.asList("5.0", "5.1", "5.2", "5.3", "5.4", "5.5", "5.6", "5.7", "5.8", "5.9", "5.10a", "5.10b"));

    HashMap<String, Integer> uiaa2Int = new HashMap<String, Integer>() {{
        put("I", 0);
        put("II", 1);
        put("II+", 2);
        put("III", 3);
        put("IV", 4);
        put("IV+", 6);
        put("V-", 7);
        put("V", 8);
        put("V+", 9);
        put("VI", 10);
        put("VI+", 11);
        put("VII-", 12);
    }};
    HashMap<Integer, String> Int2uiaa = new HashMap<Integer, String>() {{
        put(0, "I");
        put(1, "II");
        put(2, "II+");
        put(3, "III");
        put(4, "IV");
        put(5, "IV");
        put(6, "IV+");
        put(7, "V-");
        put(8, "V");
        put(9, "V+");
        put(10, "VI");
        put(11, "VI+");
        put(12, "VII-");
    }};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calcViewModel =
                ViewModelProviders.of(this).get(CalcViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calc, container, false);



        final HashMap<String, ArrayList<String>> scales_map = new HashMap<String, ArrayList<String>>();
        scales_map.put("UIAA", uiaa);
        scales_map.put("Kurtyki", kurtyki);
        scales_map.put("Francuska", francuska);
        scales_map.put("USA", usa);

        //TODO = scales_map.keySet()asdasd
        final String [] scales = {"UIAA","Francuska","Kurtyki","USA",};
        current_grades1 = new ArrayList<String>();
        current_grades1.addAll(scales_map.get(scales[0]));
        current_grades2 = new ArrayList<String>();
        current_grades2.addAll(scales_map.get(scales[0]));


        Spinner scaleSpinner1 = root.findViewById(R.id.spinner1);
        Spinner scaleSpinner2 = root.findViewById(R.id.spinner2);
        Spinner gradeSpinner1 = root.findViewById(R.id.spinner3);
        Spinner gradeSpinner2 = root.findViewById(R.id.spinner4);

        ArrayAdapter<String> scalesAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, scales);
        scalesAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        gradesAdapter1 = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, current_grades1);
        gradesAdapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        gradesAdapter2 = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, current_grades2);
        gradesAdapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        scaleSpinner1.setAdapter(scalesAdapter);
        scaleSpinner2.setAdapter(scalesAdapter);
        gradeSpinner1.setAdapter(gradesAdapter1);
        gradeSpinner2.setAdapter(gradesAdapter2);

        scaleSpinner1.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Object item = parent.getItemAtPosition(position);
                        choosen_scale_from = choosen_scale_to;
                        choosen_scale_to = item.toString();
                        current_grades1.clear();
                        current_grades1.addAll(scales_map.get(item.toString()));
                        current_grades2.clear();
                        gradesAdapter2.notifyDataSetChanged();
                        gradesAdapter1.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "wybrano " + item.toString(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        scaleSpinner2.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Object item = parent.getItemAtPosition(position);
                        choosen_scale_from = choosen_scale_to;
                        choosen_scale_to = item.toString();
                        current_grades2.clear();
                        current_grades2.addAll(scales_map.get(item.toString()));
                        current_grades1.clear();
                        gradesAdapter2.notifyDataSetChanged();
                        gradesAdapter1.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "wybrano " + item.toString(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        gradeSpinner1.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Object item = parent.getItemAtPosition(position);
                        choosen_grade_from = item.toString();
                        Toast.makeText(getActivity(), "wybrano " + item.toString(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );
        gradeSpinner2.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Object item = parent.getItemAtPosition(position);
                        choosen_grade_from = item.toString();
                        Toast.makeText(getActivity(), "wybrano " + item.toString(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );
        Button convert = root.findViewById(R.id.convertButton);
        convert.setOnClickListener(this);

//        final TextView textView = root.findViewById(R.id.text_calc);
//        calcViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onClick(View root) {
        Toast.makeText(getActivity(),"przliczam",Toast.LENGTH_SHORT).show();
        switch (choosen_scale_from){
            case "UIAA":
                switch (choosen_scale_to){
                    case "Francuska":

                    case "Kurtyki":

                    case "USA":

                }
            case "Francuska":
                switch (choosen_scale_to){
                    case "UIAA":

                    case "Kurtyki":

                    case "USA":

                }

            case "Kurtyki":
                switch (choosen_scale_to){
                    case "Francuska":

                    case "UIAA":

                    case "USA":

                }
            case "USA":
                switch (choosen_scale_to){
                    case "Francuska":

                    case "Kurtyki":

                    case "UIAA":

                }
        }

    }
}
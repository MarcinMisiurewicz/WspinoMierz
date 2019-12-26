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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.wspinomierz.R;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

//test z lapka
public class CalcFragment extends Fragment implements View.OnClickListener {
    private CalcViewModel calcViewModel;

    private String choosen_scale_from;
    private String choosen_scale_to;
    private String choosen_grade_from;
    private ArrayList<String> current_grades1;
    private ArrayAdapter<String> gradesAdapter1;
    private TextView resultGradeTextView;

    //TODO: przenieść to do resources Strings i rozszerzyć
    ArrayList<String> uiaa = new ArrayList<>(Arrays.asList("I", "II", "II+", "III", "IV", "IV+", "V-", "V", "V+", "VI", "VI+", "VII-"));
    ArrayList<String> kurtyki = new ArrayList<>(Arrays.asList("I", "II", "II+", "III", "IV", "IV", "IV+", "V-", "V", "V+", "VI", "VI+", "VI.1"));
    ArrayList<String> francuska  = new ArrayList<>(Arrays.asList("1", "2", "2+", "3", "4a", "4b", "4c", "5a", "5b", "5c", "6a", "6a+", "6b"));
    ArrayList<String> usa  = new ArrayList<>(Arrays.asList("5.1", "5.2", "5.3", "5.4", "5.5", "5.6", "5.7", "5.8", "5.9", "5.10a", "5.10b"));

    private HashMap<String, Integer> uiaa2int = new HashMap<String, Integer>() {{
        put("I", 0);
        put("II", 1);
        put("II+", 2);
        put("III", 3);
        put("IV", 4);
        put("IV+", 6);
        put("V-", 7);
        put("V", 8);
        put("V+", 9);
        put("-VI", 10);
        put("VI", 11);
        put("VI+", 12);
        put("VII-", 13);
    }};
    HashMap<Integer, String> int2uiaa = new HashMap<Integer, String>() {{
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
        put(10, "VI-");
        put(11, "VI");
        put(12, "VI+");
        put(13, "VII-");
    }};

    HashMap<Integer, String> int2kurtyki = new HashMap<Integer, String>() {{
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
        put(10, "VI-");
        put(11, "VI");
        put(12, "VI+");
        put(13, "VI.1");
    }};

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

    private HashMap<String, Integer> francuska2int = new HashMap<String, Integer>() {{
        put("1", 0);
        put("2", 1);
        put("2+", 2);
        put("3", 3);
        put("4a", 4);
        put("4b", 5);
        put("4c", 6);
        put("5a", 7);
        put("5b", 8);
        put("5c", 9);
        put("6a", 11);
        put("6a+", 12);
        put("6b", 13);
    }};

    private HashMap<Integer, String> int2francuska = new HashMap<Integer, String>() {{
        put(0, "1");
        put(1, "2");
        put(2, "2+");
        put(3, "3");
        put(4, "4a");
        put(5, "4b");
        put(6, "4c");
        put(7, "5a");
        put(8, "5b");
        put(9, "5c");
        put(11, "6a");
        put(12, "6a+");
        put(13, "6b");
    }};

    HashMap<Integer, String> int2usa = new HashMap<Integer, String>() {{
        put(0, "5.1");
        put(1, "5.2");
        put(2, "5.2");
        put(3, "5.3");
        put(4, "5.4");
        put(5, "5.4");
        put(6, "5.5");
        put(7, "5.5");
        put(8, "5.6");
        put(9, "5.7");
        put(10, "5.8");
        put(11, "5.9");
        put(12, "5.10a");
        put(13, "5.10b");
    }};

    private HashMap<String, Integer> usa2int = new HashMap<String, Integer>() {{
        put("5.1", 0);
        put("5.2", 1);
        put("5.3", 3);
        put("5.4", 4);
        put("5.5", 6);
        put("5.6", 8);
        put("5.7", 9);
        put("5.8", 10);
        put("5.9", 11);
        put("5.10a", 12);
        put("5.10b", 13);
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


        final Spinner scaleSpinner1 = root.findViewById(R.id.spinner1);
        Spinner scaleSpinner2 = root.findViewById(R.id.spinner2);
        Spinner gradeSpinner1 = root.findViewById(R.id.spinner3);
        resultGradeTextView = root.findViewById(R.id.textGrade);

        Button convert = root.findViewById(R.id.convertButton);
        convert.setOnClickListener(this);
        convert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View root) {
                Toast.makeText(getActivity(),"przliczam",Toast.LENGTH_SHORT).show();
                Integer scaleInInt = 0;
                String scaleInString = "I";
                switch (choosen_scale_from){
                    case "UIAA":
                        scaleInInt = Integer.valueOf(uiaa2int.get(choosen_grade_from));
                        break;
                    case "Francuska":
                        scaleInInt = Integer.valueOf(francuska2int.get(choosen_grade_from));
                        break;
                    case "Kurtyki":
                        scaleInInt = Integer.valueOf(kurtyki2int.get(choosen_grade_from));
                        break;
                    case "USA":
                        scaleInInt = Integer.valueOf(usa2int.get(choosen_grade_from));
                        break;
                }
                switch (choosen_scale_to){
                    case "Francuska":
                        scaleInString = int2francuska.get(scaleInInt);
                        break;
                    case "Kurtyki":
                        scaleInString = int2kurtyki.get(scaleInInt);
                        break;
                    case "UIAA":
                        scaleInString = int2uiaa.get(scaleInInt);
                        break;
                    case "USA":
                        scaleInString = int2usa.get(scaleInInt);
                        break;
                }
                resultGradeTextView.setText(scaleInString);
            }
        });

        Button change = root.findViewById(R.id.changeButton);
        change.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View root) {
                Toast.makeText(getActivity(),"aaa",Toast.LENGTH_SHORT).show();
                //TODO: zrobić przerzucenien skal prawo-lewo
            }
        });

        ArrayAdapter<String> scalesAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, scales);
        scalesAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        gradesAdapter1 = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, current_grades1);
        gradesAdapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        scaleSpinner1.setAdapter(scalesAdapter);
        scaleSpinner2.setAdapter(scalesAdapter);
        gradeSpinner1.setAdapter(gradesAdapter1);

        scaleSpinner1.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Object item = parent.getItemAtPosition(position);
                        choosen_scale_from = item.toString();
                        current_grades1.clear();
                        current_grades1.addAll(scales_map.get(item.toString()));
                        gradesAdapter1.notifyDataSetChanged();
                        choosen_grade_from = current_grades1.get(0);
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
                        choosen_scale_to = item.toString();
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


    }
}
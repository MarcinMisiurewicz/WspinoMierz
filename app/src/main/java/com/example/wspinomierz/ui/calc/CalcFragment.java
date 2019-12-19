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
    //lala
    private CalcViewModel calcViewModel;

    Integer myint = 6;
    public String choosen_scale1;
    public String choosen_scale2;
    public ArrayList<String> current_grades1;
    public ArrayList<String> current_grades2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calcViewModel =
                ViewModelProviders.of(this).get(CalcViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calc, container, false);

        //TODO: przenieść to do resources Strings i rozszerzyć
        ArrayList<String> uiaa = new ArrayList<>(Arrays.asList("I", "II", "II+", "III", "IV", "IV", "IV+", "V-", "V", "V+", "VI", "VI+", "VII-"));
        ArrayList<String> kurtyki = new ArrayList<>(Arrays.asList("I", "II", "II+", "III", "IV", "IV", "IV+", "V-", "V", "V+", "VI", "VI+", "V.1"));
        ArrayList<String> francuska  = new ArrayList<>(Arrays.asList("1", "2", "2+", "3", "4a", "4b", "4c", "5a", "5b", "5c", "6a", "6a+", "6b"));
        ArrayList<String> usa  = new ArrayList<>(Arrays.asList("5.0", "5.1", "5.2", "5.3", "5.4", "5.5", "5.6", "5.7", "5.8", "5.9", "5.10a", "5.10b"));

        final HashMap<String, ArrayList<String>> scales_map = new HashMap<String, ArrayList<String>>();
        scales_map.put("UIAA", uiaa);
        scales_map.put("Kurtyki", kurtyki);
        scales_map.put("Francuska", francuska);
        scales_map.put("USA", usa);

        //TODO = scales_map.keySet()
        final String [] scales = {"UIAA","Francuska","Kurtyki","USA",};
        current_grades1 = scales_map.get(scales[0]);
        current_grades2 = scales_map.get(scales[0]);
        Toast.makeText(getActivity(), myint.toString(),Toast.LENGTH_SHORT).show();

        Spinner scaleSpinner1 = root.findViewById(R.id.spinner1);
        Spinner scaleSpinner2 = root.findViewById(R.id.spinner2);
        Spinner gradeSpinner1 = root.findViewById(R.id.spinner3);
        Spinner gradeSpinner2 = root.findViewById(R.id.spinner4);

        ArrayAdapter<String> scalesAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, scales);
        scalesAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        final ArrayAdapter<String> gradesAdapter1 = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, current_grades1);
        gradesAdapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        final ArrayAdapter<String> gradesAdapter2 = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, current_grades2);
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
                        choosen_scale1 = item.toString();
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
                        choosen_scale2 = item.toString();
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
        Toast.makeText(getActivity(),"Text!",Toast.LENGTH_SHORT).show();
    }
}
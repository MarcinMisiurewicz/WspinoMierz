package com.example.wspinomierz.ui.calc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class CalcFragment extends Fragment {
    //lala
    private CalcViewModel calcViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calcViewModel =
                ViewModelProviders.of(this).get(CalcViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calc, container, false);

        String [] scales = {"UIAA","Francuska","Kurtyki","USA",};

        //TODO: przenieść to do resources Strings i rozszerzyć
        String [] uiaa = {"I", "II", "II+", "III", "IV", "IV", "IV+", "V-", "V", "V+", "VI", "VI+", "VII-"};
        String [] kurtyki = {"I", "II", "II+", "III", "IV", "IV", "IV+", "V-", "V", "V+", "VI", "VI+", "V.1"};
        String [] francuska = {"1", "2", "2+", "3", "4a", "4b", "4c", "5a", "5b", "5c", "6a", "6a+", "6b"};
        String [] usa = {"5.0", "5.1", "5.2", "5.3", "5.4", "5.5", "5.6", "5.7", "5.8", "5.9", "5.10a", "5.10b"};

        Spinner scale1 = root.findViewById(R.id.spinner1);
        Spinner scale2 = root.findViewById(R.id.spinner2);
        Spinner grade1 = root.findViewById(R.id.spinner3);
        Spinner grade2 = root.findViewById(R.id.spinner4);

        ArrayAdapter<String> scalesAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, scales);
        scalesAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        ArrayAdapter<String> gradesAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, uiaa);
        scalesAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        scale1.setAdapter(scalesAdapter);
        scale2.setAdapter(scalesAdapter);
        grade1.setAdapter(gradesAdapter);
        grade2.setAdapter(gradesAdapter);

        Button convert = root.findViewById(R.id.convertButton);

//        final TextView textView = root.findViewById(R.id.text_calc);
//        calcViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}
package com.example.wspinomierz.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wspinomierz.MainActivity;
import com.example.wspinomierz.R;
import com.example.wspinomierz.Route;
import com.example.wspinomierz.ScaleConverter;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private MainActivity context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textViewMeanValue = root.findViewById(R.id.textViewMeanValue);
        final TextView textViewMaxValue = root.findViewById(R.id.textViewMaxValue);
        final TextView textViewCountValue = root.findViewById(R.id.textViewCountValue);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        context = (MainActivity) getActivity();
        int allValues = 0;
        int maxGrade = 0;
        for (Route r: context.routeList) {
            allValues += r.getGrade();
            if (r.getGrade() > maxGrade) {
                maxGrade = r.getGrade();
            }
        }
        int meanValue = Math.round(allValues/context.routeList.size());
        ScaleConverter scaleConverter = new ScaleConverter();
        textViewMeanValue.setText(scaleConverter.Int2String("Kurtyki", meanValue));
        textViewMaxValue.setText(scaleConverter.Int2String("Kurtyki", maxGrade));
        textViewCountValue.setText(String.valueOf(context.routeList.size()));



        return root;
    }
}
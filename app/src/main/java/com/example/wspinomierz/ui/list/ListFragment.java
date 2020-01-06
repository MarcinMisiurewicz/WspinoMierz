package com.example.wspinomierz.ui.list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wspinomierz.MainActivity;
import com.example.wspinomierz.R;

import com.example.wspinomierz.Route;
import com.example.wspinomierz.ui.addRoute.addRouteFragment;
import com.example.wspinomierz.ui.map.DirsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListFragment extends Fragment {

    public MainActivity context;
    private ListViewModel listViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listViewModel =
                ViewModelProviders.of(this).get(ListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        final ListView listView = root.findViewById(R.id.listView);
        final TextView textViewNameLabel = root.findViewById((R.id.textViewNameLabel));
        final TextView textViewGradeLabel = root.findViewById((R.id.textViewGradeLabel));
        final TextView textViewPitchNumberLabel = root.findViewById((R.id.textViewPitchNumberLabel));
//        final TextView textView = root.findViewById(R.id.text_list);
//        listViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        context = (MainActivity) getActivity();
        final RouteArrayAdapter adapter = new RouteArrayAdapter(context, R.layout.route_list_adapter_layout, context.routeList);
        listView.setAdapter(adapter);

        FloatingActionButton addButton = root.findViewById(R.id.fab);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View root) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                addRouteFragment NAME = new addRouteFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                fragmentTransaction.commit();
            }
        });

//        final LinearLayout rowLayout = root.findViewById(R.id.rowLayout);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                TextView tvName = v.findViewById(R.id.textViewName);
                TextView tvLoc = v.findViewById(R.id.textViewLocation);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DirsFragment NAME = new DirsFragment();
                NAME.locationTo = tvLoc.getText().toString();
                NAME.locationToName = tvName.getText().toString();
                fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                fragmentTransaction.commit();
            }
        });
        textViewNameLabel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View root) {
                Collections.sort(context.routeList, new Comparator<Route>() {
                    @Override
                    public int compare(Route r1, Route r2) {
                        return r1.getName().compareTo(r2.getName());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });

        textViewGradeLabel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View root) {
                Collections.sort(context.routeList, new Comparator<Route>() {
                    @Override
                    public int compare(Route r1, Route r2) {
                        return r1.getGrade().compareTo(r2.getGrade());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });

        textViewPitchNumberLabel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View root) {
                Collections.sort(context.routeList, new Comparator<Route>() {
                    @Override
                    public int compare(Route r1, Route r2) {
                        return r1.getPitchNumber().compareTo(r2.getPitchNumber());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });

        return root;
    }
}
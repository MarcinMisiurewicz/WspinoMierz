package com.example.wspinomierz.ui.list;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    public MainActivity context;
    private ListViewModel listViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listViewModel =
                ViewModelProviders.of(this).get(ListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        final ListView listView = root.findViewById(R.id.listView);
//        final TextView textView = root.findViewById(R.id.text_list);
//        listViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        context = (MainActivity) getActivity();
        RouteArrayAdapter adapter = new RouteArrayAdapter(context, R.layout.route_list_adapter_layout, context.routeList);
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

        return root;
    }
}
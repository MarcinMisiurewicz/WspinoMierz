package com.example.wspinomierz.ui.pastList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.wspinomierz.MainActivity;
import com.example.wspinomierz.R;
import com.example.wspinomierz.ui.addRoute.addRouteFragment;
import com.example.wspinomierz.ui.list.ListViewModel;
import com.example.wspinomierz.ui.list.RouteArrayAdapter;
import com.example.wspinomierz.ui.map.DirsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PastListFragment extends Fragment {

    private PastListViewModel pastListViewModel;
    public MainActivity context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pastListViewModel =
                ViewModelProviders.of(this).get(PastListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_past_list, container, false);
        final ListView listView = root.findViewById(R.id.pastListView);
//        final TextView textView = root.findViewById(R.id.text_list);
//        listViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        context = (MainActivity) getActivity();
        PastRouteArrayAdapter adapter = new PastRouteArrayAdapter(context, R.layout.past_route_list_adapter_layout, context.pastRouteList);
        listView.setAdapter(adapter);

        FloatingActionButton addButton = root.findViewById(R.id.pastFab);
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
                TextView tvName = v.findViewById(R.id.pastTextViewName);
                TextView tvLoc = v.findViewById(R.id.pastTextViewLocation);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DirsFragment NAME = new DirsFragment();
                NAME.locationTo = tvLoc.getText().toString();
                NAME.locationToName = tvName.getText().toString();
                fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                fragmentTransaction.commit();
            }
        });
        return root;
    }
}
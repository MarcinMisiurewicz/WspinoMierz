package com.example.wspinomierz.ui.pastList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.wspinomierz.MainActivity;
import com.example.wspinomierz.R;
import com.example.wspinomierz.Route;
import com.example.wspinomierz.ui.addRoute.addRouteFragment;
import com.example.wspinomierz.ui.list.ListViewModel;
import com.example.wspinomierz.ui.list.RouteArrayAdapter;
import com.example.wspinomierz.ui.map.DirsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class PastListFragment extends Fragment {

    private PastListViewModel pastListViewModel;
    public MainActivity context;

    private View root;
    private TextView textViewNameLabel;
    private ListView listView;
    private TextView textViewGradeLabel;
    private TextView textViewUserGradeLabel;
    private TextView textViewPitchNumberLabel;
    private  TextView textViewRouteTimeLabel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pastListViewModel =
                ViewModelProviders.of(this).get(PastListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_past_list, container, false);
        ListView listView = root.findViewById(R.id.pastListView);
        TextView textViewNameLabel = root.findViewById((R.id.pastTextViewNameLabel));
        TextView textViewGradeLabel = root.findViewById((R.id.pastTextViewGradeLabel));
        TextView textViewUserGradeLabel = root.findViewById((R.id.pastTextViewUserGradeLabel));
        TextView textViewPitchNumberLabel = root.findViewById((R.id.pastTextViewPitchNumberLabel));
        TextView textViewRouteTimeLabel = root.findViewById((R.id.pastTextViewRouteTimeLabel));
//        final TextView textView = root.findViewById(R.id.text_list);
//        listViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        context = (MainActivity) getActivity();
        final PastRouteArrayAdapter adapter = new PastRouteArrayAdapter(context, R.layout.past_route_list_adapter_layout, context.pastRouteList);
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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Uwaga")
                        .setMessage("Czy na pewno chcesz usunąć przejście?")
                        .setPositiveButton("pozostaw", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("usuń", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Route route = context.pastRouteList.get(position);
                                context.pastRouteList.remove(position);
                                eraseFromDb(route);
                                adapter.notifyDataSetChanged();
                                saveToFile(context.FILE_NAME_PAST_LIST, context.pastRouteList);
                            }
                        });
                builder.show();
                return true;
            }
        });

        textViewNameLabel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View root) {
                Collections.sort(context.pastRouteList, new Comparator<Route>() {
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
                Collections.sort(context.pastRouteList, new Comparator<Route>() {
                    @Override
                    public int compare(Route r1, Route r2) {
                        return r1.getGrade().compareTo(r2.getGrade());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });

        textViewUserGradeLabel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View root) {
                Collections.sort(context.pastRouteList, new Comparator<Route>() {
                    @Override
                    public int compare(Route r1, Route r2) {
                        return r1.getUserGrade().compareTo(r2.getUserGrade());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });

        textViewPitchNumberLabel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View root) {
                Collections.sort(context.pastRouteList, new Comparator<Route>() {
                    @Override
                    public int compare(Route r1, Route r2) {
                        return r1.getPitchNumber().compareTo(r2.getPitchNumber());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });
        textViewRouteTimeLabel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View root) {
                Collections.sort(context.pastRouteList, new Comparator<Route>() {
                    @Override
                    public int compare(Route r1, Route r2) {
                        return r1.getRouteTime().compareTo(r2.getRouteTime());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });
        return root;
    }
    private void eraseFromDb(Route route) {
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference routesRef = database.getReference().child("users").child(mFirebaseAuth.getUid()).child("pastRoutesList");
        Query routeQuery = routesRef.orderByChild("name").equalTo(route.getName());

        routeQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    ds.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    private void saveToFile(String filename, ArrayList<Route> routeList) {
        String serializedRouteList = new Gson().toJson(routeList);
        FileOutputStream fos = null;

        try {
            fos = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(serializedRouteList.getBytes());
//            Toast.makeText(getActivity(), "Saved to file " + getActivity().getFilesDir() + "/" + filename, Toast.LENGTH_LONG).show();
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
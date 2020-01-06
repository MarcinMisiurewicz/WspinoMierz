package com.example.wspinomierz.ui.list;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wspinomierz.R;
import com.example.wspinomierz.Route;
import com.example.wspinomierz.ScaleConverter;

import java.util.List;

public class RouteArrayAdapter extends ArrayAdapter<Route> {

    private static final String TAG = "RouteListAdapter";

    private Context context;
    int resource;

    public RouteArrayAdapter(@NonNull Context context, int resource, @NonNull List<Route> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ScaleConverter scaleConverter = new ScaleConverter();
        String name = getItem(position).getName();
        Integer grade = getItem(position).getGrade();
        Location location = getItem(position).getLocation();
        Integer userGrade = getItem(position).getUserGrade();
        Integer routeTime = getItem(position).getRouteTime();
        Integer pitchNumber = getItem(position).getPitchNumber();

        Route route = new Route(name, grade, location, userGrade, routeTime, pitchNumber);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvName = convertView.findViewById(R.id.textViewName);
        TextView tvGrade = convertView.findViewById(R.id.textViewGrade);
        TextView tvUserGrade = convertView.findViewById(R.id.textViewUserGrade);
        TextView tvRouteTime = convertView.findViewById(R.id.textViewRouteTime);
        TextView tvPitchNumber = convertView.findViewById(R.id.textViewPitchNumber);
        TextView tvLocation = convertView.findViewById(R.id.textViewLocation);

        tvName.setText(name);
        tvGrade.setText(scaleConverter.Int2String("Kurtyki", grade));
        tvUserGrade.setText(scaleConverter.Int2String("Kurtyki", userGrade));
        tvRouteTime.setText(routeTime.toString());
        tvPitchNumber.setText(pitchNumber.toString());
        tvLocation.setText(location.getLatitude() + "," + location.getLongitude());
        return convertView;
    }
}

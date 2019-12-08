package com.example.wspinomierz.ui.timer;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wspinomierz.R;

public class TimerFragment extends Fragment {
    //TODO: Countdown for chrono
    //TODO: upload wyniku timera do listy
    private TimerViewModel timerViewModel;
    private Chronometer chronometer;
    private long pauseOffset;
    private Button startButton, pauseButton, resetButton;

    private boolean running;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        timerViewModel =
                ViewModelProviders.of(this).get(TimerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_timer, container, false);
        final TextView textView = root.findViewById(R.id.text_timer);
        timerViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        chronometer = root.findViewById(R.id.chronometer);
        startButton = root.findViewById(R.id.start_button);
        pauseButton = root.findViewById(R.id.pause_button);
        resetButton = root.findViewById(R.id.reset_button);
        //TODO: nie da się tego zrobić bez listenerów, tylko w xmlu zrobić onClick?
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View root) {
                startChronometer(root);
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View root) {
                pauseChronometer(root);
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View root) {
                resetChronometer(root);
            }
        });
        return root;
    }

    public void startChronometer(View v) {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    public void pauseChronometer(View v) {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void resetChronometer(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }
}
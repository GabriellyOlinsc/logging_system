package com.example.registrodeponto;

import android.os.Build;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;

public class HoraUtils {
    private static Handler handler;

    public static void updateHour(TextView textView) {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                LocalTime currentHour = LocalTime.now();
                int hour = currentHour.getHour();
                int minute = currentHour.getMinute();
                String formattedMinute = minute < 10 ? "0" + minute : String.valueOf(minute);
                String formattedHour = hour + ":" + formattedMinute;
                textView.setText(formattedHour);

                RegistroUtils.updateAvailableFields(hour); // Corrigido o parâmetro para activity

                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }
}

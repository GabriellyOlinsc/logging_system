package com.example.registrodeponto;

import android.os.Handler;
import android.widget.TextView;

import java.time.LocalTime;

public class HoraUtils {
    private static Handler handler;

    public static void atualizarHora(TextView textView, MainActivity activity) {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LocalTime horaAtual = LocalTime.now();
                int hora = horaAtual.getHour();
                int minuto = horaAtual.getMinute();
                String minutoFormatado = minuto < 10 ? "0" + minuto : String.valueOf(minuto);
                String horaFormatada = hora + ":" + minutoFormatado;

                textView.setText(horaFormatada);

                RegistroUtils.atualizarCamposDisponiveis(hora); // Corrigido o parâmetro para activity

                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }
}

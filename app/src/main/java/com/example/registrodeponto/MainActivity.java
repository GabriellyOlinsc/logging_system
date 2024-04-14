package com.example.registrodeponto;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import android.os.Handler;
import android.widget.Toast;


@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
    TextView tv_horas;
    Handler handler;
    int camposDisponiveis = 0;
    LocalTime horaAtual = LocalTime.now();
    int hora = horaAtual.getHour();
    int minuto = horaAtual.getMinute();

    String minutoFormatado = minuto < 10 ? "0" + minuto : String.valueOf(minuto); // Caso o minuto seja menor que 10, coloca o 0 na frente.
    String horaFormatada = hora + ":" + minutoFormatado; // forma a hora com minuto

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = findViewById(R.id.campos_registros);
        linearLayout.removeAllViews();


        Button btn = findViewById(R.id.btn_registrar_ponto);

       EditText matricula = findViewById(R.id.matriculaInput); // pega a matricula que o usuário colocar
        EditText nome = findViewById(R.id.nomeInput); // pega o nome
        EditText lotacao = findViewById(R.id.lotacaoInput); // pega a lotação
        EditText funcao = findViewById(R.id.cargoInput); // pega a função

        // Função que irá ativar quando o usuário clicar no botão
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camposDisponiveis > 0) {
                    TextView textView = new TextView(MainActivity.this);

                    // Vai colocar parâmetros no layout
                    textView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    String info;

                    if (linearLayout.getChildCount() > 0) {
                        info = "Saída: " + horaFormatada + "     ->     " + " Matricula: " + matricula.getText() + "  |  " + " Nome: " + nome.getText();

                    } else {
                        info = "Entrada: " + horaFormatada + "     ->     " + " Matricula: " + matricula.getText() + "  |  " + " Nome: " + nome.getText();
                    }
                    textView.setText(info); // adiciona as informações no textView

                    linearLayout.addView(textView); // Adiciona o textView no layout.
                    camposDisponiveis--;
                } else {
                    // mostra a notificação caso não esteja nas horas
                    Toast.makeText(MainActivity.this, "Não há mais campos disponíveis", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_horas = findViewById(R.id.horas);
        handler = new Handler();
        atualizarHora();
    }


    // Função para atualizar a hora dinamicamente
    private void atualizarHora() {

        LocalTime horaAtual = LocalTime.now();
        int hora = horaAtual.getHour();
        int minuto = horaAtual.getMinute();

        String minutoFormatado = minuto < 10 ? "0" + minuto : String.valueOf(minuto);
        horaFormatada = hora + ":" + minutoFormatado;

        tv_horas.setText(horaFormatada);
        if (hora >= 8 && hora <= 22) {
            if (hora < 12) {
                camposDisponiveis = hora - 8;
            } else if (hora >= 13 && hora < 22) {
                camposDisponiveis = hora - 9;
            } else {
                camposDisponiveis = 8;
            }
        } else {
            camposDisponiveis = 0;
        }
        handler.postDelayed(this::atualizarHora, 1000);
    }

    // Obter a hora atual
    private String obterHoraAtual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return dateFormat.format(new Date());
    }

}

package com.example.registrodeponto;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
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

public class MainActivity extends AppCompatActivity {
    TextView tv_horas;
    private EditText matricula, nome, lotacao, funcao;;
    private Button btn;
    private static Registro historicoRegistro;
    LocalTime horaAtual = LocalTime.now();
    String horaFormatada = horaAtual.getMinute() < 10 ? horaAtual.getHour() + ":0" + horaAtual.getMinute() :
            horaAtual.getHour() + ":" + horaAtual.getMinute();
    int camposDisponiveis = RegistroUtils.atualizarCamposDisponiveis(horaAtual.getHour(), MainActivity.this);
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = findViewById(R.id.campos_registros);
        linearLayout.removeAllViews();

        btn = findViewById(R.id.btn_registrar_ponto);
        btn.setEnabled(false);

        matricula = findViewById(R.id.matriculaInput);
        nome = findViewById(R.id.nomeInput);
        lotacao = findViewById(R.id.lotacaoInput); // pega a lotação
        funcao = findViewById(R.id.cargoInput); // pega a função

        matricula.addTextChangedListener(textWatcher);
        nome.addTextChangedListener(textWatcher);
        lotacao.addTextChangedListener(textWatcher);
        funcao.addTextChangedListener(textWatcher);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(historicoRegistro == null){  //verificando se o registro do usuário já foi instaciado
                    System.out.print("Objeto sendo instanciado");
                    historicoRegistro = new Registro(nome.getText().toString(),matricula.getText().toString(),lotacao.getText().toString(),funcao.getText().toString());
                }
                RegistroUtils.registrarPonto(MainActivity.this, linearLayout, historicoRegistro, horaFormatada, horaAtual.getHour(), camposDisponiveis);
            }
        });


        tv_horas = findViewById(R.id.horas);
        HoraUtils.atualizarHora(tv_horas, this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LocalTime horaAtual = LocalTime.now();
                if (horaAtual.getHour() > 16 && horaAtual.getHour() < 9) {
                    linearLayout.removeAllViews();
                }
                handler.postDelayed(this, 60000); // Verifica a cada minuto
            }
        }, 60000); // Inicia após 1 minuto
    }


    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            // Verificar se todos os campos estão preenchidos
            boolean camposPreenchidos = !matricula.getText().toString().isEmpty() &&
                    !nome.getText().toString().isEmpty() &&
                    !lotacao.getText().toString().isEmpty() &&
                    !funcao.getText().toString().isEmpty();

            // Habilitar ou desabilitar o botão com base no estado dos campos
            btn.setEnabled(camposPreenchidos);
        }
    };
}

package com.example.registrodeponto;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;

import android.os.Handler;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private static final int DELAY_ONE_MINUTE = 20000; // 1 minuto em milissegundos

    private TextView tv_horas;
    private EditText matricula, nome, lotacao, funcao;
    private Button btn;
    private static Registro historicoRegistro;
    private LinearLayout linearLayout;
    private LocalTime horaAtual;
    private String horaFormatada;
    private int camposDisponiveis;

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            boolean camposPreenchidos = !matricula.getText().toString().isEmpty() &&
                    !nome.getText().toString().isEmpty() &&
                    !lotacao.getText().toString().isEmpty() &&
                    !funcao.getText().toString().isEmpty();

            btn.setEnabled(camposPreenchidos);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.campos_registros);
        btn = findViewById(R.id.btn_registrar_ponto);
        matricula = findViewById(R.id.matriculaInput);
        nome = findViewById(R.id.nomeInput);
        lotacao = findViewById(R.id.lotacaoInput);
        funcao = findViewById(R.id.cargoInput);

        // Desabilita o botão até que todos os campos estejam preenchidos
        btn.setEnabled(false);

        matricula.addTextChangedListener(textWatcher);
        nome.addTextChangedListener(textWatcher);
        lotacao.addTextChangedListener(textWatcher);
        funcao.addTextChangedListener(textWatcher);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(historicoRegistro == null){
                    historicoRegistro = new Registro(nome.getText().toString(),
                            matricula.getText().toString(),
                            lotacao.getText().toString(),
                            funcao.getText().toString());
                    Log.d("TAG","ja foi criado");
                }


                RegistroUtils.registrarPonto(MainActivity.this,
                        linearLayout,
                        historicoRegistro,
                        LocalTime.now().getHour(),
                        camposDisponiveis);
            }
        });

        // Inicializa a hora atual e os campos disponíveis no
        horaAtual = LocalTime.now();
        horaFormatada = String.format(Locale.getDefault(), "%02d:%02d", horaAtual.getHour(), horaAtual.getMinute());
        camposDisponiveis = RegistroUtils.updateAvailableFields(horaAtual.getHour());

        // Atualiza o texto da hora na TextView
        tv_horas = findViewById(R.id.horas);
        HoraUtils.updateHour(tv_horas, this);

        // Configura o Handler para limpar o LinearLayout se for entre meia-noite e 9h
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                horaAtual = LocalTime.now();
                if (horaAtual.getHour() > 23 || horaAtual.getHour() < 9) {
                    historicoRegistro.clearRecords();
                    linearLayout.removeAllViews();
                }
                handler.postDelayed(this, DELAY_ONE_MINUTE); // Verifica a cada minuto
            }
        }, DELAY_ONE_MINUTE); // Inicia após 1 minuto
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Salvar o estado dos EditText em um Bundle
        Bundle editTextState = new Bundle();
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View view = linearLayout.getChildAt(i);
            if (view instanceof EditText) {
                String editTextValue = ((EditText) view).getText().toString();
                editTextState.putString("editText_" + i, editTextValue);
            }
        }
        onSaveInstanceState(editTextState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Restaurar o estado dos EditText
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View view = linearLayout.getChildAt(i);
            if (view instanceof EditText) {
                String key = "editText_" + i;
                if (savedInstanceState.containsKey(key)) {
                    String editTextValue = savedInstanceState.getString(key);
                    ((EditText) view).setText(editTextValue);
                }
            }
        }
    }
}


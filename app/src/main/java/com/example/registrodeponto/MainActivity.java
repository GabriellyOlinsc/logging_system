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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalTime;

import android.os.Handler;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private static final int DELAY_ONE_MINUTE = 20000; // 1 minuto em milissegundos

    private EditText registration, name, department, function;
    private Button btn;
    private static Register historyRecords;
    private LinearLayout linearLayout;
    private LocalTime currentHour;
    private int availableFields;

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            boolean camposPreenchidos = !registration.getText().toString().isEmpty() &&
                    !name.getText().toString().isEmpty() &&
                    !department.getText().toString().isEmpty() &&
                    !function.getText().toString().isEmpty();

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
        registration = findViewById(R.id.matriculaInput);
        name = findViewById(R.id.nomeInput);
        department = findViewById(R.id.lotacaoInput);
        function = findViewById(R.id.cargoInput);

        // Desabilita o botão até que todos os campos estejam preenchidos
        btn.setEnabled(false);

        registration.addTextChangedListener(textWatcher);
        name.addTextChangedListener(textWatcher);
        department.addTextChangedListener(textWatcher);
        function.addTextChangedListener(textWatcher);

        btn.setOnClickListener(v -> {
            if(historyRecords == null){
                historyRecords = new Register(name.getText().toString(),
                        registration.getText().toString(),
                        department.getText().toString(),
                        function.getText().toString());
                Log.d("TAG","ja foi criado");
            }


            RegistroUtils.recordWorkHours(MainActivity.this,
                    linearLayout,
                    historyRecords,
                    LocalTime.now().getHour(),
                    availableFields);
        });

        // Inicializa a hora atual e os campos disponíveis no
        currentHour = LocalTime.now();
        availableFields = RegistroUtils.updateAvailableFields(currentHour.getHour());

        // Atualiza o texto da hora na TextView
        TextView tvHour = findViewById(R.id.horas);
        HoraUtils.updateHour(tvHour);

        // Configura o Handler para limpar o LinearLayout se for entre meia-noite e 9h
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentHour = LocalTime.now();
                if (currentHour.getHour() > 23 || currentHour.getHour() < 9) {
                    historyRecords.clearRecords();
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
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
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


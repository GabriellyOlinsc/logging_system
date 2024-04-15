package com.example.registrodeponto;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegistroUtils {

    public static void registrarPonto(Context context, LinearLayout linearLayout,Registro historicoRegistro, String horaFormatada, int horaAtual, int camposDisponiveis) {
        if (camposDisponiveis > 0) {
            int horaInicial = 9; // Começando às 9 horas
            int pontosRegistrados = historicoRegistro.getSize();  //contagem dos pontos que já foram regitrados  5

            int horarioDisponivel = 8 - pontosRegistrados;    //pontos disponíveis  por exemplo: 5
            if (pontosRegistrados < 8) {

                for (int i = 0; i <= horarioDisponivel; i++) {
                    historicoRegistro.adicionarRegistro(horaAtual + i);

                   /* if (horaInicial + i <= 23) { // Garantindo que não ultrapasse as 17 horas
                        if (horaInicial + i == 13){
                            continue;
                        }*/
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    String info = (horaInicial + pontosRegistrados + i) + ":00h" + "      ->     " + " Matricula: " + historicoRegistro.getMatricula() + "  |  " + " Nome: " + historicoRegistro.getNome();
                    textView.setText(info);
                    linearLayout.addView(textView);
                    //  }

              }
            } else {
                Toast.makeText(context, "Não há mais campos disponíveis", Toast.LENGTH_SHORT).show();
            }
       }
    }

    public static int atualizarCamposDisponiveis(int hora, MainActivity activity) { // Alterado o parâmetro para MainActivity
        int camposDisponiveis;
        if (hora >= 8 && hora <= 17) {
            if (hora < 12) {
                camposDisponiveis = hora - 8;
            } else if (hora >= 13 && hora < 17) {
                camposDisponiveis = hora - 9;
            } else {
                camposDisponiveis = 8;
            }
        } else {
            camposDisponiveis = 0;
        }
        return camposDisponiveis; // Atribuindo o valor diretamente à activity
    }


}

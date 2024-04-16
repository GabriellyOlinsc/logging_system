package com.example.registrodeponto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class RegistroUtils {

    @SuppressLint("ResourceAsColor")
    public static void registrarPonto(Context context, LinearLayout linearLayout, Registro historicoRegistro, String horaFormatada, int horaAtual, int camposDisponiveis) {
        if (camposDisponiveis > 0) {
            int horaInicial = 9; // Começando às 9 horas
            int pontosRegistrados = historicoRegistro.getSize();
            int qtdeRegistros = calcularQuantidadeRegistros(pontosRegistrados,horaAtual);  //contagem dos pontos que já foram regitrados  5

            if(pontosRegistrados==4){
                Log.i("HORA", "4 PONTOS");
            }
            if (pontosRegistrados < 8) {
                for (int i = 0; i < qtdeRegistros; i++) {
                    historicoRegistro.adicionarRegistro(horaAtual + i);
                    EditText editText = new EditText(context);
                    editText.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    if (horaInicial + pontosRegistrados + i - 1 == 12) {
                        continue;
                    }
                    if (i > 8) {
                        break;
                    }
                    String info = (horaInicial + pontosRegistrados + i - 1) + ":00h - " +(horaInicial + pontosRegistrados + i) + ":00h" + "    " + " Matricula: " + historicoRegistro.getMatricula() + "  |  " + " Nome: " + historicoRegistro.getNome();
                    editText.setText(info);
                    editText.setTextColor(R.color.gray);
                    editText.setTextSize(15f);
                    editText.setPadding(2,5,15,2);
                    linearLayout.addView(editText);

                    Toast.makeText(context, "Campos Inseridos com sucesso.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d("HORA","campos - 0" );
                Toast.makeText(context, "Não há mais campos disponíveis", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static int atualizarCamposDisponiveis(int hora) { // Alterado o parâmetro para MainActivity
        int camposDisponiveis;
        if (hora >= 8 && hora <= 24) {
            if (hora < 12) {
                camposDisponiveis = hora - 8;
            } else if (hora >= 13 && hora < 24) {
                camposDisponiveis = hora - 9;
            } else {
                camposDisponiveis = 8;
            }
        } else {
            camposDisponiveis = 0;
        }
        return camposDisponiveis; // Atribuindo o valor diretamente à activity
    }

    public static int calcularQuantidadeRegistros(int camposPreenchidos, int horaRegistro) {
        int qtdeRegistro;
        if(horaRegistro>17){
            qtdeRegistro = 9 - camposPreenchidos;
        }else{
            int camposMax = horaRegistro - 8;
            if (horaRegistro > 13) {
                camposMax--;
            }
            qtdeRegistro = camposMax - camposPreenchidos;
        }
        return qtdeRegistro > 0 ? qtdeRegistro : 0;
    }
}

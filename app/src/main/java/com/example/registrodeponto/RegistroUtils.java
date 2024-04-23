package com.example.registrodeponto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class RegistroUtils {

    @SuppressLint("ResourceAsColor")
    public static void recordWorkHours(Context context, LinearLayout linearLayout, Registro recordHistory, int currentHour, int availableFields) {
        if (availableFields > 0) {
            int initialHour = 9; // Começando às 9 horas
            int recordedWorkedHours = recordHistory.getSize();
            int recordsAmount  = calculateRecordAmount(recordedWorkedHours,currentHour);  //contagem dos pontos que já foram regitrados  5

            if(recordedWorkedHours==4){
                Log.i("HORA", "4 PONTOS");
            }
            if (recordedWorkedHours < 8) {
                for (int i = 0; i < recordsAmount ; i++) {
                    recordHistory.addWorkedHourRecord(currentHour + i);

                    if (initialHour + recordedWorkedHours + i - 1 == 12) {
                        continue;
                    }
                    if (i > 8) {
                        break;
                    }
                    String info = (initialHour + recordedWorkedHours + i - 1) + ":00h - " +(initialHour + recordedWorkedHours + i) + ":00h" + "    " + " Matricula: " + recordHistory.getEnrollment() + "  |  " + " Nome: " + recordHistory.getName();
                    addEditText(context,linearLayout,info);

                    Toast.makeText(context, "Campos Inseridos com sucesso.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d("HORA","campos - 0" );
                Toast.makeText(context, "Não há mais campos disponíveis", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private static void addEditText(Context context, LinearLayout linearLayout, String text){
        EditText editText = new EditText(context);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        editText.setText(text);
        editText.setTextColor(R.color.gray);
        editText.setTextSize(15f);
        editText.setPadding(2,5,15,2);
        editText.setEnabled(false);
        linearLayout.addView(editText);
    }

    public static int updateAvailableFields(int hour) { // Changed parameter to MainActivity
        int availableFields;
        if (hour >= 8 && hour <= 24) {
            if (hour < 12) {
                availableFields = hour - 8;
            } else if (hour >= 13 && hour < 24) {
                availableFields = hour - 9;
            } else {
                availableFields = 8;
            }
        } else {
            availableFields = 0;
        }
        return availableFields; // Atribuindo o valor diretamente à activity
    }

    public static int calculateRecordAmount(int filledFields, int recordHour) {
        int recordCount;
        if (recordHour > 17) {
            recordCount = 9 - filledFields;
        } else {
            int maxFields = recordHour - 8;
            if (recordHour > 13) {
                maxFields--;
            }
            recordCount = maxFields - filledFields;
        }
        return recordCount > 0 ? recordCount : 0;
    }
}

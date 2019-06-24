package com.example.connect4.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.connect4.R;

public class Configuracio extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText alias;
    CheckBox temps;
    Button btnStart;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracio);

        radioGroup = findViewById(R.id.radioGroup);
        alias = findViewById(R.id.alias);
        temps = findViewById(R.id.checkBox);
        btnStart = findViewById(R.id.btnComençar);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mida;
                if (count == 0){
                    mida = "7";
                }else{
                    mida = radioButton.getText().toString();
                }

                Intent in = new Intent(Configuracio.this, Partida.class);
                in.putExtra("alias", alias.getText().toString());
                in.putExtra("graella", mida);
                in.putExtra("temps", temps.isChecked());
                startActivity(in);
                finish();
            }
        });

    }

    public void checkButton(View v){
        count = 1;
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(this, "Mida graella seleccionada: "+ radioButton.getText(), Toast.LENGTH_SHORT).show();
    }
}

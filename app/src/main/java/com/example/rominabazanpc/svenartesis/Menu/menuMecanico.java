package com.example.rominabazanpc.svenartesis.Menu;
/**
 * Created by RominaBazanPc on 19/03/2020.
 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.rominabazanpc.svenartesis.Diaria.diaria;
import com.example.rominabazanpc.svenartesis.HojaServicio.hojaServicio;
import com.example.rominabazanpc.svenartesis.R;

public class menuMecanico extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_mecanico);
    }
    public void onClick(View view){
        Intent miIntent = null;

        switch(view.getId()){


            case  R.id.diaria:
                miIntent = new Intent(menuMecanico.this, diaria.class);
                startActivity(miIntent);
                break;

            case  R.id.hojaServicio:
                miIntent = new Intent(menuMecanico.this, hojaServicio.class);
                startActivity(miIntent);
                break;

        }

    }
}

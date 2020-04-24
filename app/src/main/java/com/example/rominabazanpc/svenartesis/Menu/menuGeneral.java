package com.example.rominabazanpc.svenartesis.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.rominabazanpc.svenartesis.Clientes.listadoPersonas;
import com.example.rominabazanpc.svenartesis.Diaria.diaria;
import com.example.rominabazanpc.svenartesis.Facturas.listadoFacturas;
import com.example.rominabazanpc.svenartesis.HojaServicio.hojaServicio;
import com.example.rominabazanpc.svenartesis.R;
import com.example.rominabazanpc.svenartesis.Stock.listadoStock;
import com.example.rominabazanpc.svenartesis.Turnos.busquedaTurno;
import com.example.rominabazanpc.svenartesis.Turnos.listadoTurnos;

public class menuGeneral extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_general);
    }

    public void onClick(View view){
        Intent miIntent = null;

        switch(view.getId()){
            case  R.id.diaria:
                miIntent = new Intent(menuGeneral.this, diaria.class);
                startActivity(miIntent);
                break;

            case  R.id.hojaServicio:
                miIntent = new Intent(menuGeneral.this, hojaServicio.class);
                startActivity(miIntent);
                break;

            case  R.id.busquedaTurno:
                miIntent = new Intent(menuGeneral.this, busquedaTurno.class);
                startActivity(miIntent);
                break;
            case  R.id.listadoPersonas:
                miIntent = new Intent(menuGeneral.this, listadoPersonas.class);
                startActivity(miIntent);
                break;
            case  R.id.listadoStock:
                miIntent = new Intent(menuGeneral.this, listadoStock.class);
                startActivity(miIntent);
                break;

            case  R.id.listadoTurnos:
                miIntent = new Intent(menuGeneral.this, listadoTurnos.class);
                startActivity(miIntent);
                break;

            case  R.id.listadoFacturas:
                miIntent = new Intent(menuGeneral.this, listadoFacturas.class);
                startActivity(miIntent);
                break;

        }

    }
}
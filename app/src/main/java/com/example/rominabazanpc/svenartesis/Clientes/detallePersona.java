package com.example.rominabazanpc.svenartesis.Clientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.rominabazanpc.svenartesis.Diaria.diaria;
import com.example.rominabazanpc.svenartesis.Menu.menuGeneral;
import com.example.rominabazanpc.svenartesis.R;

public class detallePersona extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView idinfo,nombreEmpresa2,cuitEmpresa2,direccionEmpresa2,telefonoEmpresa2;
        TextView ApellidoNombre,tipoDocumento,numeroDocumento,telefono,correo,direccion,cuil;
        TextView nombreEmpresa,cuitEmpresa,direccionEmpresa,telefonoEmpresa;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_persona);

        idinfo = findViewById(R.id.idinfo);
        nombreEmpresa2 = findViewById(R.id.nombreEmpresa2);
        cuitEmpresa2 = findViewById(R.id.cuitEmpresa2);
        direccionEmpresa2 = findViewById(R.id.direccionEmpresa2);
        telefonoEmpresa2 = findViewById(R.id.telefonoEmpresa2);


        ApellidoNombre = findViewById(R.id.ApellidoNombre);
        tipoDocumento = findViewById(R.id.tipoDocumento);
        numeroDocumento = findViewById(R.id.numeroDocumento);
        telefono = findViewById(R.id.telefono);
        correo = findViewById(R.id.correo);
        direccion = findViewById(R.id.direccion);
        cuil = findViewById(R.id.cuil);
        nombreEmpresa = findViewById(R.id.nombreEmpresa);
        cuitEmpresa = findViewById(R.id.cuitEmpresa);
        direccionEmpresa = findViewById(R.id.direccionEmpresa);
        telefonoEmpresa = findViewById(R.id.telefonoEmpresa);

        Log.d("-->","ESTOY EN DETALLE PERSONA");

        //obtener el dato
        Bundle miBundle = this.getIntent().getExtras();
        //Validacion
        if(miBundle!=null){
            String informacion= miBundle.getString("informacion");
            String[] lista = informacion.split("@!@");

            ApellidoNombre.setText(lista[1]);
            String tipo = lista[2];
            if(tipo.equals("D")){ tipoDocumento.setText("D.N.I");}
            if(tipo.equals("P")){ tipoDocumento.setText("Pasaporte");}
            if(tipo.equals("L")){ tipoDocumento.setText("Libreta Civica");}
            numeroDocumento.setText(lista[3]);
            telefono.setText(lista[4]);
            correo.setText(lista[5]);
            direccion.setText(lista[6]);
            cuil.setText(lista[7]);


            String bandera = lista[8];
            if ( bandera.contains("null")){
                idinfo.setVisibility( View.INVISIBLE );
                nombreEmpresa2.setVisibility(View.INVISIBLE);
                cuitEmpresa2.setVisibility(View.INVISIBLE);
                direccionEmpresa2.setVisibility(View.INVISIBLE);
                telefonoEmpresa2.setVisibility(View.INVISIBLE);

                nombreEmpresa.setVisibility(View.INVISIBLE);
                cuitEmpresa.setVisibility(View.INVISIBLE);
                direccionEmpresa.setVisibility(View.INVISIBLE);
                telefonoEmpresa.setVisibility(View.INVISIBLE);


            }else {//significa que tiene empresa
                nombreEmpresa.setText(lista[8]);
                cuitEmpresa.setText(lista[9]);
                direccionEmpresa.setText(lista[10]);
                telefonoEmpresa.setText(lista[11]);
            }

        }
    }
    public void back(View view) {
        Intent miIntent= new Intent(detallePersona.this, menuGeneral.class);
        startActivity(miIntent);
    }
}

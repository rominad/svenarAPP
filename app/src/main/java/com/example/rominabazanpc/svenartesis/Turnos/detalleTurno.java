package com.example.rominabazanpc.svenartesis.Turnos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.rominabazanpc.svenartesis.Diaria.diaria;
import com.example.rominabazanpc.svenartesis.Menu.menuGeneral;
import com.example.rominabazanpc.svenartesis.R;

public class detalleTurno extends AppCompatActivity {
    TextView btninfo1,NombreEmpresa2,TelefonoEmpresa2;
    TextView FechaHora,Detalle,ApellidoNombre,Telefono,TipoDocumento,NumeroDocumento,NombreEmpresa,TelefonoEmpresa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_turno);


        FechaHora = findViewById(R.id.FechaHora);
        Detalle = findViewById(R.id.Detalle);
        ApellidoNombre = findViewById(R.id.ApellidoNombre);
        Telefono = findViewById(R.id.Telefono);
        TipoDocumento = findViewById(R.id.TipoDocumento);
        NumeroDocumento = findViewById(R.id.NumeroDocumento);
        NombreEmpresa = findViewById(R.id.NombreEmpresa);
        TelefonoEmpresa = findViewById(R.id.TelefonoEmpresa);

        btninfo1 = findViewById(R.id.btninfo1);
        NombreEmpresa2 = findViewById(R.id.NombreEmpresa2);
        TelefonoEmpresa2 = findViewById(R.id.TelefonoEmpresa2);

        Log.d("-->","ESTOY EN DETALLE TURNOS");

        //obtener el dato
        Bundle miBundle = this.getIntent().getExtras();
        //Validacion
        if(miBundle!=null){
            String informacion= miBundle.getString("informacion");
            String[] lista = informacion.split("@!@");

            FechaHora.setText(lista[1]);
            Detalle.setText(lista[2]);
            ApellidoNombre.setText(lista[4]);
            Telefono.setText(lista[5]);
            String tipo = lista[6];
            if(tipo.equals("D")){ TipoDocumento.setText("D.N.I");}
            if(tipo.equals("P")){ TipoDocumento.setText("Pasaporte");}
            if(tipo.equals("L")){ TipoDocumento.setText("Libreta Civica");}
            NumeroDocumento.setText(lista[7]);

            String bandera = lista[8];
            if ( bandera.contains("null")){
                btninfo1.setVisibility( View.INVISIBLE );
                NombreEmpresa2.setVisibility(View.INVISIBLE);
                TelefonoEmpresa2.setVisibility(View.INVISIBLE);
            }else{
                NombreEmpresa.setText(lista[8]);
                TelefonoEmpresa.setText(lista[9]);
            }
        }
    }
    public void back(View view) {
        Intent miIntent= new Intent(detalleTurno.this, menuGeneral.class);
        startActivity(miIntent);
    }
}

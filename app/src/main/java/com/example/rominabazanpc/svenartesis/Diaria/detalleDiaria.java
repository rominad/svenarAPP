package com.example.rominabazanpc.svenartesis.Diaria;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rominabazanpc.svenartesis.Menu.menuGeneral;
import com.example.rominabazanpc.svenartesis.R;
import static com.example.rominabazanpc.svenartesis.Rutas.Utilidades.CARGAR_DIARIA_REQUEST_URL;
import static com.example.rominabazanpc.svenartesis.Rutas.Utilidades.USERID;

public class detalleDiaria extends AppCompatActivity {
    TextView Dominio,Color,Marca,Modelo,Anio,Chasis,Motor,ApellidoNombre,NumeroDocumento,NombreEmpresa;
    EditText diaria;
    String IdVehiculo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_diaria);

        Dominio = findViewById(R.id.Dominio);
        Color= findViewById(R.id.Color);
        Marca= findViewById(R.id.Marca);
        Modelo= findViewById(R.id.Modelo);
        Anio= findViewById(R.id.Año);
        Chasis= findViewById(R.id.Chasis);
        Motor= findViewById(R.id.Motor);
        ApellidoNombre= findViewById(R.id.ApellidoNombre);
        NumeroDocumento= findViewById(R.id.NumeroDocumento);
        NombreEmpresa= findViewById(R.id.NombreEmpresa);
        diaria= findViewById(R.id.diaria);

        //obtener el dato: IdVehiculo,Dominio,Color,Marca,Modelo,Año,Chasis,Motor,ApellidoNombre,TipoDocumento,NumeroDocumento,NombreEmpresa
        Bundle miBundle = this.getIntent().getExtras();
        //Validacion
        if (miBundle != null) {
            String informacion = miBundle.getString("informacion");
            String[] lista = informacion.split("@!@");
            IdVehiculo= lista[0];
            Dominio.setText(lista[1]);
            Color.setText(lista[2]);
            Marca.setText(lista[3]);
            Modelo.setText(lista[4]);
            Anio.setText(lista[5]);
            Chasis.setText(lista[6]);
            Motor.setText(lista[7]);
            ApellidoNombre.setText(lista[8]);
            //TipoDocumento.setText(lista[9]);
            NumeroDocumento.setText(lista[10]);
            NombreEmpresa.setText(lista[11]);


        }
    }
    private void cargarALaBD(String URL) {
        Log.d("==>"," DETALLE DIARIA / URL: "+URL);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length()>0){
                    //UNA VEZ QUE CARGA VAMOS AL OTRO ACTIVITY.
                    Intent miIntent;
                    miIntent= new Intent(detalleDiaria.this,diaria.class);
                    startActivity(miIntent);
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btnCargar) {
            Toast.makeText(this,"CARGANDO..",Toast.LENGTH_SHORT).show();
            //IdVehiculo,IdUsuario,Descripcion
            String alPhp = IdVehiculo+"@!@"+USERID+"@!@"+diaria.getText();
            Log.d("Tag:","==> ESTOY EN onClick! info al php: "+alPhp);
            diaria.setText("");
            cargarALaBD(CARGAR_DIARIA_REQUEST_URL+ "?termino=" + alPhp);
        }
    }//BOTON FINALIZAR CARGA
    public void back(View view) {
        Intent miIntent= new Intent(detalleDiaria.this, menuGeneral.class);
        startActivity(miIntent);
    }
}

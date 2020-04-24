package com.example.rominabazanpc.svenartesis.Diaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rominabazanpc.svenartesis.Menu.menuGeneral;
import com.example.rominabazanpc.svenartesis.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static com.example.rominabazanpc.svenartesis.Rutas.Utilidades.BUSCAR_VEHICULOS_DIARIA_REQUEST_URL;
import static com.example.rominabazanpc.svenartesis.Rutas.Utilidades.LISTAR_TODO_VEHICULOS_DIARIA_REQUEST_URL;
import static com.example.rominabazanpc.svenartesis.Rutas.Utilidades.LISTAR_VEHICULOS_REQUEST_URL;

public class diaria extends AppCompatActivity {
    EditText str_buscar;
    ListView listaResultado;
    ArrayList<String> listaInformacion = new ArrayList<>();
    ArrayList<String> listaInformacionCompleta = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diaria);
        Log.d("==>"," ESTOY EN DIARIA!");
        listaResultado = findViewById(R.id.lvLista);
        str_buscar = findViewById(R.id.str_buscar);
        llamamosAlPHP(LISTAR_TODO_VEHICULOS_DIARIA_REQUEST_URL);
    }
    private void llamamosAlPHP(String URL) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length()>0){
                    try {
                        JSONArray jsonA = new JSONArray(response);
                        cargoListaFinal(jsonA);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
        switch(view.getId()) {
            case R.id.btnBuscar:
                //Log.d("==>"," ESTOY EN onClick: ");
                consultarLista(BUSCAR_VEHICULOS_DIARIA_REQUEST_URL+"?termino="+str_buscar.getText());
                break;
        }
    }

    private void consultarLista(String URL) {
        Log.d("==>"," DIARIA / URL: "+URL);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.length()>0){
                    try {
                        //Log.d("==>"," ESTOY EN TRY DEL consultarLista! response: "+response);
                        JSONArray jsonA = new JSONArray(response);
                        //Log.d("==>"," ESTOY EN TRY jsonA! jsonA: "+jsonA);
                        cargoListaFinal(jsonA);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }

    private void cargoListaFinal(JSONArray listaRecibida) {
        //Log.d("==>"," ESTOY EN cargoListaFinal: ");
        listaInformacion = new ArrayList<String>();
        listaInformacionCompleta = new ArrayList<String>();
        int contador = 0;
        for (int i = 0; i < listaRecibida.length(); i += 1) {
            try {
                if (contador == 11) {
                    //IdVehiculo,Dominio,Color,Marca,Modelo,Año,Chasis,Motor, ApellidoNombre,TipoDocumento,NumeroDocumento, NombreEmpresa
                    listaInformacion.add(listaRecibida.getString(i-10) + " - " + listaRecibida.getString(i-9) + " - " +
                            listaRecibida.getString(i - 8) );
                    listaInformacionCompleta.add(listaRecibida.getString(i-11) + "@!@" + listaRecibida.getString(i-10) + "@!@"+
                            listaRecibida.getString(i - 9) + "@!@" + listaRecibida.getString(i-8)+ "@!@" +
                            listaRecibida.getString(i-7)+ "@!@" + listaRecibida.getString(i-6)+ "@!@" +
                            listaRecibida.getString(i-5)+ "@!@" + listaRecibida.getString(i-4)+ "@!@" +
                            listaRecibida.getString(i-3)+ "@!@" + listaRecibida.getString(i-2)+ "@!@" +
                            listaRecibida.getString(i-1)+ "@!@" + listaRecibida.getString(i));
                    contador = -1;
                }
                contador++;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
        listaResultado.setAdapter(adaptador);
        listaResultado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String informacion =listaInformacionCompleta.get(position)+"\n";
                //Toast.makeText(getApplicationContext(),informacion,Toast.LENGTH_LONG).show();
                //enviamos al otro activity:
                Intent miIntent= new Intent(diaria.this,detalleDiaria.class);
                Bundle miBundle= new Bundle();
                miBundle.putString("informacion",informacion);
                miIntent.putExtras(miBundle);
                startActivity(miIntent);
            }
        });
    }

    public void back(View view) {
        Intent miIntent= new Intent(diaria.this, menuGeneral.class);
        startActivity(miIntent);
    }
}



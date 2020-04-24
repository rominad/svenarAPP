package com.example.rominabazanpc.svenartesis.HojaServicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rominabazanpc.svenartesis.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Hashtable;

import static com.example.rominabazanpc.svenartesis.Rutas.Utilidades.CARGAR_ARTICULOS_HOJASERVICIO_REQUEST_URL;
import static com.example.rominabazanpc.svenartesis.Rutas.Utilidades.USERID;

public class tablaHojaServicio extends AppCompatActivity {
    ListView listviewlistaFinal;
    ArrayList<String> DATOSCOMPLETOS= new ArrayList<String>();
    ArrayList<String> MOSTRARDATOS = new ArrayList<>();
    ArrayList<String> selectedItems=new ArrayList<String>();
    String IdTurno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla_hoja_servicio);
        listviewlistaFinal= findViewById(R.id.lvLista);
        Log.d("===*==*==*==> ","ESTOY EN TABLA DE HOJA DE SERVICIO!");
        //recuperamos info del otro activity.
        Bundle miBundle = this.getIntent().getExtras();
        int contador = 0;
        if(miBundle!=null) {
            IdTurno = miBundle.getString("IdTurno");
            DATOSCOMPLETOS = miBundle.getStringArrayList("DATOSCOMPLETOS");
            Log.d("Tag:","==> DATOSCOMPLETOS PRIMER INGRESO:  "+DATOSCOMPLETOS);
            MOSTRARDATOS = miBundle.getStringArrayList("MOSTRARDATOS");
            //idTurno-UserId-(Idproducto-IdMarcaProducto-cantidad-detalle)..
        }
        cargamosListaFinal();
    }

    private void cargamosListaFinal() {
        ArrayAdapter<String> aa=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,MOSTRARDATOS);
        listviewlistaFinal.setAdapter(aa);
        listviewlistaFinal.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DATOSCOMPLETOS.remove(position);
                MOSTRARDATOS.remove(position);
                Log.d("==> ","REMOVE EN POSICION: "+position+" / la lista DATOSCOMPLETOS queda asi: "+ DATOSCOMPLETOS);
                cargamosListaFinal();
            }

        });
    }
    private void cargarALaBD(String URL) {
        Log.d("==>"," TABLA HOJA SERVICIO / URL: "+URL);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("==>"," TABLA HOJA SERVICIO / onresponse: "+response);
                if (response.length()>0){
                    //UNA VEZ QUE CARGA VAMOS AL OTRO ACTIVITY.
                    Intent miIntent;
                    miIntent= new Intent(tablaHojaServicio.this,hojaServicio.class);
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
    public void onClick(View view) { //BOTON FINALIZAR CARGA
        switch (view.getId()) {
            case R.id.btnRegresar:
                Intent miIntent = new Intent(tablaHojaServicio.this, detalleHojaServicio.class);
                Bundle miBundle = new Bundle();
                miBundle.putString("desde", "tabla");
                miBundle.putString("IdTurno", IdTurno);
                miBundle.putStringArrayList("MOSTRARDATOS", MOSTRARDATOS);
                miBundle.putStringArrayList("DATOSCOMPLETOS", DATOSCOMPLETOS);
                miIntent.putExtras(miBundle);
                startActivity(miIntent);
                break;
            case R.id.btnEnd:
                String alPhp = IdTurno + "@!@" + USERID + "@!@";
                //idTurno-UserId-(Idproducto-IdMarcaProducto-cantidad-detalle)..
                for (int i = 0; i < DATOSCOMPLETOS.size(); i += 1) {
                    String contenido = DATOSCOMPLETOS.get(i);
                    if (contenido != null) {
                        alPhp += contenido + "//";
                    }
                }
                Toast.makeText(this, "CARGANDO..", Toast.LENGTH_SHORT).show();
                //idTurno-UserId-Idproducto-(cantidad+detalle)-Idproducto-(cantidad+detalle)-..
                Log.d("Tag:", "==> ESTOY EN onClick! info al php: " + alPhp);
                cargarALaBD(CARGAR_ARTICULOS_HOJASERVICIO_REQUEST_URL + "?termino=" + alPhp);
                break;
        }
    }
}

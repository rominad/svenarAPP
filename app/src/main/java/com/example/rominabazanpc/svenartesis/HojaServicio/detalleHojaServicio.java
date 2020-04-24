package com.example.rominabazanpc.svenartesis.HojaServicio;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
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
import java.util.Hashtable;
import static com.example.rominabazanpc.svenartesis.Rutas.Utilidades.CARGAR_ARTICULOS_HOJASERVICIO_REQUEST_URL;
import static com.example.rominabazanpc.svenartesis.Rutas.Utilidades.LISTAR_PRODUCTOS_REQUEST_URL;
import static com.example.rominabazanpc.svenartesis.Rutas.Utilidades.USERID;

public class detalleHojaServicio extends AppCompatActivity {
    EditText editCantidad;
    ListView listviewlistaCompletaProductos;
    ArrayList<String> MOSTRARDATOS = new ArrayList<>();
    ArrayList<String> DATOSCOMPLETOS = new ArrayList<>();
    ArrayList<String> listaFinal = new ArrayList<>();
    ArrayList<String> listaFinalID = new ArrayList<>();
    String IdTurno,IdUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_hoja_servicio);
        Log.d("===*==*==*==> ", "<===*==*==*==");
        Log.d("===*==*==*==> ","ESTOY EN DETALLE HS!!!");
        editCantidad= findViewById(R.id.editCantidad);
        listviewlistaCompletaProductos= findViewById(R.id.listaCompletaProductos);

        //recuperamos info del otro activity.
        Bundle miBundle = this.getIntent().getExtras();
        int contador = 0;
        if(miBundle!=null) {
            String desde = miBundle.getString("desde");
            if (desde == null) { //si viene nulo viene desde listado.
                IdTurno = miBundle.getString("IdTurno");
                DATOSCOMPLETOS = miBundle.getStringArrayList("DATOSCOMPLETOS");
                MOSTRARDATOS = miBundle.getStringArrayList("MOSTRARDATOS");
                Log.d("Tag:","==> DATOSCOMPLETOS PRIMER INGRESO:  "+DATOSCOMPLETOS);
                Log.d("Tag:","==> MOSTRARDATOS PRIMER INGRESO:  "+MOSTRARDATOS);
                ////idTurno  @!@  UserId - Idproducto-cantidad-detalle

            } else {//si trae algo viene desde la tabla.
                IdTurno = miBundle.getString("IdTurno");
                DATOSCOMPLETOS = miBundle.getStringArrayList("DATOSCOMPLETOS");
                Log.d("Tag:","==> DATOSCOMPLETOS PRIMER INGRESO:  "+DATOSCOMPLETOS);
                MOSTRARDATOS = miBundle.getStringArrayList("MOSTRARDATOS");
            }
        }
        listviewlistaCompletaProductos.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        llamamosAlPHP(LISTAR_PRODUCTOS_REQUEST_URL );
    }

    private void llamamosAlPHP(String URL) {
        Log.d("Tag:","==> ESTOY EN llamamosAlPHP! URL: "+URL);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length()>0){
                    try {
                        //Log.d("Tag:","==> ESTOY EN EL TRY!");
                        JSONArray jsonA = new JSONArray(response);
                        cargamosListaProductos(jsonA);
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
        //Log.d("Tag:","==> NO ENTRE AL TRY!");
        queue.add(stringRequest);
    }

    private void cargamosListaProductos(JSONArray listaRecibida) {
        listaFinal = new ArrayList<String>();
        listaFinalID = new ArrayList<String>();
        int contador = 0;
        for (int i = 0; i < listaRecibida.length(); i += 1) {
            try {
                if (contador == 2) {
                    // // -- IdProductoServicio,IdMarcaProducto, Descripcion
                    listaFinal.add(listaRecibida.getString(i));
                    listaFinalID.add(listaRecibida.getString(i-2)+"//"+listaRecibida.getString(i-1));
                    contador = -1;
                }
                contador++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> aa=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listaFinal);
        listviewlistaCompletaProductos.setAdapter(aa);
        listviewlistaCompletaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fila = editCantidad.getText()+"//"+listaFinal.get(position);

                DATOSCOMPLETOS.add(listaFinalID.get(position)+"//"+fila);//IdProducto-IdMarca-cantidad-descripcion
                MOSTRARDATOS.add(editCantidad.getText()+"-"+listaFinal.get(position));//Icantidad-descripcion
                Log.d("===> ","MOSTRARDATOS = "+MOSTRARDATOS);
                Log.d("===> ","DATOSCOMPLETOS = "+DATOSCOMPLETOS);
                editCantidad.setText("");
            }

        });
    }



    private void cargarALaBD(String URL) {
       // Log.d("========", "========");
       // Log.d("===*==*==*==> ","ESTOY EN cargarProductosALaBD! URL: "+URL);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length()>0){
                    //UNA VEZ QUE CARGA VAMOS AL OTRO ACTIVITY.
                    Intent miIntent;
                    miIntent= new Intent(detalleHojaServicio.this,hojaServicio.class);
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
        switch(view.getId()) {
            case R.id.btnTabla:
                Intent miIntent = new Intent(detalleHojaServicio.this, tablaHojaServicio.class);
                Bundle miBundle= new Bundle();
                miBundle.putString("IdTurno",IdTurno);
                miBundle.putStringArrayList("MOSTRARDATOS",MOSTRARDATOS);
                miBundle.putStringArrayList("DATOSCOMPLETOS",DATOSCOMPLETOS);
                miIntent.putExtras(miBundle);
                startActivity(miIntent);
                break;
            case R.id.btnEnd:
                String alPhp = IdTurno+"@!@"+USERID+"@!@";
                //idTurno-UserId-(Idproducto-IdMarcaProducto-cantidad-detalle)..
                for (int i = 0; i < DATOSCOMPLETOS.size(); i += 1) {
                    String contenido = DATOSCOMPLETOS.get(i);
                    if (contenido != null){
                        alPhp += contenido+"//";
                    }
                }
                Toast.makeText(this,"CARGANDO..",Toast.LENGTH_SHORT).show();
                //idTurno-UserId-Idproducto-(cantidad+detalle)-Idproducto-(cantidad+detalle)-..
                Log.d("Tag:","==> ESTOY EN onClick! info al php: "+alPhp);
                cargarALaBD(CARGAR_ARTICULOS_HOJASERVICIO_REQUEST_URL+ "?termino=" + alPhp);
                break;
        }
    }//BOTON FINALIZAR CARGA

    public void back(View view) {
        Intent miIntent= new Intent(detalleHojaServicio.this, menuGeneral.class);
        startActivity(miIntent);
    }
}
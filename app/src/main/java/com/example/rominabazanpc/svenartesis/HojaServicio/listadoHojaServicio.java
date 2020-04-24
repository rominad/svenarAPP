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
import com.example.rominabazanpc.svenartesis.Menu.menuGeneral;
import com.example.rominabazanpc.svenartesis.R;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import static com.example.rominabazanpc.svenartesis.Rutas.Utilidades.CARGAR_ARTICULOS_HOJASERVICIO_REQUEST_URL;
import static com.example.rominabazanpc.svenartesis.Rutas.Utilidades.LISTAR_ARTICULOS_HOJASERVICIO_REQUEST_URL;
import static com.example.rominabazanpc.svenartesis.Rutas.Utilidades.USERID;

public class listadoHojaServicio extends AppCompatActivity {
    TextView fecha,dominio,color,marca,modelo,anio,chasis,motor,apellido,tipo,numero,empresa;
    ListView listaResultado;
    ArrayList<String> selectedItems;
    ArrayList<String> MOSTRARDATOS = new ArrayList<>();
    ArrayList<String> LISTADEIDPRODUCOTOS = new ArrayList<>();
    ArrayList<String> DATOSCOMPLETOS = new ArrayList<>();
    ArrayList<String> ListaFinal = new ArrayList<>();
    String IdTurno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_hoja_servicio);
        ///Log.d("===*==*==*==> ", "<===*==*==*==");
        //Log.d("Tag:","==> ESTOY EN LISTADO HS ");

        fecha= findViewById(R.id.fecha);
        dominio= findViewById(R.id.dominio);
        color= findViewById(R.id.color);
        marca= findViewById(R.id.marca);
        modelo= findViewById(R.id.modelo);
        anio= findViewById(R.id.anio);
        chasis= findViewById(R.id.chasis);
        motor= findViewById(R.id.motor);
        apellido= findViewById(R.id.apellido);
        tipo= findViewById(R.id.tipo);
        numero= findViewById(R.id.numero);
        empresa= findViewById(R.id.empresa);

        selectedItems=new ArrayList<String>();
        listaResultado= findViewById(R.id.checkable_list);
        //obtener el id_vehiculo del activity presupuesto
        Bundle miBundle = this.getIntent().getExtras();
        if(miBundle!=null) {
            String informacion = miBundle.getString("informacion");
            String[] lista = informacion.split("@!@");
            IdTurno = lista[0];
            fecha.setText(lista[1]);
            dominio.setText(lista[2]);
            color.setText(lista[3]);
            marca.setText(lista[4]);
            modelo.setText(lista[5]);
            anio.setText(lista[6]);
            chasis.setText(lista[7]);
            motor.setText(lista[8]);
            apellido.setText(lista[9]);
            tipo.setText(lista[10]);
            numero.setText(lista[11]);
            empresa.setText(lista[12]);

        }
        listaResultado.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        consultarListaPresupuesto(LISTAR_ARTICULOS_HOJASERVICIO_REQUEST_URL + "?termino=" + IdTurno);
    }

    private void consultarListaPresupuesto(String URL) {
        Log.d("Tag:","==> LISTADO HOJA SERVICIO! URL: "+URL);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length()>0){
                    try {
                        //Log.d("Tag:","==> ESTOY EN EL TRY!");
                        JSONArray jsonA = new JSONArray(response);
                        obtenerLista(jsonA);
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

    private void obtenerLista(JSONArray listaRecibida) {
        int contador = 0;
        for (int i = 0; i < listaRecibida.length(); i += 1) {
            try {
                if (contador == 4) {
                    // IdEncabezadoComprobante,IdProductoServicio,IdMarca,Cantidad,DescripcionProducto
                    ListaFinal.add(listaRecibida.getString(i - 1) + "-" + listaRecibida.getString(i ));
                    LISTADEIDPRODUCOTOS.add(listaRecibida.getString(i-3)+"//"+listaRecibida.getString(i-2)+"//"+listaRecibida.getString(i-1)+"//"+listaRecibida.getString(i));

                    contador = -1;
                }
                contador++;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> aa=new ArrayAdapter<String>(this,R.layout.activity_checkable_list,R.id.txt_title,ListaFinal);
        listaResultado.setAdapter(aa);
        listaResultado.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView) view).getText().toString();
                if(selectedItems.contains(selectedItem)) {
                    DATOSCOMPLETOS.remove(position);
                    MOSTRARDATOS.remove(position);
                    selectedItems.remove(selectedItem); //quita lo seleccionado.
                   // Log.d("==> ","REMOVE EN POSICION: "+position+" / la lista DATOSCOMPLETOS queda asi: "+ DATOSCOMPLETOS);


                }else{
                    selectedItems.add(selectedItem); //agrega lo seleccionado.
                    DATOSCOMPLETOS.add(LISTADEIDPRODUCOTOS.get(position));
                    MOSTRARDATOS.add(selectedItem);
                  //  Log.d("==> ","AGREGE EN POSICION: "+position+" / la lista DATOSCOMPLETOS queda asi: "+ DATOSCOMPLETOS);
                }
            }
        });
    }


    public void showSelectedItems(View view){
        Intent miIntent;
        miIntent= new Intent(listadoHojaServicio.this,detalleHojaServicio.class);
        Bundle miBundle= new Bundle();
        miBundle.putString("desde",null);
        miBundle.putString("IdTurno",IdTurno);
        miBundle.putStringArrayList("MOSTRARDATOS",MOSTRARDATOS);
        miBundle.putStringArrayList("DATOSCOMPLETOS",DATOSCOMPLETOS);
        miIntent.putExtras(miBundle);
        startActivity(miIntent);
    }

    private void cargarALaBD(String URL) {
        //Log.d("Tag:","==> ESTOY EN consultarListaPresupuesto! URL: "+URL);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length()>0){
                    Intent miIntent;
                    miIntent= new Intent(listadoHojaServicio.this,hojaServicio.class);
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
        // Log.d("==> ","DATOSCOMPLETOS: "+ DATOSCOMPLETOS);
        if (view.getId() == R.id.btnEnd) {
            String alPhp = IdTurno+"@!@"+USERID+"@!@";
            //idTurno-UserId-idproducto-idmarca- cantidad-detalle-..
            for (int i = 0; i < DATOSCOMPLETOS.size(); i += 1) {
                String contenido = DATOSCOMPLETOS.get(i);
                if (contenido != null){
                    alPhp += contenido+"//";
                }
            }
            Toast.makeText(this,"CARGANDO..",Toast.LENGTH_SHORT).show();
            Log.d("==> ","alPhp: "+CARGAR_ARTICULOS_HOJASERVICIO_REQUEST_URL+ "?termino=" + alPhp);
            cargarALaBD(CARGAR_ARTICULOS_HOJASERVICIO_REQUEST_URL+ "?termino=" + alPhp);
        }
    }
    public void back(View view) {
        Intent miIntent= new Intent(listadoHojaServicio.this, menuGeneral.class);
        startActivity(miIntent);
    }
}

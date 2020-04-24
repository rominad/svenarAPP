package com.example.rominabazanpc.svenartesis.Facturas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

import static com.example.rominabazanpc.svenartesis.Rutas.Utilidades.BUSCAR_DETALLE_FACTURA_COMPRA_REQUEST_URL;
import static com.example.rominabazanpc.svenartesis.Rutas.Utilidades.BUSCAR_DETALLE_FACTURA_VENTA_REQUEST_URL;

public class detalleFactura extends AppCompatActivity {
    TextView tipoFactura,numeroFactura, FechaHora, responsable, cuit_dni, direccion, subtotal, descuento, total;
    ListView listaResultado;
    ArrayList<String> listaInformacion = new ArrayList<>();
    ArrayList<String> listaInformacionCompleta = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_factura);
//// numeroFactura,FechaHora,responsable,cuit_dni,direccion, subtotal, descuento, total, tipoFactura

        listaResultado = findViewById(R.id.lvLista);
        tipoFactura = findViewById(R.id.tipoFactura);
        numeroFactura = findViewById(R.id.numeroFactura);
        FechaHora = findViewById(R.id.FechaHora);
        responsable = findViewById(R.id.responsable);
        cuit_dni = findViewById(R.id.cuit_dni);
        direccion = findViewById(R.id.direccion);
        subtotal = findViewById(R.id.subtotal);
        descuento = findViewById(R.id.descuento);
        total = findViewById(R.id.total);

        Log.d("-->", "ESTOY EN DETALLE FACTURA");

        //obtener el dato
        Bundle miBundle = this.getIntent().getExtras();
        //Validacion
        if (miBundle != null) {
            String informacion = miBundle.getString("informacion");
            String[] lista = informacion.split("@!@");


            numeroFactura.setText(lista[0]);
            FechaHora.setText(lista[1]);
            responsable.setText(lista[2]);
            cuit_dni.setText(lista[3]);
            direccion.setText(lista[4]);
            subtotal.setText('$'+lista[5]);
            descuento.setText(lista[6]+'%');
            total.setText('$'+lista[7]);
            tipoFactura.setText(lista[8]);
            Log.d("=== >","tipo factura: "+lista[8]);
            String termino = "";
            if(tipoFactura.getText().toString().contains("Compra")){
                termino = tipoFactura.getText().toString();
                Log.d("=== >","alPHP de compra: "+BUSCAR_DETALLE_FACTURA_COMPRA_REQUEST_URL+"?termino="+numeroFactura.getText());
                llamamosAlPHP(BUSCAR_DETALLE_FACTURA_COMPRA_REQUEST_URL+"?termino="+numeroFactura.getText());

            }else{
                termino = tipoFactura.getText().toString();
                Log.d("=== >","alPHP: "+BUSCAR_DETALLE_FACTURA_VENTA_REQUEST_URL+"?termino="+numeroFactura.getText());
                llamamosAlPHP(BUSCAR_DETALLE_FACTURA_VENTA_REQUEST_URL+"?termino="+numeroFactura.getText());
            }

        }

    }

    private void llamamosAlPHP(String URL) {
        Log.d("==>"," DETALLE FACTURA / URL: "+URL);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //response = response.replace("][",",");
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

    private void cargoListaFinal(JSONArray listaRecibida) {
        listaInformacion = new ArrayList<String>();
        listaInformacionCompleta = new ArrayList<String>();
        //String filaTotal = "";
        int contador = 0;
        for (int i = 0; i < listaRecibida.length(); i += 1) {
            try {

                if (contador == 3) {
                    //Cantidad,DetalleProductoServicio,PrecioUnitario,subtotal
                    listaInformacion.add(listaRecibida.getString(i-3) + " - " +listaRecibida.getString(i-2) + " - " +listaRecibida.getString(i-1) + " - " +listaRecibida.getString(i) );
                    contador = -1;
                    //filaTotal= listaInformacion.get(i);
                }
                contador++;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //total.setText(filaTotal);
        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
        listaResultado.setAdapter(adaptador);
        listaResultado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String informacion =listaInformacionCompleta.get(position)+"\n";
                //Toast.makeText(getApplicationContext(),informacion,Toast.LENGTH_LONG).show();

            }
        });
    }
    public void back(View view) {
        Intent miIntent= new Intent(detalleFactura.this, menuGeneral.class);
        startActivity(miIntent);
    }
}

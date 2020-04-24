package com.example.rominabazanpc.svenartesis.Turnos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.rominabazanpc.svenartesis.Rutas.Utilidades.BUSQUEDA_TURNOSX_FECHA_REQUEST_URL;

public class busquedaTurno extends AppCompatActivity implements View.OnClickListener {
    Button btnfechadesde,btnfechahasta;
    EditText efechadesde,efechahasta;
    ListView listaResultado;
    ArrayList<String> listaInformacion = new ArrayList<>();
    ArrayList<String> listaInformacionCompleta = new ArrayList<>();
    Date fechaActual;    private int diaDesde,mesDesde,anoDesde,diaHasta,mesHasta,anoHasta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_turno);
        Log.d("====>"," ESTOY EN BUSQUEDA TURNO!");
        listaResultado = findViewById(R.id.lvLista);
        btnfechadesde = findViewById(R.id.btnfechaDesde);
        btnfechahasta = findViewById(R.id.btnfechaHasta);
        efechadesde = findViewById(R.id.efechaDesde);
        efechahasta = findViewById(R.id.efechaHasta);

        Date d=new Date();
        //OBTENEMOS LA FECHA COMPLETA
        SimpleDateFormat fecc=new SimpleDateFormat("d/MM/yyyy");
        String fechaActual = fecc.format(d);
        efechadesde.setText(fechaActual);
        efechahasta.setText(fechaActual);

        btnfechadesde.setOnClickListener(this);
        btnfechahasta.setOnClickListener(this);
        //Locale locale = new Locale ("es"); // Su localidad específica.
        //Locale.setDefault (locale);


        String languageToLoad  = "es"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        //this.setContentView(R.layout.activity_busqueda_turno);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        if(view == btnfechadesde){
            final Calendar c = Calendar.getInstance();
            diaDesde = c.get(Calendar.DAY_OF_MONTH);
            mesDesde = c.get(Calendar.MONTH);
            anoDesde = c.get(Calendar.YEAR);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    efechadesde.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                }
            },diaDesde,mesDesde,anoDesde);
            datePickerDialog.show();
        }
        if(view == btnfechahasta){

            final Calendar c = Calendar.getInstance();
            diaHasta = c.get(Calendar.DAY_OF_MONTH);
            mesHasta = c.get(Calendar.MONTH);
            anoHasta = c.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    efechahasta.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                }
            },diaHasta,mesHasta,anoHasta);
            datePickerDialog.show();
        }
    }


    private void consultarLista(String URL) {
        //Log.d("==>"," URL: "+URL);
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

    private void cargoListaFinal(JSONArray listaRecibida) {
        listaInformacion = new ArrayList<String>();
        listaInformacionCompleta = new ArrayList<String>();
        int contador = 0;
        for (int i = 0; i < listaRecibida.length(); i += 1) {
            try {
                if (contador == 9) {
                    /*IdTurno,FechaHora,DetalleTurno,IdPersona,ApellidoNombre,Telefono,TipoDocumento,NumeroDocumento,NombreEmpresa,TelefonoEmpresa*/
                    listaInformacion.add(listaRecibida.getString(i -8 ) + " - " + listaRecibida.getString(i-7));
                    listaInformacionCompleta.add(listaRecibida.getString(i - 9) + "@!@" + listaRecibida.getString(i-8)+ "@!@" + listaRecibida.getString(i-7)+ "@!@" + listaRecibida.getString(i-6)+ "@!@" + listaRecibida.getString(i-5)+ "@!@" + listaRecibida.getString(i-4)+ "@!@" + listaRecibida.getString(i-3)+ "@!@" + listaRecibida.getString(i-2)+ "@!@" + listaRecibida.getString(i-1)+ "@!@" + listaRecibida.getString(i));
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
                Intent miIntent= new Intent(busquedaTurno.this,detalleBusquedaTurno.class);
                Bundle miBundle= new Bundle();
                miBundle.putString("informacion",informacion);
                miIntent.putExtras(miBundle);
                startActivity(miIntent);
            }
        });
    }

    public void busqueda(View view) {
        switch(view.getId()) {
            case R.id.btnBuscar:
                String info = "";
                if(efechadesde.getText().toString().isEmpty()){
                    info = null+"/"+null+"/"+null+"/"+"@!@"+efechahasta.getText();
                } else if(efechahasta.getText().toString().isEmpty()){
                    info = efechadesde.getText()+"@!@"+null+"/"+null+"/"+null;
                } else {
                    info = efechadesde.getText()+"@!@"+efechahasta.getText();
                }

                Log.d("===== >","info a enviar al php: "+BUSQUEDA_TURNOSX_FECHA_REQUEST_URL+"?termino="+info);
                consultarLista(BUSQUEDA_TURNOSX_FECHA_REQUEST_URL+"?termino="+info);
                break;
        }
    }
    public void back(View view) {
        Intent miIntent= new Intent(busquedaTurno.this, menuGeneral.class);
        startActivity(miIntent);
    }
}
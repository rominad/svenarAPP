package com.example.rominabazanpc.svenartesis;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.rominabazanpc.svenartesis.Menu.menuGeneral;
import com.example.rominabazanpc.svenartesis.Menu.menuMecanico;

import org.json.JSONException;
import org.json.JSONObject;
import static com.example.rominabazanpc.svenartesis.Rutas.Utilidades.USERID;
import static com.example.rominabazanpc.svenartesis.Rutas.Utilidades.USERROL;

public class MainActivity extends AppCompatActivity {
    EditText et_username, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_username = findViewById(R.id.TextV_rut);
        et_password = findViewById(R.id.TextV_password);
        Button btn_log = findViewById(R.id.Btn_iniciar);
        Log.d("====> ", "ESTOY EN MAIN ACTIVITY! " );
        btn_log.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (et_username.getText().toString().isEmpty()) {
               Toast.makeText(MainActivity.this, "Debe ingresar un usuario!", Toast.LENGTH_SHORT).show();
            } else if (et_password.getText().toString().isEmpty()) {
               Toast.makeText(MainActivity.this, "Debe ingresar la contraseña!", Toast.LENGTH_SHORT).show();
            }
            final String username = et_username.getText().toString();
            final String password = et_password.getText().toString();
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.d("====> ", "ENTRE AL RESPONSE! response: " + response);
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) { // SI VUELVE BIEN LOS DATOS DEL PHP CARGAMOS LOS DATOS EN VARIABLES LOCALES.
                            String username = jsonResponse.getString("username");
                            String password = jsonResponse.getString("password");
                            USERID = jsonResponse.getInt("userId");
                            USERROL = jsonResponse.getInt("userRol");
                            Log.d("====> ", "USERID: " + USERID);
                            Log.d("====> ", "USERROL: " + USERROL);

                            if(USERROL == 1 ||USERROL == 2 ){ //SIGNIFICA QUE ES ADMINISTRADOR/SECRETARIA
                               Intent intent = new Intent(MainActivity.this, menuGeneral.class);
                               intent.putExtra("username", username);
                               intent.putExtra("password", password);
                               MainActivity.this.startActivity(intent);
                            }
                            if(USERROL == 2){ //SIGNIFICA QUE ES MECANICO
                               Intent intent = new Intent(MainActivity.this, menuMecanico.class);
                               intent.putExtra("username", username);
                               intent.putExtra("password", password);
                               MainActivity.this.startActivity(intent);
                            }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("Usuario y/o Contraseña Incorrecto")
                                       .setNegativeButton("Volver", null)
                                       .create().show();
                            }
                        } catch (JSONException e) {
                           e.printStackTrace();

                        }Log.d("====> ", "NO ENTRE AL RESPONSE! :/");
                    }
           };
           LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
           RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
           queue.add(loginRequest);
       }

    }
        );
    }
}
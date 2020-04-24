package com.example.rominabazanpc.svenartesis;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;
import static com.example.rominabazanpc.svenartesis.Rutas.Utilidades.LOGGIN_REQUEST_URL;

public class LoginRequest extends StringRequest {
    private Map<String,String> params;
    public LoginRequest(String username, String password, Response.Listener<String> Listener ){
        super(Request.Method.POST, LOGGIN_REQUEST_URL,Listener,null);
        params= new HashMap<>();
        params.put("username",username);
        params.put("password",password);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
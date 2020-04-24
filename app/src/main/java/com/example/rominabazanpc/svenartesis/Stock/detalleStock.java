package com.example.rominabazanpc.svenartesis.Stock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.rominabazanpc.svenartesis.Diaria.diaria;
import com.example.rominabazanpc.svenartesis.Menu.menuGeneral;
import com.example.rominabazanpc.svenartesis.R;

public class detalleStock extends AppCompatActivity {
    TextView Stock2,UnidadMedida2,Marca2;
    TextView DescripcionProducto,TipoProducto,Precio,Stock,UnidadMedida,Marca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_stock);

        DescripcionProducto = findViewById(R.id.DescripcionProducto);
        TipoProducto = findViewById(R.id.TipoProducto);
        Precio = findViewById(R.id.Precio);
        Stock = findViewById(R.id.Stock);
        UnidadMedida = findViewById(R.id.UnidadMedida);
        Marca = findViewById(R.id.Marca);
        Stock2 = findViewById(R.id.Stock2);
        UnidadMedida2 = findViewById(R.id.UnidadMedida2);
        Marca2 = findViewById(R.id.Marca2);


        //obtener el dato
        Bundle miBundle = this.getIntent().getExtras();
        //Validacion
        if(miBundle!=null){
            String informacion= miBundle.getString("informacion");
            String[] lista = informacion.split("@!@");

            DescripcionProducto.setText(lista[1]);
            String letra = "P"; //LA P SIGNIFICA QUE ES PRODUCTO. S = SEVICIO
            String contenido = lista[2];
            if (contenido.equals(letra)){
                TipoProducto.setText("Producto");
            }else{
                TipoProducto.setText("Servicio");
            }
            Precio.setText('$'+lista[3]);

            String bandera = lista[5];
            Log.d("==>", "bandera: " +bandera);
            if (bandera.contains("--")) {
                Stock2.setVisibility(View.INVISIBLE);
                UnidadMedida2.setVisibility(View.INVISIBLE);
                Marca2.setVisibility(View.INVISIBLE);

                Stock.setVisibility(View.INVISIBLE);
                UnidadMedida.setVisibility(View.INVISIBLE);
                Marca.setVisibility(View.INVISIBLE);

            }else{
                Stock.setText(lista[4]);
                UnidadMedida.setText(lista[5]);
                Marca.setText(lista[6]);

            }

        }
    }
    public void back(View view) {
        Intent miIntent= new Intent(detalleStock.this, menuGeneral.class);
        startActivity(miIntent);
    }
}

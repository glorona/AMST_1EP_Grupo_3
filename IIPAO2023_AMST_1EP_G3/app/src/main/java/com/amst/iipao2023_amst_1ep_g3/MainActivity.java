package com.amst.iipao2023_amst_1ep_g3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buscar(View view) {
        final EditText busqueda = (EditText) findViewById(R.id.txtHeroe);
        String heroebuscar = busqueda.getText().toString();
        Intent intent = new Intent(this, busqueda.class);
        intent.putExtra("busqueda", heroebuscar);
        startActivity(intent);
    }
}
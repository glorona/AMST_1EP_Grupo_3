package com.amst.iipao2023_amst_1ep_g3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class busqueda extends AppCompatActivity {
    private RequestQueue mQueue;

    private String busqueda = "";
    private String token = "2927635707394084";

    private ArrayList<Superhero> listasuperheroes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);
        mQueue = Volley.newRequestQueue(this);

        Intent main = getIntent();
        this.busqueda = (String)main.getExtras().get("busqueda");
        buscarHeroe(this.busqueda);

    }

    protected void buscarHeroe(String buscar){

        String url_temp = "https://www.superheroapi.com/api/" + this.token + "/search/" + buscar;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url_temp, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Try");
                            procesarResultados(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        mQueue.add(request);

    }

    private void procesarResultados(JSONObject respuesta) throws JSONException {
        JSONArray results_query = respuesta.getJSONArray("results");
        int count = 0;
        for (int i = 0; i < results_query.length(); i++) {
            JSONObject character = results_query.getJSONObject(i);

            String id = character.getString("id");
            String name = character.getString("name");
            String image = character.getJSONObject("image").getString("url");

            System.out.println(image);

            Superhero h = new Superhero(id,name,image);

            this.listasuperheroes.add(h);

            count++;
        }
        final TextView texto = (TextView) findViewById(R.id.textViewResultadosCount);
        String texto_elementos = "Resultados de la búsqueda: " + count + " elementos.";
        texto.setText(texto_elementos);

        ListView listview = findViewById(R.id.heroesListView);

        CustomHeroAdapter adapter = new CustomHeroAdapter(this,this.listasuperheroes);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Superhero selectedSuperhero = adapter.getItem(position);
                mostrarDetallesHeroe(selectedSuperhero);
            }
        });
    }

    private void mostrarDetallesHeroe(Superhero superhero) {
        Intent intent = new Intent(this, DetallesHeroe.class);
        intent.putExtra("id", superhero.getId());
        startActivity(intent);
    }


}
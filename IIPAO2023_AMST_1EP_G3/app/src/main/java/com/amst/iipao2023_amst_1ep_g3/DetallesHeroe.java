package com.amst.iipao2023_amst_1ep_g3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DetallesHeroe extends AppCompatActivity {


    private RequestQueue mQueue;

    public BarChart graficoBarras;

    private String id = "";
    private String token = "2927635707394084";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_heroe);

        mQueue = Volley.newRequestQueue(this);

        Intent main = getIntent();
        this.id = (String)main.getExtras().get("id");
        obtenerDetalles(this.id);
    }

    private void obtenerDetalles(String idh){
        String url_temp = "https://www.superheroapi.com/api/" + this.token + "/" + idh;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url_temp, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
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

    private void procesarResultados(JSONObject response) throws JSONException {

        String name = response.getString("name");
        String fullname = response.getJSONObject("biography").getString("full-name");
        String image = response.getJSONObject("image").getString("url");

        JSONObject stats = response.getJSONObject("powerstats");


        final TextView textoNombre = (TextView) findViewById(R.id.textName);
        final TextView textoNombreCompleto = (TextView) findViewById(R.id.textNameComplete);

        String text_name = "Nombre: " + name;
        textoNombre.setText(text_name);

        String text_namecomplete = "Nombre completo: " + fullname;
        textoNombreCompleto.setText(text_namecomplete);

        ImageView imageViewHero = findViewById(R.id.imageViewHero);
        Picasso.get().load(image).into(imageViewHero);

        iniciarGrafico(stats);

    }

    public void iniciarGrafico(JSONObject estadisticas) {

        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        Iterator<String> keys = estadisticas.keys();
        int index = 0;
        while(keys.hasNext()) {

            String key = keys.next();
            int value = estadisticas.optInt(key);
            key = key.substring(0,1).toUpperCase() + key.substring(1).toLowerCase();

            entries.add(new BarEntry(index,value));
            labels.add(key);
            index++;
        }

        BarDataSet dataSet = new BarDataSet(entries, "Habilidades");
        BarData barData = new BarData(dataSet);


        graficoBarras = findViewById(R.id.barChart);
        graficoBarras.setData(barData);
        XAxis xAxis = graficoBarras.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true); // Enable granularity
        xAxis.setLabelCount(labels.size());
        xAxis.setLabelRotationAngle(-45);

        graficoBarras.getDescription().setEnabled(false);
        graficoBarras.getAxisLeft().setDrawGridLines(false);
        graficoBarras.getAxisRight().setEnabled(false);
        graficoBarras.setFitBars(true);
        graficoBarras.getLegend().setEnabled(false);
        graficoBarras.setExtraBottomOffset(50);

        graficoBarras.invalidate();



    }
}
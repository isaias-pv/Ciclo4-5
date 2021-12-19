package com.example.reto2.datos;

import static com.example.reto2.datos.Urls._services;

import android.content.Context;
import android.os.Build;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.reto2.adaptadores.ServicioAdapter;
import com.example.reto2.casos_uso.CasoUsoServicio;
import com.example.reto2.modelos.Servicio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApiOracleServices {
    private RequestQueue queue;
    private CasoUsoServicio casoUsoServices;
    private String url = _services;
    private Context context;

    public ApiOracleServices(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
        casoUsoServices = new CasoUsoServicio();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertServicio(String name, String description, ImageView imageView) {
        String image = casoUsoServices.imageViewToString(imageView);

        JSONObject json = new JSONObject();
        try {
            json.put("title", name);
            json.put("description", description);
            json.put("image", image);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // PETICION(POST,DELETE,PUT,GET), URL, JSON, RESPONSE.LISTENER, ERROR.LISTENER
        JsonObjectRequest postServicio = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(postServicio);
    }

    public void getAllServiceFragment(GridView gridView, ProgressBar progressBar) {
        // PETICION(POST,DELETE,PUT,GET), URL, JSON, RESPONSE.LISTENER, ERROR.LISTENER
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<Servicio> lista = new ArrayList<>();
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Servicio servicio = new Servicio(
                                jsonObject.getString("service_id"),
                                jsonObject.getString("title"),
                                jsonObject.getString("description"),
                                casoUsoServices.stringToByte(jsonObject.getString("image"))
                        );
                        lista.add(servicio);
                    }
                    ServicioAdapter servicioAdapter = new ServicioAdapter(context, lista);
                    //progressBar.setVisibility(ProgressBar.INVISIBLE);

                    gridView.setAdapter(servicioAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }

    public void getServiceById(String id, EditText name, EditText description,ImageView imageView){
        JsonObjectRequest getServiceById = new JsonObjectRequest(Request.Method.GET, url + id, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        name.setText(jsonObject.getString("title"));
                        description.setText(jsonObject.getString("description"));
                        byte[] img = casoUsoServices.stringToByte(jsonObject.getString("image"));
                        imageView.setImageBitmap(casoUsoServices.byteToBitmap(img));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(getServiceById);
    }

    public void deleteService(String service_id){
        JsonObjectRequest deleteServicio = new JsonObjectRequest(Request.Method.DELETE, url + service_id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(deleteServicio);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateService(Integer service_id, String name, String description, ImageView imageView) {
        String image = casoUsoServices.imageViewToString(imageView);

        JSONObject json = new JSONObject();
        try {
            json.put("service_id", service_id);
            json.put("title", name);
            json.put("description", description);
            json.put("image", image);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest postService = new JsonObjectRequest(Request.Method.PUT, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(postService);
    }
}

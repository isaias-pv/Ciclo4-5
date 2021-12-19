package com.example.reto2.datos;

import static com.example.reto2.datos.Urls._products;

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
import com.example.reto2.adaptadores.ProductoAdapter;
import com.example.reto2.casos_uso.CasoUsoProducto;
import com.example.reto2.modelos.Producto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApiOracleProducts {
    private RequestQueue queue;
    private CasoUsoProducto casoUsoProducts;
    private String url = _products;
    private Context context;

    public ApiOracleProducts(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
        casoUsoProducts = new CasoUsoProducto();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertProdcuto(String name, String description, Integer price, ImageView imageView) {
        String image = casoUsoProducts.imageViewToString(imageView);

        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            json.put("description", description);
            json.put("price", price);
            json.put("image", image);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // PETICION(POST,DELETE,PUT,GET), URL, JSON, RESPONSE.LISTENER, ERROR.LISTENER
        JsonObjectRequest postProducto = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(postProducto);
    }

    public void getAllProductFragment(GridView gridView, ProgressBar progressBar) {
        // PETICION(POST,DELETE,PUT,GET), URL, JSON, RESPONSE.LISTENER, ERROR.LISTENER
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<Producto> lista = new ArrayList<>();
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Producto producto = new Producto(
                                jsonObject.getInt("product_id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("description"),
                                String.valueOf(jsonObject.getInt("price")),
                                casoUsoProducts.stringToByte(jsonObject.getString("image"))
                        );
                        lista.add(producto);
                    }
                    ProductoAdapter productAdapter = new ProductoAdapter(context, lista);
                    gridView.setAdapter(productAdapter);

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

    public void getProductById(String id, EditText name, EditText description, EditText price,ImageView imageView){
        JsonObjectRequest getProductById = new JsonObjectRequest(Request.Method.GET, url + id, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        name.setText(jsonObject.getString("name"));
                        description.setText(jsonObject.getString("description"));
                        price.setText(jsonObject.getString("price"));
                        byte[] img = casoUsoProducts.stringToByte(jsonObject.getString("image"));
                        imageView.setImageBitmap(casoUsoProducts.byteToBitmap(img));
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

        queue.add(getProductById);
    }

    public void deleteProducto(String product_id){
        JsonObjectRequest deleteSucursal = new JsonObjectRequest(Request.Method.DELETE, url + product_id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(deleteSucursal);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateProducto(Integer product_id, String name, String description, Integer price, ImageView imageView) {
        String image = casoUsoProducts.imageViewToString(imageView);

        JSONObject json = new JSONObject();
        try {
            json.put("product_id", product_id);
            json.put("name", name);
            json.put("description", description);
            json.put("price", price);
            json.put("image", image);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest postProducto = new JsonObjectRequest(Request.Method.PUT, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(postProducto);
    }
}

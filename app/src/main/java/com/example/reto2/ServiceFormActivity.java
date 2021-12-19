package com.example.reto2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.reto2.datos.ApiOracleServices;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ServiceFormActivity extends AppCompatActivity {
    private final int REQUEST_CODE_GALLERY = 999;
    private TextView tvTitulo;
    private EditText campo1, campo2, editId;
    private Button btnChoose, btnInsertar, btnEliminar, btnConsultar, btnActualizar;
    private ImageView imgSelected;
    String name = "";
    private ApiOracleServices apiOracleServices;

    String campo1Insert;
    String campo2Insert;
    byte[] imageInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_service);

        tvTitulo = (TextView) findViewById(R.id.tvTituloService);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        editId = (EditText) findViewById(R.id.editIdItemService);
        campo1 = (EditText) findViewById(R.id.editCampo1Service);
        campo2 = (EditText) findViewById(R.id.editCampo2Service);
        btnChoose = (Button) findViewById(R.id.btnChooseService);
        btnInsertar = (Button) findViewById(R.id.btnInsertarService);
        btnConsultar = (Button) findViewById(R.id.btnConsultarService);
        btnEliminar = (Button) findViewById(R.id.btnEliminarService);
        btnActualizar = (Button) findViewById(R.id.btnActualizarService);
        imgSelected = (ImageView) findViewById(R.id.imgSelectedService);
        apiOracleServices = new ApiOracleServices(getApplicationContext());

        tvTitulo.setText(name);
        campo1.setHint("Name");
        campo2.setHint("Description");


        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        ServiceFormActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    llenarCampos();
                    apiOracleServices.insertServicio(campo1Insert, campo2Insert, imgSelected);
                    limpiarCampos();
                    Snackbar.make(v, "Servicio ingresado", Snackbar.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  apiOracleServices.getServiceById(editId.getText().toString().trim(), campo1, campo2, imgSelected);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiOracleServices.deleteService(editId.getText().toString().trim());
                limpiarCampos();
                Snackbar.make(v, "Servicio eliminado", Snackbar.LENGTH_SHORT).show();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                llenarCampos();
                try{
                    apiOracleServices.updateService(
                            Integer.parseInt(editId.getText().toString().toString()),
                            campo1Insert,
                            campo2Insert,
                            imgSelected
                    );
                    limpiarCampos();
                    Snackbar.make(v, "Servicio actualizado", Snackbar.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public byte[] imageViewToByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void llenarCampos(){
        campo1Insert = campo1.getText().toString().trim();
        campo2Insert = campo2.getText().toString().trim();
        imageInsert = imageViewToByte(imgSelected);
    }

    public void limpiarCampos(){
        campo1.setText("");
        campo2.setText("");
        imgSelected.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }else{
                Toast.makeText(getApplicationContext(), "Sin Permisos", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgSelected.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
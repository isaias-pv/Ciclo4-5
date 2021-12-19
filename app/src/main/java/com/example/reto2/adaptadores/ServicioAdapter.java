package com.example.reto2.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reto2.R;
import com.example.reto2.modelos.Servicio;

import java.util.ArrayList;

public class ServicioAdapter extends BaseAdapter {
    Context context;
    ArrayList<Servicio> servicios;
    LayoutInflater inflater;

    public ServicioAdapter(Context context, ArrayList<Servicio> servicios) {
        this.context = context;
        this.servicios = servicios;
    }

    @Override
    public int getCount() {
        return servicios.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.service_item, null);
        }

        ImageView imageView = convertView.findViewById(R.id.imgItemService);
        TextView campo1 = convertView.findViewById(R.id.tvCampo1Service);
        TextView campo2 = convertView.findViewById(R.id.tvCampo2Service);
        TextView campoId = convertView.findViewById(R.id.tvIdService);

        Servicio servicio = servicios.get(position);
        byte[] image = servicio.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);


        imageView.setImageBitmap(bitmap);
        campoId.setText(servicio.getId());
        campo1.setText(servicio.getName());
        campo2.setText(servicio.getDescription());

        return convertView;
    }
}

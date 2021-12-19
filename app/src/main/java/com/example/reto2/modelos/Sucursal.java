package com.example.reto2.modelos;

import com.google.android.gms.maps.model.LatLng;

public class Sucursal {
    private String id;
    private String name;
    private String localization;
    private byte[] image;

    public Sucursal(String id, String name, String localization, byte[] image) {
        this.id = id;
        this.name = name;
        this.localization = localization;
        this.image = image;
    }

    public Sucursal(String name, String location) {
        this.name = name;
        this.localization = location;
    }

    public Sucursal() {}

    public LatLng stringToLatLong(){
        String[] coordenadas = this.localization.split(",");
        Double latitud = Double.parseDouble(coordenadas[0]);
        Double longitud = Double.parseDouble(coordenadas[1]);

        LatLng latLng = new LatLng(latitud, longitud);
        return latLng;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

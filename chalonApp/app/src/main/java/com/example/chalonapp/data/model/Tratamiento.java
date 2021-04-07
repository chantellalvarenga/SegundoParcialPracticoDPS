package com.example.chalonapp.data.model;

public class Tratamiento {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public Tratamiento(int id, String nombre, double precio, String img_url) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.img_url = img_url;
    }

    int id;
    String nombre;
    double precio;
    String img_url;
}

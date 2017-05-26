package com.dima.restaurantmanager;

/**
 * Created by David on 20/05/2017.
 */

public class ProductoMenu {

    private double precio;
    private String nombre;

    public ProductoMenu() {
    }

    public ProductoMenu(double precio, String nombre) {
        this.precio = precio;
        this.nombre = nombre;
    }


    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "ProductoMenu{" +
                "precio=" + precio +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

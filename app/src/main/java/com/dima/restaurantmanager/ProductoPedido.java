package com.dima.restaurantmanager;

/**
 * Created by David on 20/05/2017.
 */

public class ProductoPedido {

    private int cantidad;
    private String nombre;

    public ProductoPedido() {
    }

    public ProductoPedido(int cantidad, String nombre) {
        this.cantidad = cantidad;
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "ProductoPedido{" +
                "cantidad=" + cantidad +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

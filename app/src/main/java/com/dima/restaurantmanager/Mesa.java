package com.dima.restaurantmanager;

/**
 * Created by David on 18/05/2017.
 */

public class Mesa {

    private int numero;
    private int tam;
    private boolean libre;

    public Mesa() {
    }

    public Mesa(int numero, int tam, boolean libre) {
        this.numero = numero;
        this.tam = tam;
        this.libre = libre;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getTam() {
        return tam;
    }

    public void setTam(int tam) {
        this.tam = tam;
    }

    public boolean isLibre() {
        return libre;
    }

    public void setLibre(boolean libre) {
        this.libre = libre;
    }

    @Override
    public String toString() {
        return "Mesa{" +
                "numero=" + numero +
                ", tam=" + tam +
                ", libre=" + libre +
                '}';
    }
}

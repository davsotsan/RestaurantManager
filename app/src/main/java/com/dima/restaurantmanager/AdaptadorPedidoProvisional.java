package com.dima.restaurantmanager;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashSet;


public class AdaptadorPedidoProvisional extends RecyclerView.Adapter<ProductoPedidoHolder> {

    private ArrayList<ProductoPedido> datos;

//    public AdaptadorPedidoProvisional(ArrayList<ProductoPedido> datos) {
//        this.datos = datos;
//    }
    public AdaptadorPedidoProvisional() {
        this.datos = new ArrayList<>();
    }


    @Override
    public ProductoPedidoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lista_productos, viewGroup, false);

        ProductoPedidoHolder tvh = new ProductoPedidoHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(ProductoPedidoHolder viewHolder, int pos) {
        ProductoPedido item = datos.get(pos);

        viewHolder.setNombre(item.getNombre());
        viewHolder.setCantidad(String.valueOf(item.getCantidad()));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public ArrayList<ProductoPedido> getItems() {
        return datos;
    }

    public void swap(ProductoPedido p){
        ArrayList<ProductoPedido> datosAux = new ArrayList<>();
        datosAux.addAll(datos);

        for(ProductoPedido pFor: datosAux){
            if(datosAux.contains(p) && pFor.getCantidad() != p.getCantidad()){
                datos.remove(p);
                notifyDataSetChanged();
            }
        }
        if(p.getCantidad() == 0)
            datos.remove(p);
        else
            datos.add(p);

        notifyDataSetChanged();

    }


}
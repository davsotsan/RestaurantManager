package com.dima.restaurantmanager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by David on 19/05/2017.
 */

public class ProductoPedidoHolder extends RecyclerView.ViewHolder {
    private View mView;

    public ProductoPedidoHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setCantidad(String cantidad) {
        TextView field = (TextView) mView.findViewById(R.id.lblCantidad);
        field.setText(cantidad);
    }

    public void setNombre(String nombre) {
        TextView field = (TextView) mView.findViewById(R.id.lblProducto);
        field.setText(nombre);
    }
}


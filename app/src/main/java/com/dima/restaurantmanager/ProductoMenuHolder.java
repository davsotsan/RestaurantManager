package com.dima.restaurantmanager;


import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by David on 19/05/2017.
 */

public class ProductoMenuHolder extends RecyclerView.ViewHolder {
    private View mView;
    private final ImageButton addProductButton;
    private AutoCompleteTextView textCantidad;
    private ImageButton mas;
    private ImageButton menos;

    public ProductoMenuHolder(View itemView) {
        super(itemView);
        mView = itemView;
        addProductButton = (ImageButton) itemView.findViewById(R.id.buttonAddProduct);
        textCantidad = (AutoCompleteTextView) itemView.findViewById(R.id.textCantidad);
        mas = (ImageButton) itemView.findViewById(R.id.buttonAddProductSingle);
        menos = (ImageButton) itemView.findViewById(R.id.buttonRemoveProductSingle);
    }

    public void setPrecio(String precio) {
        TextView field = (TextView) mView.findViewById(R.id.lblPrecio);
        field.setText(precio);
    }

    public void setNombre(String nombre) {
        TextView field = (TextView) mView.findViewById(R.id.lblNombreProductoMenu);
        field.setText(nombre);
    }

    public ImageButton getAddProductButton() {
        return this.addProductButton;
    }

    public ImageButton getAddProductSingleButton() {
        return this.mas;
    }

    public ImageButton getRemoveProductSingleButton() {
        return this.menos;
    }

    public AutoCompleteTextView getCantidad() {
        return this.textCantidad;
    }

    public void setCantidad(String cantidad) {
        AutoCompleteTextView field = (AutoCompleteTextView) mView.findViewById(R.id.textCantidad);
        field.setText(cantidad);
    }



}


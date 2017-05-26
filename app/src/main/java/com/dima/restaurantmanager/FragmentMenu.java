package com.dima.restaurantmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.stream.IntStream;


public class FragmentMenu extends Fragment {

    private static final String TAGLOG = "firebase-db";

    private DatabaseReference dbMenu;
    private DatabaseReference dbPedido;
    FirebaseRecyclerAdapter mAdapter;
    String[] numbers;
    ArrayAdapter<String> arrayAdapter;
    private String categoria;
    private String numero_mesa;

    public String[] loop() {
        String[] a = new String[100];
        for (int i = 0; i < 100; ++i) {
            a[i] = String.valueOf(i);
        }
        return  a;
    }


    public static FragmentMenu newInstance(String categoria, String numero_mesa) {
        FragmentMenu fragment = new FragmentMenu();
        Bundle b = new Bundle();
        b.putString("categoria", categoria);
        b.putString("numero_mesa", numero_mesa);
        fragment.setArguments(b);

        return fragment;
    }


    public FragmentMenu() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = this.getArguments();
        categoria = b.getString("categoria");
        numero_mesa = b.getString("numero_mesa");

        dbMenu = FirebaseDatabase.getInstance().getReference().child("menu").child(categoria);
        dbPedido = FirebaseDatabase.getInstance().getReference().child("pedidos").child("pedido" + numero_mesa).child("productos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewFragment=inflater.inflate(R.layout.fragment_menu, container, false);
        final View viewListaMenu = viewFragment.findViewById(R.id.listaMenu);

        numbers = loop();

        RecyclerView recycler = (RecyclerView) viewFragment.findViewById(R.id.listaMenu);
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter =
                new FirebaseRecyclerAdapter<ProductoMenu, ProductoMenuHolder>(
                        ProductoMenu.class, R.layout.lista_menu, ProductoMenuHolder.class, dbMenu) {

                    @Override
                    public void populateViewHolder(ProductoMenuHolder productoMenuHolder, final ProductoMenu producto, int position) {
                        final String key = mAdapter.getRef(position).getKey();

                        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, numbers);
                        final AutoCompleteTextView auto = productoMenuHolder.getCantidad();
                        auto.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                        auto.setAdapter(arrayAdapter);
                        //auto.setCursorVisible(false);

                        auto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                auto.showDropDown();
                            }
                        });

                        auto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if (hasFocus) {
                                    auto.showDropDown();
                                    auto.setShowSoftInputOnFocus(false);
                                }
                            }
                        });

                        productoMenuHolder.setNombre(producto.getNombre());
                        productoMenuHolder.setPrecio(String.valueOf(producto.getPrecio()));
                        productoMenuHolder.getAddProductButton().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ProductoPedido newProductoPedido = new ProductoPedido( Integer.parseInt(((AutoCompleteTextView)viewListaMenu.findViewById(R.id.textCantidad)).getText().toString()) , producto.getNombre());
                                dbPedido.child(key).setValue(newProductoPedido);
                            }
                        });
                    }
                };

        recycler.setAdapter(mAdapter);

        return viewFragment;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }




}
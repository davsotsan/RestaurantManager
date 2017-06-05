package com.dima.restaurantmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        final MenuActivity activity = (MenuActivity) getActivity();


        numbers = loop();

        final RecyclerView recycler = (RecyclerView) viewFragment.findViewById(R.id.listaMenu);
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));


        mAdapter =
                new FirebaseRecyclerAdapter<ProductoMenu, ProductoMenuHolder>(
                        ProductoMenu.class, R.layout.lista_menu, ProductoMenuHolder.class, dbMenu) {

                    @Override
                    public void populateViewHolder(final ProductoMenuHolder productoMenuHolder, final ProductoMenu producto, final int position) {
                        final String key = mAdapter.getRef(position).getKey();

                        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, numbers);
                        final AutoCompleteTextView auto = productoMenuHolder.getCantidad();

                        auto.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {}
                            @Override
                            public void afterTextChanged(Editable s) {
                                ProductoPedido newProductoPedido = new ProductoPedido( Integer.parseInt(auto.getText().toString()), producto.getNombre());
                                System.out.println(newProductoPedido.toString());
                                //dbPedido.child(key).setValue(newProductoPedido);
                                activity.getAdaptador().swap(newProductoPedido);
                               // activity.findViewById(R.id.listaProductos).setVisibility(View.VISIBLE);
                                Button a = (Button) activity.findViewById(R.id.buttonCabecera);
                                a.setText("Productos para añadir al pedido: " + activity.getAdaptador().getItemCount());
                                Toast.makeText(getContext(), "Agregado " + newProductoPedido.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        auto.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                        auto.setAdapter(arrayAdapter);
                        auto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                auto.showDropDown();
                            }
                        });

                        auto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if (hasFocus){
                                    auto.showDropDown();
                                    auto.setShowSoftInputOnFocus(false);
                                }
                            }
                        });

                        productoMenuHolder.getAddProductSingleButton().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int cantActual = Integer.parseInt(auto.getText().toString());
                                productoMenuHolder.setCantidad(String.valueOf(cantActual + 1));;
                            }
                        });

                        productoMenuHolder.getRemoveProductSingleButton().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int cantActual = Integer.parseInt(auto.getText().toString());
                                if(cantActual > 0)
                                    productoMenuHolder.setCantidad(String.valueOf(cantActual - 1));
                            }
                        });

                        productoMenuHolder.setNombre(producto.getNombre());
                        productoMenuHolder.setPrecio(String.valueOf(producto.getPrecio()));
                        productoMenuHolder.getAddProductButton().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ProductoPedido newProductoPedido = new ProductoPedido( Integer.parseInt(productoMenuHolder.getCantidad().getText().toString()), producto.getNombre());
                                System.out.println(newProductoPedido.toString());
                                //dbPedido.child(key).setValue(newProductoPedido);
                                activity.getAdaptador().swap(newProductoPedido);
                                //activity.findViewById(R.id.listaProductos).setVisibility(View.VISIBLE);
                                Toast.makeText(getContext(), "Agregado " + newProductoPedido.toString(), Toast.LENGTH_SHORT).show();
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
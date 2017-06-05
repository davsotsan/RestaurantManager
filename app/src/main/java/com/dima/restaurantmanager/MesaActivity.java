package com.dima.restaurantmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MesaActivity extends AppCompatActivity {
    private static final String TAGLOG = "firebase-db";

    private TextView lblTamano;
    private TextView lblLibre;
    private TextView lblNumero;
    private FloatingActionButton fabAnadirProdcuto;

    private DatabaseReference dbMesa;
    private DatabaseReference dbPedido;
    private ValueEventListener eventListenerMesa;

    FirebaseRecyclerAdapter mAdapter;
    private FirebaseRecyclerAdapter listaProductos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesa);

        lblTamano = (TextView)findViewById(R.id.lblTamano);
        lblLibre = (TextView)findViewById(R.id.lblLibre);
        lblNumero = (TextView)findViewById(R.id.lblNumero);
        final Bundle bundle = this.getIntent().getExtras();
        dbMesa = FirebaseDatabase.getInstance().getReference().child("mesas").child("mesa" + bundle.getString("numero_mesa"));
        dbPedido = FirebaseDatabase.getInstance().getReference().child("pedidos").child("pedido" + bundle.getString("numero_mesa")).child("productos");



        eventListenerMesa = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Mesa mesa = dataSnapshot.getValue(Mesa.class);
                lblTamano.setText(String.valueOf(mesa.getTam()));
                lblLibre.setText(String.valueOf(mesa.isLibre()));
                lblNumero.setText(String.valueOf(mesa.getNumero()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };
        dbMesa.addListenerForSingleValueEvent(eventListenerMesa);


        fabAnadirProdcuto = (FloatingActionButton)  findViewById(R.id.fabAnadirProdcuto);
        fabAnadirProdcuto.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               //Creamos el Intent
                Intent intent = new Intent(MesaActivity.this, MenuActivity.class);
               //Creamos la información a pasar entre actividades
                Bundle b = new Bundle();
                 b.putString("numero_mesa", bundle.getString("numero_mesa"));

               //Añadimos la información al intent
                 intent.putExtras(b);

               //Iniciamos la nueva actividad
                 startActivity(intent);
            }
        });

        RecyclerView recycler = (RecyclerView) findViewById(R.id.listaProductos);
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recycler.setHasFixedSize(false);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        mAdapter =
                new FirebaseRecyclerAdapter<ProductoPedido, ProductoPedidoHolder>(
                        ProductoPedido.class, R.layout.lista_productos, ProductoPedidoHolder.class, dbPedido) {

                    @Override
                    public void populateViewHolder(ProductoPedidoHolder productosViewHolder, ProductoPedido producto, int position) {
                        productosViewHolder.setNombre(producto.getNombre());
                        productosViewHolder.setCantidad(String.valueOf(producto.getCantidad()));
                    }
                };

        System.out.println("mAdapter MESA " + mAdapter);
        recycler.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }
}

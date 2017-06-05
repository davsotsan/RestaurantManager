package com.dima.restaurantmanager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    private static final String TAGLOG = "firebase-db";
    private String numero_mesa;
    private ArrayList<ProductoPedido> list;
    private AdaptadorPedidoProvisional adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final Bundle bundle = this.getIntent().getExtras();
        numero_mesa = bundle.getString("numero_mesa");

        list = new ArrayList<>();
        ProductoPedido p1 = new ProductoPedido(2, "Macuca");
        list.add(p1);
        for (ProductoPedido p : list) {
            System.out.println("PRODUCTOOOOOOOOOOOOOOOOO: " + p.getNombre());
        }


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(new MiFragmentPagerAdapter(
                getSupportFragmentManager(), numero_mesa));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);

        RecyclerView recView = (RecyclerView) findViewById(R.id.listaProductos);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //adaptador = new AdaptadorPedidoProvisional(list);
        adaptador = new AdaptadorPedidoProvisional();
        recView.setAdapter(adaptador);

        final LinearLayout containerExpandible = (LinearLayout) findViewById(R.id.containerExpandible);
        containerExpandible.setVisibility(View.GONE);

        Button buttonCabecera = (Button) findViewById(R.id.buttonCabecera);
        buttonCabecera.setText("Productos para añadir al pedido: " + adaptador.getItems().size());
        buttonCabecera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (containerExpandible.getVisibility() == View.GONE)
                    containerExpandible.setVisibility(View.VISIBLE);
                else
                    containerExpandible.setVisibility(View.GONE);
            }
        });
    }


    public AdaptadorPedidoProvisional getAdaptador() {
        return adaptador;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

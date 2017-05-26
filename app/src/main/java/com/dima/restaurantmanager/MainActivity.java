package com.dima.restaurantmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAGLOG = "firebase-db";

    private Button button1;
    private Button button2;

    private DatabaseReference dbMesaLibre;
    private ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        dbMesaLibre = FirebaseDatabase.getInstance().getReference().child("mesas");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (Boolean.parseBoolean(dataSnapshot.child("mesa1").child("libre").getValue().toString())){
                    button1.setBackgroundColor(0xFF00FF00);
                }else{
                    button1.setBackgroundColor(0xFFFF0000);
                }

                if (Boolean.parseBoolean(dataSnapshot.child("mesa2").child("libre").getValue().toString())){
                    button2.setBackgroundColor(0xFF00FF00);
                }else{
                    button2.setBackgroundColor(0xFFFF0000);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };
        dbMesaLibre.addValueEventListener(eventListener);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    public void onClick(View v) {
        //Creamos el Intent
        Intent intent = new Intent(MainActivity.this, MesaActivity.class);

        //Creamos la información a pasar entre actividades
        Bundle b = new Bundle();
        b.putString("numero_mesa", ((Button) findViewById(v.getId())).getText().toString().substring(5));

        //Añadimos la información al intent
        intent.putExtras(b);

        //Iniciamos la nueva actividad
        startActivity(intent);

        v.findViewById(v.getId());
    }


}

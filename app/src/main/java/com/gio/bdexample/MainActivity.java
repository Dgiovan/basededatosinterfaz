package com.gio.bdexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference mibasededatos;
    FirebaseDatabase instanciandolabase;
    EditText Orden
             ,Tipo,
              Folio,
              Material;
    Button   insertar,
             actualizar,
             consultar;
    TextView resultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Orden = findViewById(R.id.orden);
        Tipo  = findViewById(R.id.tipo);
        Folio = findViewById(R.id.folio);
        Material = findViewById(R.id.material);
        insertar = findViewById(R.id.registrar);
        actualizar = findViewById(R.id.actualizar);
        consultar  = findViewById(R.id.Consultar);
        resultado  = findViewById(R.id.resultado);

        insertar.setOnClickListener(this);
        actualizar.setOnClickListener(this);
        consultar.setOnClickListener(this);
        /**
         * crea la base de datos desde nuestra aplicacion*/
        instanciandolabase = FirebaseDatabase.getInstance();
        /**Creara un padre llamado Trabajadores*/
        mibasededatos = instanciandolabase.getReference("Documentos");
        /**esto creara un identificador unico para el usuario*/


    }

    private void agregarDocumento(String tipo,String folio,String material)
    {
        /**crea un objeto de tipo Trabajadores y envia los datos*/
        Documento trabajador = new Documento(tipo,folio,material);
        /**crea un objeto de tipo Trabajador y Agrega un identificador unico*/
       mibasededatos.child("Documento").child(Orden.getText().toString()).setValue(trabajador);
    }

    private void InsertaralaBD(){
        agregarDocumento(Tipo.getText().toString(),Folio.getText().toString(),Material.getText().toString());
    }
    private void Actualizar (String tipo,String folio,String material)
    {
        mibasededatos.child("Documento").child(Orden.getText().toString()).child("tipo").setValue(tipo);
        mibasededatos.child("Documento").child(Orden.getText().toString()).child("folio").setValue(folio);
        mibasededatos.child("Documento").child(Orden.getText().toString()).child("material").setValue(material);
    }

    private void ObtenerDatos(){
        mibasededatos.child("Documento").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot db : dataSnapshot.getChildren())
                    {
                        String tipo = db.child("tipo").getValue(String.class);
                        String folio = db.child("folio").getValue(String.class);
                        String material = db.child("material").getValue(String.class);

                        resultado.append("tipo "+tipo+"\n"+"folio "+folio+"\n"+"material "+material);
                    }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.registrar:
                if (Orden.getText().toString().equals("")){
                    Orden.setError("Este campo es necesario");
                }else if (Tipo.getText().toString().equals("")||Folio.getText().toString().equals("")||Material.getText().toString().equals(""))
                {
                    Toast.makeText(this, "llena todos los campos", Toast.LENGTH_SHORT).show();
                }else {
                InsertaralaBD();}
                break;
            case R.id.actualizar:
                if (Orden.getText().toString().equals(""))
                {
                    Orden.setError("Necesitas el numero de orden para actualizar");
                }else {
                    Actualizar(Tipo.getText().toString(), Folio.getText().toString(), Material.getText().toString());
                }
                break;
            case R.id.Consultar:
                resultado.setText("");
                ObtenerDatos();
                break;
        }
    }
}

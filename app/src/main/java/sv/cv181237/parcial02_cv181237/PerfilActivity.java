package sv.cv181237.parcial02_cv181237;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilActivity extends AppCompatActivity {

    public static FirebaseDatabase Database = FirebaseDatabase.getInstance();
    public static DatabaseReference refUsuarios = Database.getReference("Usuarios");
    private EditText txt_nombre, txt_apellido, txt_carnet, txt_telefono, txt_edad;
    private String nombre, apellido, carnet, telefono, edad, proveedor, provider;//variables para crear el objeto que se va a guardar en el servicio de realtime database
    private String nombreF, apellidoF, carnetF, telefonoF, edadF, keyF = ""; // para rellenar los inputs con los valores asociados a la cuenta
    private Button guardarBtn, regresarBtn;


    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();


    private DatabaseReference mread;// para recuperar datos guardados en realtime database


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        InicializarUI();

        guardarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });

        regresarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresar();
            }
        });


        Log.e("ANTES DEL IF", " no lo esta haciendo");
        if (user != null) {
            Log.e("EN EL IF", " si esta haciendo");
            for (UserInfo perfileUsuario : user.getProviderData()) {

                provider = perfileUsuario.getProviderId();

                if (provider.equals("facebook.com")) {

                    proveedor = provider;
                    Log.e(" FOR USERINFO proveedor", provider);


                } else if (provider.equals("google.com")) {
                    proveedor = provider;
                    Log.e(" FOR USERINFO proveedor", provider);

                }

            }


        }


        mread = FirebaseDatabase.getInstance().getReference();

        mread.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (final DataSnapshot registros : snapshot.getChildren()) {


                    mread.child("Usuarios").child(registros.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Persona persona = registros.getValue(Persona.class);

                            Log.e("proveedor guardado   ", persona.getProveedor());

                            if (persona.getProveedor().equals("facebook.com") && proveedor.equals("facebook.com")) {

                                keyF = registros.getKey();
                                nombreF = persona.getNombre();
                                apellidoF = persona.getApellido();
                                carnetF = persona.getCarnet();
                                telefonoF = persona.getTelefono();
                                edadF = persona.getEdad();
                            } else if (persona.getProveedor().equals("google.com") && proveedor.equals("google.com")) {

                                keyF = registros.getKey();
                                nombreF = persona.getNombre();
                                apellidoF = persona.getApellido();
                                carnetF = persona.getCarnet();
                                telefonoF = persona.getTelefono();
                                edadF = persona.getEdad();
                            }

                            txt_nombre.setText(nombreF);
                            txt_apellido.setText(apellidoF);
                            txt_carnet.setText(carnetF);
                            txt_telefono.setText(telefonoF);
                            txt_edad.setText(edadF);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void InicializarUI() {
        txt_nombre = findViewById(R.id.editNombre);
        txt_apellido = findViewById(R.id.editApellido);
        txt_carnet = findViewById(R.id.editCarnet);
        txt_telefono = findViewById(R.id.editTelefono);
        txt_edad = findViewById(R.id.editEdad);

        guardarBtn = findViewById(R.id.guardarBtn);
        regresarBtn = findViewById(R.id.regresarBtn);

    }


    private void guardar() {

        nombre = txt_nombre.getText().toString();
        apellido = txt_apellido.getText().toString();
        carnet = txt_carnet.getText().toString();
        telefono = txt_telefono.getText().toString();
        edad = txt_edad.getText().toString();


        Persona person = new Persona(nombre, apellido, carnet, telefono, edad, proveedor);


//        Log.e("proveedor ", proveedor);

        if (keyF == "") {// si es la primera vez que entra a la app significa que en la bd no hay una key asociada

            refUsuarios.push().setValue(person);
        } else
            refUsuarios.child(keyF).setValue(person);


        Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show();

        Log.e("nombre - ", nombre);
        Log.e("apellido", apellido);
        Log.e("carnet", carnet);
        Log.e("telefono", telefono);
        Log.e("edad", edad);


    }


    private void regresar() {
        startActivity(new Intent(this, DashboardActivity.class));
    }


}
package sv.cv181237.parcial02_cv181237;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class DashboardActivity extends AppCompatActivity {
    String email, mensaje, foto, provider;
    private Button perfilBtn, salirBtn;
    private FirebaseAuth mAuth;
    private TextView saludo;
    private ImageView imgFoto;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth = FirebaseAuth.getInstance();
        initializeUI();

        //leyendo datos apartir de la instancia
        user = mAuth.getCurrentUser();

        if (user != null) {

            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                provider = profile.getProviderId();
                email = profile.getEmail();
                mensaje = "¡Bienvenido de nuevo " + email;

                if (provider.equals("facebook.com")) {
                    Log.e("proveedor", provider);
                    foto = user.getPhotoUrl().toString();

                } else if (provider.equals("google.com")) {
                    Log.e("proveedor", provider);
                    foto = user.getPhotoUrl().toString();
                }

            }


        }

        saludo.setText(mensaje);
        Picasso.get().load(foto).resize(200, 200).into(imgFoto);
        perfilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perfilOpen();
            }
        });


        salirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void signOut() {
        mAuth.signOut();
        startActivity(new Intent(this, MainActivity.class));
    }

    private void perfilOpen() {

        startActivity(new Intent(this, PerfilActivity.class));

    }

    private void initializeUI() {
        perfilBtn = findViewById(R.id.btnPerfil);
        saludo = findViewById(R.id.welcome);
        imgFoto = findViewById(R.id.imageView);
        salirBtn = findViewById(R.id.btnSalir);
    }


}
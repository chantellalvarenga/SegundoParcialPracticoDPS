package com.example.chalonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    private EditText edt1, edt2;
    private Button btn1, btn2;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    int RC_SIGN_IN = 1;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();

        edt1 = (EditText)findViewById(R.id.txtemaillogin);
        edt2 = (EditText)findViewById(R.id.txtpasslogin);
        btn1 = (Button)findViewById(R.id.btnlogin);
        btn2 = (Button)findViewById(R.id.btnlogingoogle);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    Intent intentDashboard = new Intent(getApplicationContext(), activity_selecion.class);
                    intentDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentDashboard);
                }
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()) {
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    Toast.makeText(this, "Ocurrio un error. " + task.getException().toString(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Ocurrio un error. " + task.getException().toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,"Sesión iniciada con éxito",Toast.LENGTH_SHORT).show();
                            Intent i2 = new Intent(LoginActivity.this,activity_selecion.class);
                            startActivity(i2);
                        } else {
                            Toast.makeText(LoginActivity.this, "Ocurrio un error. " + task.getException().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        firebaseAuth.addAuthStateListener(mAuthStateListener);
        super.onStart();
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void SignGoogle(View view){
        signIn();
    }

    public void ToRegister(View view){
        Intent i = new Intent(this,activity_loginuser.class);
        startActivity(i);
    }

    public void loginearse(View view){
        String email = edt1.getText().toString();
        String password = edt2.getText().toString();

        if (password.length() == 0 || email.length() == 0)
            Toast.makeText(LoginActivity.this,"No pueden haber campos en blanco.",Toast.LENGTH_SHORT).show();
        else{
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Sesión iniciada con éxito",Toast.LENGTH_SHORT).show();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Intent i2 = new Intent(LoginActivity.this,activity_selecion.class);
                        startActivity(i2);
                    }else{
                        String ErrorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                        Toast.makeText(LoginActivity.this,ErrorCode,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    //public void IrSelecciones(View view)
    //{
      //  Intent seleccion=new Intent(this,activity_selecion.class);
       // seleccion.putExtra("nombres","Josue");
        //seleccion.putExtra("apellidos","Chacon");
        //startActivity(seleccion);
   // }
}
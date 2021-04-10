package com.example.chalonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class activity_loginuser extends AppCompatActivity {
    private EditText edt1, edt2, edt3, edt4;
    private Button btn1, btn2;
    FirebaseAuth firebaseAuth;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginuser);

        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.txtemailuserlog, Patterns.EMAIL_ADDRESS,R.string.wrong_mail);
        awesomeValidation.addValidation(this,R.id.txtpassuserlog, ".{6,}",R.string.wrong_pass);

        edt1 = (EditText)findViewById(R.id.txtnameuserlog);
        edt2 = (EditText)findViewById(R.id.txtsnameuserlog);
        edt3 = (EditText)findViewById(R.id.txtemailuserlog);
        edt4 = (EditText)findViewById(R.id.txtpassuserlog);
        btn1 = (Button)findViewById(R.id.btnregisteruserlog);
        btn2 = (Button)findViewById(R.id.btnbackuserlog);
    }

    public void IngresarUser(View view){
        String nombre = edt1.getText().toString();
        String apellido = edt2.getText().toString();
        String correo = edt3.getText().toString();
        String contra = edt4.getText().toString();

        if (nombre.length() == 0 || apellido.length() == 0 || correo.length() == 0 || contra.length() == 0)
            Toast.makeText(activity_loginuser.this,"No pueden haber campos en blanco.",Toast.LENGTH_SHORT).show();
        else{
            if (awesomeValidation.validate()){
                firebaseAuth.createUserWithEmailAndPassword(correo, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(activity_loginuser.this,"Usuario creado con éxito",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(activity_loginuser.this,LoginActivity.class);
                            startActivity(i);
                            FirebaseAuth.getInstance().signOut();
                            activity_loginuser.this.finish();
                        }else{
                            String ErrorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            Toast.makeText(activity_loginuser.this,ErrorCode,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(activity_loginuser.this,"Los datos ingresados están incompletos",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public  void back(View view){
        activity_loginuser.this.finish();
    }
}
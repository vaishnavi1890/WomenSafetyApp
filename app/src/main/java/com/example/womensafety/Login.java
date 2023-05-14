package com.example.womensafety;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity implements View.OnClickListener {


    EditText mem,mps;
    Button loginbtn;
    TextView txt;

    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mem = (EditText) findViewById(R.id.inputEmail);
        mps = (EditText) findViewById(R.id.inputPssword);
        loginbtn = (Button) findViewById(R.id.button2);
        txt = (TextView) findViewById(R.id.dontHaveAccount);


        db = FirebaseFirestore.getInstance();
        loginbtn.setOnClickListener(this);
        txt.setOnClickListener(this);

    }
    public void onClick (View v)
    {

        switch (v.getId()) {
            case R.id.button2:
                if (mem.getText().toString().equals("")) {
                    Toast.makeText(Login.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                } else if (mps.getText().toString().equals("")) {
                    Toast.makeText(Login.this, "Please enter valid password", Toast.LENGTH_SHORT).show();
                } else if (mem!=null && mem.getText().toString().equals("admin@gmail.com") && mps.getText().toString().equals("admin123")) {
                    startActivity(new Intent(Login.this,AdminSplash.class));
                }

                db.collection("user")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                        String a = doc.getString("Email");
                                        String b = doc.getString("Password");
                                        String a1 = mem.getText().toString().trim();
                                        String b1 = mps.getText().toString().trim();

                                        if (a!=null && a.equals(a1) && b.equals(b1)) {
                                            startActivity(new Intent(Login.this,Dashboard.class));
                                            Toast.makeText(Login.this, "Logged In", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                        else{
                                           Toast.makeText(Login.this, "Cannot login,incorrect Email and Password", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                }
                            }
                        });


                break;

            case R.id.dontHaveAccount:
                Intent register_view = new Intent(Login.this, Register.class);
                startActivity(register_view);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
package com.example.womensafety;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {


    EditText mname,memail,mpass,mconf;
    Button reg;
    TextView txt1;

    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mname=(EditText) findViewById(R.id.inputUsername);
        memail=(EditText) findViewById(R.id.inputEmail);
        mpass=(EditText) findViewById(R.id.inputPssword);
        mconf=(EditText) findViewById(R.id.inputConfirmPassword);
        reg=(Button) findViewById(R.id.btnRegister);
        txt1=(TextView) findViewById(R.id.alreadyHaveAccount);

        firebaseFirestore=FirebaseFirestore.getInstance();
        ref = firebaseFirestore.collection("user").document();

        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mname.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "Please type a username", Toast.LENGTH_SHORT).show();

                }else if(memail.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "Please type an email id", Toast.LENGTH_SHORT).show();

                }else if(mpass.getText().toString().equals("")){
                    Toast.makeText(Register.this, "Please type a password", Toast.LENGTH_SHORT).show();

                }else if(!mconf.getText().toString().equals(mpass.getText().toString())){
                    Toast.makeText(Register.this, "Password mismatch", Toast.LENGTH_SHORT).show();

                }else {

                    ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if (documentSnapshot.exists())
                            {
                                Toast.makeText(Register.this, "Sorry,this user exists", Toast.LENGTH_SHORT).show();
                            } else {
                                Map<String, Object> reg_entry = new HashMap<>();
                                reg_entry.put("Name", mname.getText().toString());
                                reg_entry.put("Email", memail.getText().toString());
                                reg_entry.put("Password", mpass.getText().toString());

                                //   String myId = ref.getId();
                                firebaseFirestore.collection("user")
                                        .add(reg_entry)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(Register.this, "Successfully added", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(Register.this,Login.class));
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Error", e.getMessage());
                                            }
                                        });
                            }
                        }
                    });
                }
            }
        });

    }
}
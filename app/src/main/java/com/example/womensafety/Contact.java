package com.example.womensafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Contact extends AppCompatActivity {
ImageView back;
Button addcnt;
EditText nm,cnt;

Button viewcnt;

    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        back=findViewById(R.id.backbtn);
        addcnt=findViewById(R.id.addContact);
        cnt=findViewById(R.id.contactGet);
        nm=findViewById(R.id.nameGet);
        viewcnt=findViewById(R.id.viewContact);

        firebaseFirestore=FirebaseFirestore.getInstance();
        ref = firebaseFirestore.collection("contact").document();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Contact.this,Dashboard.class));
            }
        });

        viewcnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Contact.this,MainActivity2.class));
            }
        });

        addcnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cnt.getText().toString().equals("") && nm.getText().toString().equals("")) {
                    Toast.makeText(Contact.this, "Please enter all details!", Toast.LENGTH_SHORT).show();
                } else {

                }
                ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists()) {
                            Toast.makeText(Contact.this, "Sorry,this name or number already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            Map<String, Object> reg_entry = new HashMap<>();
                            reg_entry.put("Name", nm.getText().toString());
                            reg_entry.put("Contact", cnt.getText().toString());

                            //   String myId = ref.getId();
                            firebaseFirestore.collection("contact")
                                    .add(reg_entry)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(Contact.this, "Successfully added", Toast.LENGTH_SHORT).show();

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
        });
    }
}
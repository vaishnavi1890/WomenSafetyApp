package com.example.womensafety;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    RecyclerView recylerview;
    ArrayList<cnt> cntArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore db;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data.....");
        progressDialog.show();

        recylerview=findViewById(R.id.recylerview);
        recylerview.setHasFixedSize(true);
        recylerview.setLayoutManager(new LinearLayoutManager(this));

        db=FirebaseFirestore.getInstance();
        cntArrayList=new ArrayList<cnt>();
        myAdapter=new MyAdapter(MainActivity2.this,cntArrayList);

        recylerview.setAdapter(myAdapter);

        EventChangeListner();
    }

    private void EventChangeListner() {
        db.collection("contact").orderBy("Name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null){

                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    Log.e("Firestore error",error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if (dc.getType() == DocumentChange.Type.ADDED){
                        cntArrayList.add(dc.getDocument().toObject(cnt.class));
                    }
                    myAdapter.notifyDataSetChanged();
                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }

            }
        });
    }
}
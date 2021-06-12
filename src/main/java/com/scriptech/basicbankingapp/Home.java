package com.scriptech.basicbankingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    List<Model> modelList_showlist = new ArrayList<>();
    RecyclerView mRecyclerView;
    Adapter_users adapter;
    String phonenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Button view_transactions;
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        view_transactions = (Button) findViewById(R.id.transaction);
        view_transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, transactions.class);
                startActivity(i);
            }
        });
        showData();
    }

    private void showData() {
        modelList_showlist.clear();
        Cursor cursor = new Database(this).readalldata();
        while (cursor.moveToNext()) {
            String balance = cursor.getString(4);
            Double balance1 = Double.parseDouble(balance);

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String price = nf.format(balance1);

            Model model = new Model(cursor.getString(0), cursor.getString(2), price, cursor.getString(1));
            modelList_showlist.add(model);
        }

        adapter = new Adapter_users(Home.this, modelList_showlist);
        mRecyclerView.setAdapter(adapter);

    }

    public void nextActivity(int position) {
        phonenumber = modelList_showlist.get(position).getPhoneno();
        Intent intent = new Intent(Home.this, UserDetails.class);
        intent.putExtra("phonenumber", phonenumber);
        startActivity(intent);
    }
}
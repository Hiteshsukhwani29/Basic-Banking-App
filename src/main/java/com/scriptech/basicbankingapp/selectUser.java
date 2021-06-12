package com.scriptech.basicbankingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class selectUser extends AppCompatActivity {

    List<Model> modelList_selectUser = new ArrayList<>();
    RecyclerView mRecyclerView;
    Adapter_selectUser adapter;

    String phonenumber, name, currentamount, transferamount, remainingamount,description;
    String selectuser_phonenumber, selectuser_name, selectuser_balance, date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectuser);

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy, hh:mm a");
        date = simpleDateFormat.format(calendar.getTime());

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            phonenumber = bundle.getString("phonenumber");
            name = bundle.getString("name");
            currentamount = bundle.getString("currentamount");
            transferamount = bundle.getString("transferamount");
            description = bundle.getString("description");
            showData(phonenumber);
        }
    }

    private void showData(String phonenumber) {
        modelList_selectUser.clear();
        Log.d("DEMO",phonenumber);
        Cursor cursor = new Database(this).readselectuserdata(phonenumber);
        while(cursor.moveToNext()){
            String balancefromdb = cursor.getString(4);
            Double balance = Double.parseDouble(balancefromdb);

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String price = nf.format(balance);

            Model model = new Model(cursor.getString(0), cursor.getString(2), price,cursor.getString(1));
            modelList_selectUser.add(model);
        }

        adapter = new Adapter_selectUser(selectUser.this, modelList_selectUser);
        mRecyclerView.setAdapter(adapter);
    }

    public void selectuser(int position) {
       selectuser_phonenumber = modelList_selectUser.get(position).getPhoneno();
        Cursor cursor = new Database(this).readparticulardata(selectuser_phonenumber);
        while(cursor.moveToNext()) {
            selectuser_name = cursor.getString(2);
            selectuser_balance = cursor.getString(4);
            Double Dselectuser_balance = Double.parseDouble(selectuser_balance);
            Double Dselectuser_transferamount = Double.parseDouble(transferamount);
            Double Dselectuser_remainingamount = Dselectuser_balance + Dselectuser_transferamount;

            new Database(this).insertTransferData(date, name, selectuser_name, transferamount, description);
            new Database(this).updateAmount(selectuser_phonenumber, Dselectuser_remainingamount.toString());
            calculateAmount();
            Toast.makeText(this, "Transaction Successful", Toast.LENGTH_LONG).show();
            startActivity(new Intent(selectUser.this, Home.class));
            finish();
        }
    }

    private void calculateAmount() {
        Double Dcurrentamount = Double.parseDouble(currentamount);
        Double Dtransferamount = Double.parseDouble(transferamount);
        Double Dremainingamount = Dcurrentamount - Dtransferamount;
        remainingamount = Dremainingamount.toString();
        new Database(this).updateAmount(phonenumber, remainingamount);
    }

    @Override
    public void onBackPressed() {
                        startActivity(new Intent(selectUser.this, Home.class));
                        finish();
    }

}

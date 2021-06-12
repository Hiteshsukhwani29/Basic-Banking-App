package com.scriptech.basicbankingapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;


public class UserDetails extends AppCompatActivity {

    ProgressDialog progressDialog;
    String phonenumber;
    Double balance2;
    TextView fname, lname, phoneNumber, email, account_no, ifsc_code, balance,card_name,card_account_no;
    Button transfer_button;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        card_name = findViewById(R.id.name_card);
        phoneNumber = findViewById(R.id.userphonenumber);
        email = findViewById(R.id.email);
        account_no = findViewById(R.id.account_no);
        card_account_no = findViewById(R.id.account_no1);
        ifsc_code = findViewById(R.id.ifsc_code);
        balance = findViewById(R.id.balance);
        transfer_button = findViewById(R.id.transfer_button);

        progressDialog = new ProgressDialog(UserDetails.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            phonenumber = bundle.getString("phonenumber");
            showData(phonenumber);
        }

        transfer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAmount();
            }
        });
    }

    private void showData(String phonenumber) {
        Cursor cursor = new Database(this).readparticulardata(phonenumber);
        while(cursor.moveToNext()) {
            String balance1 = cursor.getString(4);
            balance2 = Double.parseDouble(balance1);

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String price = nf.format(balance2);

            progressDialog.dismiss();

            phoneNumber.setText(cursor.getString(0));
            fname.setText(cursor.getString(2)+" ");
            lname.setText(cursor.getString(3));
            card_name.setText(cursor.getString(2)+" "+cursor.getString(3));
            email.setText(cursor.getString(5));
            balance.setText(price);
            account_no.setText(cursor.getString(6));
            card_account_no.setText(cursor.getString(6));
            ifsc_code.setText(cursor.getString(7));
        }

    }

    private void enterAmount() {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(UserDetails.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_view_amount, null);
        mBuilder.setTitle("Send Money").setView(mView).setCancelable(false);

        final EditText mAmount = (EditText) mView.findViewById(R.id.enter_money);
        final EditText mDesc = (EditText) mView.findViewById(R.id.enter_description);

        mBuilder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog = mBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAmount.getText().toString().isEmpty()){
                    mAmount.setError("Amount can't be empty");
                }else if(Double.parseDouble(mAmount.getText().toString()) > balance2){
                    mAmount.setError("Your account don't have enough balance");
                }else{
                    Intent intent = new Intent(UserDetails.this, selectUser.class);
                    intent.putExtra("phonenumber", phoneNumber.getText().toString());
                    intent.putExtra("name", fname.getText().toString());
                    intent.putExtra("currentamount", balance2.toString());
                    intent.putExtra("transferamount", mAmount.getText().toString());
                    intent.putExtra("description", mDesc.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

}

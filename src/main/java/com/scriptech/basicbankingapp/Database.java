package com.scriptech.basicbankingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private String TABLE_1 = "EBANK_USERS";
    private String Table_2 = "EBANK_TRANSACTIONS";

    public Database(@Nullable Context context) {
        super(context, "User.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_1 +" (PHONENUMBER INTEGER PRIMARY KEY ,IMAGE TEXT,FNAME TEXT,LNAME TEXT,BALANCE DECIMAL,EMAIL VARCHAR,ACCOUNT_NO VARCHAR,IFSC_CODE VARCHAR)");
        db.execSQL("create table " + Table_2 +" (TRANSACTIONID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT,FROMNAME TEXT,TONAME TEXT,AMOUNT DECIMAL,DESCRIPTION TEXT)");
        db.execSQL("insert into "+TABLE_1+" values(7689687987,'1','Om','Gupta',3000.00,'omgupta01@gmail.com','XXXX-XXXX-XXXX-6878','ALB3782382828')");
        db.execSQL("insert into "+TABLE_1+" values(7779783900,'2','Sourabh','Singh',4500.65,'ssingh02@gmail.com','XXXX-XXXX-XXXX-8080','SBI3782382828')");
        db.execSQL("insert into "+TABLE_1+" values(9988797990,'1','Pratham','Sharma',6800.50,'prathams03@gmail.com','XXXX-XXXX-XXXX-5678','SBI3782382828')");
        db.execSQL("insert into "+TABLE_1+" values(7876787689,'3','Praveen','Sharma',1500.88,'praveen.sharma04@gmail.com','XXXX-XXXX-XXXX-5678','PNB3782382828')");
        db.execSQL("insert into "+TABLE_1+" values(9307876787,'2','Aditya','Jain',5400.50,'jainaditya05@gmail.com','XXXX-XXXX-XXXX-0989','ICICI32382828')");
        db.execSQL("insert into "+TABLE_1+" values(6236787839,'3','Hitesh','Sukhwani',8450.60,'hiteshsukhwani@gmail.com','XXXX-XXXX-XXXX-2345','BOI3782382828')");
        db.execSQL("insert into "+TABLE_1+" values(7364782379,'5','Priya','Singh',3782.00,'priyas07@gmail.com','XXXX-XXXX-XXXX-7678','SBI3782382828')");
        db.execSQL("insert into "+TABLE_1+" values(9878987678,'5','Anushka','Tripathi',8128.10,'tripathianushka08@gmail.com','XXXX-XXXX-XXXX-6789','BOB3782382828')");
        db.execSQL("insert into "+TABLE_1+" values(8987678987,'2','Ajit','Singh',4005.40,'ajitsingh09@gmail.com','XXXX-XXXX-XXXX-2321','DNBK378238828')");
        db.execSQL("insert into "+TABLE_1+" values(9999966627,'4','Priyanka','Bari',2403.99,'priyanka0101@gmail.com','XXXX-XXXX-XXXX-0010','PNB3782382828')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_1);
        db.execSQL("DROP TABLE IF EXISTS "+ Table_2);
        onCreate(db);
    }

    public Cursor readalldata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_1, null);
        return cursor;
    }

    public Cursor readparticulardata(String phonenumber){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_1+" where phonenumber = " +phonenumber, null);
        return cursor;
    }

    public Cursor readselectuserdata(String phonenumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_1+" except select * from "+TABLE_1+" where phonenumber = " +phonenumber, null);
        return cursor;
    }

    public void updateAmount(String phonenumber, String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update "+TABLE_1+" set balance = " + amount + " where phonenumber = " +phonenumber);
    }

    public Cursor readtransferdata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ Table_2, null);
        return cursor;
    }

    public boolean insertTransferData(String date,String from_name, String to_name, String amount, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("DATE", date);
        contentValues.put("FROMNAME", from_name);
        contentValues.put("TONAME", to_name);
        contentValues.put("AMOUNT", amount);
        contentValues.put("DESCRIPTION", description);
        Long result = db.insert(Table_2, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }


}

package com.example.mysqlitedemo;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText idET, nameET, surenameET, phET;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idET = findViewById(R.id.idET);
        nameET = findViewById(R.id.nameET);
        surenameET = findViewById(R.id.surenameET);
        phET = findViewById(R.id.phET);
        db = new DatabaseHelper(this);

        //myDb = new DatabaseHelper(this);
        setBtnClicks();
    }

    private void setBtnClicks()
    {
        // ADD
        findViewById(R.id.button_add).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (db.insertData(nameET.getText().toString(),
                                surenameET.getText().toString(),
                                phET.getText().toString()))
                            QuickToast("Inserted...");
                        else
                            QuickToast("Failed...");
                    }
                }
        );

        // View All
        findViewById(R.id.button_viewAll).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor cursor = db.getAllData();
                        if (cursor.getCount() == 0)
                        {
                            QuickToast("No data!");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (cursor.moveToNext())
                        {
                            //" (ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                            //"NAME TEXT, SURENAME TEXT, PHONE TEXT)");
                            buffer.append("Id :" + cursor.getString(0) + "\n");
                            buffer.append("Name :" + cursor.getString(1) + "\n");
                            buffer.append("Surename :" + cursor.getString(2) + "\n");
                            buffer.append("Phone :" + cursor.getString(3) + "\n");
                        }
                        showMessage("View All", buffer.toString());
                    }
                }
        );
    }

    private void QuickToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


}
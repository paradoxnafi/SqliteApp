package com.paradoxnafi.sqliteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper scheduleDb;

    EditText deviceID, medicineID, quantity, time;
    Button submut_button;
    Button viewall_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scheduleDb = new DatabaseHelper(this);

        deviceID = (EditText) findViewById(R.id.machine_id_input);
        medicineID = (EditText) findViewById(R.id.medicine_id_input);
        quantity = (EditText) findViewById(R.id.quantity_input);
        time = (EditText) findViewById(R.id.time_input);
        submut_button = (Button) findViewById(R.id.button_submit_entry);
        viewall_button = (Button) findViewById(R.id.button_view_all);

        AddData();
        viewAll();
    }

    public void AddData() {
        submut_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted =  scheduleDb.insertData(
                                deviceID.getText().toString(),
                                medicineID.getText().toString(),
                                quantity.getText().toString(),
                                time.getText().toString()
                        );

                        if(isInserted = true) {
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Data is not Inserted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void viewAll() {
        viewall_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = scheduleDb.getAllData();
                        if(res.getCount() == 0) {

                            // Show message
                            showMessage("Error", "No data found");
                            return;
                        }

                        StringBuffer buffer =  new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("No :" + res.getString(0) + "\n");
                            buffer.append("Device :" + res.getString(1) + "\n");
                            buffer.append("Medicine :" + res.getString(2) + "\n");
                            buffer.append("Quantity :" + res.getString(3) + "\n");
                            buffer.append("Time :" + res.getString(4) + "\n\n");
                        }

                        // Show all data
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}

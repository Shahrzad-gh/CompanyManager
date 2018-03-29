package ir.quera.companymanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {


    private Button btn_register;
    private Button btn_report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StaffDataBaseAdapter staffAdapter = new StaffDataBaseAdapter(this);

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_report = (Button) findViewById(R.id.btn_report);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(register);
            }
        });

        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent report = new Intent(MainActivity.this,ReportActivity.class);
                startActivity(report);

            }
        });

    }
}




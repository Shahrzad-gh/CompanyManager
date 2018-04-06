package ir.quera.companymanager;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ReportActivity extends Activity  {

    private Button getReport;
    private Spinner getPosition;
    private EditText Code;
    private Button getPerson;
    private EditText Top;
    private Button getTop;
    private TextView report;
    StaffDataBaseAdapter staffDatabaseAdapter;
    Staff staff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        getPosition = (Spinner)findViewById(R.id.drop_report_position);
        getReport = (Button)findViewById(R.id.btn_payment);
        Code = (EditText)findViewById(R.id.edit_get_code);
        getPerson = (Button)findViewById(R.id.btn_person);
        Top = (EditText)findViewById(R.id.edit_top);
        getTop = (Button)findViewById(R.id.btn_top);
        report = (TextView)findViewById(R.id.txt_report);
        staffDatabaseAdapter = new StaffDataBaseAdapter(ReportActivity.this);

        getReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                report.setText(staffDatabaseAdapter.getSalary(getPosition.getSelectedItemPosition()));

            }
        });

        getPerson.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                report.setText("");
                String person ;
                if(Code.getText().toString().matches("")){
                    Code.setHintTextColor(Color.parseColor("#FF0000"));
                }else {
                    report.setText("");
                    person = staffDatabaseAdapter.getPerson(Code.getText().toString());
                    Log.d("person ","Is:"+person.length());
                    if (person.length()==1) {
                        report.setText("Not found");
                    }
                    else report.setText(person);
                }

                }
        }));

        getTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report.setText("");
                String person ;
                //Log.d("getTop","IS:"+ staffDatabaseAdapter.getTop(Integer.parseInt(Top.getText().toString())));
                if(Top.getText().toString().matches("")){
                    Top.setHintTextColor(Color.parseColor("#FF0000"));
                }else{
                    report.setText("");
                    person = staffDatabaseAdapter.getTop(Integer.parseInt(Top.getText().toString()));
                    Log.d("person ","Is:"+person.length());
                    if (Integer.parseInt(Top.getText().toString()) >=4) {
                        report.setText("Not found");
                    }
                    else report.setText(person);
                }

            }
        });
        
    }

}

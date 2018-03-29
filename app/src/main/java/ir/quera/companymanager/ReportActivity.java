package ir.quera.companymanager;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class ReportActivity extends Activity {

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

        getPosition = (Spinner)findViewById(R.id.drop_position);
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

                if(getPosition.getSelectedItem() != null){
                    report.setText("");
                    report.setText(staffDatabaseAdapter.getSalary(getPosition.getSelectedItemPosition()));
                }else
                    Toast.makeText(ReportActivity.this, "Select a position", Toast.LENGTH_LONG).show();
            }
        });

        getPerson.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Code.getText() != null){
                    report.setText("");
                    report.setText(staffDatabaseAdapter.getPerson(Integer.parseInt(Code.getText().toString())));
                }else {
                    Code.setHintTextColor(Color.parseColor("FF0000"));
                    report.setText("Not found");
                }
            }
        }));

        getTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getTop.getTag() != null){
                    report.setText("");
                    report.setText(staffDatabaseAdapter.getTop(Integer.parseInt(getTop.getText().toString())));
                }else{
                    Code.setHintTextColor(Color.parseColor("FF0000"));
                    report.setText("Not found");
                }

            }
        });
        
    }
}

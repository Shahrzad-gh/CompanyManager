package ir.quera.companymanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class RegisterActivity extends Activity {
    private Button submit;
    private EditText code;
    private EditText firstName;
    private EditText salary;
    private EditText lastName;
    private EditText hour;
    private Spinner position;
    Staff staff;
    StaffDataBaseAdapter staffDatabaseAdapter;
    private TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        submit = (Button)findViewById(R.id.btn_submit);

        code = (EditText)findViewById(R.id.edit_code);
        firstName = (EditText)findViewById(R.id.edit_firstname);
        lastName = (EditText)findViewById(R.id.edit_lastname);
        salary = (EditText)findViewById(R.id.edit_salary);
        hour = (EditText)findViewById(R.id.edit_hour);
        position = (Spinner)findViewById(R.id.drop_position);

        result = (TextView)findViewById(R.id.txt_result);

        staffDatabaseAdapter = new StaffDataBaseAdapter(RegisterActivity.this);
        staff = new Staff();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(code.getText().toString().matches(""))
                    code.setHintTextColor(Color.parseColor("#FF0000"));
                else
                    staff.setId(code.getText().toString());

                if (firstName.getText().toString().matches(""))
                    firstName.setHintTextColor(Color.parseColor("#FF0000"));
                else
                    staff.setS_name(firstName.getText().toString());

                if (lastName.getText().toString().matches(""))
                    lastName.setHintTextColor(Color.parseColor("#FF0000"));
                else
                    staff.setS_Family(lastName.getText().toString());

                if (salary.getText().toString().matches(""))
                    salary.setHintTextColor(Color.parseColor("#FF0000"));
                else
                    staff.setS_per_hour(Integer.parseInt(salary.getText().toString()));

                if (hour.getText().toString().matches(""))
                    hour.setHintTextColor(Color.parseColor("#FF0000"));
                else
                    staff.setS_hour(Integer.parseInt(hour.getText().toString()));

                staff.setS_position(position.getSelectedItem().toString());

                if (staffDatabaseAdapter.readStaff(staff.getId()) != null){
                    result.setText("Duplicate Personal Code");
                }
                else if(staffDatabaseAdapter.saveStaff(staff) !=0 ){
                    result.setText("Registration successful");
                    finish();
                    startActivity(new Intent(RegisterActivity.this, RegisterActivity.class));
                }

            }
        });
}
}
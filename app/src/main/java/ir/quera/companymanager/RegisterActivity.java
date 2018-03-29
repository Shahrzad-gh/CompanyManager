package ir.quera.companymanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.DropBoxManager;
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
                if(code.getText() == null)
                    code.setHintTextColor(Color.parseColor("#FF0000"));
                else if (firstName.getText() == null)
                    firstName.setHintTextColor(Color.parseColor("#FF0000"));
                else if (lastName.getText() == null)
                    lastName.setHintTextColor(Color.parseColor("#FF0000"));
                else if (salary.getText() == null)
                    salary.setHintTextColor(Color.parseColor("FF0000"));
                else if (hour.getText() == null)
                    salary.setHintTextColor(Color.parseColor("FF0000"));
                else
                {
                    staff.setId(Integer.parseInt(code.getText().toString()));
                    staff.setS_name(firstName.getText().toString());
                    staff.setS_Family(lastName.getText().toString());
                    staff.setS_per_hour(Integer.parseInt(salary.getText().toString()));
                    staff.setS_hour(Integer.parseInt(hour.getText().toString()));
                    staff.setS_position(Integer.parseInt(position.getSelectedItem().toString()));

                    if (staffDatabaseAdapter.updateStaff(staff) > 0){
                        result.setText("Duplicate Personal Code");
                    }

                    else {
                        staffDatabaseAdapter.saveStaff(staff);
                        result.setText("Duplicate Personal Code");
                        startActivity(new Intent(RegisterActivity.this, RegisterActivity.class));
                    }

                }
            }
        });
}
}
package com.example.lab3_csis4280;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONArray;

public class EmployeeSearchActivity extends AppCompatActivity implements OnEventListener<String> {

    EditText txtLastName;
    Button btnSearch;
    Intent intent;
    TextView res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_search);
        intent=getIntent();
        txtLastName=findViewById(R.id.et_lastName);
        btnSearch=findViewById(R.id.btn_search);
        res=findViewById(R.id.txt_res);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intent!=null)
                {
                    String loginValues[] =intent.getStringArrayExtra("loginValues");
                    String loginFields[] =intent.getStringArrayExtra("loginFields");
                    String site=intent.getStringExtra("site");
                    Log.d("employee l....", "onCreate: "+loginValues.length);
                    Log.d("employee v...", "onCreate: "+loginValues[0]);
                    String[] newLoginValues=new String[5];
                    String[] newLoginFields=new String[5];
                    for(int i=0;i<loginFields.length;++i)
                    {
                        newLoginValues[i]= loginValues[i];
                        newLoginFields[i]= loginFields[i];
                    }
                    newLoginValues[3]="EmployeeSearch";
                    String queryT="SELECT * from EMPLOYEE where lastname='"+txtLastName.getText().toString()+"'";
                    newLoginValues[4]=queryT;
                    newLoginFields[4]="query";
                    MainActivity mainActivity=new MainActivity();
                    String params=mainActivity.putParamsTogether(newLoginFields,newLoginValues);
                    Log.d("employee param..", "onClick: "+params);
                    new DownloadAsync(EmployeeSearchActivity.this).execute(site,params);
                }

            }
        });


    }

    @Override
    public void onSuccess(String result) {

        Log.d("res....", "onSuccess: "+result);
        JSONArray ar=null;
        String columns[] = {"EmployeeNumber","FirstName","LastName","Department"};
        res.setText("Result");
        try {
            ar = new JSONArray(result);

        }catch (Exception e)
        {

        }
        if(ar!=null && ar.length()==0)
        {
            res.setText("Sorry no Data found!");
            final TableLayout tbl = findViewById(R.id.tableOutput);
            tbl.removeAllViews();
        }else {
            DataExtractor de = new DataExtractor();
            de.extract(this, result, columns);
        }
    }

    @Override
    public void onFailure(Exception e) {

    }
}

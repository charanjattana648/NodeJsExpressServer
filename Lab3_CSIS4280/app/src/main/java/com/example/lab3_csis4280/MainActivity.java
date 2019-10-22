package com.example.lab3_csis4280;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity implements OnEventListener<String>{

    Button btnSignIn;
    EditText txtName,txtPass,txtDbName;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtName=findViewById(R.id.et_userName);
        txtDbName=findViewById(R.id.et_dbName);
        txtPass=findViewById(R.id.et_pass);
        btnSignIn=findViewById(R.id.btn_SignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MainActivity.this,EmployeeSearchActivity.class);
                String loginFields[]={"name","pwd","db","type"};
                String loginValues[]={txtName.getText().toString(),txtPass.getText().toString(),txtDbName.getText().toString(),"login"};
                String params=putParamsTogether(loginFields,loginValues);
                Log.d("params", "onClick: "+params);
                String site = "http://54.163.223.98:8000";
                intent.putExtra("site",site);
                intent.putExtra("loginFields",loginFields);
                intent.putExtra("loginValues",loginValues);


                new DownloadAsync(MainActivity.this).execute(site,params);
                Log.d("Testing..........", "onClick: "+loginFields.length+" ----- "+loginFields[1]);

            }
        });
    }

    public String putParamsTogether(String[] loginFields, String... loginValues) {
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<loginFields.length;++i)
        {
            try {
                loginValues[i]= URLEncoder.encode(loginValues[i],"UTF-8");
            }catch (UnsupportedEncodingException ux)
            {

            }
            if(sb.length()>0)
            {
                sb.append("&");
            }

                sb.append(loginFields[i]).append("=").append(loginValues[i]);

        }

        return sb.toString();
    }

    @Override
    public void onSuccess(String result) {

        Log.d("rrrrr...", "onSuccess: --"+result+"---");
        String res[]=result.split("\n");
        Log.d("rrrr111...", "onSuccess: --"+res+"---");
        if(res[0].equalsIgnoreCase("0"))
        {
            Log.d("LogIn Failed","Please try again");
            Toast.makeText(this, "Sorry,Log In falied Please try again!", Toast.LENGTH_SHORT).show();

        }else{

            startActivity(intent);
        }

    }

    @Override
    public void onFailure(Exception e) {

    }
}

package com.example.api_test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Network.WebApi;
import com.example.Response.AllUserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
private  ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        //Sign up method
        signUpUser();
    }

    private void signUpUser() {

        Button btn_submit;
        EditText et_name,et_email,et_mobileNo,et_password;

        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_mobileNo = findViewById(R.id.et_mobileNo);
        et_password = findViewById(R.id.et_password);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {




            @Override
            public void onClick(View v) {
                if(et_name.getText().toString().isEmpty())
                {
                    et_name.setError("Enter name");
                }
                    else if(et_email.getText().toString().isEmpty())
                    {
                        et_email.setError("Enter email address");
                    }
                    else if(et_mobileNo.getText().toString().isEmpty())
                    {
                        et_mobileNo.setError("Enter phone number");
                    }
                    else if(et_password.getText().toString().isEmpty())
                    {
                        et_password.setError("Enter password");
                    }
                else {
                    progressDialog.show();
                    WebApi webApi = ApiUtils.getClient().create(WebApi.class);
                    Call<AllUserResponse> call = webApi.register(et_name.getText().toString(),et_email.getText().toString(),et_mobileNo.getText().toString(),et_password.getText().toString());
                    call.enqueue(new Callback<AllUserResponse>() {
                        @Override
                        public void onResponse(Call<AllUserResponse> call, Response<AllUserResponse> response) {
                            progressDialog.dismiss();
                            if (response.code() == 200){
                                // SharedPrefsUtils.setSharedPreferenceString(getApplicationContext(),"access_token",response.body().getAccessToken());
                                // SharedPrefsUtils.setSharedPreferenceString(getApplicationContext(),"login","1");
                                Intent intent = new Intent(MainActivity.this,Simple.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else {
                                Toast.makeText(getApplicationContext(),"Something is wrong",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AllUserResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });

    }
}
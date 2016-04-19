package com.peterstev.unify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.peterstev.unify.login.Staff;
import com.peterstev.unify.login.UnifyAuthenticationApiInterface;
import com.peterstev.unify.login.UnifyAuthenticationApiResponse;

import org.w3c.dom.Text;

import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    EditText emailTextBox;
    EditText passwordTextBox;

    private Retrofit client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setupRetrofit();

        btnLogin = (Button) findViewById(R.id.btn_login);
        emailTextBox = (EditText) findViewById(R.id.et_user);
        passwordTextBox = (EditText) findViewById(R.id.et_pass);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String email = this.emailTextBox.getText().toString();
        String password = this.passwordTextBox.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){

            Toast.makeText(this,"Email and Password cannot be empty",Toast.LENGTH_SHORT).show();
            return;
        }

        v.setActivated(false);

        this.loginUser(email,password);
    }

    private void loginUser(String email, String password) {

        UnifyAuthenticationApiInterface service = this.client.create(UnifyAuthenticationApiInterface.class);
        Call<UnifyAuthenticationApiResponse> call = service.staffLogin(email,password);

        call.enqueue(new Callback<UnifyAuthenticationApiResponse>() {
            @Override
            public void onResponse(Call<UnifyAuthenticationApiResponse> call,
                                   Response<UnifyAuthenticationApiResponse> response) {
                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    UnifyAuthenticationApiResponse result = response.body();

                    Staff staff = result.getData().getStaff();

                    Toast.makeText(MainActivity.this,"Hello, "+staff.getFullName(),Toast.LENGTH_LONG).show();

                    gotoHomeActivity();

                } else {
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UnifyAuthenticationApiResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void gotoHomeActivity(){
        Intent intent = new Intent(MainActivity.this, ChooseSchool.class);
        startActivity(intent);
    }

    private void setupRetrofit(){
        String baseUrl = "http://account.klipboard.com.ng/api/v1/" ;
        this.client = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}

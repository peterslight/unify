package com.peterstev.unify;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.peterstev.unify.login.Staff;
import com.peterstev.unify.login.UnifyAuthenticationApiInterface;
import com.peterstev.unify.login.UnifyAuthenticationApiResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    EditText emailTextBox;
    EditText passwordTextBox;
    String password;
    String email;
    private Staff staff;

    private Retrofit client;
    Button assCourses, assClasses;
    Intent intent;

    private List<String> schools;
    String[] schoolsArray;
    ArrayAdapter<String> schoolsArrayAdapter;
    ListView schoolsListView;
    Dialog dialog;

    private ProgressDialog progressDialog;

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

        email = this.emailTextBox.getText().toString();
        password = this.passwordTextBox.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            Toast.makeText(this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        networkCheck();
    }

    private void loginUser(String email, String password) {

        UnifyAuthenticationApiInterface service = this.client.create(UnifyAuthenticationApiInterface.class);
        Call<UnifyAuthenticationApiResponse> call = service.staffLogin(email, password);

        call.enqueue(new Callback<UnifyAuthenticationApiResponse>() {
            @Override
            public void onResponse(Call<UnifyAuthenticationApiResponse> call,
                                   Response<UnifyAuthenticationApiResponse> response) {
                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    UnifyAuthenticationApiResponse result = response.body();

                    com.peterstev.unify.login.Data data = result.getData();

                    staff = data.getStaff();
                    schools = new ArrayList<String>();
                    schools = data.getSchools();
                    schoolsArray = schools.toArray(new String[]{});


                    Toast.makeText(MainActivity.this, "Hello, " + staff.getFullName(), Toast.LENGTH_LONG).show();

                    gotoHomeActivity();

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UnifyAuthenticationApiResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void gotoHomeActivity() {
        progressDialog.dismiss();
        if (schoolsArray.length > 1) {

            schoolsListView = new ListView(MainActivity.this);

            schoolsArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, schoolsArray);
            schoolsListView.setAdapter(schoolsArrayAdapter);

            dialog = new Dialog(MainActivity.this);
            dialog.setContentView(schoolsListView);
            dialog.setTitle("Welcome " + staff.getFullName());
            dialog.show();


        } else {
            Intent intent = new Intent(MainActivity.this, ChooseSchool.class);
            startActivity(intent);
        }
    }

    private void setupRetrofit() {
        String baseUrl = "http://account.klipboard.com.ng/api/v1/";
        this.client = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void progress() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    public void networkCheck() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo mData = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isConnectedOrConnecting() || mData.isConnectedOrConnecting()) {
            this.loginUser(email, password);
            progress();
        } else {
            alertPopup();
        }
    }

    protected void alertPopup() {

        AlertDialog.Builder build = new AlertDialog.Builder(
                MainActivity.this);
        build.setMessage("YOUR MOBILE DATA IS TURNED OFF." + "\n"
                + "You have to be connected to a WIFI or turn on your MOBILE DATA");
        build.setCancelable(false);
        build.setPositiveButton("Turn On", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(
                        android.provider.Settings.ACTION_SETTINGS);
                startActivity(intent);

            }
        });

        build.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.cancel();

            }
        });
        AlertDialog alert = build.create();
        alert.show();
    }

}
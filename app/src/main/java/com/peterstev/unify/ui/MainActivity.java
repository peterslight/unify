package com.peterstev.unify.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.peterstev.unify.R;
import com.peterstev.unify.adapters.SchoolListAdapter;
import com.peterstev.unify.login.School;
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

    private static final String TAG = MainActivity.class.getSimpleName();
    Button btnLogin;
    EditText emailTextBox;
    EditText passwordTextBox;
    String password, email;
    com.peterstev.unify.login.Data data;
    Intent intent;
    SchoolListAdapter schoolsArrayAdapter;
    String staffName, schoolName;
    ListView schoolsListView;
    Dialog dialog;
    private Staff staff;
    private Retrofit client;
    private List<School> mySchoolsList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.setupRetrofit();

        btnLogin = (Button) findViewById(R.id.btn_login);
        emailTextBox = (EditText) findViewById(R.id.et_user);
        passwordTextBox = (EditText) findViewById(R.id.et_pass);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        intent = new Intent(MainActivity.this, NavMainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                MainActivity.this, transitionView, DetailActivity.EXTRA_IMAGE);
//        ActivityCompat.startActivity(MainActivity.this, new Intent(MainActivity.this, NavMainActivity.class),options.toBundle());

//        passwordTextBox.setText("password");
//       // emailTextBox.setText("admin@admin.com");
//        email = this.emailTextBox.getText().toString();
//        password = this.passwordTextBox.getText().toString();
//
//        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
//
//            Toast.makeText(this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        networkCheck();
    }

    protected void loginUser(String email, String password) {

        UnifyAuthenticationApiInterface service = this.client.create(UnifyAuthenticationApiInterface.class);
        Call<UnifyAuthenticationApiResponse> call = service.staffLogin(email, password);

        call.enqueue(new Callback<UnifyAuthenticationApiResponse>() {
            @Override
            public void onResponse(Call<UnifyAuthenticationApiResponse> call,
                                   Response<UnifyAuthenticationApiResponse> response) {

                if (response.isSuccessful()) {

                    UnifyAuthenticationApiResponse result = response.body();
                    School school = new School();
                    data = result.getData();
                    if (data == null) {
                        try {
                            this.onResponse(call, response);
                        } catch (NullPointerException e) {
                            Log.d("NPE", e.getMessage());
                        }
                    } else if (data != null) {
                        mySchoolsList = new ArrayList<School>();
                        mySchoolsList = data.getSchools();
                        staff = data.getStaff();
                        staffName = staff.getFullName();

                        gotoHomeActivity();
//                        TempPrefrence.saveToPref(MainActivity.this, "PREFS_LOGIN_LOGIN_USERNAME_KEY", emailTextBox.getText().toString());
//                        TempPrefrence.saveToPref(MainActivity.this, "PREFS_LOGIN_LOGIN_PASSWORD_KEY", passwordTextBox.getText().toString());
                        //TempPrefrence.
                        passwordTextBox.getText().clear();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UnifyAuthenticationApiResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Network Failure Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void gotoHomeActivity() {
        progressDialog.dismiss();
            schoolsListView = new ListView(MainActivity.this);
            schoolsArrayAdapter = new SchoolListAdapter(MainActivity.this, android.R.layout.simple_list_item_1, mySchoolsList);
            schoolsListView.setAdapter(schoolsArrayAdapter);

            dialog = new Dialog(MainActivity.this);
            dialog.setContentView(schoolsListView);
            dialog.setTitle("Welcome " + staff.getFullName());
            dialog.show();

        schoolsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                schoolName = schoolsArrayAdapter.getItem(position).getName();
                Log.d(TAG, schoolName + staffName);
                intent = new Intent(MainActivity.this, NavMainActivity.class);
                intent.putExtra("staffIntent", staffName);
                intent.putExtra("schoolIntent", schoolName);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });
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
        progressDialog.setCancelable(true);
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
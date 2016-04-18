package com.peterstev.unify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(btnLogin.isPressed()){
            Intent intent = new Intent(MainActivity.this, ChooseSchool.class);
            startActivity(intent);
        }

    }
}

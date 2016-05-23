package com.peterstev.unify.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.peterstev.unify.R;

public class NavMainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = NavMainActivity.class.getSimpleName();

    Toolbar toolbar;
    NavigationView navigationView;
    LinearLayout mainView;
    TextView school_name, staff_name;
    Intent intent;
    String staffIntentString, schoolIntentString;
    String[] dummyList = {"Jss1", "Jss2", "Jss3", "Ss1", "Ss2", "Ss3"};
    ListView mainListView, gradeListView, coursesListView, lv;
    ArrayAdapter<String> adapter;
    Dialogs dialogs = new Dialogs();
    private DrawerLayout drawerLayout;
    private RelativeLayout headerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);

        intent = getIntent();
        staffIntentString = intent.getStringExtra("staffIntent");
        schoolIntentString = intent.getStringExtra("schoolIntent");

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            headerLayout = (RelativeLayout) navigationView.getHeaderView(0);
        }

        staff_name = (TextView) headerLayout.findViewById(R.id.username);
        school_name = (TextView) headerLayout.findViewById(R.id.schoolname);

        staff_name.setText(staffIntentString);
        school_name.setText(schoolIntentString);

        mainListView = (ListView) findViewById(R.id.list_view_of_staff_classes);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dummyList);
        mainListView.setAdapter(adapter);
        mainListView.setOnItemClickListener(this);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.default_toolbar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
        mainView = (LinearLayout) findViewById(R.id.main_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {

//                    case R.id.myClasses:
////                        Toast.makeText(getApplicationContext(), "My Classes Selected", Toast.LENGTH_SHORT).show();
////                        ClassListFragment class_fragment = new ClassListFragment();
////                        android.support.v4.app.FragmentTransaction class_fragmentTransaction = getSupportFragmentManager().beginTransaction();
////                        class_fragmentTransaction.replace(R.id.frame, class_fragment);
////                        class_fragmentTransaction.commit();
//                        Toast.makeText(getApplicationContext(), "My Classes Selected", Toast.LENGTH_SHORT).show();
//                        return true;
                    case R.id.myStudents:
                        intent = new Intent(NavMainActivity.this, ClassList.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                        return true;
                    case R.id.logout:

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        Toast.makeText(getApplicationContext(), "Logging out...", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                supportInvalidateOptionsMenu();
            }

            @Override

            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainView.setTranslationX(slideOffset * drawerView.getWidth());
                drawerLayout.bringChildToFront(drawerView);
                drawerView.requestLayout();
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dialogs.gradeOptionsDialog();
    }

    public class Dialogs {

        private void gradeOptionsDialog() {
            String names[] = {"Behavior and Skills", "Cognitive Domain"};
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(NavMainActivity.this, R.style.AppCompatAlertDialogStyle);
            LayoutInflater inflater = getLayoutInflater();
            View convertView = (View) inflater.inflate(R.layout.alert_dialog_list_view, null);
            alertDialog.setView(convertView);
            alertDialog.setTitle("Grading Options");
            lv = (ListView) convertView.findViewById(R.id.listView);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(NavMainActivity.this, android.R.layout.simple_list_item_1, names);
            lv.setAdapter(adapter);
            alertDialog.setCancelable(true);
            alertDialog.show();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            Toast.makeText(getApplicationContext(), "Coming soon...", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            intent = new Intent(NavMainActivity.this, MyCourses.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    }

                }
            });
        }

    }
}
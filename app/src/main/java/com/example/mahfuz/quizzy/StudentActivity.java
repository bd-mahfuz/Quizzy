package com.example.mahfuz.quizzy;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class StudentActivity extends AppCompatActivity {

    private Toolbar mToolBar;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        initView();

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Student Panel");
        getSupportActionBar().setElevation(5);


        mActionBarToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mActionBarToggle);
        mActionBarToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void initView() {
        mToolBar = findViewById(R.id.main_tool_bar);
        mDrawerLayout = findViewById(R.id.student_drawer);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mActionBarToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

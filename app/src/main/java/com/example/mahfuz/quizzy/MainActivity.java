package com.example.mahfuz.quizzy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mahfuz.quizzy.Utility.CustomToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    private DatabaseReference mRootRef;
    private FirebaseUser mCurrentUser;

    private DatabaseReference mUserRef;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       mAuth = FirebaseAuth.getInstance();
       mCurrentUser = mAuth.getCurrentUser();

       mRootRef = FirebaseDatabase.getInstance().getReference();
       mUserRef = mRootRef.child("User");

       mProgress = new ProgressDialog(this);


    }


    @Override
    protected void onStart() {
        super.onStart();

        mProgress.setCancelable(false);
        mProgress.setMessage(getString(R.string.loading));
        mProgress.show();

        mUserRef.child(mCurrentUser.getUid()).child("role")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            String role = dataSnapshot.getValue().toString();
                            if (role.equals("Student")) {

                                //addFragment(new StudentFragment());

                                gotoStudentActivity();
                                mProgress.dismiss();
                            } else if (role.equals("Teacher")){

                                gotoTeacherActivity();

                                //addFragment(new TeacherFragment());

                                mProgress.dismiss();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, databaseError.toException().toString());
                        CustomToast.showCustomToast(MainActivity.this, getString(R.string.something_wrong));
                        mProgress.dismiss();
                    }
                });

    }


    public void gotoStudentActivity() {
        startActivity(new Intent(MainActivity.this, StudentActivity.class));
        finish();
    }

    public void gotoTeacherActivity() {
        startActivity(new Intent(MainActivity.this, TeacherActivity.class));
        finish();
    }
}

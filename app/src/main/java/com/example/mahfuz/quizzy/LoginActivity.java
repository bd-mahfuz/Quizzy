package com.example.mahfuz.quizzy;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahfuz.quizzy.Utility.CustomToast;
import com.example.mahfuz.quizzy.Utility.CustomValidator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private TextInputLayout mEmailEt, mPassEt;
    private Button mLoginBtn;
    private TextView signUpTv;

    private FirebaseAuth mAuth;
    private DatabaseReference mRootRef;

    private DatabaseReference mUserRef;

    private ProgressDialog mProgress;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mUserRef = mRootRef.child("User");

        mProgress = new ProgressDialog(this);

        // handling user session based on Role
        if (mAuth.getCurrentUser() != null) {
           gotoMainActivity();
        }


        initView();

        validationForField();




    }

    private void validationForField() {

        mEmailEt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (CustomValidator.isEmailValid(editable.toString())) {
                    mEmailEt.setError(null);
                } else {
                    mEmailEt.setError("Email is not valid!");
                }
            }
        });

        mPassEt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (CustomValidator.isPasswordValid(editable.toString())) {
                    mPassEt.setError(null);
                } else {
                    mPassEt.setError(getString(R.string.password_message));
                }

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailEt.getEditText().getText().toString();
                String password = mPassEt.getEditText().getText().toString();
                if (CustomValidator.isEmailValid(email) && CustomValidator.isPasswordValid(password)) {

                    performLogin(email, password);

                } else {
                    CustomToast.showCustomToast(LoginActivity.this,
                            getString(R.string.validation_failed_message));
                }


            }
        });

        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSignUpActivity();
            }
        });


    }

    public void performLogin(String email, String password) {
        mProgress.setCancelable(false);
        mProgress.setMessage(getString(R.string.loading));
        mProgress.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {



                    mUserRef.child(mAuth.getCurrentUser().getUid()).child("role")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot != null) {
                                        String role = dataSnapshot.getValue().toString();
                                        if (role.equals("Student")) {
                                            CustomToast.showCustomToast(LoginActivity.this, getString(R.string.login_successfull));

                                            gotoStudentActivity();
                                            mProgress.dismiss();
                                        } else if (role.equals("Teacher")){
                                            CustomToast.showCustomToast(LoginActivity.this, getString(R.string.login_successfull));
                                            gotoTeacherActivity();
                                            mProgress.dismiss();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.d(TAG, databaseError.toException().toString());
                                    CustomToast.showCustomToast(LoginActivity.this, getString(R.string.something_wrong));
                                    mProgress.dismiss();
                                }
                            });

//                   gotoMainActivity();
                   mProgress.dismiss();

                } else {
                    Toast.makeText(LoginActivity.this, "Authentication",
                            Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                }

            }
        });


    }

    private void gotoMainActivity() {

        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }


    private void gotoSignUpActivity() {

        Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(signUpIntent);
        finish();

    }

    public void gotoStudentActivity() {
        startActivity(new Intent(LoginActivity.this, StudentActivity.class));
        finish();
    }

    public void gotoTeacherActivity() {
        startActivity(new Intent(LoginActivity.this, TeacherActivity.class));
        finish();
    }


    private void initView() {

        mEmailEt = findViewById(R.id.s_emailEt);
        mPassEt = findViewById(R.id.s_passwordEt);
        mLoginBtn = findViewById(R.id.s_login_bt);
        signUpTv = findViewById(R.id.s_signUpTv);
        mProgressBar = findViewById(R.id.l_progress);

    }




}

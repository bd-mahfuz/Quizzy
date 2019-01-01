package com.example.mahfuz.quizzy;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.example.mahfuz.quizzy.Utility.CustomToast;
import com.example.mahfuz.quizzy.Utility.CustomValidator;
import com.example.mahfuz.quizzy.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout mFullNameEt, mInstituteNameEt, mEmailEt, mPassEt;
    private RadioGroup mGenderRg, mRoleRg;
    private AppCompatButton mSignUpBt;
    private ProgressBar mProgressBar;
    private Toolbar mToolbar;

    private String mGender = "Male";
    private String mRole = "Student";

    private FirebaseAuth mAuth;
    private DatabaseReference mRootRef;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();

        //initializing view
        initView();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("SignUp");

        mGenderRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {

                switch (checkId) {
                    case R.id.st_male:
                        mGender = "Male";
                    case R.id.st_female:
                        mGender = "Female";
                }

            }
        });


        mRoleRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                switch (checkId) {
                    case R.id.studentRd:
                        mRole = "Student";
                    case R.id.teacherRd:
                        mRole = "Teacher";
                }
            }
        });



        validationForField();



    }

    @Override
    protected void onResume() {
        super.onResume();

        mSignUpBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName = mFullNameEt.getEditText().getText().toString();
                String instituteName = mInstituteNameEt.getEditText().getText().toString();
                String email = mEmailEt.getEditText().getText().toString();
                String password = mPassEt.getEditText().getText().toString();


                if (CustomValidator.isFullNameValid(fName) && CustomValidator.isEmailValid(email)
                        && CustomValidator.isPasswordValid(password) && !TextUtils.isEmpty(instituteName)) {

                    mUser = new User("", fName, instituteName, email, password, mGender, mRole);
                    signUpWithEmailPass(email, password);

                } else {
                    CustomToast.showCustomToast(SignUpActivity.this, "Validation Failed! Please try again.");
                }
            }
        });

    }

    private void validationForField() {

        mFullNameEt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if ( ! TextUtils.isEmpty(editable)) {
                    mFullNameEt.setError(null);
                } else {
                    mFullNameEt.setError(getString(R.string.field_empty_message));
                }

                if (editable.toString().length() >= 3) {
                    mFullNameEt.setError(null);
                } else {
                    mFullNameEt.setError("Full name should not be less than 3 char.");
                }

            }
        });

        mInstituteNameEt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if ( ! TextUtils.isEmpty(editable)) {
                    mInstituteNameEt.setError(null);
                } else {
                    mInstituteNameEt.setError(getString(R.string.field_empty_message));
                }

            }
        });



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
                    mEmailEt.setError(getString(R.string.email_message));
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


    public void initView() {

        mFullNameEt = findViewById(R.id.full_name_et);
        mInstituteNameEt = findViewById(R.id.institute_et);
        mEmailEt = findViewById(R.id.email_et);
        mPassEt = findViewById(R.id.st_password_et);

        mGenderRg = findViewById(R.id.gender_rg);
        mRoleRg = findViewById(R.id.role_rg);

        mSignUpBt = findViewById(R.id.signUPBt);
        mProgressBar = findViewById(R.id.s_progress);

        mToolbar = findViewById(R.id.signup_app_bar);


    }


    public void signUpWithEmailPass(String email, String password) {
        mProgressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mUser.setId(mAuth.getCurrentUser().getUid());
                            mRootRef.child("User").child(mAuth.getCurrentUser().getUid()).setValue(mUser)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                CustomToast.showCustomToast(SignUpActivity.this,
                                                        "Sign up Successful.");
                                                // move to the activity based on user role
                                                if (mUser.getRole().equals("Student")){
                                                    gotoStudentActivity();
                                                } else if (mUser.getRole().equals("Teacher")) {
                                                    gotoTeacherActivity();
                                                }

                                                mProgressBar.setVisibility(View.GONE);
                                            } else {
                                                CustomToast.showCustomToast(SignUpActivity.this,
                                                        "Sign up Failed. Please try again.");
                                                mProgressBar.setVisibility(View.GONE);
                                            }

                                        }
                                    });

                        }
                    }
                });


    }

    private void gotoMainActivity() {

        Intent mainIntent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }

    private void gotoStudentActivity() {

        Intent mainIntent = new Intent(SignUpActivity.this, StudentActivity.class);
        startActivity(mainIntent);
        finish();

    }

    private void gotoTeacherActivity() {

        Intent mainIntent = new Intent(SignUpActivity.this, TeacherActivity.class);
        startActivity(mainIntent);
        finish();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();

    }
}

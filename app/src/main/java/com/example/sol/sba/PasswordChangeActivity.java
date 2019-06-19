package com.example.sol.sba;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PasswordChangeActivity extends AppCompatActivity {

    private EditText current_password;
    private EditText new_password;
    private EditText new_password_confirm;

    private Toolbar password_change_Toolbar;
    FirebaseAuth firebaseAuth;

    private Button change_password_tn;

    private ProgressBar change_password_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        password_change_Toolbar = findViewById(R.id.password_change_toolbar);
        setSupportActionBar(password_change_Toolbar);
        getSupportActionBar().setTitle("Modify Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        current_password = findViewById(R.id.current_password);
        new_password = findViewById(R.id.new_password);
        new_password_confirm = findViewById(R.id.confirm_new_pass);

        change_password_tn = findViewById(R.id.change_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        change_password_progress = findViewById(R.id.change_pass_progress);

        change_password_tn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String current = current_password.getText().toString();
                String newPass = new_password.getText().toString();
                String confirmNewPass = new_password_confirm.getText().toString();

                if(!TextUtils.isEmpty(current) && !TextUtils.isEmpty(newPass) & !TextUtils.isEmpty(confirmNewPass)){

                    if(newPass.equals(confirmNewPass)){

                        change_password_progress.setVisibility(View.VISIBLE);

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){

                                    change_password_progress.setVisibility(View.INVISIBLE);
                                    Toast.makeText(PasswordChangeActivity.this,"Password changed successfully",Toast.LENGTH_LONG).show();
                                    firebaseAuth.signOut();
                                    finish();
                                    Intent intent = new Intent(PasswordChangeActivity.this,LoginActivity.class);
                                    startActivity(intent);

                                }else {

                                    change_password_progress.setVisibility(View.INVISIBLE);
                                    Toast.makeText(PasswordChangeActivity.this,"Failed To Change Password",Toast.LENGTH_LONG).show();


                                }


                            }
                        });

                    }

                }

            }
        });

    }

    public void changePassword(View v){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if(user != null){

        }

    }

}

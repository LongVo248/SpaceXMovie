package cf.vandit.movie_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.fragments.ProfileFragment;
import cf.vandit.movie_app.retrofit.RetrofitService;
import cf.vandit.movie_app.retrofit.dto.AccountInfo;
import cf.vandit.movie_app.retrofit.dto.Password;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText oldPassword, newPassword, confirmPassword;
    Button btnChange;
    AccountInfo accountInfo;
    Boolean check= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        accountInfo = MainActivity.accountInfo;
        setControl();
        setEvent();
    }

    private void setEvent() {

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Password password = new Password();
                password.setPassword(oldPassword.getText().toString());

                if (!oldPassword.getText().toString().isEmpty() && !newPassword.getText().toString().isEmpty() && !confirmPassword.getText().toString().isEmpty()) {
                    Call<Boolean> booleanCall = RetrofitService.getAccountService().checkPassword(accountInfo.getUsername(), password);
                    booleanCall.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (response.body() != null) {
                                check= true;
                                System.out.println("check ="+response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                        }
                    });
                    password.setPassword(newPassword.getText().toString());
                    System.out.println("check ="+check);
                    System.out.println("so sanh "+newPassword.getText().toString().equals(confirmPassword.getText().toString()));
                    if (check && newPassword.getText().toString().equals(confirmPassword.getText().toString())){
                        Call<String> stringCall = RetrofitService.getAccountService().changePassword(accountInfo.getUsername(), password);
                        stringCall.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.body() != null){
                                    Toast.makeText(ChangePasswordActivity.this,"Change Successfully", Toast.LENGTH_LONG ).show();
                                    startActivity(new Intent(ChangePasswordActivity.this, MainActivity.class));
                                }

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });

                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Change Not Empty", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Change Fail", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setControl() {
        oldPassword = findViewById(R.id.inputOldPass);
        newPassword = findViewById(R.id.inputNewPass);
        confirmPassword = findViewById(R.id.inputConfirmPass);
        btnChange = findViewById(R.id.btnChange);
    }
}
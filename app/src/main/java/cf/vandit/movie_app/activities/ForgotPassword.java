package cf.vandit.movie_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {
    EditText email;
    Button btnSendMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Boolean> booleanCall= RetrofitService.getAccountService().forgotPassword(email.getText().toString());
                booleanCall.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.body()!= null){
                            startActivity(new Intent(ForgotPassword.this, VerifyEmail.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        startActivity(new Intent(ForgotPassword.this, VerifyEmail.class));
                    }
                });
            }
        });
    }

    private void setControl() {
        email= findViewById(R.id.inputEmail);
        btnSendMail= findViewById(R.id.btnSendMail);
    }
}
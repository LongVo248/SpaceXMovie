package cf.vandit.movie_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cf.vandit.movie_app.R;

public class VerifyEmail extends AppCompatActivity {
    Button btnBackToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VerifyEmail.this, LoginActivity.class));
            }
        });
    }

    private void setControl() {
        btnBackToLogin= findViewById(R.id.btnBackToLogin);
    }
}
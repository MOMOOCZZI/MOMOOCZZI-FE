package com.example.momooczzi_fe;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.momooczzi_fe.network.ApiSignupService;

import java.io.IOException;


public class Register3 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register3);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register3), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton backButton = findViewById(R.id.backbtn);
        backButton.setOnClickListener(v -> finish());

        EditText nicknameEditText = findViewById(R.id.registernick);
        EditText introEditText = findViewById(R.id.introet);
        Button registerButton = findViewById(R.id.btnRegister);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nickname = nicknameEditText.getText().toString().trim();
                String intro = introEditText.getText().toString().trim();

                if (!nickname.isEmpty() && !intro.isEmpty()) {
                    registerButton.setEnabled(true);
                    registerButton.setBackgroundTintList(getResources().getColorStateList(R.color.register_grey, null));
                } else {
                    registerButton.setEnabled(false);
                    registerButton.setBackgroundTintList(getResources().getColorStateList(R.color.gray, null));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        nicknameEditText.addTextChangedListener(textWatcher);
        introEditText.addTextChangedListener(textWatcher);

        registerButton.setOnClickListener(v -> {
            Intent intent = getIntent();
            String userId = intent.getStringExtra("userId");
            String password = intent.getStringExtra("password");
            String nickname = nicknameEditText.getText().toString().trim();
            String intro = introEditText.getText().toString().trim();

            ApiSignupService.signup(
                    userId, password, nickname, intro,
                    false, // marketing
                    true,  // personal
                    new okhttp3.Callback() {
                        @Override
                        public void onFailure(okhttp3.Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                            if (response.isSuccessful()) {
                                runOnUiThread(() -> {
                                    Intent intent = new Intent(Register3.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                });
                            } else {
                                Log.e("API", "Signup failed: " + response.body().string());
                            }
                        }
                    }
            );
        });

    }


}

package com.example.momooczzi_fe;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class Register3 extends AppCompatActivity {
    private Button btnRegister;

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
        EditText nicknameEditText = findViewById(R.id.registernick);
        EditText introEditText = findViewById(R.id.introet);
        Button registerButton = findViewById(R.id.btnRegister);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력값 체크
                String nickname = nicknameEditText.getText().toString().trim();
                String intro = introEditText.getText().toString().trim();

                if (!nickname.isEmpty() && !intro.isEmpty()) {
                    registerButton.setEnabled(true);
                    registerButton.setBackgroundTintList(getResources().getColorStateList(R.color.blue, null));
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
        btnRegister.setOnClickListener(v -> {
            FragmentRegister fragment = new FragmentRegister();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            // 프래그먼트 추가
            transaction.replace(R.id.frag, fragment);
            transaction.commit();

            // 프래그먼트 레이아웃 보이게
            findViewById(R.id.frag).setVisibility(View.VISIBLE);
        });

    }
}

package com.example.momooczzi_fe;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


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

    }
}

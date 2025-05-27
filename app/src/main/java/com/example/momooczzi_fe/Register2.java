package com.example.momooczzi_fe;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class Register2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton backButton = findViewById(R.id.backbtn);
        backButton.setOnClickListener(v -> finish());

        EditText idEditText = findViewById(R.id.registerId);
        EditText pass1EditText = findViewById(R.id.registerPass1);
        EditText pass2EditText = findViewById(R.id.registerPass2);
        Button nextButton = findViewById(R.id.btnNext);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String id = idEditText.getText().toString().trim();
                String pass1 = pass1EditText.getText().toString();
                String pass2 = pass2EditText.getText().toString();

                if (!id.isEmpty() && pass1.equals(pass2) && !pass1.isEmpty()) {
                    nextButton.setEnabled(true);
                    nextButton.setBackgroundTintList(getResources().getColorStateList(R.color.register_grey, null));
                } else {
                    nextButton.setEnabled(false);
                    nextButton.setBackgroundTintList(getResources().getColorStateList(R.color.gray, null));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}

        };

        idEditText.addTextChangedListener(watcher);
        pass1EditText.addTextChangedListener(watcher);
        pass2EditText.addTextChangedListener(watcher);

        nextButton.setOnClickListener(v -> {
            Log.d("Register2", "Next button clicked");
            Intent intent = new Intent(getApplicationContext(), Register3.class);
            startActivity(intent);
        });

    }
}

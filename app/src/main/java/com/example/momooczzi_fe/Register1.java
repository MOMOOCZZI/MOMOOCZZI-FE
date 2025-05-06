package com.example.hello;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.List;

public class Register1 extends AppCompatActivity {

    private CheckBox cbAll, cb1, cb2, cb3, cb4, cb5, cb6, cb7;
    private Button btnNext;

    private List<CheckBox> requiredCheckboxes;
    private List<CheckBox> allCheckboxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register1);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register1), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 체크박스 연결
        cbAll = findViewById(R.id.cbAll);
        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
        cb3 = findViewById(R.id.cb3);
        cb4 = findViewById(R.id.cb4);
        cb5 = findViewById(R.id.cb5);
        cb6 = findViewById(R.id.cb6); 
        cb7 = findViewById(R.id.cb7); //6,7은 선택항목목
        btnNext = findViewById(R.id.btnNext);

        requiredCheckboxes = Arrays.asList(cb1, cb2, cb3, cb4, cb5);
        allCheckboxes = Arrays.asList(cb1, cb2, cb3, cb4, cb5, cb6, cb7);

        // 전체 동의 체크하면 모두 체크/해제
        cbAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (CheckBox cb : allCheckboxes) {
                cb.setChecked(isChecked);
            }
            updateNextButtonState();
        });

        // 개별 체크박스 변화 감지
        for (CheckBox cb : allCheckboxes) {
            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                // 모든 항목 체크되었는지 확인 후 전체동의 상태 업데이트
                cbAll.setChecked(areAllChecked(allCheckboxes));
                updateNextButtonState();
            });
        }

        // 버튼 초기 상태 설정
        updateNextButtonState();
    }

    private boolean areAllChecked(List<CheckBox> checkboxes) {
        for (CheckBox cb : checkboxes) {
            if (!cb.isChecked()) return false;
        }
        return true;
    }

    private boolean areRequiredChecked() {
        for (CheckBox cb : requiredCheckboxes) {
            if (!cb.isChecked()) return false;
        }
        return true;
    }

    private void updateNextButtonState() {
        boolean enabled = areRequiredChecked();
        btnNext.setEnabled(enabled);
        btnNext.setBackgroundTintList(ColorStateList.valueOf(
                Color.parseColor(enabled ? "#1E90FF" : "#D9D9D9")));
    }
}

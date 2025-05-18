package com.example.momooczzi_fe;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RecomandedList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.recommanded_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recommandList), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView textView = findViewById(R.id.textV);

        String name = "won_ee";
        String fullText = "지금 " + name + " 님께 추천드리는 음식은?";
        SpannableString spannable = new SpannableString(fullText);

        int start = 0;
        int end = name.length();

        spannable.setSpan(
                new ForegroundColorSpan(Color.parseColor("#00B6F1")),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        textView.setText(spannable);
        ImageView[] menuImages = new ImageView[3];
        TextView[] menuNames = new TextView[3];
        TextView[] menuDatas = new TextView[3];
        TextView[] hashtags = new TextView[3];

        int[] imgIds = { R.id.menuImg1, R.id.menuImg2, R.id.menuImg3 };
        int[] nameIds = { R.id.menuName1, R.id.menuName2, R.id.menuName3 };
        int[] dataIds = { R.id.menuData1, R.id.menuData2, R.id.menuData3 };
        int[] hashtagIds = { R.id.hashtag1, R.id.hashtag2, R.id.hashtag3 };

        for (int i = 0; i < 3; i++) {
            menuImages[i] = findViewById(imgIds[i]);
            menuNames[i] = findViewById(nameIds[i]);
            menuDatas[i] = findViewById(dataIds[i]);
            hashtags[i] = findViewById(hashtagIds[i]);
        }
        int[] newImages = { R.drawable.donkatsu, R.drawable.ramen, R.drawable.pasta };
        String[] newNames = { "돈까스", "라멘", "파스타" };
        String[] newDescs = {
                "기분이 울적할 땐 바삭한\n돈까스가 딱이지!",
                "쌀쌀한 날씨엔 따끈한\\n국물이 최고!",
                "기분 좋은 날엔 크리미한\\n파스타의 여유를!"
        };
        String[] newTags = {
                "\\#기분전환 \\#바삭한한입\n\\#스트레스해소",
                "\\#따끈한그릇 \\#국물충전\\n\\#오늘비오면라멘각",
                "\\#여유로운한끼 \\#감성식사\\n\\#좋은날엔파스타"
        };

        for (int i = 0; i < 3; i++) {
            menuImages[i].setImageResource(newImages[i]);
            menuNames[i].setText(newNames[i]);
            menuDatas[i].setText(newDescs[i]);
            hashtags[i].setText(newTags[i]);
        }

    }
}

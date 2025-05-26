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

import com.bumptech.glide.Glide;
import com.example.momooczzi_fe.network.ApiClient;
import com.example.momooczzi_fe.network.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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

        for (int i = 0; i < 3; i++) {
            menuImages[i] = findViewById(imgIds[i]);
            menuNames[i] = findViewById(nameIds[i]);
            menuDatas[i] = findViewById(dataIds[i]);
        }
        Retrofit retrofit = ApiClient.getClient();
        Food foodService = retrofit.create(Food.class);

        foodService.getFoodList().enqueue(new Callback<List<FoodItem>>() {
            @Override
            public void onResponse(Call<List<FoodItem>> call, Response<List<FoodItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FoodItem> foodList = response.body();

                    for (int i = 0; i < Math.min(3, foodList.size()); i++) {
                        FoodItem item = foodList.get(i);
                        menuNames[i].setText(item.getFood());
                        menuDatas[i].setText(item.getDescription().replace("\\n", "\n"));
                        Glide.with(RecomandedList.this)
                                .load(item.getImage_url())
                                .into(menuImages[i]);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<FoodItem>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}

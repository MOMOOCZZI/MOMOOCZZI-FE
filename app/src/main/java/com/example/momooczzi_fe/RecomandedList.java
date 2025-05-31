package com.example.momooczzi_fe;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.momooczzi_fe.network.ApiFoodService;
import com.example.momooczzi_fe.network.FoodItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RecomandedList extends AppCompatActivity {
    private double lat, lng;

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

        Intent intent = getIntent();

        TextView textView = findViewById(R.id.textV);
        textView.setText("지금 당신에게 추천드리는 음식은?");

        ImageView[] menuImages = new ImageView[3];
        TextView[] menuNames = new TextView[3];
        TextView[] menuDatas = new TextView[3];
        Button[] btnMap = new Button[3];

        int[] imgIds = { R.id.menuImg1, R.id.menuImg2, R.id.menuImg3 };
        int[] nameIds = { R.id.menuName1, R.id.menuName2, R.id.menuName3 };
        int[] dataIds = { R.id.menuData1, R.id.menuData2, R.id.menuData3 };
        int[] btnIds = {R.id.btnMap1, R.id.btnMap2, R.id.btnMap3};

        for (int i = 0; i < 3; i++) {
            menuImages[i] = findViewById(imgIds[i]);
            menuNames[i] = findViewById(nameIds[i]);
            menuDatas[i] = findViewById(dataIds[i]);
            btnMap[i] = findViewById(btnIds[i]);
        }

        String emotion = intent.getStringExtra("emotion");
        String happen = intent.getStringExtra("happen");
        Boolean gender = intent.getBooleanExtra("gender", false);
        lat = intent.getDoubleExtra("lat", 0.0);
        lng = intent.getDoubleExtra("lng", 0.0);


        Log.e("RECOMMAND_DEBUG", "요청 파라미터: gender=" + gender +
                ", emotion=" + emotion +
                ", happen=" + happen +
                ", lat=" + lat + ", lng=" + lng);

        ApiFoodService.postFoodRecommendation(
                gender,
                emotion,
                happen,
                lat,
                lng,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e("RECOMMAND_DEBUG", "서버 요청시작");
                        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                        if (response.isSuccessful()) {
                            String json = response.body().string();
                            Log.e("RECOMMAND_DEBUG", "서버 응답 JSON: " + json);
                            try {
                                JSONObject root = new JSONObject(json);
                                JSONArray foodArray = root.getJSONArray("ㄴfood");
                                List<FoodItem> foodList = new ArrayList<>();

                                for (int i = 0; i < foodArray.length(); i++) {
                                    JSONObject item = foodArray.getJSONObject(i);
                                    FoodItem foodItem = new FoodItem(
                                            item.getString("food"),
                                            item.getString("description"),
                                            item.getString("image_url")
                                    );
                                    foodList.add(foodItem);
                                }

                                runOnUiThread(() -> {
                                    Log.e("RECOMMAND_DEBUG", "UI 업데이트 시작");
                                    for (int i = 0; i < Math.min(3, foodList.size()); i++) {
                                        FoodItem item = foodList.get(i);
                                        menuNames[i].setText(item.getFood());
                                        menuDatas[i].setText(item.getDescription().replace("\\n", "\n"));
                                        Glide.with(RecomandedList.this)
                                                .load(item.getImage_url())
                                                .into(menuImages[i]);
                                        final int index = i;
                                        btnMap[i].setOnClickListener(v->{
                                            intent.putExtra("key", menuNames[index].getText().toString());
                                        });
                                    }
                                });

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        intent.putExtra("latitude", lat);
                        intent.putExtra("longitude", lng);
                        startActivity(intent);
                    }
                }
        );
    }
}

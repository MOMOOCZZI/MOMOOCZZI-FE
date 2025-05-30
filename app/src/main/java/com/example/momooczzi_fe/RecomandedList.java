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

        SharedViewModel vm = new ViewModelProvider(this).get(SharedViewModel.class);

        TextView textView = findViewById(R.id.textV);
        textView.setText("지금 님께 추천드리는 음식은?");

        ImageView[] menuImages = new ImageView[3];
        TextView[] menuNames = new TextView[3];
        TextView[] menuDatas = new TextView[3];

        int[] imgIds = { R.id.menuImg1, R.id.menuImg2, R.id.menuImg3 };
        int[] nameIds = { R.id.menuName1, R.id.menuName2, R.id.menuName3 };
        int[] dataIds = { R.id.menuData1, R.id.menuData2, R.id.menuData3 };

        for (int i = 0; i < 3; i++) {
            menuImages[i] = findViewById(imgIds[i]);
            menuNames[i] = findViewById(nameIds[i]);
            menuDatas[i] = findViewById(dataIds[i]);
        }

        ApiFoodService.postFoodRecommendation(
                vm.getGender(),
                vm.getEmotion(),
                vm.getHappen(),
                vm.getLatitude(),
                vm.getLongitude(),
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String json = response.body().string();
                            try {
                                JSONObject root = new JSONObject(json);
                                JSONArray foodArray = root.getJSONArray("food");
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
                                    for (int i = 0; i < Math.min(3, foodList.size()); i++) {
                                        FoodItem item = foodList.get(i);
                                        menuNames[i].setText(item.getFood());
                                        menuDatas[i].setText(item.getDescription().replace("\\n", "\n"));
                                        Glide.with(RecomandedList.this)
                                                .load(item.getImage_url())
                                                .into(menuImages[i]);
                                    }
                                });

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
    }
}

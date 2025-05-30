package com.example.momooczzi_fe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LocationFoundFragment extends Fragment {

    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location_found, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView = view.findViewById(R.id.kakaoMapWebView);

        if (webView == null) {
            Log.e("WebView", "kakaoMapWebView not found in layout");
            return;
        }

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webView.clearCache(true);

        LocationViewModel viewModel = new ViewModelProvider(requireActivity()).get(LocationViewModel.class);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                viewModel.getLatitude().observe(getViewLifecycleOwner(), lat -> {
                    viewModel.getLongitude().observe(getViewLifecycleOwner(), lng -> {
                        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
                        sharedViewModel.setLatitude(lat);
                        sharedViewModel.setLongitude(lng);

                        String js = "showMap(" + lat + "," + lng + ")";
                        view.evaluateJavascript(js, null);
                    });
                });
            }
        });

        webView.loadUrl("file:///android_asset/kakaomap.html");

        view.findViewById(R.id.btn_next_recommand).setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), RecomandedList.class);
            startActivity(intent);
        });
    }

}

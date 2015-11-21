package com.example.qingao.mytest;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.qingao.AppVolley;
import com.example.qingao.R;

/**
 * Created by qingao on 15-11-20.
 */
public class SecondActivity extends Activity {

    private Button btnLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lyt_second);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        btnLoad = (Button) findViewById(R.id.btn_load);
        final View lytMain = findViewById(R.id.lyt_main);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppVolley.getInstance(SecondActivity.this).getImageLoader()
                        .get("http://www.baomi.com/images/b-2.jpg", new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        lytMain.setBackground(
                                new BitmapDrawable(getResources(),response.getBitmap()));
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }
        });

    }
}

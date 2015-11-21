package com.example.qingao.mytest;

import android.app.KeyguardManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.qingao.AppVolley;
import com.example.qingao.R;
import com.example.qingao.model.Category;
import com.example.qingao.model.Item;

public class MainActivity extends AppCompatActivity {

    private Button btnTest,btnHandler;
    private int count;
    private KeyguardManager km;
    private KeyguardManager.KeyguardLock kl;
    private static String TAG = MainActivity.class.getCanonicalName();

    private TextView tvHello;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    tvHello.setText(""+count++);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        // Turn on the screen unless we are being launched from the AlarmAlert
        // subclass as a result of the screen turning off.
        win.addFlags(
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);


        final View decorView = win.getDecorView();
        /* 获取KeyGuardManager对象 */
        km = (KeyguardManager)this.getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);

        /* 获取KeyguardLock对象 */
        kl = km.newKeyguardLock(TAG);

        setContentView(R.layout.activity_main);
        final Switch aSwitch = (Switch) findViewById(R.id.btnToogle);
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aSwitch.isChecked()) {
                    Log.d(TAG, "enabled");
                    kl.disableKeyguard();

                    int uiOptions =
                            View.SYSTEM_UI_FLAG_IMMERSIVE
                            |View.SYSTEM_UI_FLAG_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            ;
                    decorView.setSystemUiVisibility(uiOptions);

                }else{
                    Log.d(TAG, "disabled");
                    kl.reenableKeyguard();

                    int uiOptions = 0;
                    decorView.setSystemUiVisibility(uiOptions);

//                    gotoActivity("android.intent.action.POWER_USAGE_SUMMARY","com.android.settings");

                    StringRequest stringRequest = new StringRequest("http://www.baidu.com", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG,response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG,error.getMessage());
                        }
                    });
                    AppVolley.getInstance(MainActivity.this).addRequest(stringRequest);
                }
            }
        });

        tvHello = (TextView)findViewById(R.id.textView);
        btnTest = (Button)findViewById(R.id.btnTest);
        btnHandler = (Button)findViewById(R.id.btnTestHandler);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count ++;
                Category c = new Category("C"+count);
                Item i = new Item("I*"+count,c);

                c.save();
                i.save();


                Log.i("TTT", Model.load(Item.class, 1).toString());

                Log.i("TTT", new Select().from(Item.class).execute().toString());

            }
        });

        btnHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvHello.setText("hhhhh");
                    }
                },1000);
            }
        });
    }


    private void gotoActivity(String action,String pkgName) {
        Intent i = new Intent();
        i.setAction(action);
        i.setPackage(pkgName);
        i.putExtra("USE_CMX_POWER",false);
        try{
            startActivity(i);
        }catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Please install " + action + " first", Toast.LENGTH_LONG).show();
            Log.e("SecuritySettings", "Action: " + action + " not found", e);
        }
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    public void gotoSecond(View v) {
        Intent i = new Intent(this,SecondActivity.class);
        startActivity(i);
    }
}

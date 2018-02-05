package mybreed.andrewlaurien.com.gamecocksheet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                String state = preferences.getString("State", "");
                if (state.equalsIgnoreCase("Verification")) {
                    Intent intent = new Intent(SplashActivity.this, Verify.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                } else if (state.equalsIgnoreCase("LoggedIn")) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }

            }
        }, 6000);

    }

}

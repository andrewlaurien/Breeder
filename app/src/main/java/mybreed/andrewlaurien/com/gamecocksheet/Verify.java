package mybreed.andrewlaurien.com.gamecocksheet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Verify extends AppCompatActivity {

    Button btnVerify;
    TextInputEditText txtcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnVerify = findViewById(R.id.btnVerify);
        txtcode = findViewById(R.id.txtcode);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtcode.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), "Please enter your verification code.", Toast.LENGTH_SHORT).show();
                    return;
                }


                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                String code = preferences.getString("VerificationCode", "");
                if (code.equals(txtcode.getText().toString())) {

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("State", "LoggedIn");
                    editor.commit();

                    Intent intent = new Intent(Verify.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                } else {
                    Toast.makeText(getBaseContext(), "Invalid verification code.", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });


    }

}

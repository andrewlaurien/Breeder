package mybreed.andrewlaurien.com.gamecocksheet.services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import mybreed.andrewlaurien.com.gamecocksheet.Common.CommonFunc;
import mybreed.andrewlaurien.com.gamecocksheet.LoginActivity;
import mybreed.andrewlaurien.com.gamecocksheet.MainActivity;
import mybreed.andrewlaurien.com.gamecocksheet.Model.User;

/**
 * Created by andrewlaurienrsocia on 10/01/2018.
 */

public class WebRequest {

    private static WebRequest webRequest;
    private RequestQueue requestQueue;
    private static Context mcontext;

    public WebRequest(Context c) {
        this.mcontext = c;
        this.requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {

        if (requestQueue == null) {
            Cache cache = new DiskBasedCache(mcontext.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue = Volley.newRequestQueue(mcontext.getApplicationContext());
            requestQueue.start();
        }
        return requestQueue;
    }

    public static synchronized WebRequest getInstance(Context context) {
        if (webRequest == null) {
            webRequest = new WebRequest(context);
        }
        return webRequest;
    }

    public void sendVerification(final String mobilenum, final String password) {


        // generate a 4 digit integer 1000 <10000
        final int randomPIN = (int) (Math.random() * 9000) + 1000;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mcontext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("State", "Verification");
        editor.putString("Mobile", mobilenum);
        editor.putString("Pass", password);
        editor.putString("VerificationCode", "" + randomPIN);
        //  CommonFunc.setPreferenceObject(mcontext, randomPIN, "VerificationCode");
        editor.apply();
        Intent intent = new Intent(mcontext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        User user = new User();
        user.setMobile(mobilenum);
        CommonFunc.setPreferenceObject(mcontext, user, "User");

        DatabaseReference mDatabase;

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase.child(user.getMobile()).child("Profile").setValue(user);


        mcontext.startActivity(intent);
        if (mcontext instanceof LoginActivity) {
            ((LoginActivity) mcontext).finish();
        }



    }


    public String evaluateResult(JSONObject response) {

        String message = "";
        String resultcode = "";
        String result = "";


        try {
            JSONObject loadwallet = response.getJSONObject("NUMBER");
            message = loadwallet.getString("Message");
            resultcode = loadwallet.getString("ResultCode");
            result = loadwallet.getString("Result");
            Toast.makeText(mcontext, message, Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            Toast.makeText(mcontext, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return result;

    }

}

package mybreed.andrewlaurien.com.gamecocksheet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import mybreed.andrewlaurien.com.gamecocksheet.Common.CommonFunc;
import mybreed.andrewlaurien.com.gamecocksheet.Model.User;

public class LoginActivity extends AppCompatActivity {


    Button btnLogin;
    TextInputEditText txtmobile;
    TextInputEditText txtpassword;
    String TAG = "LoginActivity";


    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private boolean mVerificationInProgress = false;

    TextInputLayout txtpasslayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Restore instance state
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtmobile = findViewById(R.id.txtmobile);
        txtpassword = findViewById(R.id.txtpassword);
        txtpasslayout = findViewById(R.id.textInputLayout2);
        btnLogin = findViewById(R.id.btnLogin);


        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]


        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]
                //updateUI(STATE_CODE_SENT);
                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                updateUI(STATE_VERIFY_SUCCESS, credential);
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    txtmobile.setError("Invalid phone number.");
                    txtmobile.setError(e.getMessage());
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), "Cannot Login right now. Try again Later.",
                            Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]
                updateUI(STATE_VERIFY_FAILED);
                // [END_EXCLUDE]
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                updateUI(STATE_CODE_SENT);

            }
        };
        // [END phone_auth_callbacks]


        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                  String mobile = txtmobile.getText().toString();

                Log.d("Result", "" + CommonFunc.isNumberValid(mobile) + "" + !CommonFunc.isNumberValid(mobile));

                if (btnLogin.getText().toString().equalsIgnoreCase("login")) {
                    startPhoneNumberVerification(txtmobile.getText().toString());
                } else if (btnLogin.getText().toString().equalsIgnoreCase("verify")) {

                    if (txtpassword.getText().toString().isEmpty()) {
                        showSnakBar("Please input the verification code.");
                        return;
                    }

                    verifyPhoneNumberWithCode(mVerificationId, txtpassword.getText().toString());
                }


            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // [START_EXCLUDE]
                            updateUI(STATE_SIGNIN_SUCCESS, user);
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                //mVerificationField.setError("Invalid code.");
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }


    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user);
        } else {
            updateUI(STATE_INITIALIZED);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:
                // Initialized state, show only the phone number field and start button
                //enableViews(mStartButton, mPhoneNumberField);
                //disableViews(mVerifyButton, mResendButton, mVerificationField);
                //mDetailText.setText(null);
                Log.d(TAG, "HERE1");
                break;
            case STATE_CODE_SENT:
                // Code sent state, show the verification field, the
                //enableViews(mVerifyButton, mResendButton, mPhoneNumberField, mVerificationField);
                //disableViews(mStartButton);
                //mDetailText.setText(R.string.status_code_sent);
                txtpasslayout.setVisibility(View.VISIBLE);
                btnLogin.setText("Verify");
                Log.d(TAG, "HERE2");
                break;
            case STATE_VERIFY_FAILED:
                // Verification has failed, show all options
                //enableViews(mStartButton, mVerifyButton, mResendButton, mPhoneNumberField,
                //        mVerificationField);
                //mDetailText.setText(R.string.status_verification_failed);
                showSnakBar("Verification Faield. Pleas check your number.");
                Log.d(TAG, "HERE3");
                break;
            case STATE_VERIFY_SUCCESS:
                // Verification has succeeded, proceed to firebase sign in
                //disableViews(mStartButton, mVerifyButton, mResendButton, mPhoneNumberField,
                //        mVerificationField);
                //mDetailText.setText(R.string.status_verification_succeeded);

                Log.d(TAG, "HERE4");
                // Set the verification text based on the credential
                txtpasslayout.setVisibility(View.VISIBLE);
                if (cred != null) {
                    if (cred.getSmsCode() != null) {
                        Log.d(TAG, "HERE4.1");
                        //mVerificationField.setText(cred.getSmsCode());
                        txtpassword.setText(cred.getSmsCode());
                    } else {
                        Log.d(TAG, "HERE4.2");
                        //txtpassword.setText("");
                        //mVerificationField.setText(R.string.instant_validation);
                    }
                }

                break;
            case STATE_SIGNIN_FAILED:
                // No-op, handled by sign-in check
                //mDetailText.setText(R.string.status_sign_in_failed);
                Log.d(TAG, "HERE5");
                showSnakBar("Network error has occured. Please check your internet connection");
                break;
            case STATE_SIGNIN_SUCCESS:
                // Np-op, handled by sign-in check
                Log.d(TAG, "HERE6");

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("State", "LoggedIn");
                editor.putString("FirstTime", "Yes");
                editor.putString("Mobile", txtmobile.getText().toString());
                //  CommonFunc.setPreferenceObject(mcontext, randomPIN, "VerificationCode");
                editor.apply();


                User myuser = new User();
                myuser.setMobile(txtmobile.getText().toString());
                CommonFunc.setPreferenceObject(getBaseContext(), myuser, "User");
                DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                mDatabase.child(myuser.getMobile()).child("Profile").setValue(user);

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                LoginActivity.this.finish();

                break;
        }

        if (user == null) {
            // Signed out
            //mPhoneNumberViews.setVisibility(View.VISIBLE);
            //mSignedInViews.setVisibility(View.GONE);

            // mStatusText.setText(R.string.signed_out);
        } else {
            // Signed in
            //mPhoneNumberViews.setVisibility(View.GONE);
            //mSignedInViews.setVisibility(View.VISIBLE);

            //enableViews(mPhoneNumberField, mVerificationField);
            //mPhoneNumberField.setText(null);
            //mVerificationField.setText(null);

            //mStatusText.setText(R.string.signed_in);
            //mDetailText.setText(getString(R.string.firebase_status_fmt, user.getUid()));
        }
    }

    private void enableViews(View... views) {
        for (View v : views) {
            v.setEnabled(true);
        }
    }

    private void disableViews(View... views) {
        for (View v : views) {
            v.setEnabled(false);
        }
    }

    // [END sign_in_with_phone]
    public void showSnakBar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message,
                Snackbar.LENGTH_SHORT).show();
    }

}

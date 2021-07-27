package com.quiqgenie.acsdataapp;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kyanogen.signatureview.SignatureView;

import java.io.File;
import java.util.List;

public class TermsandConditionsActivity extends BaseActivity {

    private static final String TAG = TermsandConditionsActivity.class.getSimpleName();
    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private Uri uriContact;
    private String contactID;
    Button proceedbtn;
    private PopupWindow pw;
    private File file;
    private LinearLayout canvasLL;
    private View view;
    private SignatureView mSignature;
    private Bitmap bitmap;
    String phoneNumber1 = " ";

    private static final int CREDENTIAL_PICKER_REQUEST = 1;
    String mobNumber;
    String currentVersion;
    GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsand_conditions);


        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        proceedbtn = findViewById(R.id.proceedbtn);
/*

        if (new SavePref(this).getMyPhoneNumber().isEmpty()) {
            requestHint();
        }
*/


        proceedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

     /*           startActivity(new Intent(TermsandConditionsActivity.this, CommonFetchingActivity.class));
                finish();*/

              /*  if (abc()) {
                    startActivity(new Intent(TermsandConditionsActivity.this,
                            StartCommonFetchingActivity.class));
                } else {
                    if (Build.VERSION.SDK_INT >= 21) {
                        UsageStatsManager mUsageStatsManager = (UsageStatsManager) TermsandConditionsActivity.this.getSystemService(Context.USAGE_STATS_SERVICE);
                        long time = System.currentTimeMillis();
                        List stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 10, time);

                        if (stats == null || stats.isEmpty()) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                            startActivity(intent);


                        }
                    }
                }*/
               /* if (phoneNumber1 == " ") {
                    Toast.makeText(getApplicationContext(), "Please select mobile number", Toast.LENGTH_LONG).show();
                    requestHint();

                } else {


                }*/
                startActivity(new Intent(TermsandConditionsActivity.this, Signatureview.class));

                //showPopupWindow(view);


            }
        });
    }

    private void requestHint() {
        CredentialPickerConfig conf  = new CredentialPickerConfig.Builder()
                .setShowAddAccountButton(false)
                .build();
        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .setHintPickerConfig(conf)
                .build();


        PendingIntent intent = Credentials.getClient(this).getHintPickerIntent(hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(), CREDENTIAL_PICKER_REQUEST, null, 0, 0, 0, new Bundle());
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == RESULT_OK)
        {

            Credential credentials = data.getParcelableExtra(Credential.EXTRA_KEY);

            phoneNumber1=credentials.getId();


            Log.e("rammmkska",phoneNumber1);
        }
        else if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == CredentialsApi.ACTIVITY_RESULT_NO_HINTS_AVAILABLE)
        {
            // *** No phone numbers available ***
            Toast.makeText(TermsandConditionsActivity.this, "No phone numbers found", Toast.LENGTH_LONG).show();
        }


    }

}
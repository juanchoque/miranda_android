package codeformas.com.miranda_wear.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.wearable.activity.WearableActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import codeformas.com.miranda_wear.R;
import codeformas.com.miranda_wear.services.MirandaBackgroudService;
import codeformas.com.miranda_wear.util.ConstantsMiranda;

public class MainActivity extends WearableActivity {
    @BindView(R.id.tog_on_of)
    ToggleButton togOnOf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        this.togOnOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOfApp();
            }
        });

        // Enables Always-on
        setAmbientEnabled();

        //get permi
        this.getPermitions();

    }

    private void getPermitions(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck == PackageManager.PERMISSION_DENIED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},2);
        }

        //String imei = telephonyManager.getDeviceId();

        //get imei fist time
        /*permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if(permissionCheck == PackageManager.PERMISSION_DENIED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {

            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},1);
            }
            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            ConstantsMiranda.IMEI = telephonyManager.getDeviceId();
            Log.v("-------------->", ">>>>>>>IMEI>>>>>>>" + ConstantsMiranda.IMEI);
        }*/
    }

    public void onOfApp(){
        String myAndroidDeviceId = "";
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

        }

        TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephony.getDeviceId() != null){
            myAndroidDeviceId = mTelephony.getDeviceId();
        }else{
            myAndroidDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        //Log.v("-------------->", ">>>>>>>IMEI>>>>>>>" + myAndroidDeviceId);
        ConstantsMiranda.IMEI = myAndroidDeviceId;

        if(this.togOnOf.isChecked()){
            ////////////////////////////////////////////////////////
            Intent intent = new Intent(this, MirandaBackgroudService.class);
            startService(intent);
            ////////////////////////////////////////////////////////
        }
        else{
            ////////////////////////////////////////////////////////
            Intent intent = new Intent(this, MirandaBackgroudService.class);
            stopService(intent);
            ////////////////////////////////////////////////////////
        }

    }
}

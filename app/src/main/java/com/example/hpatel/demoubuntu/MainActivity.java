package com.example.hpatel.demoubuntu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button enable_b,disable_b;
    Boolean IsRegistered = false;
    Boolean IsWifiEnable = false;
    String pass;
    Context context;
    WifiManager wifi;
    boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // instance of resources
        enable_b = (Button)findViewById(R.id.enable_b);
        disable_b = (Button)findViewById(R.id.disable_b);

        context = this.getApplicationContext();
        wifi = (WifiManager) context.getApplicationContext().getSystemService(context.WIFI_SERVICE);


        // filter for reciever
        final IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.wifi.STATE_CHANGE");

        //setting password as 123
        pass="123";
        //checking is wifi is already enabled
        if(wifi.isWifiEnabled()){
//            IsWifiEnable=true;
//            wifi.setWifiEnabled(false);
        }

        // click event listeners
        enable_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!IsRegistered) {
                    context.registerReceiver(receiver, filter);
                    Toast.makeText(context,"registered ", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Reciever Laready Registered.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        disable_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IsRegistered){
                    context.unregisterReceiver(receiver);
                    Toast.makeText(context,"unregistered.",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Reciever Already Unregistered.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void custom_dialog(){
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
        final EditText etPassword = (EditText) alertLayout.findViewById(R.id.et_password);
        final CheckBox cbShowPassword = (CheckBox) alertLayout.findViewById(R.id.cb_show_password);


        cbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // to encode password in dots
                    etPassword.setTransformationMethod(null);
                } else {
                    // to display the password in normal text
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Enter Password");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                //alert.setCancelable(true);
            }
        });

        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String pass1 = etPassword.getText().toString();
                //Toast.makeText(context,"pass "+ pass +"pass1 "+ pass1 +" match "+pass1.equals(pass),Toast.LENGTH_SHORT).show();
                if(pass1.equals(pass)){
                    //Toast.makeText(context,"okk",Toast.LENGTH_SHORT).show();
                    //dialog.dismiss();
                    result = true;
                    Log.d("result1 ",""+result);
//                    if(IsWifiEnable==false)
//                    wifi.setWifiEnabled(true);
                    dialog.dismiss();
//                    dialog.cancel();
                    Log.d("result2 ",""+result);

                }else{
//                    wifi.setWifiEnabled(false);
                    Toast.makeText(context,"wrong password",Toast.LENGTH_SHORT).show();
                    result = false;
                    Log.d("result2 ",""+result);
                }
                if(etPassword.getText().toString().length()>0){
                    TextKeyListener.clear(etPassword.getText());
                }

            }
        });
        Toast.makeText(context,"result "+result,Toast.LENGTH_SHORT).show();
        Log.d("result ",""+result);
        AlertDialog dialog = alert.create();
        dialog.show();
       // return result;



    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//
            switch (wifi.getWifiState()){
                case WifiManager.WIFI_STATE_DISABLED:

//                    if(IsWifiEnable == false){
//                        custom_dialog();
//                        IsWifiEnable = true;
//                    }
//                    Log.d("wifi state dis",""+IsWifiEnable);
//                    if(IsWifiEnable == true){
//                        custom_dialog();
//                        IsWifiEnable = false;
//                    }
//                    Log.d("s","Disabled");
                    break;
               case WifiManager.WIFI_STATE_ENABLED:
                   Log.d("wifi state en",""+IsWifiEnable);
                   if(IsWifiEnable == false){
                       //wifi.setWifiEnabled(false);
                        boolean x = custom_dialog();

                            Log.d("x",""+x);
                       //if{
                         //  IsWifiEnable = true;
                           //wifi.setWifiEnabled(true);
//                       }else

                       IsWifiEnable = true;
                   }
                    Log.d("s","en");
                    break;
               case WifiManager.WIFI_STATE_DISABLING:
                   Log.d("wifi state dis",""+IsWifiEnable);
//                   if(IsWifiEnable == true){
//                       custom_dialog();
//                       IsWifiEnable = false;
//                   }
                    Log.d("s","Disabling");
                    break;
               case WifiManager.WIFI_STATE_ENABLING:
                    Log.d("s","eng");
                   Log.d("wifi state dis",""+IsWifiEnable);
                   if(IsWifiEnable == true){
                       custom_dialog();
                       IsWifiEnable = false;
                   }

                   break;
               case WifiManager.WIFI_STATE_UNKNOWN:
                    Log.d("s","unk");
                    break;
               default:
                   Log.d("d","kuch nai");break;
            }

        }
    };



    @Override
    protected void onPause() {
        super.onPause();
        if(IsRegistered) {
            context.unregisterReceiver(receiver);
            Toast.makeText(context, " unregistered reciver", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(IsRegistered) {
            context.unregisterReceiver(receiver);
            Toast.makeText(context, " unregistered reciver", Toast.LENGTH_SHORT).show();
        }
    }
}

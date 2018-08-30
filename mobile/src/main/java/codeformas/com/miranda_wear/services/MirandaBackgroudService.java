package codeformas.com.miranda_wear.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import codeformas.com.miranda_wear.model.Command;
import codeformas.com.miranda_wear.model.Ubication;
import codeformas.com.miranda_wear.socket.TCPCliente;
import codeformas.com.miranda_wear.util.ConstantsMiranda;
import codeformas.com.miranda_wear.util.GpsTracker;
import codeformas.com.miranda_wear.view.IUbicationsView;
import codeformas.com.miranda_wear.view.UbicationsView;

public class MirandaBackgroudService extends Service {
    private Gson gson = null;

    private ArrayList<String> arrayList;
    private TCPCliente mTcpClient = null;
    private ReceiveDataTask receiveDataTask = null;
    private SendDataTask sendDataTask = null;
    private boolean continueSendData = true;

    //atributes for location
    private GpsTracker gpsTracker = null;
    private Location location = null;

    private AppCompatActivity context = null;
    private IUbicationsView iUbicationsView;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "INICIANDO>>>", Toast.LENGTH_LONG).show();

        this.gson = new Gson();
        this.gpsTracker = new GpsTracker(getApplicationContext());

        arrayList = new ArrayList<String>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //run receive data
        receiveDataTask = new ReceiveDataTask();
        receiveDataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        //run send data
        sendDataTask = new SendDataTask();
        sendDataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        return START_STICKY;
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        onDestroy();
    }

    @Override
    public void onDestroy() {
        try
        {
            mTcpClient.sendMessage("Chau Bendiciones");
            mTcpClient.stopClient();
            continueSendData = false;

            receiveDataTask.cancel(true);
            receiveDataTask = null;

            sendDataTask.cancel(true);
            sendDataTask = null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        super.onDestroy();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    private class ReceiveDataTask extends AsyncTask<String, String, TCPCliente> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected TCPCliente doInBackground(String... message)
        {
            //creamos un objeto TCPCliente
            if(mTcpClient == null){
                mTcpClient = new TCPCliente(new TCPCliente.OnMessageReceived()
                {
                    @Override
                    //aquí se implementa el método messageReceived
                    public void messageReceived(String message)
                    {
                        try
                        {
                            publishProgress(message);
                            if(message != null)
                            {
                                System.out.println("Retornamos el mensaje del socket::::: >>>>> "+message);
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });

                mTcpClient.run();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            try {
                String value = values[0];
                sendBroadcastMessage(value);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void sendBroadcastMessage(String data) {
        Log.v("-sendBroadcastMessage->", "----->" + data);
        if (data != null) {
            Intent intent = new Intent(ConstantsMiranda.ACTION_LOCATION_BROADCAST);
            intent.putExtra(ConstantsMiranda.VALUE_LOCATION, data);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //task for send
    private class SendDataTask extends AsyncTask<String, String, TCPCliente> {
        private double latitude = 0;
        private double longitude = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected TCPCliente doInBackground(String... message)
        {
            //creamos un objeto TCPCliente
            while (continueSendData){
                if(mTcpClient != null){
                    publishProgress("");
                    mTcpClient.sendMessage("{\"cmd\":\"A\",\"latitude\": " + latitude + ",\"longitude\": " + longitude + ", \"imei\": \"" + ConstantsMiranda.IMEI + "\"}");
                }
                try {
                    Thread.sleep(5000);
                }catch (Exception e){

                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            location = gpsTracker.getLocation();
            if( location != null){
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Log.v("======>","GPS Lat = "+latitude+"\n lon = "+longitude);
            }else {
                Log.v("=======>",">>>NO ESTA ACTIVADO EL GPS>>>");
            }
        }
    }

}

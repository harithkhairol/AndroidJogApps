package net.harithproperties.jogapps;

/**
 * Created by Pika on 3/2/2018.
 */

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class LocationService extends Service implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final long INTERVAL = 1000 * 2;
    private static final long FASTEST_INTERVAL = 1000 * 1;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation, lStart, lEnd;
    static double distance = 0;
    double speed;
    private SQLiteDatabase dataBase;



    private final IBinder mBinder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
        return mBinder;
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onConnected(Bundle bundle) {
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        } catch (SecurityException e) {
        }
    }


    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        distance = 0;
    }


    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onLocationChanged(Location location) {
        Run.locate.dismiss();
        mCurrentLocation = location;
        if (lStart == null) {
            lStart = mCurrentLocation;
            lEnd = mCurrentLocation;
        } else
            lEnd = mCurrentLocation;

        //Calling the method below updates the  live values of distance and speed to the TextViews.
        updateUI();
        //calculating the speed with getSpeed method it returns speed in m/s so we are converting it into kmph
        speed = location.getSpeed() * 18 / 5;

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public class LocalBinder extends Binder {

        public LocationService getService() {
            return LocationService.this;
        }


    }

    //The live feed of Distance and Speed are being set in the method below .
    public void updateUI() {
        if (Run.p == 0) {
            distance = distance + (lStart.distanceTo(lEnd) / 1000.00);
            Run.endTime = System.currentTimeMillis();
            long diff = Run.endTime - Run.startTime;
            diff = TimeUnit.MILLISECONDS.toMinutes(diff);
            Run.time.setText("Total Time: " + diff + " minutes");
            if (speed > 0.0)
                Run.speed.setText("Current speed: " + new DecimalFormat("#.##").format(speed) + " km/hr");
            else
                Run.speed.setText(".......");

            Run.dist.setText(new DecimalFormat("#.###").format(distance) + " Km's.");

            lStart = lEnd;

        }

        if (Run.p == 3) {
            distance = distance + (lStart.distanceTo(lEnd) / 1000.00);
            Run.endTime = System.currentTimeMillis();
            long diff = Run.endTime - Run.startTime;
            diff = TimeUnit.MILLISECONDS.toMinutes(diff);
            Run.time.setText("Total Time: " + diff + " minutes");
            if (speed > 0.0)
                Run.speed.setText("Current speed: " + new DecimalFormat("#.##").format(speed) + " km/hr");
            else
                Run.speed.setText(".......");

            Run.dist.setText(new DecimalFormat("#.###").format(distance) + " Km's.");

            lStart = lEnd;

    /*        final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            ContentValues values=new ContentValues();

            values.put(DBHelper.DATE,day+ "/" + (month+1) + "/" + year);
            values.put(DBHelper.TIME,System.currentTimeMillis());
            values.put(DBHelper.DISTANCEKM, distance );
            values.put(DBHelper.SPEED,speed );
            values.put(DBHelper.DURATION,diff );

            System.out.println("");

            //save new record to the database into database
            dataBase.insert(DBHelper.TABLE_NAME, null, values);

            //close database
            dataBase.close();

            finish();

            Toast.makeText(LocationService.this,"Data Inserted Successfully", Toast.LENGTH_LONG).show(); */


        }


    }


    @Override
    public boolean onUnbind(Intent intent) {
        stopLocationUpdates();
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
        lStart = null;
        lEnd = null;
        distance = 0;
        return super.onUnbind(intent);
    }
}


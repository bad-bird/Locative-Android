package io.locative.app.beacon;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.RemoteException;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

import javax.inject.Inject;

import dagger.Module;
import io.locative.app.utils.Constants;

@Module
public class BeaconController implements BeaconConsumer, RangeNotifier {

    private Context mContext;
    private BeaconManager mManager;

    @Inject
    public BeaconController(Context context) {
        mContext = context;
        mManager = BeaconManager.getInstanceForApplication(mContext);
        mManager.getBeaconParsers().add(
                new BeaconParser(Layout.IBEACON_LAYOUT)
        );
        mManager.bind(this);
        mManager.addRangeNotifier(this);
    }

    public void startRanging(BeaconItem beacon) {
        try {
            mManager.startRangingBeaconsInRegion(beacon.toRegion());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stopRanging(BeaconItem beacon) {
        try {
            mManager.stopRangingBeaconsInRegion(beacon.toRegion());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBeaconServiceConnect() {

    }

    @Override
    public Context getApplicationContext() {
        return mContext;
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {

    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return false;
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
        Log.d(Constants.LOG, "didRangeBeacons: " + collection + "InRegion: " + region);
    }
}
package com.example.krishnaachanta.beacondemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import org.altbeacon.beacon.BeaconConsumer
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.MonitorNotifier
import org.altbeacon.beacon.Region

class MainActivity : AppCompatActivity(), BeaconConsumer {
    private lateinit var beaconManager: BeaconManager;
    protected val TAG = "MonitoringActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager.bind(this)
    }

    override fun onBeaconServiceConnect() {
        beaconManager.addMonitorNotifier(object : MonitorNotifier {

            override fun didDetermineStateForRegion(state: Int, region: Region) {
                Log.i(TAG, "I have just switched from seeing/not seeing beacons: "+state);
            }

            override fun didEnterRegion(region: Region) {
                Log.i(TAG, "I just saw an beacon for the first time!");
            }

            override fun didExitRegion(region: Region) {
                Log.i(TAG, "I no longer see an beacon")
            }
        })

        try {
            beaconManager.startMonitoringBeaconsInRegion(Region("myMonitoringUniqueId", null, null, null))
        } catch (e: RemoteException) {
        }

    }
}

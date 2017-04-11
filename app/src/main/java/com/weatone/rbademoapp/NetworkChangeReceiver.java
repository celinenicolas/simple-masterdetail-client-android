package com.weatone.rbademoapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

/**
 * @author Celine Nicolas
 * @version 1.0.0, 2017-04-11
 * @since 1.0.0
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    private NetworkChangeDelegate delegate;
    private ConnectivityManager cm;
    private NetworkInfo mNetworkInfo;
    private NetworkInfo.State mState;

    public NetworkChangeReceiver(Context context) {
        delegate = (NetworkChangeDelegate) context;
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        mNetworkInfo = cm.getActiveNetworkInfo();
        if ( mNetworkInfo != null ) {
            Log.d("CONNECTIVITY", "State:" + mNetworkInfo.getState());
            mState = mNetworkInfo.getState();
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        mNetworkInfo = cm.getActiveNetworkInfo();

        if ( delegate != null) {
            checkConnection();
        }
    }

    private void checkConnection() {

        if ( mNetworkInfo != null ) {
            Log.d("CONNECTIVITY", "State:" + mNetworkInfo.getState());
            mState = mNetworkInfo.getState();
        }

        // Connected
        if ( mNetworkInfo != null && mNetworkInfo.isConnected() ) {
            delegate.onConnected();

            // Disconnected, Failed to connect
        } else {
            if (mNetworkInfo != null && mNetworkInfo.getState() != NetworkInfo.State.CONNECTING)
                return;
            delegate.onDisconnected();

        }
    }

    public NetworkInfo.State getState() {
        return mState;
    }

    public boolean isConnected() {
        return mState == NetworkInfo.State.CONNECTED;
    }

}

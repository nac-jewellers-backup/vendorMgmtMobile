package com.adminapp.nac_admin.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Networkconnectivity {

    public Context context;

    /**
     * <p>
     * Constructor for ConnectionDetector.
     * </p>
     *
     * @param cx
     *            The context of the calling activity.
     */
    public Networkconnectivity(Context cx) {
        context = cx;
    }

    /**
     * Returns true if device is connected to any network.
     */
    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
}

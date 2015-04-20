package com.feedhenry.sdk.tests.api;

import android.test.AndroidTestCase;
import com.feedhenry.sdk.NetworkManager;

public class NetworkManagerTest extends AndroidTestCase {

  public void testNetworkManager(){
    NetworkManager.init(getContext());
    NetworkManager nm = NetworkManager.getInstance();
    nm.checkNetworkStatus();
    boolean isOnline = nm.isOnline();
    assertTrue(isOnline);
  }
}

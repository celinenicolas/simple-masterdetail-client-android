package com.weatone.rbademoapp;

/**
 * @author Celine Nicolas
 * @version 1.0.0, 2017-04-11
 * @since 1.0.0
 */

public interface NetworkChangeDelegate {
    void onConnected();
    void onDisconnected();
    boolean isConnected();
}

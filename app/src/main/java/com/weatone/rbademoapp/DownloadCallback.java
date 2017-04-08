package com.weatone.rbademoapp;

/**
 * @author Celine Nicolas
 * @version 1.0.0, 2017-03-16
 * @since 1.0.0
 */

public interface DownloadCallback<T> {

    //void onProgressUpdate(int progressCode, int percentCompleted);
    //void onDownloadComplete();
    void onData(T result);
    //void onError(String message);

}

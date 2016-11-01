package org.ucomplex.ucomplex.Interfaces;

/**
 * Created by Sermilion on 01/11/2016.
 */

public interface ProgressTracker {

    void onProgress(String message);

    void onComplete();

}

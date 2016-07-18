package com.eidehua.quickpinyin;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Intent;
import android.os.IBinder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Edward on 7/17/2016.
 * Based off http://stackoverflow.com/questions/22277598/permanently-listen-to-clipboard-changes
 */
public class CBWatcherService extends Service {

    // TODO: fix
    private String ClipboardCacheFolderPath = "/temp/";
    private final String tag = "[[ClipboardWatcherService]] ";

    private OnPrimaryClipChangedListener listener = new OnPrimaryClipChangedListener() {
        public void onPrimaryClipChanged() {
            performClipboardCheck();
        }
    };

    @Override
    public void onCreate() {
        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).addPrimaryClipChangedListener(listener);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        File folder = new File(ClipboardCacheFolderPath);
        // ClipboardCacheFolderPath is a predefined constant with the path
        // where the clipboard contents will be written

        if (!folder.exists()) { folder.mkdir(); }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void performClipboardCheck() {
        ClipboardManager cb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (cb.hasPrimaryClip()) {
            ClipData cd = cb.getPrimaryClip();
            if (cd.getDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                try {
                    File folder = new File(ClipboardCacheFolderPath);
                    if (!folder.exists()) { folder.mkdir(); }
                    Calendar cal = Calendar.getInstance();
                    String newCachedClip =
                            cal.get(Calendar.YEAR) + "-" +
                                    cal.get(Calendar.MONTH) + "-" +
                                    cal.get(Calendar.DAY_OF_MONTH) + "-" +
                                    cal.get(Calendar.HOUR_OF_DAY) + "-" +
                                    cal.get(Calendar.MINUTE) + "-" +
                                    cal.get(Calendar.SECOND);

                    // The name of the file acts as the timestamp (ingenious, uh?)
                    File file = new File(ClipboardCacheFolderPath + newCachedClip);
                    file.createNewFile();
                    BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
                    bWriter.write((cd.getItemAt(0).getText()).toString());
                    bWriter.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
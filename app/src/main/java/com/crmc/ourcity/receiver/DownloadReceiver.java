package com.crmc.ourcity.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.utils.FilePath;

import java.io.File;

/**
 * Created by SetKrul on 27.07.2015.
 */
public class DownloadReceiver extends BroadcastReceiver {
    public DownloadReceiver() {
    }

    @Override
    public void onReceive(Context _context, Intent _intent) {
        String action = _intent.getAction();
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            DownloadManager.Query query = new DownloadManager.Query();
            long downloadId = _intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            query.setFilterById(downloadId);
            DownloadManager mDownloadManager = (DownloadManager) _context.getSystemService(Context.DOWNLOAD_SERVICE);
            Cursor c = mDownloadManager.query(query);
            if (c.moveToFirst()) {
                int status = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(status)) {
                    String uriString = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    File file = new File(new FilePath().getPath(_context, Uri.parse(uriString)));
                    if (file.exists()) {
                        Toast.makeText(_context, _context.getResources().getString(R.string.download_success), Toast
                                .LENGTH_SHORT).show();
                    }
                } else if (status == DownloadManager.STATUS_FAILED) {
                    Toast.makeText(_context, _context.getResources().getString(R.string.download_fail), Toast
                            .LENGTH_SHORT).show();
                }
            }
        }
    }
}

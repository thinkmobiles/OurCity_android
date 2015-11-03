package com.crmc.ourcity.utils;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import com.crmc.ourcity.R;
import com.crmc.ourcity.receiver.DownloadReceiver;

import java.io.File;

public class DownloadFile {

    public void downloadPdf(Context _context, String _url) {
//        _url = "http://dlcdnet.asus.com/pub/ASUS/mb/socket775/P5B/e2620_p5b.pdf";
        DownloadManager myDownloadManager;
        DownloadReceiver myDownLoadReceiver = new DownloadReceiver();
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        _context.registerReceiver(myDownLoadReceiver, intentFilter);
        if (!TextUtils.isEmpty(_url)) {
            String fileName = _url.substring(_url.lastIndexOf("/") + 1);
            Uri linkFile = Uri.parse("file://" + Environment.getExternalStorageDirectory() + "/" + fileName);
            File file = new File(new FilePath().getPath(_context, linkFile));
            if (!file.exists()) {
                try {
                    myDownloadManager = (DownloadManager) _context.getSystemService(Context.DOWNLOAD_SERVICE);
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse((_url)));
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setMimeType("application/pdf");
                    request.setDescription(_context.getResources().getString(R.string.download_from) + " " + _url);
                    request.setTitle(_context.getResources().getString(R.string.download) + " " + fileName);
                    request.setDestinationUri(linkFile);
                    myDownloadManager.enqueue(request);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Uri path = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(path, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                try {
                    _context.startActivity(intent);
                } catch (ActivityNotFoundException _e) {
                    Toast.makeText(_context, _context.getResources().getString(R.string.no_application_to_open_pdf), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(_context, "Empty _url", Toast.LENGTH_SHORT).show();
        }
    }

}

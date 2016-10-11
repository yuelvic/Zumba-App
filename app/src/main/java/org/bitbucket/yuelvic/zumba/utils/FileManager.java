package org.bitbucket.yuelvic.zumba.utils;

import android.net.Uri;
import android.os.Environment;

import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListenerV1;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by yuelvic on 10/11/16.
 */
public class FileManager {

    public ArrayList<File> getListOfFiles(File dir) {
        ArrayList<File> files = new ArrayList<>();
        File[] arrFiles = dir.listFiles();
        for (File file : arrFiles) {
            if (file.isDirectory()) {
                files.addAll(getListOfFiles(file));
            } else {
                if (file.getName().endsWith(".mp4")) {
                    files.add(file);
                }
            }
        }
        return files;
    }

    public void downloadFiles(String name, String[] urls, final DownloadProgressListener listener) {
        File file = new File(Environment.getExternalStorageDirectory() + "/Zumba/" + name + "/");
        if (!file.exists()) file.mkdirs();

        Uri downloadUri;
        Uri destinationUri;
        DownloadRequest request;
        ThinDownloadManager downloadManager = new ThinDownloadManager();

        for (int step = 0; step < urls.length; step++) {
            downloadUri = Uri.parse(urls[step]);
            destinationUri = Uri.parse(file.getAbsolutePath() + String.format("%s-%d", name, step) + ".mp4");
            request = new DownloadRequest(downloadUri)
                    .setDestinationURI(destinationUri)
                    .setRetryPolicy(new DefaultRetryPolicy())
                    .setStatusListener(new DownloadStatusListenerV1() {
                        @Override
                        public void onDownloadComplete(DownloadRequest downloadRequest) {
                            listener.onSuccess(downloadRequest.getDownloadId());
                        }

                        @Override
                        public void onDownloadFailed(DownloadRequest downloadRequest, int errorCode, String errorMessage) {
                            listener.onFail(downloadRequest.getDownloadId());
                        }

                        @Override
                        public void onProgress(DownloadRequest downloadRequest, long totalBytes, long downloadedBytes, int progress) {

                        }
                    });
            downloadManager.add(request);
        }
    }

    interface DownloadProgressListener {
        void onFail(int id);
        void onSuccess(int id);
    }

}

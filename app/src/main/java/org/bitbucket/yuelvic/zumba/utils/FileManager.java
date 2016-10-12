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

    public String[] getFilePaths(String name) {
        switch (name) {
            case "CAIPIRINHA": return Constants.CAIPIRINHA;
            case "LIMBO": return Constants.LIMBO;
            case "CHUCUCHA": return Constants.CHUCUCHA;
            case "GINZA": return Constants.GINZA;
            case "SHAKE": return Constants.SHAKE;
            case "SHAKE_SENORA": return Constants.SHAKE_SENORA;
            case "TOMA_REGGAETON": return Constants.TOMA_REGGAETON;
            case "WINE_IT_UP": return Constants.WINE_IT_UP;
            case "PRETHAM": return Constants.PRETHAM;
            case "BOOM_BOOM_MAMA": return Constants.BOOM_MAMA;
            case "MONSTER_WINER": return Constants.MONSTER_WINER;
            case "ECHA_PALLA": return Constants.ECHA_PALLA;
            case "KUKERE": return Constants.KUKERE;
            case "LA_SUEVECITA": return Constants.LA_SUEVECITA;
            case "FIESTA_BAJO_EL_SOL": return Constants.FIESTA_BAJO;
            case "ADIOS": return Constants.ADIOS;
            default: return Constants.CAIPIRINHA;
        }
    }

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

    public boolean isFilesDownloaded(File dir) {
        return dir.exists() && dir.listFiles().length > 0;
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
            destinationUri = Uri.parse(file.getPath() + "/" + String.format("%s-%d", name, step+1) + ".mp4");
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

    public interface DownloadProgressListener {
        void onFail(int id);
        void onSuccess(int id);
    }

}

package org.bitbucket.yuelvic.zumba.utils;

import android.net.Uri;
import android.os.Environment;

import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListenerV1;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by yuelvic on 10/11/16.
 */
public class FileManager {

    public String[] getOnlinePaths(ArrayList<File> files) {
        List<String> filePaths = new ArrayList<>();
        for (File f : files) filePaths.add(f.getAbsolutePath());
        return Arrays.copyOf(filePaths.toArray(), filePaths.toArray().length, String[].class);
    }

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

    public String getFileFullPath(String name) {
        switch (name) {
            case "CAIPIRINHA": return Constants.CAIPIRINHA_FULL;
            case "LIMBO": return Constants.LIMBO_FULL;
            case "CHUCUCHA": return Constants.CHUCUCHA_FULL;
            case "GINZA": return Constants.GINZA_FULL;
            case "SHAKE": return Constants.SHAKE_FULL;
            case "SHAKE_SENORA": return Constants.SHAKE_SENORA_FULL;
            case "TOMA_REGGAETON": return Constants.TOMA_REGGAETON_FULL;
            case "WINE_IT_UP": return Constants.WINE_IT_UP_FULL;
            case "PRETHAM": return Constants.PRETHAM_FULL;
            case "BOOM_BOOM_MAMA": return Constants.BOOM_MAMA_FULL;
            case "MONSTER_WINER": return Constants.MONSTER_WINER_FULL;
            case "ECHA_PALLA": return Constants.ECHA_PALLA_FULL;
            case "KUKERE": return Constants.KUKERE_FULL;
            case "LA_SUEVECITA": return Constants.LA_SUEVECITA_FULL;
            case "FIESTA_BAJO_EL_SOL": return Constants.FIESTA_BAJO_FULL;
            case "ADIOS": return Constants.ADIOS_FULL;
            default: return Constants.CAIPIRINHA_FULL;
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
        Collections.sort(files);
        return files;
    }

    public boolean isFilesDownloaded(File dir) {
        return dir.exists() && dir.listFiles().length > 0;
    }

    public void downloadFiles(String name, String full, String[] urls, final DownloadProgressListener listener) {
        File file = new File(Environment.getExternalStorageDirectory() + "/Zumba/" + name + "/");
        if (!file.exists()) file.mkdirs();

        Uri downloadUri = Uri.parse(full);
        Uri destinationUri = Uri.parse(file.getPath() + "/" + String.format("%s-full", name) + ".mp4");
        DownloadRequest request;
        ThinDownloadManager downloadManager = new ThinDownloadManager();

        request = new DownloadRequest(downloadUri)
                .setDestinationURI(destinationUri)
                .setRetryPolicy(new DefaultRetryPolicy())
                .setStatusListener(new DownloadStatusListenerV1() {
                    @Override
                    public void onDownloadComplete(DownloadRequest downloadRequest) {
                        listener.onStart();
                    }

                    @Override
                    public void onDownloadFailed(DownloadRequest downloadRequest, int errorCode, String errorMessage) {

                    }

                    @Override
                    public void onProgress(DownloadRequest downloadRequest, long totalBytes, long downloadedBytes, int progress) {

                    }
                });
        downloadManager.add(request);

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
        void onStart();
        void onFail(int id);
        void onSuccess(int id);
    }

}

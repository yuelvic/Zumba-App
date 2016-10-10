package org.bitbucket.yuelvic.zumba.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListenerV1;
import com.thin.downloadmanager.ThinDownloadManager;

import org.bitbucket.yuelvic.zumba.R;
import org.bitbucket.yuelvic.zumba.models.Video;
import org.bitbucket.yuelvic.zumba.utils.Constants;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by yuelvic on 10/10/16.
 */
public class HomeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Video> videos;

    public HomeAdapter(Context context) {
        this.context = context;
        videos = new ArrayList<>();
    }

    public void addItems(ArrayList<Video> videos) {
        this.videos.addAll(videos);
        notifyDataSetChanged();
    }

    public Video getVideo(int position) {
        return this.videos.get(position);
    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Object getItem(int position) {
        return videos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(this.context).inflate(R.layout.item_video, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        final Video video = this.videos.get(position);
        Glide.with(this.context).load(R.drawable.test).into(holder.ivCover);
        holder.tvTitle.setText(video.getTitle());
        holder.tvArtist.setText(video.getArtist());
        holder.tvDuration.setText(String.format("%d", video.getDuration()));

        RxView.clicks(holder.ivDownload).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                downloadVideo(video.getUrl());
            }
        });

        return view;
    }

    private void downloadVideo(String path) {
        File file = new File(Environment.getExternalStorageDirectory() + "/Zumba/");
        if (!file.exists()) file.mkdirs();

        Uri downloadUri = Uri.parse(path);
        Uri destinationUri = Uri.parse(Environment.getExternalStorageDirectory() + "/Zumba/" + System.currentTimeMillis() + ".mp4");
        DownloadRequest request = new DownloadRequest(downloadUri)
                .setRetryPolicy(new DefaultRetryPolicy())
                .setDestinationURI(destinationUri)
                .setStatusListener(new DownloadStatusListenerV1() {
                    @Override
                    public void onDownloadComplete(DownloadRequest downloadRequest) {
                        Toast.makeText(context, "Download successful", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onDownloadFailed(DownloadRequest downloadRequest, int errorCode, String errorMessage) {
                        Toast.makeText(context, "Failed to download video", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onProgress(DownloadRequest downloadRequest, long totalBytes, long downloadedBytes, int progress) {

                    }
                });
        ThinDownloadManager manager = new ThinDownloadManager();
        manager.add(request);
    }

    static class ViewHolder {
        @BindView(R.id.iv_cover) ImageView ivCover;
        @BindView(R.id.iv_download) ImageView ivDownload;
        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.tv_artist) TextView tvArtist;
        @BindView(R.id.tv_duration) TextView tvDuration;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}

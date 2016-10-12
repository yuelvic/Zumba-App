package org.bitbucket.yuelvic.zumba.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import org.bitbucket.yuelvic.zumba.R;
import org.bitbucket.yuelvic.zumba.VideoActivity;
import org.bitbucket.yuelvic.zumba.models.VideoType;
import org.bitbucket.yuelvic.zumba.utils.Constants;
import org.bitbucket.yuelvic.zumba.utils.FileManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by yuelvic on 10/12/16.
 */
public class OfflineAdapter extends RecyclerView.Adapter<OfflineAdapter.ViewHolder> {

    private Context context;
    private ArrayList<VideoType> videoTypes;

    public OfflineAdapter(Context context) {
        this.context = context;
        this.videoTypes = new ArrayList<>();
    }

    public void addItems(ArrayList<VideoType> videoTypes) {
        this.videoTypes.addAll(videoTypes);
        notifyDataSetChanged();
    }

    public VideoType getVideoType(int position) {
        return this.videoTypes.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_type, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final VideoType videoType = videoTypes.get(position);
        holder.tvType.setText(videoType.getName());
        if (!videoType.isDownloaded()) holder.ivDownload.setVisibility(View.VISIBLE);

        if (videoType.isDownloaded()) {
            RxView.clicks(holder.itemView).subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    Toast.makeText(context, "Downloaded", Toast.LENGTH_SHORT).show();
                }
            });
        }

        RxView.clicks(holder.ivDownload).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                FileManager fileManager = new FileManager();
                fileManager.downloadFiles(videoType.getName(), fileManager.getFilePaths(videoType.getName()), new FileManager.DownloadProgressListener() {
                    @Override
                    public void onFail(int id) {

                    }

                    @Override
                    public void onSuccess(int id) {
                        Toast.makeText(context, "done", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoTypes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_type) TextView tvType;
        @BindView(R.id.iv_download) ImageView ivDownload;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

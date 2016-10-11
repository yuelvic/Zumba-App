package org.bitbucket.yuelvic.zumba.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.bitbucket.yuelvic.zumba.R;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yuelvic on 10/11/16.
 */
public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {

    private Context context;
    private ArrayList<File> files;

    public DownloadAdapter(Context context) {
        this.context = context;
        this.files = new ArrayList<>();
    }

    public void addFiles(ArrayList<File> files) {
        this.files.addAll(files);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_video, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        File file = files.get(position);
        holder.ivCover.setImageBitmap(getVideoThumbnail(file));
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    private Bitmap getVideoThumbnail(File file) {
        return ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover) ImageView ivCover;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

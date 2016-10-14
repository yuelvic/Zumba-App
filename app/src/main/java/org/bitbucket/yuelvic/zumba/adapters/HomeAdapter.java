package org.bitbucket.yuelvic.zumba.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.bitbucket.yuelvic.zumba.R;
import org.bitbucket.yuelvic.zumba.models.Video;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        holder.tvTitle.setText(video.getTitle());

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_title) TextView tvTitle;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}

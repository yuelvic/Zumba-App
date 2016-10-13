package org.bitbucket.yuelvic.zumba.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import org.bitbucket.yuelvic.zumba.R;
import org.bitbucket.yuelvic.zumba.TypeActivity;
import org.bitbucket.yuelvic.zumba.models.VideoType;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by yuelvic on 10/10/16.
 */
public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<VideoType> videoTypes;

    public TypeAdapter(Context context) {
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

        RxView.clicks(holder.itemView).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(context, TypeActivity.class);
                intent.putExtra("type", videoType);
                intent.putExtra("mode", "online");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoTypes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_type) TextView tvType;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

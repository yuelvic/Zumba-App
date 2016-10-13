package org.bitbucket.yuelvic.zumba.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.bitbucket.yuelvic.zumba.R;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yuelvic on 10/13/16.
 */
public class FileAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<File> files;

    public FileAdapter(Context context) {
        this.context = context;
        this.files = new ArrayList<>();
    }

    public void addFiles(ArrayList<File> files) {
        this.files.addAll(files);
        notifyDataSetChanged();
    }

    public File getFile(int position) {
        return this.files.get(position);
    }

    @Override
    public int getCount() {
        return this.files.size();
    }

    @Override
    public Object getItem(int position) {
        return this.files.get(position);
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
            view = LayoutInflater.from(context).inflate(R.layout.item_video, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        File file = files.get(position);
        holder.tvTitle.setText(file.getName());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_title) TextView tvTitle;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}

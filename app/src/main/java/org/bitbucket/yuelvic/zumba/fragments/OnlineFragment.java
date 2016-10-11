package org.bitbucket.yuelvic.zumba.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.bitbucket.yuelvic.zumba.R;
import org.bitbucket.yuelvic.zumba.adapters.TypeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yuelvic on 10/11/16.
 */
public class OnlineFragment extends Fragment {

    @BindView(R.id.recycler_type) RecyclerView recyclerType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_online, container, false);
        ButterKnife.bind(this, view);

        // Init views
        initViews();
        return view;
    }

    /**
     * Initialize views
     */
    private void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        TypeAdapter adapter = new TypeAdapter(getActivity());
        recyclerType.setAdapter(adapter);
    }

}

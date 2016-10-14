package org.bitbucket.yuelvic.zumba;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.devbrackets.android.exomedia.listener.OnCompletionListener;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;
import com.jakewharton.rxbinding.view.RxView;

import org.bitbucket.yuelvic.zumba.models.Video;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class NonStopActivity extends AppCompatActivity implements OnPreparedListener, OnCompletionListener {

    @BindView(R.id.vv_video) EMVideoView emVideo;

    private int index = 0;
    private String[] videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_stop);

        ButterKnife.bind(this);

        // Init views
        initViews();
    }

    private void initViews() {
        Intent intent = getIntent();
        videos = intent.getStringArrayExtra("videos");
        emVideo.setOnPreparedListener(this);
        emVideo.setOnCompletionListener(this);
        emVideo.setVideoURI(Uri.parse(videos[index]));
    }

    @Override
    public void onPrepared() {
        emVideo.start();
    }

    @Override
    public void onCompletion() {
        index++;
        if (index > videos.length) finish();
        else emVideo.setVideoURI(Uri.parse(videos[index]));
    }

    @Override
    protected void onPause() {
        super.onPause();
        emVideo.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        emVideo.release();
    }

}

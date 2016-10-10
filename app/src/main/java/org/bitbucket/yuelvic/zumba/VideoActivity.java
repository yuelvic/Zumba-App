package org.bitbucket.yuelvic.zumba;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;

import org.bitbucket.yuelvic.zumba.models.Video;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoActivity extends AppCompatActivity implements OnPreparedListener {

    @BindView(R.id.vv_video) EMVideoView emVideo;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_artist) TextView tvArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        ButterKnife.bind(this);

        // Init views
        initViews();
    }

    private void initViews() {
        Video video = getIntent().getParcelableExtra("video");
        emVideo.setOnPreparedListener(this);
        emVideo.setVideoURI(Uri.parse(video.getUrl()));
        tvTitle.setText(video.getTitle());
        tvArtist.setText(video.getArtist());
    }

    @Override
    public void onPrepared() {
        emVideo.start();
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

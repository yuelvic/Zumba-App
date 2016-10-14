package org.bitbucket.yuelvic.zumba;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;
import com.jakewharton.rxbinding.view.RxView;

import org.bitbucket.yuelvic.zumba.models.Video;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class VideoActivity extends AppCompatActivity implements OnPreparedListener {

    @BindView(R.id.vv_video) EMVideoView emVideo;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_artist) TextView tvArtist;
    @BindView(R.id.btn_prev) Button btnPrev;
    @BindView(R.id.btn_next) Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        ButterKnife.bind(this);

        // Init views
        initViews();
    }

    private void initViews() {
        Intent intent = getIntent();
        Video video = intent.getParcelableExtra("video");
        final int[] index = {intent.getIntExtra("index", 0)};
        final String[] videos = intent.getStringArrayExtra("videos");
        emVideo.setOnPreparedListener(this);
        emVideo.setVideoURI(Uri.parse(videos[index[0]]));
        tvTitle.setText(video.getTitle());

        RxView.clicks(btnPrev).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                index[0]--;
                if (index[0] <= 0) index[0] = 0;
                emVideo.setVideoURI(Uri.parse(videos[index[0]]));
            }
        });

        RxView.clicks(btnNext).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                index[0]++;
                if (index[0] >= videos.length-1) index[0] = videos.length-1;
                emVideo.setVideoURI(Uri.parse(videos[index[0]]));
            }
        });
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

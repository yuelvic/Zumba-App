package org.bitbucket.yuelvic.zumba;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;

import org.bitbucket.yuelvic.zumba.adapters.HomeAdapter;
import org.bitbucket.yuelvic.zumba.models.Video;
import org.bitbucket.yuelvic.zumba.models.VideoType;
import org.bitbucket.yuelvic.zumba.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TypeActivity extends AppCompatActivity implements OnPreparedListener {

    @BindView(R.id.vv_video) EMVideoView emVideo;
    @BindView(R.id.list_video) ListView listVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        ButterKnife.bind(this);

        // Init views
        initViews();
    }

    /**
     * Initialize views
     */
    private void initViews() {
        ArrayList<Video> videos = new ArrayList<>();
        String[] arrVideos = new String[0];
        String fullVideo = null;

        VideoType videoType = getIntent().getParcelableExtra("type");

        switch (videoType.getName()) {
            case "CAIPIRINHA":
                arrVideos = Constants.CAIPIRINHA;
                fullVideo = Constants.CAIPIRINHA_FULL;
                break;
            case "LIMBO":
                arrVideos = Constants.LIMBO;
                fullVideo = Constants.LIMBO_FULL;
                break;
            case "CHUCUCHA":
                arrVideos = Constants.CHUCUCHA;
                fullVideo = Constants.CHUCUCHA_FULL;
                break;
            case "GINZA":
                arrVideos = Constants.GINZA;
                fullVideo = Constants.GINZA_FULL;
                break;
            case "SHAKE":
                arrVideos = Constants.SHAKE;
                fullVideo = Constants.SHAKE_FULL;
                break;
            case "SHAKE_SENORA":
                arrVideos = Constants.SHAKE_SENORA;
                fullVideo = Constants.SHAKE_SENORA_FULL;
                break;
            case "TOMA_REGGAETON":
                arrVideos = Constants.TOMA_REGGAETON;
                fullVideo = Constants.TOMA_REGGAETON_FULL;
                break;
            case "WINE_IT_UP":
                arrVideos = Constants.WINE_IT_UP;
                fullVideo = Constants.WINE_IT_UP_FULL;
                break;
        }

        emVideo.setOnPreparedListener(this);
        emVideo.setVideoURI(Uri.parse(fullVideo));

        int step = 0;
        for (String arrVideo : arrVideos) {
            Video video = new Video();
            video.setTitle("Step " + ++step);
            video.setUrl(arrVideo);
            video.setArtist("STI");
            video.setDuration(7537128);
            videos.add(video);
        }
        final HomeAdapter adapter = new HomeAdapter(getApplicationContext());
        adapter.addItems(videos);
        listVideo.setAdapter(adapter);
        listVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                intent.putExtra("video", adapter.getVideo(position));
                startActivity(intent);
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
        if (emVideo.isPlaying()) emVideo.pause();
    }
}

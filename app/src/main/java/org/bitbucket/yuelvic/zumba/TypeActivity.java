package org.bitbucket.yuelvic.zumba;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;

import org.bitbucket.yuelvic.zumba.adapters.DownloadAdapter;
import org.bitbucket.yuelvic.zumba.adapters.FileAdapter;
import org.bitbucket.yuelvic.zumba.adapters.HomeAdapter;
import org.bitbucket.yuelvic.zumba.models.Video;
import org.bitbucket.yuelvic.zumba.models.VideoType;
import org.bitbucket.yuelvic.zumba.utils.Constants;
import org.bitbucket.yuelvic.zumba.utils.FileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TypeActivity extends AppCompatActivity implements OnPreparedListener {

    @BindView(R.id.vv_video) EMVideoView emVideo;
    @BindView(R.id.list_video) ListView listVideo;

    private FileManager fileManager;
    private Intent intent;
    private String[] arrVideos;
    private String fullVideo;
    private VideoType videoType;

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
        arrVideos = new String[0];
        fullVideo = null;

        intent = getIntent();
        videoType = intent.getParcelableExtra("type");
        String mode = intent.getStringExtra("mode");

        fileManager = new FileManager();
        if (mode.equals("online")) initOnline();
        else initOffline();

        emVideo.setOnPreparedListener(this);
        emVideo.setVideoURI(Uri.parse(fullVideo));
    }

    /**
     * Initialize online
     */
    private void initOnline() {
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
            case "SHAKE SENORA":
                arrVideos = Constants.SHAKE_SENORA;
                fullVideo = Constants.SHAKE_SENORA_FULL;
                break;
            case "TOMA REGGAETON":
                arrVideos = Constants.TOMA_REGGAETON;
                fullVideo = Constants.TOMA_REGGAETON_FULL;
                break;
            case "WINE IT UP":
                arrVideos = Constants.WINE_IT_UP;
                fullVideo = Constants.WINE_IT_UP_FULL;
                break;
            case "PRETHAM":
                arrVideos = Constants.PRETHAM;
                fullVideo = Constants.PRETHAM_FULL;
                break;
            case "BOOM BOOM MAMA":
                arrVideos = Constants.BOOM_MAMA;
                fullVideo = Constants.BOOM_MAMA_FULL;
                break;
            case "MONSTER WINER":
                arrVideos = Constants.MONSTER_WINER;
                fullVideo = Constants.MONSTER_WINER_FULL;
                break;
            case "ECHA PALLA":
                arrVideos = Constants.ECHA_PALLA;
                fullVideo = Constants.ECHA_PALLA_FULL;
                break;
            case "KUKERE":
                arrVideos = Constants.KUKERE;
                fullVideo = Constants.KUKERE_FULL;
                break;
            case "LA SUEVECITA":
                arrVideos = Constants.LA_SUEVECITA;
                fullVideo = Constants.LA_SUEVECITA_FULL;
                break;
            case "FIESTA BAJO EL SOL":
                arrVideos = Constants.FIESTA_BAJO;
                fullVideo = Constants.FIESTA_BAJO_FULL;
                break;
            case "ADIOS":
                arrVideos = Constants.ADIOS;
                fullVideo = Constants.ADIOS_FULL;
                break;
            default: arrVideos = Constants.CAIPIRINHA; fullVideo = Constants.CAIPIRINHA_FULL;
        }

        ArrayList<Video> videos = new ArrayList<>();
        int step = 0;
        for (String arrVideo : arrVideos) {
            Video video = new Video();
            video.setTitle("Step " + ++step);
            video.setUrl(arrVideo);
            video.setArtist("STI");
            video.setDuration(7537128);
            videos.add(video);
        }
        final HomeAdapter adapter = new HomeAdapter(this);
        adapter.addItems(videos);
        listVideo.setAdapter(adapter);
        listVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                intent.putExtra("video", adapter.getVideo(position));
                intent.putExtra("index", position);
                intent.putExtra("videos", fileManager.getFilePaths(videoType.getName()));
                startActivity(intent);
            }
        });
    }

    /**
     * Initialize offline
     */
    private void initOffline() {
        fullVideo = intent.getStringExtra("full");
        File file = new File(Environment.getExternalStorageDirectory() + "/Zumba/" + videoType.getName() + "/");
        if (file.exists() && file.listFiles().length > 0) {
            final ArrayList<File> files = fileManager.getListOfFiles(file);
            final FileAdapter adapter = new FileAdapter(this);
            adapter.addFiles(files);
            listVideo.setAdapter(adapter);
            listVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    File file = adapter.getFile(position);
                    Video video = new Video();
                    video.setUrl(file.getAbsolutePath());
                    video.setTitle(file.getName());
                    Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                    intent.putExtra("video", video);
                    intent.putExtra("index", position);
                    intent.putExtra("videos", fileManager.getOnlinePaths(files));
                    startActivity(intent);
                }
            });
        }
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

package org.bitbucket.yuelvic.zumba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_start) Button btnStart;
    @BindView(R.id.btn_about) Button btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        RxView.clicks(btnStart).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

        RxView.clicks(btnAbout).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            }
        });
    }

}

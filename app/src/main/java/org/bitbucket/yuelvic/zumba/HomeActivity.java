package org.bitbucket.yuelvic.zumba;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import org.bitbucket.yuelvic.zumba.adapters.TypeAdapter;
import org.bitbucket.yuelvic.zumba.models.Day;
import org.bitbucket.yuelvic.zumba.models.VideoType;
import org.bitbucket.yuelvic.zumba.utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.functions.Func1;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.recycler_type) RecyclerView recyclerType;

    private TextView tvMode;
    private Switch switchMode;
    private boolean isOnline = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_home);
        tvMode = (TextView) headerView.findViewById(R.id.tv_mode);
        switchMode = (Switch) headerView.findViewById(R.id.switch_mode);

        // Init views
        initViews();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_fav) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Initialize views
     */
    private void initViews() {
        ArrayList<VideoType> videoTypes = new ArrayList<>();
        String[] types = new String[0];

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.MONDAY:case Calendar.WEDNESDAY:case Calendar.FRIDAY:
                types = Constants.MWF;
                break;
            case Calendar.TUESDAY:case Calendar.THURSDAY:
                types = Constants.TTH;
                break;
        }

        for (String type : types) {
            VideoType videoType = new VideoType();
            videoType.setName(type);
            videoType.setDay(Day.MWF);
            videoTypes.add(videoType);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        TypeAdapter adapter = new TypeAdapter(getApplicationContext());
        adapter.addItems(videoTypes);

        recyclerType.setLayoutManager(layoutManager);
        recyclerType.setAdapter(adapter);

        switchMode.setChecked(true);
        RxView.clicks(switchMode)
                .map(new Func1<Void, Boolean>() {
                    @Override
                    public Boolean call(Void aVoid) {
                        isOnline = !isOnline;
                        return isOnline;
                    }
                })
                .map(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean flag) {
                        if (flag) tvMode.setText("Online Mode");
                        else tvMode.setText("Offline Mode");
                        return flag;
                    }
                })
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean flag) {
                        Toast.makeText(getApplicationContext(), ""+flag, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}

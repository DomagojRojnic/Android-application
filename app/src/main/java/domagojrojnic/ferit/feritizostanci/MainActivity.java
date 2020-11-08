package domagojrojnic.ferit.feritizostanci;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import domagojrojnic.ferit.feritizostanci.adapters.FragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private CustomViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setUpPager();
    }

    private void initViews() {
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tab);
    }

    private void setUpPager() {
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPagingEnabled(false);
        tabLayout.setupWithViewPager(viewPager);
    }
}

package domagojrojnic.ferit.feritizostanci.adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import domagojrojnic.ferit.feritizostanci.fragments.HomeFragment;
import domagojrojnic.ferit.feritizostanci.fragments.InfoFragment;


public class FragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 2;

    public FragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return HomeFragment.newInstance();
            default:
                return InfoFragment.newInstance();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Izostanci";
            case 1:
                return "Informacije";
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}

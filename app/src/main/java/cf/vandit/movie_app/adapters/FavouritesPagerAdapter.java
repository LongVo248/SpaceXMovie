package cf.vandit.movie_app.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import cf.vandit.movie_app.fragments.FavouriteMoviesFragment;

public class FavouritesPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public FavouritesPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new FavouriteMoviesFragment();
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "Movies";
    }
}

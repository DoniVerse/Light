package com.example.light;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeSliderFragment extends Fragment {
    private static final String[] TITLES = {"Videos", "Songs", "Albums", "Artists"};
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

        viewPager.setAdapter(new SliderAdapter());
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(TITLES[position]);
        }).attach();

        return view;
    }

    private class SliderAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {
        public SliderAdapter() {
            super(requireActivity());
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new VideosFragment();
                case 1:
                    return new SongsFragment();
                case 2:
                    return new AlbumsFragment();
                default:
                    return new VideosFragment();
            }
        }

        @Override
        public int getItemCount() {
            return TITLES.length;
        }
    }
}

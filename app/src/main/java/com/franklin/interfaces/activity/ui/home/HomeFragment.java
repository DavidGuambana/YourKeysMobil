package com.franklin.interfaces.activity.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.franklin.interfaces.R;
import com.franklin.interfaces.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable changePageRunnable = new Runnable() {
        @Override
        public void run() {
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.imageLogo.setImageResource(R.drawable.logo);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(changePageRunnable, 4000);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(changePageRunnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
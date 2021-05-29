package com.demo.assignment.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.demo.assignment.databinding.ListItemRandomJokesBinding;
import com.demo.assignment.util.AppConstant;

import org.jetbrains.annotations.NotNull;

/**
 * JokesViewFragment
 */
public class JokesViewFragment extends Fragment {
    private String mJokeText;
    private ListItemRandomJokesBinding binding;

    /**
     * @param jokes :Jokes String Text
     * @return instance of JokesViewFragment
     */
    public static JokesViewFragment newInstance(String jokes) {
        JokesViewFragment jokesViewFragment = new JokesViewFragment();
        Bundle args = new Bundle();
        args.putString(AppConstant.ARGS_JOKES, jokes);
        jokesViewFragment.setArguments(args);
        return jokesViewFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (null != args) {
            mJokeText = args.getString(AppConstant.ARGS_JOKES);
        }
    }

    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ListItemRandomJokesBinding.inflate(inflater, container, false);
        binding.tvJokes.setText(mJokeText);
        return binding.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
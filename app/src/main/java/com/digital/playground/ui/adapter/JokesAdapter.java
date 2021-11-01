package com.digital.playground.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.digital.playground.repository.model.RandomJokesModel;
import com.digital.playground.ui.fragment.JokesViewFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * JokesAdapter
 */
@SuppressWarnings("deprecation")
public class JokesAdapter extends FragmentStatePagerAdapter {
    private final List<RandomJokesModel> jokesList;

    /**
     * @param fm        fm
     * @param jokesList : Jokes List Data
     */
    public JokesAdapter(FragmentManager fm, List<RandomJokesModel> jokesList) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.jokesList = jokesList;
    }

    public void addJokesData(RandomJokesModel jokesModel) {
        this.jokesList.add(jokesModel);
        notifyDataSetChanged();
    }


    @NotNull
    @Override
    public Fragment getItem(int position) {
        String jokes = "";
        RandomJokesModel.Value jokesValue = jokesList.get(position).getValue();
        if (null != jokesValue) {
            jokes = jokesValue.getJoke();
        }
        return JokesViewFragment.newInstance(jokes);

    }

    @Override
    public int getCount() {
        return null != jokesList ? jokesList.size() : 0;
    }

    @Override
    public int getItemPosition(@NotNull Object object) {
        return POSITION_NONE;
    }
}
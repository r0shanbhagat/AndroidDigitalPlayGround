package com.digital.playground.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.digital.playground.R;
import com.digital.playground.core.ViewState;
import com.digital.playground.databinding.FragmentRandomJokesBinding;
import com.digital.playground.repository.logging.NoInternetException;
import com.digital.playground.ui.adapter.JokesAdapter;
import com.digital.playground.ui.dialog.DialogUtil;
import com.digital.playground.ui.dialog.LoadingDialog;
import com.digital.playground.ui.viewmodel.RandomJokesViewModel;
import com.digital.playground.util.AppUtils;
import com.digital.playground.util.SwipeViewPager;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class RandomJokesFragment extends Fragment {
    private static final String TAG = RandomJokesFragment.class.getSimpleName();
    private FragmentRandomJokesBinding binding;
    private RandomJokesViewModel mViewModel;
    private JokesAdapter jokesAdapter;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRandomJokesBinding.inflate(inflater, container, false);
        intiViewModel();
        initData();
        observeApiResponse();
        /*
         * In the case of config changes no need to call the api again:
         *  else clear viewModel cache and fetch new Jokes
         */
        if (savedInstanceState == null) {
            mViewModel.getJokesList().clear();
            mViewModel.fetchJokesList();
        }
        return binding.getRoot();
    }

    private void intiViewModel() {
        //Setting Up ViewModel
        Bundle args = getArguments();
        String firstName = Objects.requireNonNull(args).getString("FirstName");
        String lastName = args.getString("LastName");
        RandomJokesViewModel.Factory factory = new RandomJokesViewModel.Factory(requireContext(), firstName, lastName);
        mViewModel = new ViewModelProvider(this, factory).get(RandomJokesViewModel.class);
    }

    /**
     * initData
     */
    private void initData() {
        //Setting Up Adapter
        jokesAdapter = new JokesAdapter(getChildFragmentManager(), mViewModel.getJokesList());
        binding.viewPager.setAdapter(jokesAdapter);
        binding.viewPager.setSwipeListener(new SwipeViewPager.SwipeListener() {
            @Override
            public void onLeftSwipe() {
                AppUtils.showLog(TAG, "LEFT SWIPE");
            }

            @Override
            public boolean onRightSwipe() {
                AppUtils.showLog(TAG, "RIGHT SWIPE");
                if (binding.viewPager.getCurrentItem()
                        == jokesAdapter.getCount() - 1) {
                    mViewModel.fetchJokesList();
                }
                return true;
            }
        });
    }

    /**
     * observeApiResponse
     */
    private void observeApiResponse() {
        mViewModel.getJokesData().observe(getViewLifecycleOwner(), viewState -> {
            if (null == viewState) return;
            switch (viewState.getCurrentState()) {
                case ViewState.LOADING:
                    ///ShowBaseProgress
                    LoadingDialog.show(getContext());
                    mViewModel.resetLiveData();
                    break;
                case ViewState.SUCCESS:
                    LoadingDialog.dismissDialog();
                    updateList();
                    mViewModel.resetLiveData();
                    break;
                case ViewState.FAILED:
                    LoadingDialog.dismissDialog();
                    showInfoDialog(viewState.getError());
                    mViewModel.resetLiveData();
                    break;
            }
        });
    }


    /**
     * Showing No Network Dailog
     */
    private void showInfoDialog(Throwable throwable) {
        if (isAdded()) {
            String msg = getString(R.string.error_msg);
            if (throwable instanceof NoInternetException) {
                msg = getString(R.string.no_internet_msg);
            }
            DialogUtil.show(getContext(), msg, (dialogInterface, i) -> {
                //TODO Add Error View
            });
        }
    }

    /*
     * Updating the list after new data fetched
     **/
    public void updateList() {
        jokesAdapter.notifyDataSetChanged();
        /*
         * Sliding the viewpager to current slide
         */
        if (null != binding) {
            binding.viewPager.setCurrentItem(jokesAdapter.getCount() - 1, true);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

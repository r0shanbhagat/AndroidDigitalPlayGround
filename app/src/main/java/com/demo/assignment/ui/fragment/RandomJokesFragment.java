package com.demo.assignment.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.demo.assignment.AssignmentApp;
import com.demo.assignment.R;
import com.demo.assignment.databinding.FragmentRandomJokesBinding;
import com.demo.assignment.repository.logging.NoInternetException;
import com.demo.assignment.ui.adapter.JokesAdapter;
import com.demo.assignment.ui.dialog.LoadingDialog;
import com.demo.assignment.ui.viewmodel.RandomJokesViewModel;
import com.demo.assignment.util.AppConstant;
import com.demo.assignment.util.AppUtils;
import com.demo.assignment.util.SwipeViewPager;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;


public class RandomJokesFragment extends Fragment {
    private static final String TAG = RandomJokesFragment.class.getSimpleName();
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private FragmentRandomJokesBinding binding;
    private RandomJokesViewModel mViewModel;
    private JokesAdapter jokesAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((AssignmentApp) getActivity().getApplicationContext()).getAppComponent().inject(this);
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRandomJokesBinding.inflate(inflater, container, false);
        initViewModel(savedInstanceState);
        initData();
        observeApiResponse();
        /*
         * In the case of config changes no need to call the api again.
         */
        if (savedInstanceState == null) {
            mViewModel.fetchJokesList();
        }
        return binding.getRoot();
    }


    /**
     * initViewModel
     */
    private void initViewModel(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String firstName = "", lastName = "";
        if (null != bundle) {
            firstName = bundle.getString(AppConstant.ARGS_FIRST_NAME);
            lastName = bundle.getString(AppConstant.ARGS_LAST_NAME);
        }
        mViewModel = new ViewModelProvider(getViewModelStore(), viewModelFactory).get(RandomJokesViewModel.class);
        mViewModel.getFirstName().setValue(firstName);
        mViewModel.getLastName().setValue(lastName);

        if (savedInstanceState == null) {
            mViewModel.getJokesList().clear();
        }
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
        mViewModel.getJokesData().observe(getViewLifecycleOwner(), jokesState -> {
            if (null == jokesState) return;
            switch (jokesState.getCurrentState()) {
                case 0:
                    ///ShowBaseProgress
                    LoadingDialog.show(getContext());
                    mViewModel.resetLiveData();
                    break;
                case 1:
                    LoadingDialog.dismissDialog();
                    updateList();
                    mViewModel.resetLiveData();
                    break;
                case -1:
                    LoadingDialog.dismissDialog();
                    showInfoDialog(jokesState.getError());
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
            new AlertDialog
                    .Builder(getActivity())
                    .setMessage(msg)
                    .setTitle("Alert")
                    .setPositiveButton("OK", null)
                    .create().show();
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

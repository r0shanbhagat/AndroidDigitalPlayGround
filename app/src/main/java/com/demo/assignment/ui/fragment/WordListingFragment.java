package com.demo.assignment.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.demo.assignment.database.Word;
import com.demo.assignment.databinding.FragmentWordListingBinding;
import com.demo.assignment.ui.adapter.WordListAdapter;
import com.demo.assignment.ui.dialog.LoadingDialog;
import com.demo.assignment.ui.viewmodel.WordViewModel;
import com.demo.assignment.util.AppConstant;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WordListingFragment extends Fragment {
    private static final String TAG = WordListingFragment.class.getSimpleName();
    @Inject
    WordListAdapter mAdapter;
    private FragmentWordListingBinding binding;
    private WordViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SetupViewModel
        mViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        observeWordList();
        /*
         * onFragmentResultListener:
         */
        setFragmentResultListener();
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWordListingBinding.inflate(inflater, container, false);
        init();
        /*
         * In the case of config changes no need to call the api again:
         *  else clear viewModel cache and fetch new Jokes
         */
        if (savedInstanceState == null) {
            mViewModel.fetchWordList();
        }
        return binding.getRoot();
    }

    /**
     * initData
     */
    private void init() {
        //Setting Up Adapter
        binding.recyclerview.setAdapter(mAdapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.fab.setOnClickListener(view -> {
            Navigation.findNavController(view)
                    .navigate(WordListingFragmentDirections.nextAction());
        });
    }

    /**
     * observeApiResponse
     */
    private void observeWordList() {
        mViewModel.getAllWords().observe(this, state -> {
            if (null == state) return;
            switch (state.getCurrentState()) {
                case 0:
                    ///ShowBaseProgress
                    LoadingDialog.show(getContext());
                    break;
                case 1:
                    LoadingDialog.dismissDialog();
                    mAdapter.submitList(state.getData());
                    break;
                case -1:
                    LoadingDialog.dismissDialog();
                    break;
            }
        });
    }

    /*
     * setFragmentResultListener
     */
    private void setFragmentResultListener() {
        getParentFragmentManager()
                .setFragmentResultListener(AppConstant.REQUEST_KEY_WORD, this,
                        (requestKey, result) -> {
                            //Inserting data into DataBase
                            Word word = new Word(result.getString(AppConstant.WORD));
                            mViewModel.insert(word);
                            mViewModel.fetchWordList();
                        });
    }

}

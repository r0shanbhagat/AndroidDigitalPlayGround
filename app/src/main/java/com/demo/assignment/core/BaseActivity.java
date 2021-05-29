package com.demo.assignment.core;


import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

public abstract class BaseActivity<B extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity {

    protected VM viewModel;
    protected B binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutResId());
        //Setting Up ViewModel Instance
        if (null != getViewModel()) {
            viewModel = new ViewModelProvider(this).get(getViewModel());
        }
    }

    protected abstract Class<VM> getViewModel();

    protected abstract
    @LayoutRes
    int getLayoutResId();

}

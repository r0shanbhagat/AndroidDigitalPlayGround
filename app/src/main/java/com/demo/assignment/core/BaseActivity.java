package com.demo.assignment.core;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity {
    
    protected VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = createViewModel();
    }

    @NonNull
    protected abstract VM createViewModel();



  /*  BaseActivity<M extends ViewModel, B extends ViewDataBinding> extends DaggerAppCompatActivity {

        @Inject
        ViewModelProvider.Factory viewModelFactory;

        @SuppressWarnings("unchecked")
        @SuppressLint("CheckResult")
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            ViewDataBinding binding = DataBindingUtil.setContentView(this, getLayoutResId());
            ViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel());
            onCreate(savedInstanceState, (M) viewModel, (B) binding);
        }

        protected abstract Class<M> getViewModel();

        protected abstract void onCreate(Bundle instance, M viewModel, B binding);

        protected abstract
        @LayoutRes
        int getLayoutResId();

    }*/

    /*public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {

        protected B binding;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            AndroidInjection.inject(this);
            binding = DataBindingUtil.setContentView(this, getLayout());
            super.onCreate(savedInstanceState);
        }

        public void showToast(String message) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        public abstract int getLayout();
    }
*/

}

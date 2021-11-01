package com.digital.playground.di.module;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.digital.playground.core.factory.ViewModelFactory;
import com.digital.playground.di.scope.ViewModelKey;
import com.digital.playground.ui.viewmodel.LoginViewModel;
import com.digital.playground.ui.viewmodel.RandomJokesViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@SuppressWarnings("WeakerAccess")
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RandomJokesViewModel.class)
    abstract ViewModel bindJokesViewModel(RandomJokesViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel viewModel);

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory viewModelFactory);

}

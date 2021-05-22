package com.demo.assignment.di.module;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.demo.assignment.core.factory.ViewModelFactory;
import com.demo.assignment.di.scope.ViewModelKey;
import com.demo.assignment.ui.viewmodel.RandomJokesViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@SuppressWarnings("WeakerAccess")
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RandomJokesViewModel.class)
    abstract ViewModel bindsJokesViewModel(RandomJokesViewModel viewModel);

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory viewModelFactory);

}

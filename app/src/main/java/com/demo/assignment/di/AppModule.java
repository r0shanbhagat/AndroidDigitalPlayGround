package com.demo.assignment.di;

import com.demo.assignment.ui.adapter.LoadMoreAdapter;
import com.demo.assignment.util.MovieComparator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    LoadMoreAdapter getLoadMoreAdapter() {
        return new LoadMoreAdapter(new MovieComparator());
    }


}

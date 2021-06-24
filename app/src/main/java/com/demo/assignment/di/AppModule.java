package com.demo.assignment.di;

import com.demo.assignment.repository.model.WordModel;
import com.demo.assignment.ui.adapter.WordListAdapter;
import com.demo.assignment.util.WordDiff;

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
    WordModel provideLoginModel() {
        return new WordModel();
    }

    @Provides
    @Singleton
    WordListAdapter provideWordListAdapter() {
        return new WordListAdapter(new WordDiff());
    }


}

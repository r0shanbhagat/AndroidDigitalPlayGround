package com.demo.assignment.di;

import android.content.Context;

import androidx.room.Room;

import com.demo.assignment.database.AppDatabase;
import com.demo.assignment.database.WordRepository;
import com.demo.assignment.util.AppConstant;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class, AppConstant.DATABASE_NAME)
                .build();
    }

    @Provides
    @Singleton
    WordRepository provideWordRepository(AppDatabase db) {
        return new WordRepository(db.wordDao());
    }

}

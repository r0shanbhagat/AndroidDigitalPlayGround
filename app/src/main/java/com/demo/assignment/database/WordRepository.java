package com.demo.assignment.database;


import java.util.List;

import io.reactivex.rxjava3.core.Single;


public class WordRepository {
    private final WordDao mWordDao;

    public WordRepository(WordDao wordDao) {
        this.mWordDao = wordDao;
        // comment out the following block
        insertDummyData();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public Single<List<Word>> getAllWords() {
        return mWordDao.getAlphabetizedWords();
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Word word) {
        AppDatabase.databaseWriteExecutor.execute(() -> mWordDao.insert(word));
    }

    // If you want to keep data through app restarts,
    // comment out the following block
    private void insertDummyData() {
        insert(new Word("Hello"));
        insert(new Word("World"));
    }
}
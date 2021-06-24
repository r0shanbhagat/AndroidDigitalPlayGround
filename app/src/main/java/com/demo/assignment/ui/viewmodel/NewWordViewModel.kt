package com.demo.assignment.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.demo.assignment.core.BaseViewModel
import com.demo.assignment.repository.model.WordModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class NewWordViewModel @Inject constructor(wordModel: WordModel) : BaseViewModel() {
    private val wordLiveData: MutableLiveData<WordModel> by lazy {
        MutableLiveData()
    }

    /*
     *Init Block
     */
    init {
        disposable = CompositeDisposable()
        wordLiveData.postValue(wordModel)
    }

    /*
     *getWordModel()
     */
    fun getWordModel(): MutableLiveData<WordModel> {
        return wordLiveData
    }

}
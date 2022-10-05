package com.digital.playground.unittest;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;

import com.digital.playground.core.ViewState;
import com.digital.playground.ui.viewmodel.RandomJokesViewState;

public class ActionHandler {

    public MutableLiveData<ViewState> mJokeState;
    public Service service;

    public ActionHandler(Service service) {
        this.service = service;
        mJokeState = new MutableLiveData();
    }

    public ActionHandler(ServiceImpl service) {
        this.service = service;
        mJokeState = new MutableLiveData();
    }


    public void doAction() {
        service.doAction("our-request", new Callback<Response>() {
            @Override
            public void reply(Response response) {
                handleResponse(response);
            }
        });
    }

    private void handleResponse(Response response) {
        if (response.isValid()) {
            response.setData(new Data("Successful data response"));
        }
    }

    public void doService(Bundle bundle) {
        mJokeState.postValue(new RandomJokesViewState(ViewState.LOADING));
        Command.IADISABILITY.execute(bundle, new Callback<Response>() {
            @Override
            public void reply(Response response) {
                RandomJokesViewState state = new RandomJokesViewState(ViewState.SUCCESS);
                state.setData(response);
                mJokeState.postValue(state);
                handleResponse(response);
            }
        });
    }

}
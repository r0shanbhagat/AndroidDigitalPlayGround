package com.digital.playground.unittest;

import com.digital.playground.AssignmentApp;
import com.digital.playground.repository.NetworkRepository;

import java.util.HashMap;

/**
 * @Details :ServiceImpl
 * @Author Roshan Bhagat
 */
public class ServiceImpl implements Service {

    @Override
    public void doAction(String request, Callback<Response> callback) {
        NetworkRepository.getService(AssignmentApp.app).getJokesList(new HashMap<String, String>());
    }
}

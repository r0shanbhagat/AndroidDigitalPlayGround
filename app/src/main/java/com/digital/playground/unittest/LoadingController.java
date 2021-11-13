package com.digital.playground.unittest;

import android.os.AsyncTask;
import android.os.Bundle;

/**
 * @Details :
 * @Author Roshan Bhagat
 * @Date 13-Nov-2021
 */
public class LoadingController extends AsyncTask<Void, Void, Boolean> {
    private Callback callback;

    public LoadingController(Bundle bundle, Callback callback) {
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        Response response = new Response();
        callback.reply(response);
    }
}

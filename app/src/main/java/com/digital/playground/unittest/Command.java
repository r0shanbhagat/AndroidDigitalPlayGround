package com.digital.playground.unittest;

import android.os.Bundle;

/**
 * @Details :
 * @Author Roshan Bhagat
 * @Date 13-Nov-2021
 */
public class Command {

    public static Command IADISABILITY = new Command();

    protected Command() {

    }

    public static void firstMethod(String name) {
        // return "Hello " + name + " !";
    }

    public void execute(Bundle bundle, Callback<Response> callback) {
        LoadingController loadingController = new LoadingController(null, callback);
        loadingController.execute();
    }
}

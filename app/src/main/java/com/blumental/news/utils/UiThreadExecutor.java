package com.blumental.news.utils;

import android.os.Handler;

public class UiThreadExecutor {

    private Handler uiHandler;

    public UiThreadExecutor(Handler handler) {
        this.uiHandler = handler;
    }

    public void execute(Runnable runnable) {
        uiHandler.post(runnable);
    }
}

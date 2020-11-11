package com.example.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public interface MessagePresenter {
    void updateUi(Activity activity, Intent intent);
}

package com.example.studioghibliapi;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;
import androidx.annotation.Nullable;

public class LoadGhibli extends AsyncTaskLoader<String> {
    private String mQueryString;
    LoadGhibli(Context context, String queryString) {
        super(context);
        mQueryString = queryString;
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.buscaInfosGhibli(mQueryString);
    }
}

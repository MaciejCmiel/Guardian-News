package com.example.macx.guardiannews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;


/**
 * Created by MacX on 2017-11-30.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>>
{

    /**
     * Query URL
     */
    private String url;

    /**
     * Constructs a new {@link NewsLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (url == null) {
            return null;
        }

        return QueryUtils.fetchNewsData(url);
    }
}
package com.example.macx.guardiannews;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {
    public static final String LOG_TAG = MainActivity.class.getName();

    private static final String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search";
//			"?q=debate%20AND%20economy&tag=politics/politics&from-date=2014-01-01&api-key=6d9a1b61-c3f8-43fc-9f11-aa08166288d8";

    @BindView(R.id.empty_text_view)
    TextView emptyStateTextView;
    @BindView(R.id.list)
    ListView newsListView;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        newsListView.setEmptyView(emptyStateTextView);

        adapter = new NewsAdapter(this, new ArrayList<News>());

        newsListView.setAdapter(adapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                News currentNews = adapter.getItem(position);

                Uri newsUri = Uri.parse(currentNews.getWebUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                if (websiteIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(websiteIntent);
                }
            }
        });

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {

            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(1, null, this);


        } else {

            View progressBar = findViewById(R.id.progress_indicator);
            progressBar.setVisibility(View.GONE);

            emptyStateTextView.setText(R.string.no_internet_conection);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

        //=============================================
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // getString retrieves a String value from the preferences. The second parameter is the default value for this preference.
        String date = sharedPrefs.getString(
                getString(R.string.settings_date_key),
                getString(R.string.settings_date_default));

        boolean debatePicked = sharedPrefs.getBoolean(
                getString(R.string.settings_debate_key),
                getResources().getBoolean(R.bool.settings_debate_default));

        boolean economyPicked = sharedPrefs.getBoolean(
                getString(R.string.settings_economy_key),
                getResources().getBoolean(R.bool.settings_economy_default));

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        String type = null;
        if (debatePicked) {
            if (economyPicked) {
                type = "debate AND economy";
            } else {
                type = "debate";
            }
        } else if (economyPicked) {
            type = "economy";
        }

        if (type != null) {
            uriBuilder.appendQueryParameter("q", type);
        }
        uriBuilder.appendQueryParameter("tag", "politics/politics");
        uriBuilder.appendQueryParameter("from-date",date.replace("–", "-"));
        uriBuilder.appendQueryParameter("api-key", "6d9a1b61-c3f8-43fc-9f11-aa08166288d8");


        Log.i(LOG_TAG, "TEST: " + uriBuilder.toString());
        Log.i(LOG_TAG, "TEST: date: " + date);
        Log.i(LOG_TAG, "TEST: type: " + type);

        // Create a new loader for the given URL
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.progress_indicator);
        loadingIndicator.setVisibility(View.GONE);

        emptyStateTextView.setText(R.string.no_news);

        if (newsList != null && !newsList.isEmpty()) {
            adapter.addAll(newsList);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        adapter.clear();
    }

    /**
     * ******************************** MENU ************************************
     */
    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // To determine which item was selected and what action to take, call getItemId,
        // which returns the unique ID for the menu item (defined by the android:id attribute in the menu resource).
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

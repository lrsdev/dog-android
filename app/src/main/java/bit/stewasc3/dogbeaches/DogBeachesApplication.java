package bit.stewasc3.dogbeaches;

import android.app.Application;

import com.squareup.otto.Bus;

import bit.stewasc3.dogbeaches.contentprovider.CursorHelper.Location;

/**
 * Created by sam on 16/09/15.
 */
public class DogBeachesApplication extends Application
{
    private static Bus bus;
    private static LocationManager lm;

    @Override
    public void onCreate()
    {
        super.onCreate();
        initSingletons();
    }

    // Create instances of eventbus and locationclient singletons so they persist while application
    // is running.
    private void initSingletons()
    {
        bus = BusProvider.get();
        lm = LocationManager.get(this);
    }
}

package bit.stewasc3.dogbeaches;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.HashMap;
import UserAPI.ApiGeoLocation;
import UserAPI.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MapDisplayFragment extends MapFragment
{
    private GoogleMap mMap;
    private ArrayList<UserAPI.Location> mLocations;
    private HashMap<Marker, UserAPI.Location> mMarkerToLocationMap;

    public static MapDisplayFragment newInstance()
    {
        MapDisplayFragment fragment = new MapDisplayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    // Once map is ready, pull locations, set up the map once locations have been pulled.
    public void onCreate(Bundle savedInstanceState)
    {
        mMarkerToLocationMap = new HashMap<>();
        getMapAsync(new OnMapReadyCallback()
        {
            @Override
            public void onMapReady(GoogleMap googleMap)
            {
                mMap = googleMap;
                getLocations();
            }
        });

        super.onCreate(savedInstanceState);
    }

    // Get all locations from remote DB for now, in future, restrict to user defined radius.
    public void getLocations()
    {
        RestClient.get().getAllLocations(new Callback<ArrayList<UserAPI.Location>>()
        {
            @Override
            public void success(ArrayList<UserAPI.Location> locations, Response response)
            {
                mLocations = locations;
                setupMap();
            }

            @Override
            public void failure(RetrofitError error)
            {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    public void setupMap()
    {
        mMap.setMyLocationEnabled(true);
        // Center map on Dunedin for now, User location later.
        CameraUpdate c = CameraUpdateFactory.newLatLngZoom(new LatLng(
                -45.873629, 170.503692), 10);
        mMap.moveCamera(c);
        for(UserAPI.Location l : mLocations)
        {
            ApiGeoLocation gl = l.getGeolocation();
            Marker mark = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(gl.getLatitude(), gl.getLongitude()))
                    .title(l.getName())
                    .anchor(0.5f, 0.5f) // Defines marker anchor point in the center
                    .icon(getIconBitmap(l)));
            // Add the marker to a map so we can determine which location info window was clicked later
            mMarkerToLocationMap.put(mark, l);
        }
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
        {
            @Override
            public void onInfoWindowClick(Marker marker)
            {
                // Disabled

                // Get index of location in array to open view pager correctly, start Location
                // ViewPager showing selected location from map.
                //int index = mLocations.indexOf(mMarkerToLocationMap.get(marker));
                //Intent i = new Intent(getActivity(), LocationPagerActivity.class);
                //i.putExtra(LocationPagerActivity.KEY_LOCATION, mLocations.get(index));
                //startActivity(i);
            }
        });
    }

    private BitmapDescriptor getIconBitmap(UserAPI.Location l)
    {
        int icon = 0;
        switch(l.getDogStatus().getStatus())
        {
            case "on_lead" : icon = R.drawable.dogonlead;
                break;
            case "off_lead" : icon = R.drawable.dogofflead;
                break;
            case "no_dogs" : icon = R.drawable.nodogs;
                break;
        }
        return BitmapDescriptorFactory.fromResource(icon);
    }
}

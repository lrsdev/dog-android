package bit.stewasc3.dogbeaches.Location;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bit.stewasc3.dogbeaches.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationSightingFragment extends Fragment
{


    public LocationSightingFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_sighting, container, false);
    }


}
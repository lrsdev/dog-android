package bit.stewasc3.dogbeaches;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import UserAPI.Location;
import bit.stewasc3.dogbeaches.OldLocationClasses.LocationPagerActivity;

/**
 * Created by samuel on 8/07/15.
 */
public class LocationRecyclerAdapter extends RecyclerView.Adapter<LocationRecyclerAdapter.ViewHolder>
{
    private ArrayList<Location> mLocations;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView titleTextView;
        public TextView blurbTextView;
        public TextView guidelinesTextView;
        public ImageView imageView;
        public TextView statusTextView;
        public Button mapButton;
        public Button sightingsButton;

        public ViewHolder(View recyclerView)
        {
            super(recyclerView);
            titleTextView = (TextView) recyclerView.findViewById(R.id.locationCardTitleTextView);
            imageView = (ImageView) recyclerView.findViewById(R.id.locationCardImageView);
            //blurbTextView = (TextView) recyclerView.findViewById(R.id.locationCardBlurbTextView);
            statusTextView = (TextView) recyclerView.findViewById(R.id.locationCardStatusTextView);
            guidelinesTextView = (TextView) recyclerView.findViewById(R.id.locationCardDogGuidelines);
            mapButton = (Button) recyclerView.findViewById(R.id.locationCardMapButton);
            sightingsButton = (Button) recyclerView.findViewById(R.id.locationCardSightingsButton);

            /*
            recyclerView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(mContext, LocationPagerActivity.class);
                    i.putExtra(LocationPagerActivity.KEY_LOCATION, mLocations.get(getLayoutPosition()));
                    mContext.startActivity(i);
                }
            });
            */
        }
    }

    public LocationRecyclerAdapter(ArrayList<UserAPI.Location> locations, Context context)
    {
        mLocations = locations;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Location l = mLocations.get(position);
        holder.titleTextView.setText(l.getName());
        holder.guidelinesTextView.setText(l.getDogStatus().getGuidelines());
        //holder.blurbTextView.setText(l.getBlurb());

        String statusString = "";

        switch(l.getDogStatus().getStatus())
        {
            case "on_lead": statusString = "Dogs allowed on lead";
                break;
            case "off_lead": statusString = "Dogs allowed off lead";
                break;
            case "no_dogs": statusString = "No dogs allowed";
                break;
        }

        holder.statusTextView.setText(statusString);
        Picasso.with(mContext).load(l.getImage().getMedium()).into(holder.imageView);

        // Show sightings button only if location has sightings
        if (!(l.getSightings().isEmpty()))
        {
            holder.sightingsButton.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public LocationRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_card,
                parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public int getItemCount()
    {
        return mLocations.size();
    }
}
package codeformas.com.miranda_wear.util;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

import codeformas.com.miranda_wear.R;

public class CustomInfoWindow extends MarkerInfoWindow {
    private MapView mapView;
    private String title;
    private String description;
    private String snippet;
    private String titleButton;

    public CustomInfoWindow(MapView mapView, String title, String description, String snippet, String titleButton) {
        super(R.layout.bonuspack_bubble, mapView);

        this.mapView = mapView;
        this.title = title;
        this.description = description;
        this.snippet = snippet;
        this.titleButton = titleButton;

        /*Button btn = (Button) (mView.findViewById(R.id.bubble_moreinfo));

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mSelectedPoi.getSitelink() != null) {
                    //Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mSelectedPoi.getSitelink()));
                    //view.getContext().startActivity(myIntent);
                }
            }
        });*/
    }

    @Override
    public void onOpen(Object item){
        super.onOpen(item);
        mView.findViewById(R.id.bubble_moreinfo).setVisibility(View.VISIBLE);

        Marker marker = (Marker)item;

        //Button btn = (Button) (mView.findViewById(R.id.bubble_moreinfo));
        //btn.setText(this.titleButton);

        TextView txtTitle = (TextView) mView.findViewById(R.id.bubble_title);
        TextView txtDescription = (TextView) mView.findViewById(R.id.bubble_description);
        TextView txtSubdescription = (TextView) mView.findViewById(R.id.bubble_subdescription);

        txtTitle.setText(this.title);
        txtDescription.setText(description);
        txtSubdescription.setText(snippet);
    }

}

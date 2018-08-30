package codeformas.com.miranda_wear.view;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import codeformas.com.miranda_wear.R;
import codeformas.com.miranda_wear.model.Ubication;
import codeformas.com.miranda_wear.util.ConstantsMiranda;

public class UbicationsView extends Fragment implements IUbicationsView{

    private OnFragmentInteractionListener mListener;
    private View view;

    @BindView(R.id.mapview)
    MapView mapView;

    public UbicationsView() {
        // Required empty public constructor
    }

    public static UbicationsView newInstance(String param1, String param2) {
        UbicationsView fragment = new UbicationsView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_ubications, container, false);

        ButterKnife.bind(this, view);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.INTERNET},
                        2);


                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.INTERNET)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.INTERNET},
                        2);


                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String data = (String)intent.getExtras().get(ConstantsMiranda.VALUE_LOCATION);
                        Log.v("=========>","===========>" + data);
                        loadUbications(data);
                    }
                }, new IntentFilter(ConstantsMiranda.ACTION_LOCATION_BROADCAST)
        );

        loadUbications(null);

        return this.view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    /***
     * method for load data fron server
     * @param data
     */
    public void loadUbications(String data){
        double latitude = ConstantsMiranda.MAP_DEFAULT_LATITUDE;
        double longitude = ConstantsMiranda.MAP_DEFAULT_LONGITUDE;
        Ubication ubication = null;
        Gson gson = new Gson();

        try {
            if(data != null){
                ubication = gson.fromJson(data, Ubication.class);
                if(ubication != null){
                    latitude = ubication.getLatitude();
                    longitude = ubication.getLongitude();
                }
            }

            GeoPoint geoPoint = new GeoPoint(latitude, longitude);

            // Setup the mapView controller:
            mapView.setBuiltInZoomControls(true);
            mapView.setMultiTouchControls(true);
            //mapView.setClickable(true);
            //mapView.setUseDataConnection(false);
            mapView.getController().setZoom(ConstantsMiranda.MAP_DEFAULT_ZOOM);
            mapView.getController().setCenter(geoPoint);
            //mapView.setTileSource(TileSourceFactory.MAPNIK);
            //mapView.setTileSource(new XYTileSource("OpenStreetMap MapQuest", 0, 10, 256, ".png", new String[] {}));

            //ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
            //items.add(new OverlayItem("Title", "Description", new GeoPoint(0.0d,0.0d))); // Lat/Lon decimal degrees

            //add image to marker
            /*Dim Icon5 As BitmapDrawable
            Dim Marker2 As Marker
            Dim Markers As List
            Icon5.Initialize(LoadBitmap(File.DirAssets, "Marker-Gray.png"))
            Marker2.Initialize("Title","Infor",104.123456, 11.123456,Icon5)
            Markers.Add (Marker2)*/

            Marker.ENABLE_TEXT_LABELS_WHEN_NO_IMAGE = true;

            Marker marker = new Marker(mapView);
            marker.setPosition(geoPoint);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setIcon(getResources().getDrawable(R.drawable.ic_baseline_place_24px));
            //marker.setIcon(writeOnDrawable(R.drawable.ic_baseline_place_24px, "Gabriel"));

            mapView.getOverlays().clear();
            mapView.getOverlays().add(marker);
            //mapView.invalidate();

            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setTitle("Ubication");
            marker.setSnippet("Ths app is in develop.");
            marker.setSubDescription("By codeformas");

            //marker.setInfoWindow(new CustomInfoWindow(mapView, "White House", "1600 Pennsylvania Ave NW, Washington, DC 20500", "The White House is the official residence and principal workplace of the President of the United States.", "Aceptar"));

            //InfoWindow infoWindow = new MyInfoWindow(R.layout.bonuspack_bubble, mapView);
            //marker.setInfoWindow(infoWindow);

            /*marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker, MapView mapView) {
                    Log.v("++++>", "+++++>HIZO CLICK");
                    return false;
                }
            });*/
            mapView.getOverlays().add(marker);


            //--- Create Another Overlay for multi marker
            List<OverlayItem> anotherOverlayItemArray = new ArrayList<OverlayItem>();
            anotherOverlayItemArray.add(new OverlayItem(
                    "0, 0", "0, 0", new GeoPoint(0, 0)));
            anotherOverlayItemArray.add(new OverlayItem(
                    "US", "US", new GeoPoint(38.883333, -77.016667)));
            anotherOverlayItemArray.add(new OverlayItem(
                    "China", "China", new GeoPoint(39.916667, 116.383333)));
            anotherOverlayItemArray.add(new OverlayItem(
                    "United Kingdom", "United Kingdom", new GeoPoint(51.5, -0.116667)));
            anotherOverlayItemArray.add(new OverlayItem(
                    "Germany", "Germany", new GeoPoint(52.516667, 13.383333)));
            anotherOverlayItemArray.add(new OverlayItem(
                    "Korea", "Korea", new GeoPoint(38.316667, 127.233333)));
            anotherOverlayItemArray.add(new OverlayItem(
                    "India", "India", new GeoPoint(28.613333, 77.208333)));
            anotherOverlayItemArray.add(new OverlayItem(
                    "Russia", "Russia", new GeoPoint(55.75, 37.616667)));
            anotherOverlayItemArray.add(new OverlayItem(
                    "France", "France", new GeoPoint(48.856667, 2.350833)));
            anotherOverlayItemArray.add(new OverlayItem(
                    "Canada", "Canada", new GeoPoint(45.4, -75.666667)));

            ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(this, anotherOverlayItemArray, null);
            mapView.getOverlays().add(anotherItemizedIconOverlay);
            //---

            //Add Scale Bar
            ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(mapView);
            mapView.getOverlays().add(myScaleBarOverlay);

        }catch (Exception er){
        }

    }

    public BitmapDrawable writeOnDrawable(int drawableId, String text){

        Bitmap bm = BitmapFactory.decodeResource(getResources(), drawableId).copy(Bitmap.Config.ARGB_8888, true);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);

        Canvas canvas = new Canvas(bm);
        canvas.drawText(text, 0, bm.getHeight()/2, paint);

        return new BitmapDrawable(bm);
    }
}

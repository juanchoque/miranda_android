package codeformas.com.miranda_wear.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import codeformas.com.miranda_wear.R;
import codeformas.com.miranda_wear.services.MirandaBackgroudService;
import codeformas.com.miranda_wear.view.group.GroupsView;

public class MainView extends AppCompatActivity
        implements UbicationsView.OnFragmentInteractionListener,
        GroupsView.OnFragmentInteractionListener,
        Toolbar.OnMenuItemClickListener,
        IMainView{

    @BindView(R.id.main_content)
    LinearLayout maninContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        ////////////////////////////////////////////////////////
        Intent intent = new Intent(this, MirandaBackgroudService.class);
        startService(intent);
        ////////////////////////////////////////////////////////

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        GroupsView groupsView = new GroupsView();
        setFragment(groupsView);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    GroupsView groupsView = new GroupsView();
                    setFragment(groupsView);
                    return true;
                case R.id.navigation_dashboard:
                    UbicationsView ubicationsView = new UbicationsView();
                    setFragment(ubicationsView);
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_add_group){

        }

        return false;
    }

    //method for load fragment
    public void setFragment(Fragment fragment){
        try {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_content, fragment);
            fragmentTransaction.commit();
        }catch (Exception ex){
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.v("???????????????","??????????????????????????????>" + item);
        /*switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }*/
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

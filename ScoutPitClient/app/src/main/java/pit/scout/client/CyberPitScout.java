package pit.scout.client;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;


public class CyberPitScout extends ActionBarActivity {

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    //Home Page
    public String storeMatchNumber = "Match #";
    public String storeTeamNumber = "Team #";
    public int storeAlliance = 0;
    public String storeAllianceS = null;

    //Check Page
    public boolean storeCoop = false;
    public boolean storeLandfill = false;
    public boolean storeFeeder = false;
    public boolean storeTote = false;
    public boolean storeRC;
    public boolean storeMove = false;
    public boolean storeCap = false;
    public int storeHighStack = 0;

    //Extra Info Page
    public String storeExtraInfo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_layout);
        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[4];

        drawerItem[0] = new ObjectDrawerItem("Home");
        drawerItem[1] = new ObjectDrawerItem("Auton");
        drawerItem[2] = new ObjectDrawerItem("Teleop");
        drawerItem[3] = new ObjectDrawerItem("Collection Locations");

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem);

        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        ){
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                onDrawerCloseOps();
            }

            public void onDrawerOpened(View view){
                super.onDrawerOpened(view);
                getSupportActionBar().setTitle(mTitle);
                onDrawerOpenOps();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navigation_drawer_layout, menu);
        return true;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new CheckFragment();
                break;
            case 2:
                fragment = new ExtraInfoFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title){
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    public void onDrawerCloseOps(){

        //Home Page
        if(findViewById(R.id.allianceSelector) != null){

            //Alliance Selector

            Spinner as = (Spinner) findViewById(R.id.allianceSelector);
            as.setSelection(storeAlliance);

            //Match Number
            EditText mnt = (EditText) findViewById(R.id.matchNumber);
            mnt.setText(storeMatchNumber);
            if(mnt.getText().toString().equals("Match #")) mnt.setText("");

            //Team Number
            EditText tnt = (EditText) findViewById(R.id.teamNumber);
            tnt.setText(storeTeamNumber);
            if(tnt.getText().toString().equals("Team #")) tnt.setText("");
        }

        //Check Page
        if(findViewById(R.id.highStack) != null){

            //Coopertition
            CheckBox c = (CheckBox) findViewById(R.id.coopertition);
            c.setChecked(storeCoop);

            //Landfill
            CheckBox l = (CheckBox) findViewById(R.id.landfill);
            l.setChecked(storeLandfill);

            //Feeder
            CheckBox f = (CheckBox) findViewById(R.id.feeder);
            f.setChecked(storeFeeder);

            //Tote
            CheckBox t = (CheckBox) findViewById(R.id.autonTote);
            t.setChecked(storeTote);
            
            //RC
            CheckBox r = (CheckBox) findViewById(R.id.autonRC);
            r.setChecked(storeTote);
            
            //Move Forward
            CheckBox m = (CheckBox) findViewById(R.id.autonMoveForward);
            m.setChecked(storeMove);

            //High Stack
            NumberPicker hs = (NumberPicker) findViewById(R.id.highStack);
            hs.setMaxValue(6);
            hs.setMinValue(0);
            hs.setValue(storeHighStack);
        }

        //Extra Info
        if(findViewById(R.id.ExtraInfo) != null){
            EditText ei = (EditText) findViewById(R.id.ExtraInfo);
            ei.setText(storeExtraInfo);
        }


    }
    public void onDrawerOpenOps(){

        //Home Page
        if(findViewById(R.id.allianceSelector) != null){

            //Alliance Selector
            Spinner as = (Spinner) findViewById(R.id.allianceSelector);
            storeAlliance = as.getSelectedItemPosition();
            storeAllianceS = as.getSelectedItem().toString();

            //Match Number
            EditText mnt = (EditText) findViewById(R.id.matchNumber);
            storeMatchNumber = mnt.getText().toString();

            //Team Number
            EditText tnt = (EditText) findViewById(R.id.teamNumber);
            storeTeamNumber = tnt.getText().toString();
        }

        //Check Page
        if(findViewById(R.id.highStack) != null){

            //Coopertition
            CheckBox c = (CheckBox) findViewById(R.id.coopertition);
            storeCoop = c.isChecked();

            //Landfill
            CheckBox l = (CheckBox) findViewById(R.id.landfill);
            storeLandfill = l.isChecked();

            //Feeder
            CheckBox f = (CheckBox) findViewById(R.id.feeder);
            storeFeeder = f.isChecked();

            //Tote
            CheckBox t = (CheckBox) findViewById(R.id.autonTote);
            storeTote = t.isChecked();

            //RC
            CheckBox r = (CheckBox) findViewById(R.id.autonRC);
            storeTote = t.isChecked();

            //Move Forward
            CheckBox m = (CheckBox) findViewById(R.id.autonMoveForward);
            storeMove = m.isChecked();

            //High Stack
            NumberPicker hs = (NumberPicker) findViewById(R.id.highStack);
            storeHighStack = hs.getValue();
        }

        //Extra Info
        if(findViewById(R.id.ExtraInfo) != null){
            EditText ei = (EditText) findViewById(R.id.ExtraInfo);
            storeExtraInfo = ei.getText().toString();
        }
    }

    public void sendButtonOp(View view) throws IOException{
        if(findViewById(R.id.allianceSelector) != null){

            //Alliance Selector

            Spinner as = (Spinner) findViewById(R.id.allianceSelector);
            as.setSelection(storeAlliance);

            //Match Number
            EditText mnt = (EditText) findViewById(R.id.matchNumber);
            mnt.setText(storeMatchNumber);
            if(mnt.getText().toString().equals("Match #")) mnt.setText("");

            //Team Number
            EditText tnt = (EditText) findViewById(R.id.teamNumber);
            tnt.setText(storeTeamNumber);
            if(tnt.getText().toString().equals("Team #")) tnt.setText("");
        }
        new Connection().execute();

    }

    public void resetButtonOp(View view) throws IOException{
        storeMatchNumber = "";
        storeAlliance = 0;
        storeTeamNumber = "";
        storeAllianceS = "";
        storeExtraInfo = "";
        storeHighStack = 0;
        storeFeeder = false;
        storeCoop = false;
        storeCap = false;
        storeLandfill = false;
        storeRC = false;
        storeMove = false;
        storeTote = false;

        EditText mnt = (EditText) findViewById(R.id.matchNumber);
        mnt.setText("");

        EditText tnt = (EditText) findViewById(R.id.teamNumber);
        tnt.setText("");
    }

    public void client() throws IOException{
        int PORT = 25565;
        String SERVER = "192.168.2.1";
        DataOutputStream dos = null;
        Socket socket = null;
        try {
            try {
                socket = new Socket(SERVER, PORT);
            }catch(SocketException e){
                System.out.println("Server not found");
            }
            try {
                dos = new DataOutputStream(socket.getOutputStream());
                dos.writeBytes(
                            "Pit|"
                                + "Alliance: " + storeAllianceS + "|"
                                + "Team #: " + storeTeamNumber + "|"
                                + "Match #: " + storeMatchNumber + "|"
                                + "Coopertition: " + storeCoop + "|"
                                + "Landfill: " + storeLandfill + "|"
                                + "Feeder: " + storeFeeder + "|"
                                + "Auton Tote: " + storeTote + "|"
                                + "Auton RC: " + storeRC + "|"
                                + "Auton Move Forward: " + storeMove + "|"
                                + "Highest Stack: " + storeHighStack + "|"
                                + "Cap: " + storeCap + "|"
                                + "Extra Info: " + storeExtraInfo);
            }catch(Exception e){
                e.printStackTrace();
            }
        }finally{
            if(dos != null) dos.close();
            if (socket != null) socket.close();
        }

    }

    class Connection extends AsyncTask<Void, Void, Void>{
        protected Void doInBackground(Void... voids){
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            try{
                client();
            }catch(IOException e){

            }
            return null;
        }
    }

}

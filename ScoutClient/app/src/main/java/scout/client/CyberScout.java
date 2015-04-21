package scout.client;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;


public class CyberScout extends ActionBarActivity {

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
    //Auton Page
    public String storeAutoStack = "# Of Totes Stacked";
    public String storeAutoTScore = "Totes Scored";
    public String storeAutoCScore = "Containers Scored";
    //Teleop Page
    public boolean storeContainerNoodle = false;
    public String storeTeleToteScore = "Totes Scored";
    public String storeTeleToteStack = "Highest Tote Stack";
    //Locations
    public boolean storeShootDoor = false;
    public boolean storeAuton = false;
    public boolean storeUTote = false;
    public boolean storeLandfill = false;

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
                fragment = new AutonFragment();
                break;
            case 2:
                fragment = new TeleopFragment();
                break;
            case 3:
                fragment = new LocationFragment();
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

        //Auton Page
        if(findViewById(R.id.autoContainerScore) != null){

            //Auton Container Score
            EditText acs = (EditText) findViewById(R.id.autoContainerScore);
            acs.setText(storeAutoCScore);
            if(acs.getText().toString().equals("Containers Scored")) acs.setText("");

                //Auton Tote Score
            EditText atscore = (EditText) findViewById(R.id.autoToteScore);
            atscore.setText(storeAutoTScore);
            if(atscore.getText().toString().equals("Totes Scored")) atscore.setText("");

                //Auton Tote Stack
            EditText atstack = (EditText) findViewById(R.id.autoToteStack);
            atstack.setText(storeAutoStack);
            if(atstack.getText().toString().equals("# Of Totes Stacked")) atstack.setText("");
        }

        //Teleop Page
        if(findViewById(R.id.teleHighStack) != null){

            //Teleop Container Noodle
            CheckBox tcn = (CheckBox) findViewById(R.id.containerNoodle);
            tcn.setChecked(storeContainerNoodle);

            //Teleop Tote Score
            EditText tts = (EditText) findViewById(R.id.teleToteScore);
            tts.setText(storeTeleToteScore);
            if(tts.getText().toString().equals("Totes Scored")) tts.setText("");

                //Teleop Tote Stack
            EditText ths = (EditText) findViewById(R.id.teleHighStack);
            ths.setText(storeTeleToteStack);
            if(ths.getText().toString().equals("Highest Tote Stack")) ths.setText("");
        }

        if(findViewById(R.id.shootDoor) != null){

            //Shoot Door?
            CheckBox sh = (CheckBox) findViewById(R.id.shootDoor);
            sh.setChecked(storeShootDoor);

            //Auton
            CheckBox a = (CheckBox) findViewById(R.id.hasAuton);
            a.setChecked(storeAuton);

            //Upside Down Tote
            CheckBox ut = (CheckBox) findViewById(R.id.uTote);
            ut.setChecked(storeUTote);

            //Step/Landfill
            CheckBox sl = (CheckBox) findViewById(R.id.landfill);
            sl.setChecked(storeLandfill);
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

        //Auton Page
        if(findViewById(R.id.autoContainerScore) != null){

            //Auton Container Score
            EditText acs = (EditText) findViewById(R.id.autoContainerScore);
            storeAutoCScore = acs.getText().toString();

            //Auton Tote Score
            EditText atscore = (EditText) findViewById(R.id.autoToteScore);
            storeAutoTScore = atscore.getText().toString();

            //Auton Tote Stack
            EditText atstack = (EditText) findViewById(R.id.autoToteStack);
            storeAutoStack = atstack.getText().toString();
        }

        //Teleop Page
        if(findViewById(R.id.containerNoodle) != null){

            //Teleop Container Noodle
            CheckBox tcn = (CheckBox) findViewById(R.id.containerNoodle);
            storeContainerNoodle = tcn.isChecked();

            //Teleop Tote Score
            EditText tts = (EditText) findViewById(R.id.teleToteScore);
            storeTeleToteScore = tts.getText().toString();

            //Teleop Tote Stack
            EditText ths = (EditText) findViewById(R.id.teleHighStack);
            storeTeleToteStack = ths.getText().toString();
        }

        //Locations Page
        if(findViewById(R.id.shootDoor) != null){

            //Shoot Door?
            CheckBox sh = (CheckBox) findViewById(R.id.shootDoor);
            storeShootDoor = sh.isChecked();

            //Auton
            CheckBox a = (CheckBox) findViewById(R.id.hasAuton);
            storeAuton = a.isChecked();

            //Upside Down Tote
            CheckBox ut = (CheckBox) findViewById(R.id.uTote);
            storeUTote = ut.isChecked();

            //Step/Landfill
            CheckBox sl = (CheckBox) findViewById(R.id.landfill);
            storeLandfill = sl.isChecked();
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
        storeAutoCScore = "";
        storeAuton = false;
        storeAutoCScore = "";
        storeLandfill = false;
        storeAutoStack = "";
        storeAutoTScore = "";
        storeContainerNoodle = false;
        storeShootDoor = false;
        storeTeleToteScore = "";
        storeTeleToteStack = "";
        storeUTote = false;

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
                          "Alliance: " + storeAllianceS + "|"
                        + "Team #: " + storeTeamNumber + "|"
                        + "Match #: " + storeMatchNumber + "|"
                        + "Autonomous Container Score: " + storeAutoCScore + "|"
                        + "Autonomous Tote Score: " + storeAutoTScore + "|"
                        + "Autonomous Tote Stack: " + storeAutoStack + "|"
                        +"Teleop Container Noodle: " + storeContainerNoodle + "|"
                        + "Teleop Stack Height: " + storeTeleToteStack + "|"
                        + "Teleop Tote Score: " + storeTeleToteScore + "|"
                        + "Shoot Door?: " + storeShootDoor + "|"
                        + "Upside Down Tote: " + storeUTote + "|"
                        + "Has Auton: " + storeAuton + "|"
                        + "Landfill: " + storeLandfill + "|");
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

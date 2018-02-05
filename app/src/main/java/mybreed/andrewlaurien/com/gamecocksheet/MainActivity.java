package mybreed.andrewlaurien.com.gamecocksheet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import mybreed.andrewlaurien.com.gamecocksheet.Common.CommonFunc;
import mybreed.andrewlaurien.com.gamecocksheet.Fragment.AnalyticsFragment;
import mybreed.andrewlaurien.com.gamecocksheet.Fragment.MainFragment;
import mybreed.andrewlaurien.com.gamecocksheet.Model.Breed;
import mybreed.andrewlaurien.com.gamecocksheet.Model.Fight;
import mybreed.andrewlaurien.com.gamecocksheet.Model.GameCock;
import mybreed.andrewlaurien.com.gamecocksheet.Model.User;

public class MainActivity extends AppCompatActivity {

    FragmentManager fm;
    Context mcontext;

    public static ArrayList<Breed> myBreed = new ArrayList<Breed>();
    public static ArrayList<Fight> myFights = new ArrayList<Fight>();
    public static ArrayList<GameCock> myGameCock = new ArrayList<>();
    public static ArrayList<String> GameCockList = new ArrayList<>();
    public static Breed selectedBreed = null;
    public static Fight selectedFight = null;
    public static GameCock selectedCock = null;
    public static FirebaseDatabase mDataBase;
    public static DatabaseReference dbRef;
    public static User user = new User();
    SharedPreferences preferences;

    String TAG = "MAINACTIVITY";


    //Drawer
    private DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    TextView tv_userid;
    TextView tv_name;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);

        mcontext = this;
        mDataBase = FirebaseDatabase.getInstance();
        dbRef = mDataBase.getReference();


        Type typeOfObjectsList = new TypeToken<ArrayList<Breed>>() {
        }.getType();

        MainActivity.myBreed = (ArrayList<Breed>) CommonFunc.getPreferenceObjectJson(mcontext,
                "Breed", typeOfObjectsList);


        Type typeFight = new TypeToken<ArrayList<Fight>>() {
        }.getType();
        MainActivity.myFights = (ArrayList<Fight>) CommonFunc.getPreferenceObjectJson(mcontext, "Fight", typeFight);


        Type typeStag = new TypeToken<ArrayList<GameCock>>() {
        }.getType();

        MainActivity.myGameCock = (ArrayList<GameCock>) CommonFunc.getPreferenceObjectJson(mcontext, "Cock", typeStag);


        //user = (User) CommonFunc.getPreferenceObjectJson(mcontext, "User", User.class);


        user.setMobile("639951354943");
//        Log.d(TAG, user.getMobile());

        if (MainActivity.myBreed == null) {
            MainActivity.myBreed = new ArrayList<>();
        }

        if (MainActivity.myFights == null) {
            MainActivity.myFights = new ArrayList<>();
        }

        if (MainActivity.myGameCock == null)
            MainActivity.myGameCock = new ArrayList<>();
        else
            setCockList();


        fm = getSupportFragmentManager();
        MainFragment mainFragment = (MainFragment) fm.findFragmentById(R.id.container_main);

        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance("blah", "kah");
            fm.beginTransaction().add(R.id.container_main, mainFragment).commit();
        }


        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String state = preferences.getString("FirstTime", "");
        if (state.equalsIgnoreCase("Yes")) {
            getData();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTime", "No");
            editor.commit();
        }


        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                drawerLayout.closeDrawer(GravityCompat.START);

                int id = menuItem.getItemId();

                Fragment fragment = null;

                switch (id) {
                    case R.id.addreader:
                        fragment = new MainFragment();
                        break;
                    case R.id.analytics:
                        fragment = new AnalyticsFragment();
                        break;
                }
                if (fragment != null) {
                    fm.beginTransaction()
                            .replace(R.id.container_main, fragment).commit();
                } else {
                    // error in creating fragment

                    Log.e("MainActivity", "Error in creating fragment");

                }
                return true;
            }

        });


        View header = navigationView.getHeaderView(0);
        tv_userid = (TextView) header.findViewById(R.id.tv_userid);
        tv_name = (TextView) header.findViewById(R.id.tv_name);

        setProfile();

        View navFooter1 = findViewById(R.id.LogOut);
        navFooter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mcontext);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("State", "Logout");
                editor.putString("Breed", "");
                editor.putString("Fight", "");
                editor.putString("Cock", "");
                editor.apply();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });


    }

    public static void setCockList() {


        Log.d("Data", "here1");
        if (MainActivity.myGameCock.size() != 0)
            Log.d("Data", "here2");
        for (int i = 0; i < MainActivity.myGameCock.size(); i++) {
            GameCock gameCock = MainActivity.myGameCock.get(i);
            Log.d("Data", "here3");
            if (gameCock.getStatus() != null) {
                Log.d("Data", "here4");
                MainActivity.GameCockList.add(gameCock.getWingBand());
            }
        }

    }

    public void getData() {

        Query query = mDataBase.getReference("Users").child(MainActivity.user.getMobile());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        Log.d(TAG, "" + issue.child("phoneNumber").getValue());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });

        Query fightQuery = mDataBase.getReference("Fights").child(MainActivity.user.getMobile());
        fightQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "" + dataSnapshot.getValue());
                if (dataSnapshot.exists()) {

                    //Working already but per Year
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    //Log.d(TAG, "key" + dataSnapshot.getKey());

                    for (DataSnapshot child : children) {
                        Fight value = child.getValue(Fight.class);
                        Log.d(TAG, value.getFightDate());
                        MainActivity.myFights.add(value);
                    }

                    CommonFunc.setPreferenceObject(mcontext, MainActivity.myFights, "Fight");
                    fm = getSupportFragmentManager();
                    MainFragment mainFragment = (MainFragment) fm.findFragmentById(R.id.container_main);

                    //if (mainFragment == null) {
                    mainFragment = MainFragment.newInstance("blah", "kah");
                    fm.beginTransaction().replace(R.id.container_main, mainFragment).commit();
                    //}
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });


        Query breedquery = mDataBase.getReference("Breed").child(MainActivity.user.getMobile());
        breedquery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "" + dataSnapshot.getValue());
                if (dataSnapshot.exists()) {

                    //Working already but per Year
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    //Log.d(TAG, "key" + dataSnapshot.getKey());

                    for (DataSnapshot child : children) {
                        Breed value = child.getValue(Breed.class);
                        Log.d(TAG, value.getBroodCockWB());
                        MainActivity.myBreed.add(value);
                    }

                    CommonFunc.setPreferenceObject(mcontext, MainActivity.myBreed, "Breed");
                    fm = getSupportFragmentManager();
                    MainFragment mainFragment = (MainFragment) fm.findFragmentById(R.id.container_main);

                    //if (mainFragment == null) {
                    mainFragment = MainFragment.newInstance("blah", "kah");
                    fm.beginTransaction().replace(R.id.container_main, mainFragment).commit();
                    //}

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Query gameCockQuery = mDataBase.getReference("GameCocks").child(MainActivity.user.getMobile());
        gameCockQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "" + dataSnapshot.getValue());
                if (dataSnapshot.exists()) {

                    //Working already but per Year
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    //Log.d(TAG, "key" + dataSnapshot.getKey());

                    for (DataSnapshot child : children) {
                        GameCock value = child.getValue(GameCock.class);
                        Log.d(TAG, "" + value.getWingBand()
                        );
                        MainActivity.myGameCock.add(value);
                    }

                    CommonFunc.setPreferenceObject(mcontext, MainActivity.myGameCock, "Cock");
                    fm = getSupportFragmentManager();
                    MainFragment mainFragment = (MainFragment) fm.findFragmentById(R.id.container_main);

                    //if (mainFragment == null) {
                    mainFragment = MainFragment.newInstance("blah", "kah");
                    fm.beginTransaction().replace(R.id.container_main, mainFragment).commit();
                    //}


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

//    public void loadDetailsScreen(Station selectedStation) {
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.container_main, new DetailsFragment())
//                .addToBackStack(null)
//                .commit();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_breeding) {
            CommonFunc.showAddBreedingDialog(mcontext, 0);
            return true;
        }

        if (id == R.id.action_fight) {
            CommonFunc.showFightDialog(mcontext, 0);
            return true;
        }

        if (id == R.id.action_cock) {
            CommonFunc.showAddCock(mcontext, 0);
        }

        return super.onOptionsItemSelected(item);
    }

    public void setProfile() {
        if (user != null) {
            tv_name.setText(user.getMobile());
        }
    }
}

package com.example.navdemo;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.navdemo.adapter.ExpandableListAdapter;
import com.example.navdemo.ui.bag.BagFragment;
import com.example.navdemo.ui.brands.BrandsFragment;
import com.example.navdemo.ui.home.HomeFragment;
import com.example.navdemo.ui.send.SendFragment;
import com.example.navdemo.ui.share.ShareFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    ExpandableListAdapter mMenuAdapter;
    ExpandableListView expandableList;
    List<ExpandedMenuModel> listDataHeader;
    HashMap<ExpandedMenuModel, List<String>> listDataChild;
    DrawerLayout drawer = null;
    Fragment oldFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(1).setActionView(R.layout.down_arrow);
        navigationView.getMenu().getItem(2).setActionView(R.layout.down_arrow);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_categories, R.id.nav_offers,
                R.id.nav_brands, R.id.nav_bag, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //TODO: Adding code for expandable menu items
        expandableList = findViewById(R.id.navigationMenuExpandable);

        //TODO: Adding dummy list data; Replace with real; data
        prepareDummyListData();

        //Initialize menu list adapter
        mMenuAdapter = new ExpandableListAdapter(this, listDataHeader,
                listDataChild, expandableList);

        //Setting list adapter
        expandableList.setAdapter(mMenuAdapter);

        //onClickListener for list items
        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Fragment newFragment = FragmentContent.newInstance("Item clicked!");
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);

                transaction.commit();

                int index = parent.getFlatListPosition(ExpandableListView
                        .getPackedPositionForChild(groupPosition, childPosition));
                parent.setItemChecked(index, true);

                return true;
            }
        });

        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                switch (groupPosition) {
                    case 0://Home
                        loadFragment(new HomeFragment());
                        break;
                    case 3://Brands
                        loadFragment(new BrandsFragment());
                        break;
                    case 4://Bag
                        loadFragment(new BagFragment());
                        break;
                    case 5://Share
                        loadFragment(new ShareFragment());
                        break;
                    case 6://Send
                        loadFragment(new SendFragment());
                        break;
                }
                int index = parent.getFlatListPosition(ExpandableListView
                        .getPackedPositionForGroup(groupPosition));
                parent.setItemChecked(index, true);

                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (oldFragment != null)
            transaction.remove(oldFragment);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        oldFragment = fragment;
    }

    private void prepareDummyListData() {
        listDataHeader = new ArrayList<ExpandedMenuModel>();
        listDataChild = new HashMap<ExpandedMenuModel, List<String>>();

        ExpandedMenuModel item1 = new ExpandedMenuModel();
        item1.setIconName("Home");
        item1.setIconImg(R.drawable.ic_home_black_24dp);
        // Adding data header
        listDataHeader.add(item1);

        ExpandedMenuModel item2 = new ExpandedMenuModel();
        item2.setIconName("Categories");
        item2.setIconImg(R.drawable.ic_category_black_24dp);
        listDataHeader.add(item2);

        ExpandedMenuModel item3 = new ExpandedMenuModel();
        item3.setIconName("Offers");
        item3.setIconImg(R.drawable.ic_offer_black_24dp);
        listDataHeader.add(item3);

        ExpandedMenuModel item4 = new ExpandedMenuModel();
        item4.setIconName("Brands");
        item4.setIconImg(R.drawable.ic_brand_black_24dp);
        listDataHeader.add(item4);

        ExpandedMenuModel item5 = new ExpandedMenuModel();
        item5.setIconName("My Bag");
        item5.setIconImg(R.drawable.ic_bag_black_24dp);
        listDataHeader.add(item5);

        ExpandedMenuModel item6 = new ExpandedMenuModel();
        item6.setIconName("Share");
        item6.setIconImg(R.drawable.ic_menu_share);
        listDataHeader.add(item6);

        ExpandedMenuModel item7 = new ExpandedMenuModel();
        item7.setIconName("Send");
        item7.setIconImg(R.drawable.ic_menu_send);
        listDataHeader.add(item7);

        // Adding child data
        List<String> heading1 = new ArrayList<String>();
        heading1.add("Submenu of item 1");

        List<String> heading2 = new ArrayList<String>();
        heading2.add("Submenu of item 2");
        heading2.add("Submenu of item 2");
        heading2.add("Submenu of item 2");

        listDataChild.put(listDataHeader.get(1), heading1);// Header, Child data
        listDataChild.put(listDataHeader.get(2), heading2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}

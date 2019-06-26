package com.justtannoor.justtannoor.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.justtannoor.justtannoor.R;
import com.justtannoor.justtannoor.Utilities.Constant;
import com.justtannoor.justtannoor.Utilities.SharedPreferencesUtil;
import com.justtannoor.justtannoor.custom.MaterialBetterSpinner;
import com.justtannoor.justtannoor.fragment.ContactFragment;
import com.justtannoor.justtannoor.fragment.GalleryFragment;
import com.justtannoor.justtannoor.fragment.HomeContentFragment;
import com.justtannoor.justtannoor.fragment.MenuFragment;
import com.justtannoor.justtannoor.fragment.OffersFragment;
import com.justtannoor.justtannoor.retrofit.BaseRequest;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
///public class MainActivity extends AppCompatActivity  {

    MaterialBetterSpinner spinner1, spinner2, spinner3;
    private Context mContext;
    private BaseRequest baseRequest;
    String mOrderStatus;
    String mCaseOrderStatus;
    private String mOrderID;
    private String mCaseOrderID;
    ImageView imageInToolbar;
    MenuItem itemImage;
    Menu menuImage;

    private static final int STORAGE_PERMISSION_CODE = 123;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    public static final int[] BOTTOM_MENU_ICON_ID_ARR = {
            /* R.drawable.ic_home,
             R.drawable.ic_listing,
             R.drawable.ic_category,
             R.drawable.ic_tag,
             R.drawable.ic_favourate*/

            R.drawable.img_btm_home_uact,
            R.drawable.img_btm_menu_uact,
            R.drawable.img_btm_gallery_uact,
            R.drawable.img_btm_offer_uact,
            R.drawable.img_btm_map_uact

    };

    public static final int[] SELECTED_BOTTOM_MENU_ICON_ID_ARR = {
            /* R.drawable.ic_home_selected,
             R.drawable.ic_listing_selected,
             R.drawable.ic_category_selected,
             R.drawable.ic_tag_selected,
             R.drawable.ic_favourate_selected */

            R.drawable.img_btm_home_act,
            R.drawable.img_btm_menu_act,
            R.drawable.img_btm_gallery_act,
            R.drawable.img_btm_offer_act,
            R.drawable.img_btm_map_act
    };


    private int[] mLayoutArr = {
            R.id.rl_bottom_menu_first_item,
            R.id.rl_bottom_menu_second_item,
            R.id.rl_bottom_menu_third_item,
            R.id.rl_bottom_menu_fourth_item,
            R.id.rl_bottom_menu_fifth_item
    };

    private int[] mTextViewArr = {
            R.id.tv_bottom_menu_first,
            R.id.tv_bottom_menu_second,
            R.id.tv_bottom_menu_third,
            R.id.tv_bottom_menu_fourth,
            R.id.tv_bottom_menu_five,
    };

    private int[] mImageViewArr = {
            R.id.iv_bottom_menu_first,
            R.id.iv_bottom_menu_second,
            R.id.iv_bottom_menu_third,
            R.id.iv_bottom_menu_fourth,
            R.id.iv_bottom_menu_five
    };

    private String[] mTitleArr;
    private int BackCount = 0;
    private int mSelectedIndex;
    private int mSelectedBottomMenuIndex;
    private HomeContentFragment mHomeMainFragment;
    private TextView mNotificationCountTv;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home ");
        toolbar.setSubtitle("الصفحة الرئيسية");
        // toolbar.setSubtitle("Just Tannoor ");
        setSupportActionBar(toolbar);

        checkAndRequestPermissions();
        //   imageInToolbar = (ImageView) toolbar.findViewById(R.id.action_notify);

        baseRequest = new BaseRequest(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       /* if(NetworkUtil.hasConnection(this)){

            Toast.makeText(this, "Internet available", Toast.LENGTH_SHORT).show();
        }
        else
        {

            Toast.makeText(this, "Internet not available", Toast.LENGTH_SHORT).show();

        }*/

        mSelectedBottomMenuIndex = -1;
        mTitleArr = getResources().getStringArray(R.array.bottom_menu);

        loadHomeMenu();
        initBottomMenu();
        mOrderStatus = SharedPreferencesUtil.getData(mContext, Constant.PLACE_ORDER_STATUS);

        if (Constant.CHECK_HOME_PAGE_RELOAD) {
            handleBottomClick(0);
            Constant.CHECK_HOME_PAGE_RELOAD = false;
        }

        if (mNotificationCountTv != null) {
            updateNotificationValue();
        }


        setMyICON();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            showExitAlertDialog();
        }

        showExitAlertDialog();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        //  imageInToolbar

        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        menuImage = menu;
        mCaseOrderStatus = SharedPreferencesUtil.getData(mContext, Constant.PLACE_ORDER_STATUS_FOR_CASE);
        if ((mCaseOrderStatus != null) && (mCaseOrderStatus.equalsIgnoreCase("casetrue") && (!mCaseOrderStatus.equalsIgnoreCase("")) && (!mCaseOrderStatus.equalsIgnoreCase("NULL")) && (!mCaseOrderStatus.equalsIgnoreCase("null")))) {

            try {
                menuImage.getItem(0).setIcon(R.drawable.case_notify_red);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                menuImage.getItem(0).setIcon(R.drawable.case_notify_white);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        mOrderStatus = SharedPreferencesUtil.getData(mContext, Constant.PLACE_ORDER_STATUS);
        if ((mOrderStatus != null) && (mOrderStatus.equalsIgnoreCase("true") && (!mOrderStatus.equalsIgnoreCase("")) && (!mOrderStatus.equalsIgnoreCase("NULL")) && (!mOrderStatus.equalsIgnoreCase("null")))) {

            try {
                menuImage.getItem(1).setIcon(R.drawable.notify_red);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                menuImage.getItem(1).setIcon(R.drawable.notify_white);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        // itemImage = menu.findItem(R.id.action_notification);
        //  itemImage.setIcon(R.drawable.notify_red);
        //   imageInToolbar = itemImage.getActionView().findViewById(R.id.action_notification);

      /* // mNotificationCountTv = (TextView) item.getActionView().findViewById(R.id.tv_notification_count);
        if (mNotificationCountTv != null) {
            mNotificationCountTv.setVisibility(View.GONE);
        }
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m.performIdentifierAction(item.getItemId(), 0);
            }
        });*/

     //   updateNotificationValue();
        return true;
    }

    private void updateNotificationValue() {
        if (mNotificationCountTv != null) {
            int count = 10;
            if (count == 0) {
                mNotificationCountTv.setText(" " + count + " ");
                mNotificationCountTv.setVisibility(View.GONE);
            } else {
                mNotificationCountTv.setText(" " + count + " ");
                mNotificationCountTv.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case R.id.action_notify:
                //  Intent i = new Intent(mContext, NotificationListActivity.class);
                //   startActivity(i);

                mOrderStatus = SharedPreferencesUtil.getData(mContext, Constant.PLACE_ORDER_STATUS);
                mOrderID = SharedPreferencesUtil.getData(mContext, Constant.PLACE_ORDER_ID);
             //   mCaseOrderID = SharedPreferencesUtil.getData(mContext, Constant.PLACE_ORDER_ID_FOR_CASE);

                if ((mOrderStatus != null) && (mOrderStatus.equalsIgnoreCase("true") && (!mOrderStatus.equalsIgnoreCase("")) && (!mOrderStatus.equalsIgnoreCase("NULL")) && (!mOrderStatus.equalsIgnoreCase("null")))) {
                    Intent mIntent = new Intent(mContext, UploadBankSlipActivity.class);
                    mIntent.putExtra("order_id",mOrderID);
                    startActivity(mIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "This is only for upload Bank Pay Slip. Whenever you pay order amount by Bank transfermode.", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.action_caseondelivery:

                mCaseOrderStatus = SharedPreferencesUtil.getData(mContext, Constant.PLACE_ORDER_STATUS_FOR_CASE);
               // mOrderID = SharedPreferencesUtil.getData(mContext, Constant.PLACE_ORDER_ID);
                mCaseOrderID = SharedPreferencesUtil.getData(mContext, Constant.PLACE_ORDER_ID_FOR_CASE);
                if ((mCaseOrderStatus != null) && (mCaseOrderStatus.equalsIgnoreCase("casetrue") && (!mCaseOrderStatus.equalsIgnoreCase("")) && (!mCaseOrderStatus.equalsIgnoreCase("NULL")) && (!mCaseOrderStatus.equalsIgnoreCase("null")))) {
                   Constant.CASE_COME_STATS_HOME = 1;
                    Intent mIntent = new Intent(mContext, CaseOnDeliveyActivity.class);
                    mIntent.putExtra("order_id",mCaseOrderID);
                    startActivity(mIntent);
                } else {
                    Constant.CASE_COME_STATS_HOME = 0;
                    Toast.makeText(getApplicationContext(), "One's you make order and then check your order status, Thank you.", Toast.LENGTH_LONG).show();
                }
                //showAdvanceSearch();
              //  Intent mIntent_case = new Intent(mContext, AddtocartListActivity.class);
              //  startActivity(mIntent_case);
                break;
            case R.id.action_search:
                //showAdvanceSearch();
                Intent mIntent = new Intent(mContext, AddtocartListActivity.class);
                startActivity(mIntent);
                break;


        }
        return true;

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        int bottonMenuIndexPosition = -1;
        boolean changeFragment = true;
        switch (id) {
            case R.id.nav_home:
                // mSelectedIndex = Constant.MENU_OPTION_HOME_INDEX;
                //  bottonMenuIndexPosition = 0;
               /* handleFragmentChanges(0);
                setSelectedButtomMenuItem(0);*/
                handleBottomClick(0);
                //  changeFragment = true;
                break;
            case R.id.nav_menu:
               /* handleFragmentChanges(1);
                setSelectedButtomMenuItem(1);*/
                handleBottomClick(1);
                //   changeFragment = true;
                break;
            case R.id.nav_gallery:
               /* handleFragmentChanges(2);
                setSelectedButtomMenuItem(2);*/

                handleBottomClick(2);
                //  changeFragment = true;
                break;
            case R.id.nav_offer:
                /*handleFragmentChanges(3);
                setSelectedButtomMenuItem(3);*/

                handleBottomClick(3);
                //   changeFragment = true;
                break;
            case R.id.nav_contactus:
                /*handleFragmentChanges(4);
                setSelectedButtomMenuItem(4);*/

                handleBottomClick(4);
                //  changeFragment = true;
                break;

            case R.id.nav_upload_bank_slip:

                mOrderStatus = SharedPreferencesUtil.getData(mContext, Constant.PLACE_ORDER_STATUS);
                mOrderID = SharedPreferencesUtil.getData(mContext, Constant.PLACE_ORDER_ID);
             //   mCaseOrderID = SharedPreferencesUtil.getData(mContext, Constant.PLACE_ORDER_ID_FOR_CASE);
                if ((mOrderStatus != null) && (mOrderStatus.equalsIgnoreCase("true") && (!mOrderStatus.equalsIgnoreCase("")) && (!mOrderStatus.equalsIgnoreCase("NULL")) && (!mOrderStatus.equalsIgnoreCase("null")))) {
                    Intent mIntent = new Intent(mContext, UploadBankSlipActivity.class);
                    mIntent.putExtra("order_id",mOrderID);
                    startActivity(mIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "This is only for upload Bank Pay Slip. Whenever you pay order amount by Bank transfermode.", Toast.LENGTH_LONG).show();
                }

               /* Intent mIntent = new Intent(mContext,UploadBankSlipActivity.class);
                startActivity(mIntent);*/

                break;

                case R.id.nav_case_on_delivery_status:

                mCaseOrderStatus = SharedPreferencesUtil.getData(mContext, Constant.PLACE_ORDER_STATUS_FOR_CASE);
                   // mOrderID = SharedPreferencesUtil.getData(mContext, Constant.PLACE_ORDER_ID);
                    mCaseOrderID = SharedPreferencesUtil.getData(mContext, Constant.PLACE_ORDER_ID_FOR_CASE);
                if ((mCaseOrderStatus != null) && (mCaseOrderStatus.equalsIgnoreCase("casetrue") && (!mCaseOrderStatus.equalsIgnoreCase("")) && (!mCaseOrderStatus.equalsIgnoreCase("NULL")) && (!mCaseOrderStatus.equalsIgnoreCase("null")))) {
                    Constant.CASE_COME_STATS_HOME = 1;
                    Intent mIntent = new Intent(mContext, CaseOnDeliveyActivity.class);
                    mIntent.putExtra("order_id",mCaseOrderID);
                    startActivity(mIntent);
                } else {
                    Constant.CASE_COME_STATS_HOME = 0;
                    Toast.makeText(getApplicationContext(), "Please place order and check stutus.", Toast.LENGTH_LONG).show();
                }

               /* Intent mIntent = new Intent(mContext,UploadBankSlipActivity.class);
                startActivity(mIntent);*/

                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showExitAlertDialog() {

        if (BackCount == 1) {
            BackCount = 0;
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Press Back again to exit.", Toast.LENGTH_SHORT).show();
            BackCount++;
        }
    }


    public void loadHomeMenu() {

        if (mSelectedIndex > 6) {
            mSelectedBottomMenuIndex = -1;
        }
        Log.e("mSelectedIndex", "" + mSelectedIndex);
        handleFragmentChanges(mSelectedIndex);
        setSelectedButtomMenuItem(mSelectedIndex - 1);

    }

    private void handleFragmentChanges(int aSelectedIndex) {

        Fragment curFragment = null;

        setMyICON();

        switch (aSelectedIndex) {
            case Constant.MENU_OPTION_HOME_INDEX:
                if (mHomeMainFragment == null) {
                    mHomeMainFragment = new HomeContentFragment();
                }
                curFragment = mHomeMainFragment;
                break;

            case Constant.MENU_OPTION_LISTING_INDEX:
                curFragment = new MenuFragment();
                break;

            case Constant.MENU_OPTION_CATEGORY_INDEX:
                curFragment = new GalleryFragment();
                break;

            case Constant.MENU_OPTION_OFFERS_INDEX:
                curFragment = new OffersFragment();
                break;

            case Constant.MENU_OPTION_FAVOURITE_INDEX:
                curFragment = new ContactFragment();
                break;
        }

        if (curFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container, curFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private void setMyICON() {

        mOrderStatus = SharedPreferencesUtil.getData(mContext, Constant.PLACE_ORDER_STATUS);

        if ((mOrderStatus != null) && (mOrderStatus.equalsIgnoreCase("true") && (!mOrderStatus.equalsIgnoreCase("")) && (!mOrderStatus.equalsIgnoreCase("NULL")) && (!mOrderStatus.equalsIgnoreCase("null")))) {

            try {
                menuImage.getItem(1).setIcon(R.drawable.notify_red);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                menuImage.getItem(1).setIcon(R.drawable.notify_icon);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        mCaseOrderStatus = SharedPreferencesUtil.getData(mContext, Constant.PLACE_ORDER_STATUS_FOR_CASE);

        if ((mCaseOrderStatus != null) && (mCaseOrderStatus.equalsIgnoreCase("casetrue") && (!mCaseOrderStatus.equalsIgnoreCase("")) && (!mCaseOrderStatus.equalsIgnoreCase("NULL")) && (!mCaseOrderStatus.equalsIgnoreCase("null")))) {

            try {
                menuImage.getItem(0).setIcon(R.drawable.case_notify_red);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                menuImage.getItem(0).setIcon(R.drawable.case_notify_white);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void setSelectedButtomMenuItem(int aPosition) {
        if (mSelectedBottomMenuIndex != -1) {
            setBottomMenuItemUnChecked(mSelectedBottomMenuIndex);
        }
        if (aPosition != -1 && aPosition < 5) {
            setBottomMenuItemChecked(aPosition);
            mSelectedBottomMenuIndex = aPosition;
        }
        if (mSelectedBottomMenuIndex == -1) {
            //  setBottomMenuItemChecked(0);
            setBottomMenuItemChecked(0);
            mSelectedBottomMenuIndex = 0;
        }
    }

    private void setBottomMenuItemChecked(int aPosition) {
        if (aPosition != -1) {
            TextView textView = (TextView) findViewById(mTextViewArr[aPosition]);
            // textView.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));

            textView.setTextColor(ContextCompat.getColor(this, R.color.colorBottomText));

            ImageView imageView = (ImageView) findViewById(mImageViewArr[aPosition]);
            imageView.setImageResource(SELECTED_BOTTOM_MENU_ICON_ID_ARR[aPosition]);

           /* final LinearLayout curLayout = (LinearLayout) findViewById(mLayoutArr[aPosition]);
            curLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_menu_gradient));
            curLayout.setBackgroundColor(getResources().getColor(R.color.colorGrediant));*/
        }
    }

    private void setBottomMenuItemUnChecked(int aPosition) {
        TextView textView = (TextView) findViewById(mTextViewArr[aPosition]);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));

        ImageView imageView = (ImageView) findViewById(mImageViewArr[aPosition]);
        imageView.setImageResource(BOTTOM_MENU_ICON_ID_ARR[aPosition]);

       /* final LinearLayout curLayout = (LinearLayout) findViewById(mLayoutArr[aPosition]);
       // curLayout.setBackgroundColor(getResources().getColor(R.color.colorGrediant));
       curLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_menu_gradient));*/
    }

    private void initBottomMenu() {

        for (int i = 0; i < mTitleArr.length; i++) {

            final ImageView curImageView = (ImageView) findViewById(mImageViewArr[i]);

            if (i == 0) {
                curImageView.setImageResource(SELECTED_BOTTOM_MENU_ICON_ID_ARR[i]);
            } else {
                curImageView.setImageResource(BOTTOM_MENU_ICON_ID_ARR[i]);
            }
            //   curImageView.setImageResource(BOTTOM_MENU_ICON_ID_ARR[i]);

            TextView curTextView = (TextView) findViewById(mTextViewArr[i]);
            curTextView.setText(mTitleArr[i]);

            /*if(i == 4)
            {
                curTextView.setGravity(Gravity.LEFT);
            }*/

            final LinearLayout curLayout = (LinearLayout) findViewById(mLayoutArr[i]);
            final int finalI = i;
            curLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleBottomClick(finalI);
                }
            });
        }


    }

    private void handleBottomClick(int aPosition) {
        if (mSelectedBottomMenuIndex != aPosition) {
            switch (aPosition) {

                case 0:
                    mSelectedIndex = Constant.MENU_OPTION_HOME_INDEX;
                    toolbar.setTitle("Home ");
                    toolbar.setSubtitle("الصفحة الرئيسية");
                    break;

                case 1:
                    mSelectedIndex = Constant.MENU_OPTION_LISTING_INDEX;
                    toolbar.setTitle("Menu ");
                    //toolbar.setSubtitle("قائمة طعام");
                    toolbar.setSubtitle("قائمة الطعام");
                    break;

                case 2:
                    mSelectedIndex = Constant.MENU_OPTION_CATEGORY_INDEX;
                    toolbar.setTitle("Gallery ");
                   // toolbar.setSubtitle("صالة عرض");
                    toolbar.setSubtitle("الصور");
                    break;

                case 3:
                    mSelectedIndex = Constant.MENU_OPTION_OFFERS_INDEX;
                    toolbar.setTitle("Offer ");
                    toolbar.setSubtitle("عروض");
                    break;

                case 4:
                    mSelectedIndex = Constant.MENU_OPTION_FAVOURITE_INDEX;
                    toolbar.setTitle("Contact Us ");
                    toolbar.setSubtitle("اتصل بنا");
                    break;

                default:
                    mSelectedBottomMenuIndex = -1;
            }

            handleFragmentChanges(mSelectedIndex);
            setSelectedButtomMenuItem(aPosition);
            Constant.HOME_FRAGMENT_CALL_BACK = false;
            //set value for Cattegory list load from top
        }
    }


    /////////////////////////////////////////////////////////////////////////////
    private boolean checkAndRequestPermissions() {

        int SD_CARD = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int CAMERA = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int LOCATION = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (SD_CARD != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (CAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (LOCATION != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_CODE) {

//If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
//Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }

            if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast
                Toast.makeText(this, "Permission granted now you can use camera", Toast.LENGTH_LONG).show();
            } else {
//Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }

            if (grantResults.length > 0 && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast
                Toast.makeText(this, "Permission granted now you can use user location", Toast.LENGTH_LONG).show();
            } else {
//Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
            if (grantResults.length > 0 && grantResults[3] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast
                Toast.makeText(this, "Permission granted now you can able to call", Toast.LENGTH_LONG).show();
            } else {
//Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
            if (grantResults.length > 0 && grantResults[4] == PackageManager.PERMISSION_GRANTED) {
//Displaying a toast
                Toast.makeText(this, "Permission granted now you can able to send sms", Toast.LENGTH_LONG).show();
            } else {
//Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
    ///////////////////////////////////////////////////////////////////////////////
}

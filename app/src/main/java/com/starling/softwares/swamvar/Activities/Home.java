package com.starling.softwares.swamvar.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.starling.softwares.swamvar.Fragments.AddressFragment;
import com.starling.softwares.swamvar.Fragments.CartFragment;
import com.starling.softwares.swamvar.Fragments.DrawerFragment;
import com.starling.softwares.swamvar.Fragments.LandingFragment;
import com.starling.softwares.swamvar.Fragments.OrdersById;
import com.starling.softwares.swamvar.Fragments.PlacedOrderDetails;
import com.starling.softwares.swamvar.Fragments.ProductDescription;
import com.starling.softwares.swamvar.Fragments.ProductFragment;
import com.starling.softwares.swamvar.Fragments.SimplySubCategory;
import com.starling.softwares.swamvar.Fragments.SubCategoryFrag;
import com.starling.softwares.swamvar.Fragments.ViewCustomerFragment;
import com.starling.softwares.swamvar.Fragments.ViewaddressFragment;
import com.starling.softwares.swamvar.Interfaces.serverConnectionListner;
import com.starling.softwares.swamvar.Model.LandingPageModel;
import com.starling.softwares.swamvar.Model.MenuModel;
import com.starling.softwares.swamvar.Model.OrdersByIdModel;
import com.starling.softwares.swamvar.R;
import com.starling.softwares.swamvar.Utils.Constant;
import com.starling.softwares.swamvar.Utils.Helper;
import com.starling.softwares.swamvar.Utils.ServerConnection;
import com.starling.softwares.swamvar.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.LOCATION_SERVICE;

public class Home extends AppCompatActivity {
    @BindView(R.id.parent)
    RelativeLayout parent;
    @BindView(R.id.main_content_frame)
    FrameLayout main_content_frame;
    private ActionBar actionBar;
    private DrawerFragment drawerFragment;

    public static Home mInstance;
    private String tag = Home.class.getSimpleName();
    private Toolbar toolbar;

    public static Home getInstace() {
        return mInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mInstance = this;
       handleNavigationAction();
       addHomeAsInitialPage();
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment fragment = getFragment();
                setTitleFromFragment(fragment);
            }
        });
        getLocation();
    }

    private void getLocation() {
        Location locationn = getLastKnownLocation();
        if (locationn != null) {
            Helper.logcat(tag, "location found " + locationn.getLatitude() + " : " + locationn.getLongitude());
            sendLocationToServer(locationn);
        }
    }

    private void sendLocationToServer(Location locationn) {
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", Constant.access_token);
        params.put("user_id", Utils.getActiveUser(getApplicationContext()).getUser_id());
        params.put("lat", String.valueOf(locationn.getLatitude()));
        params.put("lng", String.valueOf(locationn.getLongitude()));
        ServerConnection.requestServer(getString(R.string.set_location), params, Constant.set_location, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.logcat(tag, "location update server response >>>>   " + response);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    private Location getLastKnownLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            @SuppressLint("MissingPermission") Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }

    private void setTitleFromFragment(Fragment fragment) {
        if (fragment instanceof LandingFragment) {
            setTitLeFrag("Home");
        }
        if (fragment instanceof AddressFragment) {
            setTitLeFrag("Add New Address");
        }
        if (fragment instanceof CartFragment) {
            setTitLeFrag("Your Cart");
        }
        if (fragment instanceof OrdersById) {
            setTitLeFrag("Orders");
        }
        if (fragment instanceof PlacedOrderDetails) {
            setTitLeFrag("Order Details");
        }
        if (fragment instanceof ProductDescription) {
            ((ProductDescription) fragment).setTitle();
        }
        if (fragment instanceof ProductFragment) {
            setTitLeFrag("Products");
        }
        if (fragment instanceof SimplySubCategory) {
            ((SimplySubCategory) fragment).setTitle();
        }
        if (fragment instanceof SubCategoryFrag) {
            ((SubCategoryFrag) fragment).setTitle();
        }
        if (fragment instanceof ViewaddressFragment) {
            setTitLeFrag("Select Address");
        }
        if (fragment instanceof ViewCustomerFragment) {
            setTitLeFrag("Select Customer");
        }
    }

    private Fragment getFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.main_content_frame);
    }

    private void addHomeAsInitialPage() {
        Fragment fragment = new LandingFragment();
        FragmentManager frgManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = frgManager.beginTransaction();
        fragmentTransaction.add(R.id.main_content_frame, fragment).commitAllowingStateLoss();
        frgManager.executePendingTransactions();
    }

    /**
     * handle the navigation option press here
     */

    private void handleNavigationAction() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
        }
        drawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.main_navigation_drawer_fragment);
        drawerFragment.setUp((DrawerLayout) findViewById(R.id.main_drawer_layout), toolbar, new DrawerFragment.DrawerActionListener() {
            @Override
            public void onHeaderselected() {

            }

            @Override
            public void onCategorySelected(MenuModel.DataBean category) {

            }
        });
    }

    public void setTitLeFrag(String title) {
        toolbar.setTitle(title);
    }

    /**
     * Method creates fragment transaction and replace current fragment with new one.
     *
     * @param newFragment    new fragment used for replacement.
     * @param transactionTag text identifying fragment transaction.
     */
    private void replaceFragment(Fragment newFragment, String transactionTag) {
        if (newFragment != null) {
            FragmentManager frgManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = frgManager.beginTransaction();

            fragmentTransaction.setReorderingAllowed(false);
            fragmentTransaction.addToBackStack(transactionTag);
            fragmentTransaction.add(R.id.main_content_frame, newFragment).commitAllowingStateLoss();
            frgManager.executePendingTransactions();
        }
    }


    public void takeTosubCategory(LandingPageModel.CategoriesBean mode) {
        replaceFragment(SubCategoryFrag.newInstance(mode.getCategory_id()), SubCategoryFrag.class.getSimpleName());
    }

    public void checkAndTakeToSubCategroy(MenuModel.DataBean.SubCategoryBean model) {
        if (!Helper.isDataConnected(getApplicationContext())) {
            Helper.showNoInternetToast(getApplicationContext());
            return;
        }
        checkIfDataExits(model);

    }

    private void checkIfDataExits(final MenuModel.DataBean.SubCategoryBean model) {
        Helper.logcat(tag, "1   " + model.getSubcategory_id());
        final ProgressDialog progressDialog = Helper.showProgressDialog(Home.this, "Getting Data");
        progressDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", Constant.access_token);
        params.put("sub_category_id", model.getSubcategory_id());
        ServerConnection.requestServer(getString(R.string.check_subsub_category_status), params, Constant.check_subsub_category_status, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.logcat(tag, "checkIfDataExits   >>>  " + response);
                Helper.dismissDialog(progressDialog);
                try {
                    if (new JSONObject(response).getString("status").equalsIgnoreCase("success")) {
                        if (new JSONObject(response).getBoolean("category_status")) {
                            takeToSimplySubCategory(model.getSubcategory_id(), model.getSubcategory_name());
                        } else {
                            takeToProductFragment(Constant.GET_PRODUCT_SUB_CATEGORY, model.getSubcategory_id());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                drawerFragment.closeDrawerMenu();
            }

            @Override
            public void onError(VolleyError error) {
                Helper.dismissDialog(progressDialog);
                error.printStackTrace();
            }
        });
    }

    public void takeToProductFragment(int type, String subcategory_id) {
        replaceFragment(ProductFragment.newInstance(type, subcategory_id), ProductFragment.class.getSimpleName());
    }

    private void takeToSimplySubCategory(String subcategory_id, String subcategory_name) {
        replaceFragment(SimplySubCategory.newInstance(subcategory_id, subcategory_name), SimplySubCategory.class.getSimpleName());
    }

    public void takeToProductDescriptionPage(String design_name, String mrp, String design_id, String gst) {
        replaceFragment(ProductDescription.newInstance(design_name, design_id, mrp, gst), ProductDescription.class.getSimpleName());

    }

    public void takeToCart() {
        replaceFragment(new CartFragment(), CartFragment.class.getSimpleName());
    }

    public void takeToLandingPage() {
        clearBackStack();
        replaceFragment(new LandingFragment(), LandingFragment.class.getSimpleName());

    }


    public void takeToViewCustomer() {
        replaceFragment(new ViewCustomerFragment(), ViewCustomerFragment.class.getSimpleName());
    }

    public void takeToAddressAddPage(String array) {
        replaceFragment(AddressFragment.newInstance(array), null);
    }

    public void takeToAddressPage(String string) {
        replaceFragment(ViewaddressFragment.newInstance(string), ViewaddressFragment.class.getSimpleName());
    }

    private void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStackImmediate(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

    }

    public void taketoOrdersById(String user_id) {
        replaceFragment(OrdersById.newInstance(user_id), OrdersById.class.getSimpleName());
    }

    public void takeToProductOrdersDetails(OrdersByIdModel.DataBean model) {
        replaceFragment(PlacedOrderDetails.newInstance(model), PlacedOrderDetails.class.getSimpleName());

    }
}

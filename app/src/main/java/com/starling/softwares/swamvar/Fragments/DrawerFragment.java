package com.starling.softwares.swamvar.Fragments;


import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.android.volley.VolleyError;
import com.starling.softwares.swamvar.Activities.Home;
import com.starling.softwares.swamvar.Adapter.DrawerRecyclerAdapter;
import com.starling.softwares.swamvar.Adapter.SubMenuAdapter;
import com.starling.softwares.swamvar.Interfaces.serverConnectionListner;
import com.starling.softwares.swamvar.Model.ItemCategory;
import com.starling.softwares.swamvar.Model.MenuModel;
import com.starling.softwares.swamvar.R;
import com.starling.softwares.swamvar.Utils.Constant;
import com.starling.softwares.swamvar.Utils.Helper;
import com.starling.softwares.swamvar.Utils.ServerConnection;
import com.starling.softwares.swamvar.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends Fragment {
    @BindView(R.id.drawer_recycler)
    RecyclerView drawerRecycler;
    @BindView(R.id.drawer_submenu_recycler)
    RecyclerView drawer_submenu_recycler;
    @BindView(R.id.parent)
    RelativeLayout image;
    @BindView(R.id.drawer_submenu_layout)
    LinearLayout drawerSubmenuLayout;
    private TextView drawerSubmenuTitle;
    private static DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerActionListener listner;

    private final String tag = DrawerFragment.class.getSimpleName();
    private DrawerRecyclerAdapter drawerRecyclerAdapter;
    private SubMenuAdapter subMenuAdapter;
    private Button drawerRetryBtn;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.txt_profile)
    TextView txtProfile;
  @BindView(R.id.txt_appointments)
    TextView txtAppoitments;

    public DrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer, container, false);
        ButterKnife.bind(this, view);

        drawerSubmenuLayout =  view.findViewById(R.id.drawer_submenu_layout);
        drawerSubmenuTitle =  view.findViewById(R.id.drawer_submenu_title);
        if (Utils.getActiveUser(getActivity()) != null) {
            if (Utils.getActiveUser(getActivity()).getUser_name() != null) {
                name.setText(String.format("Hello %s", Utils.getActiveUser(getActivity()).getUser_name()));
            } else name.setVisibility(View.GONE);
        }

        Button backBtn =  view.findViewById(R.id.drawer_submenu_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            private long mLastClickTime = 0;

            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                    return;
                mLastClickTime = SystemClock.elapsedRealtime();

                animateSubListHide();
            }
        });

        txtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Home.getInstace().takeToProfilePage();
             //  Home.getInstace().takeToCart();
                ((Home)getActivity()).takeToProfilePage();
                closeDrawerMenu();
            }
        });
        txtAppoitments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Home.getInstace().takeToProfilePage();
                //  Home.getInstace().takeToCart();
                ((Home) getActivity()).takeToAppointment();
                closeDrawerMenu();
            }
        });


        prepareDrawerRecycler(view);
        return view;
    }

    private void prepareDrawerRecycler(View view) {
        drawerRecyclerAdapter = new DrawerRecyclerAdapter(new DrawerRecyclerAdapter.DrawerRecyclerInterface() {
            @Override
            public void onCategorySelected(MenuModel.DataBean model) {
                if (!model.getSub_category().isEmpty()) {
                    drawerRecycler.setVisibility(View.GONE);
                    animateSubListShow(model);
                    return;
                }
                closeDrawerMenu();

            }

            @Override
            public void onItemSlected(ItemCategory itemCategory) {
                handleCategory(itemCategory);
                closeDrawerMenu();

            }
        }, getActivity());
        drawerRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        drawerRecycler.setHasFixedSize(true);
        drawerRecycler.setAdapter(drawerRecyclerAdapter);

        subMenuAdapter = new SubMenuAdapter(getActivity(), new SubMenuAdapter.OnSubmenuSelectedListner() {
            @Override
            public void onSelected(MenuModel.DataBean.SubCategoryBean model) {
                Home.getInstace().checkAndTakeToSubCategroy(model);
            }
        });
        drawer_submenu_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        drawer_submenu_recycler.setHasFixedSize(true);
        drawer_submenu_recycler.setAdapter(subMenuAdapter);


    }

    private void handleCategory(ItemCategory itemCategory) {
        if (itemCategory.getCatergoryId() == 1) {
            Home.getInstace().takeToViewCustomer();
        } else if (itemCategory.getCatergoryId() == 2) {
            Home.getInstace().taketoOrdersById(Utils.getActiveUser(getActivity()).getUser_id());
        }
    }

    private void animateSubListHide() {
        drawerSubmenuLayout.setVisibility(View.GONE);
        Animation slideAwayDisappear = AnimationUtils.makeInAnimation(getActivity(), true);
        final Animation slideAwayAppear = AnimationUtils.makeInAnimation(getActivity(), true);
        slideAwayDisappear.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
               /* drawerRecycler.setVisibility(View.VISIBLE);
                drawerRecycler.startAnimation(slideAwayAppear);*/
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                drawerRecycler.setVisibility(View.VISIBLE);
                drawerRecycler.startAnimation(slideAwayAppear);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        drawerSubmenuLayout.startAnimation(slideAwayDisappear);
    }

    private void animateSubListShow(MenuModel.DataBean drawerItemCategory) {
        if (drawerItemCategory != null) {
            drawerRecycler.setVisibility(View.GONE);
            drawerSubmenuTitle.setText(drawerItemCategory.getCategory_name());
            subMenuAdapter.setData(drawerItemCategory.getSub_category());
            Animation slideInDisappear = AnimationUtils.makeInAnimation(getActivity(), true);
            final Animation slideInAppear = AnimationUtils.makeInAnimation(getActivity(), true);
            slideInDisappear.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    drawerSubmenuLayout.setVisibility(View.VISIBLE);
                    drawerSubmenuLayout.startAnimation(slideInAppear);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            drawerRecycler.startAnimation(slideInDisappear);
        }
    }


    /**
     * Base method for layout preparation. Also set a listener that will respond to events that occurred on the menu.
     *
     * @param drawerLayout drawer layout, which will be managed.
     * @param toolbar      toolbar bundled with a side menu.
     */
    public void setUp(DrawerLayout drawerLayout, final Toolbar toolbar, DrawerActionListener listner) {
        mDrawerLayout = drawerLayout;
        this.listner = listner;

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.content_description_open_navigation_drawer, R.string.content_description_close_navigation_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
               /* notification.setVisibility(View.GONE);
                user_logged_in.setVisibility(View.GONE);*/
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
               /* notification.setVisibility(View.VISIBLE);
                user_logged_in.setVisibility(View.VISIBLE);*/
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
              /*  toolbar.setAlpha(1 - slideOffset / 2);
                title.setAlpha(1 - slideOffset / 3);*/
            }
        };


        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        getDrawerItems();
    }

    private void getDrawerItems() {
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", Constant.access_token);
        ServerConnection.requestServer(getString(R.string.get_combined_category), params, Constant.get_combined_category, new serverConnectionListner() {
            @Override
            public void onSuccess(String response) {
                Helper.logcat(tag, "getDrawerItems >> " + response);
                try {
                    if (new JSONObject(response).getString("status").equalsIgnoreCase("success")) {
                        MenuModel menuModel = Helper.getGsonObject().fromJson(response, MenuModel.class);
                        if (!menuModel.getData().isEmpty()) {
                            drawerRecyclerAdapter.addCategory(menuModel.getData());

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

        getItemCategory();
    }

    private void getItemCategory() {
        List<ItemCategory> categoryList = new ArrayList<>();
        if (Utils.getActiveUser(getActivity()).getRole().equalsIgnoreCase("2")) {
            categoryList.add(new ItemCategory(1, "View Costumer"));
            categoryList.add(new ItemCategory(2, "My Orders"));
        } else if (Utils.getActiveUser(getActivity()).getRole().equalsIgnoreCase("1")) {
            categoryList.add(new ItemCategory(2, "My Orders"));
        }

        drawerRecyclerAdapter.addItem(categoryList);
    }


    /**
     * Check if drawer is open. If so close it.
     *
     * @return false if drawer was already closed
     */
    public boolean onBackHide() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }


    /**
     * When the drawer menu is open, close it.
     */
    public static void closeDrawerMenu() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    /**
     * When the drawer menu is open, close it. Otherwise open it.
     */
    public void toggleDrawerMenu() {
        if (mDrawerLayout != null) {
            if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        }
    }


    public interface DrawerActionListener {
        void onHeaderselected();

        void onCategorySelected(MenuModel.DataBean category);
    }

    public interface SubCategorySelectedListner {
        void onBAckselected();

        void onSubCategorySelected(MenuModel.DataBean.SubCategoryBean category);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}

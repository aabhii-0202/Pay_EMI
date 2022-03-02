package com.mediustechnologies.payemi.activities.DashBoardclasses;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mediustechnologies.payemi.ApiResponse.BaseApiResponse;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.activities.EmiCategories;
import com.mediustechnologies.payemi.activities.login.SendOTP;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivityHomeNavBinding;
import com.mediustechnologies.payemi.databinding.NotificationFragmentBinding;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;
import com.mediustechnologies.payemi.helper.RetrofitClient;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_Nav extends BaseAppCompatActivity implements DrawerAdapter.OnItemSelectedListener, ProfileFraggment.updateNameListner {

    private ActivityHomeNavBinding binding;
    private final Context context = this;
    private static final int POS_HOME = 0;
    private static final int POS_COMPLAINT = 1;
    private static final int POS_TRANSACTIONSEARCH = 2;
    private static final int POS_PROFILE = 3;
    private static final int POS_RATE = 4;
    private static final int POS_NOTIFICATION = 5;
    private static final int POS_HELP = 6;
    private String[] screenTitles;
    private Drawable[] screenIcons;
    private SlidingRootNav slidingRootNav;
    private TextView t;
    private ImageView profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityHomeNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addbutton.setOnClickListener(view -> {
            Intent myIntent = new Intent(context, EmiCategories.class);
            startActivity(myIntent);
        });

        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        slidingRootNav = new SlidingRootNavBuilder(this).withDragDistance(170)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(binding.toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();

        profile = slidingRootNav.getLayout().findViewById(R.id.navprofilepic);
        Glide.with(profile).load(utils.profileUrl).into(profile);
        ImageView logout = slidingRootNav.getLayout().findViewById(R.id.nav_logout);
        logout.setOnClickListener(view -> {
            SharedPreferences preferences = getSharedPreferences("PAY_EMI", Context.MODE_PRIVATE);
            preferences.edit().putString("phone", "").apply();
            preferences.edit().putString("token", "Bearer ").apply();
            preferences.edit().putString("profileid", "").apply();
            preferences.edit().putString("refresh_token", "Bearer ").apply();

            Intent i = new Intent(context, SendOTP.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        });
        t = slidingRootNav.getLayout().findViewById(R.id.nav_name);
        t.setText(utils.name);

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                creatItemFor(POS_HOME).setChecked(true),
                creatItemFor(POS_COMPLAINT),
                creatItemFor(POS_TRANSACTIONSEARCH),
                creatItemFor(POS_PROFILE),
                creatItemFor(POS_RATE),
                creatItemFor(POS_NOTIFICATION),
                creatItemFor(POS_HELP)
        ));

        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.drawerlist);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(context));
        list.setAdapter(adapter);
        int fragment = getIntent().getIntExtra("fragment",0);
        adapter.setSelected(fragment);
        
        binding.bell.setOnClickListener(view -> {
            onItemSelected(POS_NOTIFICATION);
            binding.notificaioncountcard.setVisibility(View.GONE);
        });


    }


    //method to set color on selected toolbar
    private DrawerItems creatItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.navText))//color of selected
                .withTextTint(color(R.color.navText))
                .withSelectedIconTint(color(R.color.teal_200))
                .withSelectedTextTint(color(R.color.teal_200));
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(context, res);
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.id_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.id_activityScreenIcon);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(context, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @Override
    public void onBackPressed() {
        if (slidingRootNav.isMenuClosed())
            finish();
        else slidingRootNav.closeMenu();
    }

    @Override
    public void onItemSelected(int position) {
        // transaction of fragments
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        if (position == POS_HOME) {
            binding.homenavtitle.setText("PayEMI");
            binding.linearLayout2.setVisibility(View.VISIBLE);
            binding.titleImage.setVisibility(View.GONE);
            binding.navtitleimg.setVisibility(View.VISIBLE);
            binding.navview.setVisibility(View.VISIBLE);
            binding.clearall.setVisibility(View.GONE);
            DashBoardFragment dashboardFrag = new DashBoardFragment();
            transaction.replace(R.id.homeframe, dashboardFrag);

            new Handler().postDelayed(() -> {
                if(utils.new_notification_count>0){
                    binding.notificaioncountcard.setVisibility(View.VISIBLE);
                    binding.notificaioncount.setText(""+utils.new_notification_count);
                }
            },2000);



        } else if (position == POS_TRANSACTIONSEARCH) {
            hide_detail("Transaction Search");
            TransactionSearchFragment transactionSearchFragment = new TransactionSearchFragment();
            transaction.replace(R.id.homeframe, transactionSearchFragment);
        } else if (position == POS_PROFILE) {
            hide_detail("Profile");
            ProfileFraggment profileFraggment = new ProfileFraggment();
            transaction.replace(R.id.homeframe, profileFraggment);
        } else if (position == POS_RATE) {
            hide_detail("Complaint Tracking");
            ComplaintTrackingFragment complaintTrackingFragment = new ComplaintTrackingFragment();
            transaction.replace(R.id.homeframe, complaintTrackingFragment);
        } else if(position == POS_HELP){
            hide_detail("Help");
            HelpFragment helpFragment = new HelpFragment();
            transaction.replace(R.id.homeframe,helpFragment);

        } else if (position == POS_NOTIFICATION) {
            hide_detail("Notifications");
            binding.clearall.setVisibility(View.VISIBLE);
            binding.titleImage.setVisibility(View.GONE);
            NotificationFragment notificationFragment = new NotificationFragment();
            transaction.replace(R.id.homeframe, notificationFragment);

            binding.clearall.setOnClickListener(view -> {

                if (utils.ids != null && utils.ids.size() > 0) {
                    Call<BaseApiResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().clearAllNotification(utils.access_token, utils.ids);

                    call.enqueue(new Callback<BaseApiResponse>() {
                        @Override
                        public void onResponse(Call<BaseApiResponse> call, Response<BaseApiResponse> response) {
                            if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                                if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {

                                    Toast.makeText(context, "All notifications cleared", Toast.LENGTH_SHORT).show();
                                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.homeframe);
                                    if (fragment instanceof NotificationFragment) {
                                        ((NotificationFragment) fragment).clear();
                                    }
                                }
                                else {
                                    try {
                                        utils.errortoast(context, response.body().getMessage());
                                    } catch (Exception e) {
                                        Log.e("tag", e.toString());
                                    }
                                }
                            } else {
                                Toast.makeText(context, "Failed " + response.code(), Toast.LENGTH_SHORT).show();
                                Log.e("tag", "" + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseApiResponse> call, Throwable t) {
                            Toast.makeText(context,"Not able to clear notification please try again later",Toast.LENGTH_LONG);
                        }
                    });

                }
            });
        } else {
            hide_detail("Register Complaint");
            ComplaintRegFrag complaintRegFrag = new ComplaintRegFrag();
            transaction.replace(R.id.homeframe, complaintRegFrag);
        }

        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();

    }


    private void hide_detail(String title) {
        binding.clearall.setVisibility(View.GONE);
        binding.homenavtitle.setText(title);
        binding.linearLayout2.setVisibility(View.GONE);
        binding.titleImage.setVisibility(View.VISIBLE);
        binding.navtitleimg.setVisibility(View.GONE);
        binding.navview.setVisibility(View.GONE);
    }


    @Override
    public void updatename(String name, String url) {
        t.setText(name);
        Glide.with(profile).load(url).into(profile);
    }
}
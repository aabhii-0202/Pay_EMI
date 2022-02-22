package com.mediustechnologies.payemi.activities.DashBoardclasses;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.activities.EmiCategories;
import com.mediustechnologies.payemi.activities.login.SendOTP;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivityHomeNavBinding;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;
import java.util.Objects;

public class Home_Nav  extends BaseAppCompatActivity implements DrawerAdapter.OnItemSelectedListener{

    ActivityHomeNavBinding binding;
    private final Context context = this;

    private static final int POS_HOME= 0;
    private static final int POS_COMPLAINT = 1;
    private static final int POS_TRANSACTIONSEARCH= 2;
    private static final int POS_PROFILE = 3;
    private static final int POS_RATE = 4;
    private static final int POS_NOTIFICATION = 5;
    private static final int POS_HELP = 6;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityHomeNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(context, EmiCategories.class);
                startActivity(myIntent);
            }
        });

        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        slidingRootNav = new SlidingRootNavBuilder(this).withDragDistance(100)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(binding.toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();

        ImageView profile  = slidingRootNav.getLayout().findViewById(R.id.navprofilepic);
        Glide.with(profile).load(utils.profileUrl).into(profile);
        ImageView logout = slidingRootNav.getLayout().findViewById(R.id.nav_logout);
        logout.setOnClickListener(view ->{
            SharedPreferences preferences = getSharedPreferences("PAY_EMI", Context.MODE_PRIVATE);
            preferences.edit().putString("phone", "").apply();
            preferences.edit().putString("token", "Bearer ").apply();
            preferences.edit().putString("profileid","").apply();
            preferences.edit().putString("refresh_token", "Bearer ").apply();

            Intent i = new Intent(context, SendOTP.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        });
        TextView t =  slidingRootNav.getLayout().findViewById(R.id.nav_name);
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
        adapter.setSelected(POS_HOME);

    }

    //method to set color on selected toolbar
    private DrawerItems creatItemFor (int position){
        return new SimpleItem(screenIcons[position],screenTitles[position])
                .withIconTint(color(R.color.navText))//color of selected
                .withTextTint(color(R.color.navText))
                .withSelectedIconTint(color(R.color.teal_200))
                .withSelectedTextTint(color(R.color.teal_200));
    }

    @ColorInt
    private int color(@ColorRes int res){
        return ContextCompat.getColor(context,res);
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.id_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.id_activityScreenIcon);
        Drawable[] icons = new Drawable[ta.length()];
        for(int i=0;i<ta.length();i++){
            int id = ta.getResourceId(i,0);
            if(id!=0){
                icons[i]=ContextCompat.getDrawable(context,id);
            }
        }
        ta.recycle();
        return icons;
    }

    @Override
    public void onBackPressed() {
        if(slidingRootNav.isMenuClosed())
            finish();
        else slidingRootNav.closeMenu();
    }

    @Override
    public void onItemSelected(int position) {
        // transaction of fragments
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        if(position == POS_HOME){
            binding.homenavtitle.setText("PayEMI");
            binding.linearLayout2.setVisibility(View.VISIBLE);
            binding.titleImage.setVisibility(View.GONE);
            binding.navtitleimg.setVisibility(View.VISIBLE);
            binding.navview.setVisibility(View.VISIBLE);
            DashBoardFragment dashboardFrag = new DashBoardFragment();
            transaction.replace(R.id.homeframe,dashboardFrag);
        }
        else if (position == POS_TRANSACTIONSEARCH){
            hide_detail("Transaction Search");
            TransactionSearchFragment transactionSearchFragment = new TransactionSearchFragment();
            transaction.replace(R.id.homeframe,transactionSearchFragment);
        }
        else if(position == POS_PROFILE){
            hide_detail("Profile");
            ProfileFraggment profileFraggment = new ProfileFraggment();
            transaction.replace(R.id.homeframe,profileFraggment);
        }
        else if(position == POS_HELP){
            hide_detail("Complaint Tracking");
            ComplaintTrackingFragment complaintTrackingFragment = new ComplaintTrackingFragment();
            transaction.replace(R.id.homeframe,complaintTrackingFragment);
        }

        else{
            hide_detail("Register Complaint");
            ComplaintRegFrag complaintRegFrag = new ComplaintRegFrag();
            transaction.replace(R.id.homeframe,complaintRegFrag);
        }

        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
        
    }

    private void hide_detail(String title){
        binding.homenavtitle.setText(title);
        binding.linearLayout2.setVisibility(View.GONE);
        binding.titleImage.setVisibility(View.VISIBLE);
        binding.navtitleimg.setVisibility(View.GONE);
        binding.navview.setVisibility(View.GONE);
    }


}
package com.mediustechnologies.payemi.activities.DashBoardclasses;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.ActivityHomeNavBinding;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class Home_Nav  extends BaseAppCompatActivity implements DrawerAdapter.OnItemSelectedListener{

    ActivityHomeNavBinding binding;
    private Context context = this;

    private static final int POS_HOME= 0;
    private static final int POS_COMPLAINT = 1;
    private static final int POS_TRANSACTIONSEARCH= 2;
    private static final int POS_PROFILE = 4;
    private static final int POS_RATE = 5;
    private static final int POS_NOTIFICATION = 6;
    private static final int POS_HELP = 7;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toolbar);
        slidingRootNav = new SlidingRootNavBuilder(this).withDragDistance(100)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(binding.toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();
        
        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                creatItemFor(POS_HOME).setChecked(true),
                creatItemFor(POS_COMPLAINT),
                creatItemFor(POS_TRANSACTIONSEARCH),
                creatItemFor(POS_PROFILE),
                creatItemFor(POS_RATE),
                creatItemFor(POS_NOTIFICATION),
                new SpaceItem(260),
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
                .withIconTint(color(R.color.btncolor))//color of selected
                .withTextTint(color(R.color.black))
                .withSelectedIconTint(color(R.color.teal_200))
                .withSelectedTextTint(color(R.color.black));
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
        finish();
    }

    @Override
    public void onItemSelected(int position) {
        // transaction of fragments
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(position == POS_HOME){
            dashboardFrag dashboardFrag = new dashboardFrag();
            transaction.replace(R.id.homeframe,dashboardFrag);
        }
        else{
            ComplaintRegFrag complaintRegFrag = new ComplaintRegFrag();
            transaction.replace(R.id.homeframe,complaintRegFrag);
        }

        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
        
    }
}
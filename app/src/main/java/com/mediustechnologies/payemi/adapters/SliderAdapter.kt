package com.mediustechnologies.payemi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import com.mediustechnologies.payemi.R;

public class SliderAdapter extends PagerAdapter {

    int[] images = {
            R.drawable.onboard1,
            R.drawable.onboard2,
            R.drawable.onboard3
    };

    String[] headings = {
            "Grow Easily.",
            "Pay EMI with One Touch",
            "Grow Easily."
    };

    String[] subheadings = {
            "See at a glance all EMIs and get instant cashback.",
            "Easy and smart way to Save your wallet.",
            "See at a glance all EMIs and get instant cashback."
    };

    int[] btnimgs ={
            R.drawable.ic_load1,
            R.drawable.ic_load2,
            R.drawable.ic_load3
    };



    private final Context context;
    private LayoutInflater inflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        inflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.slides_layout_onbordingscreen,container,false);

        ImageView imageView = view.findViewById(R.id.sliderImage);
        TextView t1 = view.findViewById(R.id.titleText1);
        TextView t2 = view.findViewById(R.id.subtitleText2);
//        ImageView btn = view.findViewById(R.id.nextbtn);

        imageView.setImageResource(images[position]);
        t1.setText(headings[position]);
        t2.setText(subheadings[position]);
//        btn.setImageResource(btnimgs[position]);


        container.addView(view);

        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}

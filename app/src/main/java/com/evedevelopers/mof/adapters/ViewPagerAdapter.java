package com.evedevelopers.mof.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.evedevelopers.mof.R;
import com.evedevelopers.mof.activities.GamePlay;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    List<Integer> levelList;
    Context context;
    LayoutInflater layoutInflater;
    int[] greens = {
            R.color.green0,
            R.color.green1,
            R.color.green2,
            R.color.green3
    };

    public ViewPagerAdapter(List<Integer> levelList, Context context) {
        this.levelList = levelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return levelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.level_item,container,false);
        TextView textView = view.findViewById(R.id.level_text);
        CardView cardView = view.findViewById(R.id.card);
        RelativeLayout r = view.findViewById(R.id.card_bg);
        r.setBackgroundColor(context.getResources().getColor(greens[position]));
        textView.setTextColor(context.getResources().getColor(greens[levelList.size()-position-1]));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, GamePlay.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("level",levelList.get(position));
                context.startActivity(i);
            }
        });
        String m = String.valueOf(levelList.get(position)).concat(" x ").concat(String.valueOf(levelList.get(position)));
        textView.setText(m);
        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}


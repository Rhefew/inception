package com.rhefew.cocdrive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rhefew.cocdrive.R;
import com.rhefew.cocdrive.WarStats;

/**
 * Created by rodrigo on 01/12/14.
 */
public class WarStatsView extends LinearLayout {

    public WarStatsView(Context context, WarStats stats) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.war_stats_view, this, false);

        TextView against = (TextView)rootView.findViewById(R.id.txtAgainstWarStats);
        TextView result = (TextView)rootView.findViewById(R.id.txtResultWarStats);
        TextView maxstars = (TextView)rootView.findViewById(R.id.txtMaxStarsWarResults);
        TextView defense = (TextView)rootView.findViewById(R.id.txtHeroicDefenseWarstars);
        TextView attack = (TextView)rootView.findViewById(R.id.txtHeroicAttackWarstars);
        TextView aused = (TextView)rootView.findViewById(R.id.txtAttacksUsedWarStats);
        TextView awon = (TextView)rootView.findViewById(R.id.txtAttacksWonWarStats);
        TextView alost = (TextView)rootView.findViewById(R.id.txtAttacksLostWarStats);
        TextView aremaining = (TextView)rootView.findViewById(R.id.txtAttacksRemainingWarStats);
        TextView three = (TextView)rootView.findViewById(R.id.txtThreeStarsWarStats);
        TextView two = (TextView)rootView.findViewById(R.id.txtTwoStarsWarStats);
        TextView one = (TextView)rootView.findViewById(R.id.txtOnestarWarStats);
        TextView perattack = (TextView)rootView.findViewById(R.id.txtStarsPerAttackWarStats);
        TextView destruction = (TextView)rootView.findViewById(R.id.txtAverageDestructionWarStats);
        TextView duration = (TextView)rootView.findViewById(R.id.txtAverageDurationWarStats);

        against.setText("Inception vs "+stats.getAgainst());
        result.setText(""+stats.getResult());
        maxstars.setText(""+stats.getMaxstars());
        defense.setText(""+stats.getHeroic_defense());
        attack.setText(""+stats.getHeroic_attack());
        aused.setText(""+stats.getAttacks_used());
        awon.setText(""+stats.getAttacks_won());
        alost.setText(""+stats.getAttacks_lost());
        aremaining.setText(""+stats.getAttacks_remaining());
        three.setText(""+stats.getThree_stars());
        two.setText(""+stats.getTwo_stars());
        one.setText(""+stats.getOne_star());
        perattack.setText(""+stats.getNew_stars_per_attack());
        destruction.setText("%"+stats.getAverage_destruction());
        int mins = (stats.getAverage_duration() / 60);
        int secs = stats.getAverage_duration() - (60 * mins);
        duration.setText(""+(stats.getAverage_duration() / 60) + " min " + (stats.getAverage_duration()));

        this.addView(rootView);
    }
}
















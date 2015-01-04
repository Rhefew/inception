package com.rhefew.cocdrive.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.gms.games.Games;
import com.rhefew.cocdrive.Cons;
import com.rhefew.cocdrive.R;
import com.rhefew.cocdrive.RankView;

import org.json.JSONObject;

/**
 * Created by rodrigo on 20/11/14.
 */
public class Rank extends Activity {

    boolean is_memory_low = false;

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        is_memory_low = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Ranking");

        LinearLayout llMemberStats = (LinearLayout)findViewById(R.id.llRank);
        for(int i=0; i< Cons.results.getMember_stars().length(); i++){

            /*Adding activity_results to ResultsView rows*/
            JSONObject member = Cons.results.getMember_stars().optJSONObject(String.valueOf(i+1));
            String nickName = member.optString("member");

            if(Cons.mGoogleClient.isConnected()) {
                if (i == 0 && Cons.member.toUpperCase().equals(nickName.toUpperCase()))
                    Games.Achievements.unlock(Cons.mGoogleClient, getString(R.string.achievement_dorado));

                if (i == 1 && Cons.member.toUpperCase().equals(nickName.toUpperCase()))
                    Games.Achievements.unlock(Cons.mGoogleClient, getString(R.string.achievement_plateado));

                if (i == 2 && Cons.member.toUpperCase().equals(nickName.toUpperCase()))
                    Games.Achievements.unlock(Cons.mGoogleClient, getString(R.string.achievement_bronceado));
            }

            String stars = member.optString("total_stars");
            try {

                llMemberStats.addView(new RankView(getApplicationContext(), i, nickName, stars));

            }catch (Exception e){

            }
        }
    }
}

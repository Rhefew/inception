package com.rhefew.cocdrive.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.rhefew.cocdrive.Cons;
import com.rhefew.cocdrive.MemberStatsView;
import com.rhefew.cocdrive.R;

import org.json.JSONObject;

/**
 * Created by rodrigo on 20/11/14.
 */
public class MemberStats extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_stats);



        LinearLayout llMemberStats = (LinearLayout)findViewById(R.id.llMemberStats);
        for(int i=0; i< Cons.results.getMember_stars().length(); i++){

            /*Adding results to ResultsView rows*/
            JSONObject member = Cons.results.getMember_stars().optJSONObject(String.valueOf(i+1));
            String nickName = member.optString("member");
            try {

                int stars_0 = member.optInt("stars_0");
                int stars_1 = member.optInt("stars_1");
                int stars_2 = member.optInt("stars_2");
                int stars_3 = member.optInt("stars_3");
                llMemberStats.addView(new MemberStatsView(getApplicationContext(), nickName, stars_0, stars_1, stars_2, stars_3));

            }catch (Exception e){

            }
        }
    }
}

package com.parse.loginsample.layoutoverride;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



/**
 * Created by oliverlewis on 7/5/15.
 */
public class Fragment_home extends Fragment {

    //private CardContainer mCardContainer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main, container, false);
        /*View view = inflater.inflate(R.layout.FragmentActivity_home, container, false);
        mCardContainer = (CardContainer)view.findViewById(R.id.layoutview);

        CardModel card = new CardModel("Title1", "Description goes here", Fragment_home.this.getResources().getDrawable(R.drawable.picture1));


        card.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
            @Override
            public void onLike() {
                Log.d("Swipeable Card", "I liked it");
            }

            @Override
            public void onDislike() {
                Log.d("Swipeable Card", "I did not liked it");
            }
        });

        card.setOnClickListener(new CardModel.OnClickListener() {
            @Override
            public void OnClickListener() {
                //Log.i("Swipeable Cards","I am pressing the card");
            }
        });*/

        return view;
    }

}

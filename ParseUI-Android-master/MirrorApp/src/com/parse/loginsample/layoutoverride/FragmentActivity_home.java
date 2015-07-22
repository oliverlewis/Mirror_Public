package com.parse.loginsample.layoutoverride;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


/**
 * Created by oliverlewis on 7/4/15.
 */
public class FragmentActivity_home extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
    }


}

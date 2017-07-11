package com.ds365.crime.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.ds365.crime.R;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_continer);

        if (fragment == null) {
//            fragment = new CrimeFragment();
            fragment = createFragment();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_continer, fragment)
                    .commit();
        }
    }

}

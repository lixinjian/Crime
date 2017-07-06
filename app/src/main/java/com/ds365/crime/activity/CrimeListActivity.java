package com.ds365.crime.activity;

import android.support.v4.app.Fragment;

import com.ds365.crime.base.SingleFragmentActivity;
import com.ds365.crime.fragment.CrimeFragment;
import com.ds365.crime.fragment.CrimeListFragment;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}

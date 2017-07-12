package com.ds365.crime.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ds365.crime.R;
import com.ds365.crime.activity.CrimePagerActivity;
import com.ds365.crime.adapter.CrimeAdapter;
import com.ds365.crime.model.Crime;
import com.ds365.crime.model.CrimeLab;

import java.util.List;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeList;
    private CrimeAdapter mCrimeAdapter;
    private List<Crime> mCrimes;
    private static final int REQUEST_CRIME = 1;
    private int selectPosition = -1;
    private boolean mSubtitleVisible;
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeList = view.findViewById(R.id.crime_list);
        mCrimeList.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.getCrimeLab(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity
                        .newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.getCrimeLab(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);
        if (!mSubtitleVisible) {
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }


    private void updateUI() {
        mCrimes = CrimeLab.getCrimeLab(getActivity()).getCrimes();
        if (mCrimeAdapter == null) {
            mCrimeAdapter = new CrimeAdapter(getActivity(), mCrimes);
            mCrimeList.setAdapter(mCrimeAdapter);
        } else {
            if (selectPosition != -1) {
                //高效刷新,只刷新当前点的行.
                mCrimeAdapter.notifyItemChanged(selectPosition);
            } else {
                mCrimeAdapter.setCrimes(mCrimes);
                mCrimeAdapter.notifyDataSetChanged();
            }
        }

        //必须写此句话,设置recycleView的模式
//        RecyclerView.LayoutManager,这是一个抽象类，好在系统提供了3个实现类：
//        LinearLayoutManager 现行管理器，支持横向、纵向。
//        GridLayoutManager 网格布局管理器
//        StaggeredGridLayoutManager 瀑布流式布局管理器

        //RecycleView的分割线
        mCrimeList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        mCrimeAdapter.setItemClickListener(new CrimeAdapter.MyItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                selectPosition = position;
                Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrimes.get(position).getId());
//                startActivityForResult(intent, REQUEST_CRIME);
                getActivity().startActivity(intent);
            }
        });
        updateSubtitle();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CRIME) {

        }
    }
}

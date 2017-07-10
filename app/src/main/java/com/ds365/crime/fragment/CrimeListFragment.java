package com.ds365.crime.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ds365.crime.R;
import com.ds365.crime.activity.CrimeActivity;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeList = view.findViewById(R.id.crime_list);

        mCrimeList.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
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
                Toast.makeText(getActivity(), "点了第" + position + "行", Toast.LENGTH_SHORT).show();
//                Intent intent = CrimeActivity.newIntent(getActivity(), mCrimes.get(position).getId());

                Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrimes.get(position).getId());

//                startActivityForResult(intent, REQUEST_CRIME);
                getActivity().startActivity(intent);
            }
        });

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CRIME) {
//
//        }
//    }
}

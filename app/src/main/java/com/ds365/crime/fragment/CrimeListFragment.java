package com.ds365.crime.fragment;

import android.content.Intent;
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
import com.ds365.crime.adapter.CrimeAdapter;
import com.ds365.crime.model.CrimeLab;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeList;

    private CrimeAdapter mCrimeAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeList = view.findViewById(R.id.crime_list);

        mCrimeAdapter = new CrimeAdapter(getActivity(), CrimeLab.getCrimeLab(getActivity()).getCrimes());

        //必须写此句话,设置recycleView的模式
//        RecyclerView.LayoutManager,这是一个抽象类，好在系统提供了3个实现类：
//        LinearLayoutManager 现行管理器，支持横向、纵向。
//        GridLayoutManager 网格布局管理器
//        StaggeredGridLayoutManager 瀑布就式布局管理器
        mCrimeList.setLayoutManager(new LinearLayoutManager(getActivity()));

        //RecycleView的分割线
        mCrimeList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        mCrimeAdapter.setItemClickListener(new CrimeAdapter.MyItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(), "点了第" + position + "行", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), CrimeActivity.class);
//                intent.getSerializableExtra()
                startActivity(intent);
            }
        });

        mCrimeList.setAdapter(mCrimeAdapter);


        return view;
    }
}

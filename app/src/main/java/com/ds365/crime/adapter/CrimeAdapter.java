package com.ds365.crime.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ds365.crime.R;
import com.ds365.crime.activity.CrimePagerActivity;
import com.ds365.crime.model.Crime;

import java.util.List;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class CrimeAdapter extends RecyclerView.Adapter<CrimeAdapter.CrimeHolder> {

    private Context mContext;
    private List<Crime> mCrimes;
    private MyItemClickListener mListener;
    public CrimeAdapter(Context context, List<Crime> crimes) {
        mContext = context;
        mCrimes = crimes;
    }

    @Override
    public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CrimeHolder crimeHolder = new CrimeHolder(LayoutInflater.from(
                mContext).inflate(R.layout.list_item_crime, parent,
                false),mListener);

        return crimeHolder;
    }

    @Override
    public void onBindViewHolder(CrimeHolder holder, final int position) {
        holder.mCheckBox.setChecked(mCrimes.get(position).isSolved());
        holder.mDate.setText(mCrimes.get(position).getDate().toString());
        holder.mTitle.setText(mCrimes.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mCrimes.size();
    }

    class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CheckBox mCheckBox;
        TextView mTitle;
        TextView mDate;
        RelativeLayout mItem;

        public CrimeHolder(final View itemView,MyItemClickListener itemClickListener) {
            super(itemView);
            mListener = itemClickListener;
            itemView.setOnClickListener(this);
            mCheckBox = itemView.findViewById(R.id.list_item_crime_solved_check_box);
            mTitle = itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDate = itemView.findViewById(R.id.list_item_crime_date_text_view);
            mItem = itemView.findViewById(R.id.item);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view,getPosition());
        }
    }

    public interface MyItemClickListener {
        void onClick(View view, int position);
    }

    public void setItemClickListener(MyItemClickListener itemClickListener){
        mListener = itemClickListener;
    }

}

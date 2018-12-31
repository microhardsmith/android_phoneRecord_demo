package cn.hust.atry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by 71084 on 2018/12/8.
 */

public class PhoneFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private PhoneAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI(){
        PhoneLab phoneLab = PhoneLab.get(getActivity());
        List<PhoneModel> models = phoneLab.getPhoneModels();
        Log.d("Communication","get models "+ String.valueOf(models.size()));

        mAdapter = new PhoneAdapter(models);
        mRecyclerView.setAdapter(mAdapter);
    }



    private class PhoneHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private PhoneModel mModel;

        private TextView mNameView;
        private TextView mNumberView;
        private TextView mDateView;
        private TextView mDurationView;
        private TextView mTypeView;

        public PhoneHolder(LayoutInflater inflater,ViewGroup parent){
            super(inflater.inflate(R.layout.tab1item,parent,false));
            itemView.setOnClickListener(this);
            mNameView = (TextView) itemView.findViewById(R.id.tab1_name);
            mNumberView = (TextView) itemView.findViewById(R.id.tab1_number);
            mDateView = (TextView) itemView.findViewById(R.id.tab1_date);
            mDurationView = (TextView) itemView.findViewById(R.id.tab1_duration);
            mTypeView = (TextView) itemView.findViewById(R.id.tab1_type);

        }

        public void bind(PhoneModel model){
            mModel = model;
            mNameView.setText(mModel.getName());
            mNumberView.setText(mModel.getNumber());
            mDateView.setText(mModel.getDate());
            mDurationView.setText(String.valueOf(mModel.getDuration()));
            mTypeView.setText(String.valueOf(mModel.getType()));

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),
                    mModel.getName() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
            Log.d("communication","click event");
        }
    }

    private class PhoneAdapter extends RecyclerView.Adapter<PhoneHolder>{
        private List<PhoneModel> mPhoneModels;

        public PhoneAdapter(List<PhoneModel> phoneModels){
            mPhoneModels = phoneModels;
        }

        @Override
        public PhoneHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PhoneHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(PhoneHolder holder, int position) {
            PhoneModel phoneModel = mPhoneModels.get(position);
            holder.bind(phoneModel);
        }

        @Override
        public int getItemCount() {
            return mPhoneModels.size();
        }
    }
}

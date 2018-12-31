package cn.hust.atry;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 71084 on 2018/12/8.
 */

public class AddressFragment extends Fragment implements HintSideBar.OnMoveLetterListener{

    private RecyclerView mRecyclerView;
    private HintSideBar mHintSideBar;
    private TextView mTextView;

    private AddressAdapter mAddressAdapter;

    private LinearLayoutManager manager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2,container,false);
        Log.d("communication","tab2 fragment check");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mHintSideBar = (HintSideBar) view.findViewById(R.id.hintSideBar);
        mHintSideBar.setOnMoveLetterListener(this);
        mTextView = (TextView) view.findViewById(R.id.tab2_header);
        AddressLab addressLab = AddressLab.get(getActivity());
        List<AddressModel> models = addressLab.getAddressModels();
        mAddressAdapter = new AddressAdapter(models);
        manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAddressAdapter);



        mRecyclerView.addOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        View stickyInfoView=recyclerView.getChildAt(0);
                        if (stickyInfoView!=null && stickyInfoView.getContentDescription()!=null) {
                            mTextView.setText(String.valueOf(stickyInfoView.getContentDescription()));
                        }
                        View transInfoView=recyclerView.findChildViewUnder(mTextView.getMeasuredWidth()/2, mTextView.getMeasuredHeight()+1);
                        if (transInfoView!=null && transInfoView.getTag()!=null) {
                            int tag= (int) transInfoView.getTag();
                            int deltaY=transInfoView.getTop()-mTextView.getMeasuredHeight();
                            if (tag==AddressAdapter.HAS_STICKY_VIEW) {
                                if (transInfoView.getTop()>0) {
                                    mTextView.setTranslationY(deltaY);
                                }
                                else {
                                    mTextView.setTranslationY(0);
                                }
                            }
                            else {
                                mTextView.setTranslationY(0);
                            }
                        }
                    }
                }
        );

        return view;
    }

    @Override
    public void onMoveEvent(String s) {
        int i = mAddressAdapter.getFirstPositionByChar(s);
        Log.d("communication","position :  "+ String.valueOf(i));
        if (i == -1) {
            return;
        }
        manager.scrollToPositionWithOffset(i, 0);
        Log.d("communication","move to "+String.valueOf(i));
    }

//    @Override
//    public void onNoChooseLetter() {
//
//    }

    private class AddressHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private AddressModel mModel;
        private TextView mNameView;
        private TextView mHeaderView;

        public AddressHolder(LayoutInflater inflater,ViewGroup parent){
            super(inflater.inflate(R.layout.tab2item,parent,false));
            itemView.setOnClickListener(this);
            mNameView = (TextView) itemView.findViewById(R.id.tab2_name);
            mHeaderView = (TextView) itemView.findViewById(R.id.tab2_header);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),
                    mModel.getName() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
            Log.d("communication","click event");
        }
    }

    private class AddressAdapter extends RecyclerView.Adapter<AddressHolder>{
        private List<AddressModel> mAddressModels;
        private Map<String,Integer> cache;//缓存索引

        public static final int FIRST_STICKY_VIEW = 1;
        public static final int HAS_STICKY_VIEW = 2;
        public static final int NONE_STICKY_VIEW = 3;


        public AddressAdapter(List<AddressModel> AddressModels){
            mAddressModels = AddressModels;
            cache = new HashMap<>();
        }

        public int getFirstPositionByChar(String sign) {
            if (sign == "↑") {
                return 0;
            }
            if(cache.containsKey(sign)) return cache.get(sign);
            else{
                for (int i = 0; i < mAddressModels.size(); i++) {
                    if (mAddressModels.get(i).getPinyin().equals(sign)) {
                        cache.put(sign,i);
                        return i;
                    }
                }
            }
            return -1;
        }

        @Override
        public AddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new AddressHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(AddressHolder holder, int position) {
            AddressModel model = mAddressModels.get(position);
            holder.mModel = model;
            holder.mHeaderView.setText(model.getPinyin());
            holder.mNameView.setText(model.getName());
            holder.itemView.setContentDescription(model.getPinyin());
            if(position == 0){
                holder.mHeaderView.setVisibility(View.VISIBLE);
                holder.itemView.setTag(FIRST_STICKY_VIEW);
            }
            else if(model.getPinyin().equals(mAddressModels.get(position-1).getPinyin())){
                holder.mHeaderView.setVisibility(View.GONE);
                holder.itemView.setTag(HAS_STICKY_VIEW);
            }
            else {
                holder.mHeaderView.setVisibility(View.VISIBLE);
                holder.itemView.setTag(NONE_STICKY_VIEW);
            }
        }

        @Override
        public int getItemCount() {
            return mAddressModels.size();
        }
    }

    private class SimplePadding extends RecyclerView.ItemDecoration {
        private int dividerHeight;

        public SimplePadding(Context context){
            dividerHeight = context.getResources().getDimensionPixelSize(R.dimen.item_margin);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = dividerHeight;
            outRect.top = dividerHeight;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }
    }

}

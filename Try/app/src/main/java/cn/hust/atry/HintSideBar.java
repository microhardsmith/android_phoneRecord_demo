package cn.hust.atry;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by 71084 on 2018/12/16.
 */

public class HintSideBar extends RelativeLayout implements SideBar.OnChooseLetterChangedListener{
    private TextView tv_hint;

    private SideBar mSideBar;

    private SideBar.OnChooseLetterChangedListener onChooseLetterChangedListener;

    private OnMoveLetterListener mOnMoveLetterListener;


    public interface OnMoveLetterListener{
        void onMoveEvent(String s);
    }

    public HintSideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.tab2side, this);
        initView();
    }

    private void initView() {
        mSideBar = (SideBar) findViewById(R.id.sideBar);
        tv_hint = (TextView) findViewById(R.id.hint);
        mSideBar.setOnTouchingLetterChangedListener(this);
    }

    public void setOnMoveLetterListener(OnMoveLetterListener onMoveLetterListener) {
        mOnMoveLetterListener = onMoveLetterListener;
    }

    @Override
    public void onChooseLetter(String s) {
        tv_hint.setText(s);
        tv_hint.setVisibility(VISIBLE);
        if (onChooseLetterChangedListener != null) {
            onChooseLetterChangedListener.onChooseLetter(s);
            Log.d("communication","HintSideBar choose" + s);
        }
        if(mOnMoveLetterListener != null){
            mOnMoveLetterListener.onMoveEvent(s);
            Log.d("communication","HintSideBar move" + s);
        }
    }

    @Override
    public void onNoChooseLetter() {
        tv_hint.setVisibility(GONE);
        if (onChooseLetterChangedListener != null) {
            onChooseLetterChangedListener.onNoChooseLetter();
            Log.d("communication","HintSideBar no choose");
        }
    }

    public void setOnChooseLetterChangedListener(SideBar.OnChooseLetterChangedListener onChooseLetterChangedListener) {
        this.onChooseLetterChangedListener = onChooseLetterChangedListener;
    }


}

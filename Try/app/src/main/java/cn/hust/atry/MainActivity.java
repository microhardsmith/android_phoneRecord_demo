package cn.hust.atry;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout mTab1;
    private LinearLayout mTab2;
    private LinearLayout mTab3;

    private ImageButton mTab1Button;
    private ImageButton mTab2Button;
    private ImageButton mTab3Button;

    private Fragment mFragTab1;
    private Fragment mFragTab2;
    private Fragment mFragTab3;

    //初始化监听器绑定，在主activity里面一起处理
    private void initEvents(){
        mTab1Button.setOnClickListener(this);
        mTab2Button.setOnClickListener(this);
        mTab3Button.setOnClickListener(this);
    }

    //初始化view的赋值
    private void initViews(){
        mTab1 = (LinearLayout) findViewById(R.id.id_tab1);
        mTab2 = (LinearLayout) findViewById(R.id.id_tab2);
        mTab3 = (LinearLayout) findViewById(R.id.id_tab3);

        mTab1Button = (ImageButton) findViewById(R.id.id_tab1button);
        mTab2Button = (ImageButton) findViewById(R.id.id_tab2button);
        mTab3Button = (ImageButton) findViewById(R.id.id_tab3button);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvents();
        selectTab(0);
    }

    //将三个imagebutton都置为灰色
    private void resetImgs(){
        mTab1Button.setImageResource(R.mipmap.phone_missed);
        mTab2Button.setImageResource(R.mipmap.address_missed);
        mTab3Button.setImageResource(R.mipmap.send_missed);
    }

    private void selectTab(int i){
        //获取FragmentManager对象
        FragmentManager manager = getSupportFragmentManager();
        //获取FragmentTransaction对象
        FragmentTransaction transaction = manager.beginTransaction();
        //先隐藏所有的Fragment
        hideFragments(transaction);
        switch (i) {
            case 0:
                mTab1Button.setImageResource(R.mipmap.phone_checked);
                //如果phone对应的Fragment没有实例化，则进行实例化，并显示出来
                Log.d("communication","0 check");
                if (mFragTab1 == null) {
                    mFragTab1 = new PhoneFragment();
                    transaction.add(R.id.id_content, mFragTab1);
                    Log.d("communication","0 1 check");
                } else {
                    //如果phone对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mFragTab1);
                    Log.d("communication","0 2 check");
                }
                break;
            case 1:
                mTab2Button.setImageResource(R.mipmap.address_checked);
                //如果address对应的Fragment没有实例化，则进行实例化，并显示出来
                Log.d("communication","1 check");
                if (mFragTab2 == null) {
                    mFragTab2 = new AddressFragment();
                    transaction.add(R.id.id_content, mFragTab2);
                    Log.d("communication","1 1 check");
                } else {
                    //如果address对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mFragTab2);
                    Log.d("communication","1 2 check");
                }
                break;
            case 2:
                mTab3Button.setImageResource(R.mipmap.send_checked);
                //如果send对应的Fragment没有实例化，则进行实例化，并显示出来
                Log.d("communication","2 check");
                if (mFragTab3 == null) {
                    mFragTab3 = new SendFragment();
                    transaction.add(R.id.id_content, mFragTab3);
                    Log.d("communication","2 1 check");
                } else {
                    //如果send对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mFragTab3);
                    Log.d("communication","2 2 check");
                }
                break;
        }
        //不要忘记提交事务
        transaction.commit();

    }

    //将三个的Fragment隐藏
    private void hideFragments(FragmentTransaction transaction) {
        if (mFragTab1 != null) {
            transaction.hide(mFragTab1);
        }
        if (mFragTab2 != null) {
            transaction.hide(mFragTab2);
        }
        if (mFragTab3 != null) {
            transaction.hide(mFragTab3);
        }
    }

    @Override
    public void onClick(View v) {
        resetImgs();
        switch (v.getId()){
            case R.id.id_tab1button:
                selectTab(0);
                break;
            case R.id.id_tab2button:
                selectTab(1);
                break;
            case R.id.id_tab3button:
                selectTab(2);
                break;
        }
    }
}

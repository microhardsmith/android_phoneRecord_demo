package cn.hust.atry;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 71084 on 2018/12/14.
 * 存储单例通讯录类
 */

public class AddressLab {
    private static AddressLab sAddressLab;

    private List<AddressModel> mAddressModels;

    public static AddressLab get(Context context){
        if(sAddressLab == null){
            sAddressLab = new AddressLab(context);
        }
        return sAddressLab;
    }

    private AddressLab(Context context){
        mAddressModels = new ArrayList<>();
        String[] namelist = {"刘希晨","蒲腾飞","张雄","李新","胡瑞星","张三","李四","王五","周瑞","冷光乾"};
        for(int i = 0;i < 100;i++){
            AddressModel model = new AddressModel();
            model.setName(namelist[i%10]);
            model.setNumber("15271845019");
            setPinYinForModel(model);
            mAddressModels.add(model);
        }
        Collections.sort(mAddressModels,new PinYinComparator());//在lab中的addressmodel成员都已经是有序的了
    }


    public void setPinYinForModel(AddressModel model){
        if(model.getName()==null) throw new RuntimeException();
        String pinyin = Pinyin4jUtil.convertToFirstSpell(model.getName());
        if(Pinyin4jUtil.isPinYin(pinyin)){
            model.setPinyin(pinyin.substring(0,1));
        }
        else{
            model.setPinyin("#");
        }
        return;
    }

    public List<AddressModel> getAddressModels(){
        return mAddressModels;
    }
}

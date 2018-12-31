package cn.hust.atry;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 71084 on 2018/12/9.
 * 单例模式存储phone对象成员
 */

public class PhoneLab {
    private static PhoneLab sPhoneLab;

    private List<PhoneModel> mPhoneModels;

    public static PhoneLab get(Context context){
        if(sPhoneLab == null){
            sPhoneLab = new PhoneLab(context);
        }
        return sPhoneLab;
    }

    private PhoneLab(Context context){
        mPhoneModels = new ArrayList<>();
        for(int i = 0;i < 100;i++){
            PhoneModel model = new PhoneModel();
            model.setDate("2018-01-01");
            model.setDuration(666);
            model.setType(1);
            model.setName("liuxichen");
            model.setNumber("886");
            mPhoneModels.add(model);
        }
    }

    public List<PhoneModel> getPhoneModels(){
        return mPhoneModels;
    }
}

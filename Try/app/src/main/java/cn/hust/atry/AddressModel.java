package cn.hust.atry;

/**
 * Created by 71084 on 2018/12/14.
 */

public class AddressModel {
    private String mName;
    private String mNumber;
    private String mPinyin;

    public String getName() {
        return mName;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

    public void setPinyin(String pinyin) {
        mPinyin = pinyin;
    }

    public String getPinyin() {
        return mPinyin;
    }
}

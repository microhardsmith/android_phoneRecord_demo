package cn.hust.atry;

import java.util.Comparator;

/**
 * Created by 71084 on 2018/12/16.
 */

public class PinYinComparator implements Comparator<AddressModel> {
    @Override
    public int compare(AddressModel o1, AddressModel o2) {
        if (o1.getPinyin().equals("#")){
            return 1;
        }else if (o2.getPinyin().equals("#")){
            return -1;
        }
        String pinyin1 = Pinyin4jUtil.convertToFirstSpell(o1.getName());
        String pinyin2 = Pinyin4jUtil.convertToFirstSpell(o2.getName());
        return pinyin1.compareToIgnoreCase(pinyin2);
    }
}

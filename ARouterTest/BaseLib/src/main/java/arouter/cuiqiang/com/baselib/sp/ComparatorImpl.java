package arouter.cuiqiang.com.baselib.sp;

import java.util.Comparator;

/**
 * Created by cuiqiang on 2018/7/20.
 */

public class ComparatorImpl implements Comparator<String> {

    @Override
    public int compare(String lhs, String rhs) {
        return lhs.compareTo(rhs);
    }
}

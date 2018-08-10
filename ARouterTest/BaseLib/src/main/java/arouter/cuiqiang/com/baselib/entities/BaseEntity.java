package arouter.cuiqiang.com.baselib.entities;

import java.io.Serializable;

/**
 * Created by cuiqiang on 2018/7/20.
 */

public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private transient int baseErrorCode;

    public int getBaseErrorCode() {
        return baseErrorCode;
    }

    public void setBaseErrorCode(int baseErrorCode) {
        this.baseErrorCode = baseErrorCode;
    }
}

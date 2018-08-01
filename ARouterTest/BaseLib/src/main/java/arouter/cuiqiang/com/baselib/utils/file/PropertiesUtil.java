package arouter.cuiqiang.com.baselib.utils.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by cuiqiang on 2017/6/22.
 */

public class PropertiesUtil {

    /**
     * 获取assets 目录下的profile 文件
     * @param absolutePath
     * @return
     */
    public static Properties getAssetProperties(String absolutePath) {
        String path = absolutePath;
        if (path.startsWith("/")){
            path = path.substring(1, path.length() - 1);
        }
        Properties pro = new Properties();
        InputStream stream = null;
        try {
            stream = PropertiesUtil.class.getResourceAsStream("/assets/" + path);
            pro.load(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return pro;
    }

    /**
     * 获取assets 目录下的profile 文件
     * @param absolutePath
     * @param key
     * @param <T>
     * @return
     */
    public static <T extends Object> T getValueFromAssetPropertiesKey(String absolutePath,
                                                                      String key){
        Properties properties = getAssetProperties(absolutePath);
        return (T) properties.get(key);
    }

    /**
     * 获取绝对路径下的properties 文件 eg: /sdcard/...
     * @param absolutePath
     * @return
     */
    public static Properties getAbsolutePathProperties(String absolutePath){
        Properties pro = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(absolutePath);
            pro.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pro;
    }

}

package arouter.cuiqiang.com.baselib.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import arouter.cuiqiang.com.baselib.entities.BaseEntity;
import arouter.cuiqiang.com.baselib.utils.extension.StringExtension;


/**
 * Created by cuiqiang on 2018/7/20.
 */

public class SPreferenceImpl implements ISPreferences {

    /**
     * 默认的文件名
     */
    private static final String FILE_NAME = "shared_preferences";

    private static ISPreferences IPREFERENCE;
    private static SharedPreferences mPreferences;

    /**
     * 对象锁
     */
    private static final Object lock = new Object();

    public static ISPreferences getPreference(Context context) {
        synchronized (lock) {
            if (null == IPREFERENCE) {
                synchronized (lock) {
                    initPreference(context, FILE_NAME);
                }
            }
        }
        return IPREFERENCE;
    }

    public static ISPreferences getPreference(Context context, String fileName) {
        synchronized (lock) {
            if (null == IPREFERENCE) {
                synchronized (lock) {
                    initPreference(context, fileName);
                }
            }
        }
        return IPREFERENCE;
    }

    private static synchronized void initPreference(Context context, String fileName) {
        if (null == IPREFERENCE) {
            IPREFERENCE = new SPreferenceImpl(context, fileName);
        }
    }

    public SPreferenceImpl(Context context) {
        mPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public SPreferenceImpl(Context context, String fileName) {
        mPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }


    @Override
    public <T> void put(String key, T value) {
        SharedPreferences.Editor edit = mPreferences.edit();
        put(edit, key, value);
        edit.apply();
    }

    @Override
    public <T> void putAll(Map<String, T> map) {
        SharedPreferences.Editor edit = mPreferences.edit();
        for (Map.Entry<String, T> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            put(edit, key, value);
        }
        edit.apply();
    }

    @Override
    public void putAll(String key, List<String> list) {
        putAll(key, list, new ComparatorImpl());
    }

    @Override
    public void putAll(String key, List<String> list, Comparator<String> comparator) {
        Set<String> set = new TreeSet<>(comparator);
        for (String value : list) {
            set.add(value);
        }
        mPreferences.edit().putStringSet(key, set).apply();
    }

    @Override
    public <T> T get(String key, DataType type) {
        return (T) getValue(key, type);
    }

    @Override
    public Map<String, ?> getAll() {
        return mPreferences.getAll();
    }

    @Override
    public List<String> getAll(String key) {
        List<String> list = new ArrayList<String>();
        Set<String> set = get(key, DataType.STRING_SET);
        for (String value : set) {
            list.add(value);
        }
        return list;
    }

    @Override
    public void remove(String key) {
        mPreferences.edit().remove(key).apply();
    }

    @Override
    public void removeAll(List<String> keys) {
        SharedPreferences.Editor edit = mPreferences.edit();
        for (String k : keys) {
            edit.remove(k);
        }
        edit.apply();
    }

    @Override
    public void removeAll(String[] keys) {
        removeAll(Arrays.asList(keys));
    }

    @Override
    public boolean contains(String key) {
        return mPreferences.contains(key);
    }

    @Override
    public void clear() {
        mPreferences.edit().clear().apply();
    }

    @Override
    public String getString(String key) {
        return get(key, DataType.STRING);
    }

    @Override
    public float getFloat(String key) {
        return get(key, DataType.FLOAT);
    }

    @Override
    public int getInteger(String key) {
        return get(key, DataType.INTEGER);
    }

    @Override
    public long getLong(String key) {
        return get(key, DataType.LONG);
    }

    @Override
    public Set<String> getSet(String key) {
        return get(key, DataType.STRING_SET);
    }

    @Override
    public boolean getBoolean(String key) {
        return get(key, DataType.BOOLEAN);
    }


    public <T extends BaseEntity> void putSpEntity(T entity, String key) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(entity);
            //将序列化的数据转为16进制保存
            String bytesToHexString = StringExtension.bytesToHexString(bos.toByteArray());
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString(key, bytesToHexString);
            editor.commit();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    @Nullable
    public <T extends BaseEntity> T getSpEntity(String key) {
        try {
            String resultStr = mPreferences.getString(key, "");
            if (resultStr.isEmpty()) {
                return null;
            }
            byte[] stringToBytes = StringExtension.StringToBytes(resultStr);
            ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
            ObjectInputStream is = new ObjectInputStream(bis);
            //返回反序列化得到的对象
            Object readObject = is.readObject();
            return (T) readObject;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }


    /**
     * 保存数据
     *
     * @param editor
     * @param key
     * @param obj
     */
    @SuppressWarnings("unchecked")
    private void put(SharedPreferences.Editor editor, String key, Object obj) {
        // key 不为null时再存入，否则不存储
        if (key != null) {
            if (obj instanceof Integer) {
                editor.putInt(key, (Integer) obj);
            } else if (obj instanceof Long) {
                editor.putLong(key, (Long) obj);
            } else if (obj instanceof Boolean) {
                editor.putBoolean(key, (Boolean) obj);
            } else if (obj instanceof Float) {
                editor.putFloat(key, (Float) obj);
            } else if (obj instanceof Set) {
                editor.putStringSet(key, (Set<String>) obj);
            } else if (obj instanceof String) {
                editor.putString(key, String.valueOf(obj));
            }
        }
    }

    /**
     * 根据key和类型取出数据
     *
     * @param key
     * @return
     */
    private Object getValue(String key, DataType type) {
        switch (type) {
            case INTEGER:
                return mPreferences.getInt(key, -1);
            case FLOAT:
                return mPreferences.getFloat(key, -1f);
            case BOOLEAN:
                return mPreferences.getBoolean(key, false);
            case LONG:
                return mPreferences.getLong(key, -1L);
            case STRING:
                return mPreferences.getString(key, null);
            case STRING_SET:
                return mPreferences.getStringSet(key, null);
            default: // 默认取出String类型的数据
                return null;
        }
    }
}

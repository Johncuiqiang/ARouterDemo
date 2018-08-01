package arouter.cuiqiang.com.dualcachekit.serializer;


import com.google.gson.Gson;

/**
 * Created by David小硕 on 2017/8/17.
 */

public class JsonSerializer<T> implements CacheSerializer<T> {

    private final Class<T> clazz;
    private final Gson gson;

    public JsonSerializer(Class<T> clazz) {
        this.clazz = clazz;
        gson = new Gson();
    }


    @Override
    public T fromString(String data) {
        T result = gson.fromJson(data, clazz);
        return result;
    }

    @Override
    public String toString(T object) {
        String result = gson.toJson(object, clazz);
        return result;
    }
}

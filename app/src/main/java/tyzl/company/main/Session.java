package tyzl.company.main;

import java.util.Map;

public abstract class Session {
    //存储数据
    public void set(String key, String value) {
        this.set(key, value, 0);
    }

    public abstract void set(String key, String value, long expireSeconds);

    public void set(Map<String, String> vals) {
        this.set(vals, 0);
    }

    public abstract void set(Map<String, String> vals, long expireSeconds);

    //根据key值获取数据
    public abstract String get(String key);

    public abstract void putInt(String key, int value);

    public abstract int getInt(String key, int defaultValue);

    public abstract void putFloat(String key, float value);

    public abstract float getFloat(String key, float defaultValue);

    public abstract void putBoolean(String key, boolean value);

    public abstract boolean getBoolean(String key, boolean defaultValue);

}

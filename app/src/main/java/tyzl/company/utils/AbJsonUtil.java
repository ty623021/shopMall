/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tyzl.company.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import tyzl.company.global.Constant;

// TODO: Auto-generated Javadoc

/**
 * © 2012 amsoft.cn
 * 名称：AbStrUtil.java
 * 描述：json处理类.
 *
 * @param <T>
 * @author 还如一梦中
 * @version v1.0
 * @date：2013-01-17 下午11:52:13
 */
public class AbJsonUtil<T> {


    /**
     * 描述：将对象转化为json.
     *
     * @param src
     * @return
     */
    public static String toJson(Object src) {
        String json = null;
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            json = gson.toJson(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 描述：将列表转化为json.
     *
     * @param list
     * @return
     */
    public static String toJson(List<?> list) {
        String json = null;
        try {
            Gson gson = new Gson();
            json = gson.toJson(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 描述：将json转化为列表.
     * 列表数据在第一级
     *
     * @param json      获取的json数据
     * @param typeToken new TypeToken<ArrayList<?>>() {}.getType();
     * @return
     */
    public static List<?> fromJson(String json, TypeToken typeToken) {
        List<?> list = null;
        try {
            Gson gson = new Gson();
            JSONArray jsonArray = new JSONArray(json);
            list = gson.fromJson(jsonArray.toString(), typeToken.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 描述：将json转化为列表.
     * 列表数据在第二级
     *
     * @param json      获取的json数据
     * @param typeToken new TypeToken<ArrayList<?>>() {}.getType();
     * @param listKey   json集合的key
     * @return
     */
    public static List<?> fromJson(String json, TypeToken typeToken, String listKey) {
        List<?> list = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            Gson gson = new Gson();
            JSONArray picList = jsonObject.getJSONArray(listKey);
            list = gson.fromJson(picList.toString(), typeToken.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 描述：将json转化为列表.
     * 列表数据在第三级
     *
     * @param json      获取的json数据
     * @param typeToken new TypeToken<ArrayList<?>>() {}.getType();
     * @param listKey   json集合的key
     * @return
     */
    public static List<?> fromJsonMall(String json, TypeToken typeToken, String listKey) {
        List<?> list = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            Gson gson = new Gson();
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray picList = result.getJSONArray(listKey);
            list = gson.fromJson(picList.toString(), typeToken.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 描述：将json转化为列表.
     *
     * @param json
     * @param type    new TypeToken<ArrayList<?>>() {}.getType();
     * @param listKey json集合的key
     * @return
     */
    public static List<?> fromJson(String json, Type type, String listKey) {
        List<?> list = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            Gson gson = new Gson();
            JSONArray picList = jsonObject.getJSONArray(listKey);
            list = gson.fromJson(picList.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 描述：将json转化为对象.
     *
     * @param json
     * @param clazz
     * @return
     */
    public static Object fromJson(String json, Class clazz) {
        Object obj = null;
        try {
            Gson gson = new Gson();
            JSONObject jsonObject = new JSONObject(json);
            JSONObject data = jsonObject.getJSONObject("result");
            obj = gson.fromJson(data.toString(), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 描述：将json转化为对象.
     *
     * @param json  获取的数据
     * @param key   对象的key
     * @param clazz 要转化成的对象
     * @return
     */
    public static Object fromJson(String json, String key, Class clazz) {
        Object obj = null;
        try {
            Gson gson = new Gson();
            JSONObject jsonObject = new JSONObject(json);
            JSONObject data = jsonObject.getJSONObject(key);
            obj = gson.fromJson(data.toString(), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 描述：将json转化为对象.
     *
     * @param json
     * @param clazz
     * @return
     */
    public static Object fromJsonMall(String json, String key, Class clazz) {
        Object obj = null;
        try {
            Gson gson = new Gson();
            JSONObject jsonObject = new JSONObject(json);
            JSONObject data = jsonObject.getJSONObject("result");
            JSONObject goods = data.getJSONObject(key);
            obj = gson.fromJson(goods.toString(), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 判断网络请求结果
     *
     * @return
     */
    public static boolean isSuccess(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.optInt("status");
            if (status == 1 || status == 200) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }


    /**
     * 获取网络请求的错误信息
     *
     * @return
     */
    public static String getError(String json) {
        String message = Constant.LOGIN_EXCEPTION;
        try {
            JSONObject jsonObject = new JSONObject(json);
            message = jsonObject.optString("msg");
            return message;
        } catch (Exception e) {

        }
        return message;
    }

    /**
     * 获取字符串
     *
     * @return
     */
    public static Object getContent(String json, String key) {
        Object content = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject data = jsonObject.getJSONObject("result");
            content = data.opt(key);
            return content;
        } catch (Exception e) {

        }
        return content;
    }

    /**
     * 获取字符串
     *
     * @return
     */
    public static String getString(String json, String key) {
        String content = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            content = jsonObject.optString(key);
            return content;
        } catch (Exception e) {

        }
        return content;
    }

    /**
     * 获取int数据
     *
     * @return
     */
    public static int getInteger(String json, String key) {
        int content = 0;
        try {
            JSONObject jsonObject = new JSONObject(json);
            content = jsonObject.optInt(key);
            return content;
        } catch (Exception e) {

        }
        return content;
    }

    /**
     * 将JSON转化为map
     *
     * @String json
     */
    public static HashMap<String, List<String>> toMap(String json) {
        HashMap<String, List<String>> result = null;
        try {
            JSONObject obj = new JSONObject(json);
            JSONObject data = obj.getJSONObject("data");
            JSONObject object = data.getJSONObject("areaJson");
            AbLogUtil.e("object", object.toString());
            result = new HashMap<>();
            Iterator iterator = object.keys();
            String key = null;
            List<String> value = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                AbLogUtil.e("key", key.toString());
                try {
                    JSONArray jsonArray = object.getJSONArray(key);
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    value = gson.fromJson(jsonArray.toString(), type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                result.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }


}

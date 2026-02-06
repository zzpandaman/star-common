package com.star.common.attribute;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 通用 AttributeHolder 接口，保持原方法签名
 * 支持 JSONObject 内部操作，落表 JSON 干净，无对象引用
 */
public interface AttributeHolder {

    /** 获取内部 JSONObject */
    JSONObject getAttributes();

    /** 设置内部 JSONObject */
    void setAttributes(JSONObject attributes);

    /** 默认 ObjectMapper 单例 */
    ObjectMapper DEFAULT_OBJECT_MAPPER = DefaultObjectMapperHolder.INSTANCE;

    /** 静态内部类实现 ObjectMapper 单例 */
    class DefaultObjectMapperHolder {
        static final ObjectMapper INSTANCE = createMapper();

        private static ObjectMapper createMapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
            mapper.disable(SerializationFeature.WRAP_ROOT_VALUE);
            mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper;
        }
    }

    /** 获取初始化 attributes，会替换已存在的 attributes */
    default JSONObject acquireAttributesForWrite() {
        JSONObject attrs = getAttributes();
        if (attrs == null) {
            JSONObject newAttrs = new JSONObject();
            setAttributes(newAttrs);
            return newAttrs;
        }
        return attrs;
    }

    /** 获取或初始化 attributes，不会替换已存在的 attributes */
    default JSONObject acquireAttributes() {
        JSONObject attrs = getAttributes();
        if (attrs == null) {
            return new JSONObject();
        }
        return attrs;
    }

    /** 添加单值属性 */
    default <T> void addAttributes(String key, T value) {
        acquireAttributesForWrite().put(key, DEFAULT_OBJECT_MAPPER.convertValue(value, Object.class));
    }

    /** 解析单值属性 */
    default <T> T parseAttributes(String key, Class<T> clazz) {
        Object val = acquireAttributes().get(key);
        if (val == null) {
            return null;
        }
        return DEFAULT_OBJECT_MAPPER.convertValue(val, clazz);
    }

    /** 解析单值属性，支持 TypeReference */
    default <T> T parseAttributes(String key, TypeReference<T> typeReference) {
        Object val = acquireAttributes().get(key);
        if (val == null) {
            return null;
        }
        return DEFAULT_OBJECT_MAPPER.convertValue(val, typeReference);
    }

    /** 添加单值数组属性，可覆盖或追加 */
    default <T> void addArrayAttributes(String key, T value, boolean isCover, Class<T> clazz) {
        if (value == null) {
            return;
        }
        List<T> existingList = isCover ? null : parseArrayAttributes(key, clazz);
        List<T> list = (existingList == null || existingList.isEmpty())
                ? new ArrayList<>() : new ArrayList<>(existingList);
        list.add(value);
        acquireAttributesForWrite().put(key, DEFAULT_OBJECT_MAPPER.convertValue(list, List.class));
    }

    /** 添加数组属性列表，可覆盖或追加 */
    default <T> void addArrayAttributes(String key, List<T> valueList, boolean isCover, Class<T> clazz) {
        List<T> existingList = isCover ? null : parseArrayAttributes(key, clazz);
        List<T> list = (existingList == null || existingList.isEmpty())
                ? new ArrayList<>() : new ArrayList<>(existingList);
        if (valueList != null) {
            list.addAll(valueList);
        }
        acquireAttributesForWrite().put(key, DEFAULT_OBJECT_MAPPER.convertValue(list, List.class));
    }

    /** 解析数组属性 */
    default <T> List<T> parseArrayAttributes(String key, Class<T> clazz) {
        Object val = acquireAttributes().get(key);
        if (val == null) {
            return new ArrayList<>();
        }
        if (val instanceof List) {
            return DEFAULT_OBJECT_MAPPER.convertValue(val,
                    DEFAULT_OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        }
        return Collections.singletonList(DEFAULT_OBJECT_MAPPER.convertValue(val, clazz));
    }
}

package pers.xiaomuma.framework.serialize;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonUtils {

    public static ObjectMapper OBJECT_MAPPER = ObjectMapperFactory.createDefaultObjectMapper();

    private static final ObjectMapper IGNORE_OBJECT_MAPPER = ObjectMapperFactory.createDefaultObjectMapper();

    public static String object2Json(Object o) {
        StringWriter sw = new StringWriter();
        JsonGenerator gen = null;
        try {
            gen = new JsonFactory().createGenerator(sw);
            OBJECT_MAPPER.writeValue(gen, o);
        } catch (IOException e) {
            throw new RuntimeException("不能序列化对象为Json", e);
        } finally {
            if (null != gen) {
                try {
                    gen.close();
                } catch (IOException e) {
                    throw new RuntimeException("不能序列化对象为Json", e);
                }
            }
        }
        return sw.toString();
    }

    /**
     * @param ignoreFiledNames 要忽略的属性名
     */
    public static String object2Json(Object o, String... ignoreFiledNames) {
        try {
            SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept(ignoreFiledNames);
            FilterProvider filters = new SimpleFilterProvider().addFilter("dynamicFilter", theFilter);
            return IGNORE_OBJECT_MAPPER.writer(filters).writeValueAsString(o);
        } catch (IOException e) {
            throw new RuntimeException("不能序列化对象为Json", e);
        }
    }

    @JsonFilter("dynamicFilter")
    private static class DynamicMixIn {

    }

    public static Map<String, Object> object2Map(Object o) {
        return OBJECT_MAPPER.convertValue(o, Map.class);
    }


    /**
     * 将 json 字段串转换为 对象.
     *
     * @param json  字符串
     * @param clazz 需要转换为的类
     */
    public static <T> T json2Object(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("将 Json 转换为对象时异常,数据是:" + json, e);
        }
    }

    /**
     * 将 json 字段串转换为 List.
     */
    public static <T> List<T> json2List(String json, Class<T> clazz) throws IOException {
        JavaType type = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);

        return OBJECT_MAPPER.readValue(json, type);
    }

    public static <T> Set<T> json2Set(String json, Class<T> clazz) throws IOException {
        JavaType type = OBJECT_MAPPER.getTypeFactory().constructCollectionType(Set.class, clazz);

        return OBJECT_MAPPER.readValue(json, type);
    }

    public static <K,V> Map<K,V> json2Map(String json,  Class<K> keyClazz,Class<V> valueClazz) throws IOException {
        JavaType type = OBJECT_MAPPER.getTypeFactory().constructMapType(Map.class, keyClazz, valueClazz);

        return OBJECT_MAPPER.readValue(json, type);
    }

    public static void main(String[] args) throws IOException {
        String input = "{\n" +
                "  \"openId\": \"OPENID\",\n" +
                "  \"nickName\": \"NICKNAME\",\n" +
                "  \"gender\": 1,\n" +
                "  \"city\": \"CITY\",\n" +
                "  \"province\": \"PROVINCE\",\n" +
                "  \"country\": \"COUNTRY\",\n" +
                "  \"avatarUrl\": \"AVATARURL\",\n" +
                "  \"unionId\": \"UNIONID\",\n" +
                "  \"watermark\": {\n" +
                "    \"appid\":\"APPID\",\n" +
                "    \"timestamp\":\"\"\n" +
                "  }\n" +
                "}";
        Map<String, Object> map = json2Map(input, String.class, Object.class);
        System.out.println(map);
    }


    /**
     * 将 json 字段串转换为 数据.
     */
    public static <T> T[] json2Array(String json, Class<T[]> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(json, clazz);

    }

    public static <T> T node2Object(JsonNode jsonNode, Class<T> clazz) {
        try {
            T t = OBJECT_MAPPER.treeToValue(jsonNode, clazz);
            return t;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("将 Json 转换为对象时异常,数据是:" + jsonNode.toString(), e);
        }
    }

    public static JsonNode object2Node(Object o) {
        try {
            if (o == null) {
                return OBJECT_MAPPER.createObjectNode();
            } else {
                return OBJECT_MAPPER.convertValue(o, JsonNode.class);
            }
        } catch (Exception e) {
            throw new RuntimeException("不能序列化对象为Json", e);
        }
    }

    /**
     * JsonNode转换为Java泛型对象，可以是各种类型。
     *
     * @param json String
     * @param tr   TypeReference,例如: new TypeReference< List<FamousUser> >(){}
     * @return List对象列表
     */
    public static <T> T json2GenericObject(String json, TypeReference<T> tr) {

        if (json == null || "".equals(json)) {
            throw new RuntimeException("将 Json 转换为对象时异常,数据是:" + json);
        } else {
            try {
                return (T) OBJECT_MAPPER.readValue(json, tr);
            } catch (Exception e) {
                throw new RuntimeException("将 Json 转换为对象时异常,数据是:" + json, e);
            }
        }
    }




}

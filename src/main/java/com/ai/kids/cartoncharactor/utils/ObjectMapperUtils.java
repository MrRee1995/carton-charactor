package com.ai.kids.cartoncharactor.utils;

import com.ai.kids.cartoncharactor.exception.UncheckedJsonProcessingException;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.Map;

public final class ObjectMapperUtils {
    private static final String EMPTY_JSON = "{}";
    private static final String EMPTY_ARRAY_JSON = "[]";
    private static final ObjectMapper MAPPER;

    public static String toPrettyJSON(@Nullable Object obj) {
        if (obj == null) {
            return null;
        } else {
            try {
                return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                throw warpException(e);
            }
        }
    }

    public static String toJSON(@Nullable Object obj) {
        if (obj == null) {
            return null;
        } else {
            try {
                return MAPPER.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                throw warpException(e);
            }
        }
    }

    public static void toJSON(@Nullable Object obj, OutputStream writer) {
        if (obj != null) {
            try {
                MAPPER.writeValue(writer, obj);
            } catch (IOException e) {
                throw warpException(e);
            }
        }
    }

    public static <T> T fromJSON(InputStream inputStream, Class<T> valueType) {
        try {
            return MAPPER.readValue(inputStream, valueType);
        } catch (IOException e) {
            throw warpException(e);
        }
    }

    public static <T> T fromJSON(@Nullable byte[] bytes, Class<T> valueType) {
        if (bytes == null) {
            return null;
        } else {
            try {
                return MAPPER.readValue(bytes, valueType);
            } catch (IOException e) {
                throw warpException(e);
            }
        }
    }

    public static <T> T fromJSON(@Nullable String json, Class<T> valueType) {
        if (json == null) {
            return null;
        } else {
            try {
                return MAPPER.readValue(json, valueType);
            } catch (IOException e) {
                throw warpException(e);
            }
        }
    }

    public static <T> T fromJSON(Object value, Class<T> valueType) {
        if (value == null) {
            return null;
        } else if (value instanceof String) {
            return fromJSON((String) value, valueType);
        } else {
            return value instanceof byte[] ? fromJSON((byte[]) value, valueType) : null;
        }
    }

    public static <E, T extends Collection<E>> T fromJSON(String json, Class<? extends  Collection> collectionType, Class<E> valueType) {
        if (StringUtils.isEmpty(json)) {
            json = EMPTY_ARRAY_JSON;
        }

        try {
            return MAPPER.readValue(json, TypeFactory.defaultInstance().constructCollectionType(collectionType, valueType));
        } catch (IOException e) {
            throw warpException(e);
        }
    }

    public static <E, T extends Collection<E>> T fromJSON(byte[] bytes, Class<? extends  Collection> collectionType, Class<E> valueType) {
        try {
            return MAPPER.readValue(bytes, TypeFactory.defaultInstance().constructCollectionType(collectionType, valueType));
        } catch (IOException e) {
            throw warpException(e);
        }
    }

    public static <E, T extends Collection<E>> T fromJSON(InputStream inputStream, Class<? extends  Collection> collectionType, Class<E> valueType) {
        try {
            return MAPPER.readValue(inputStream, TypeFactory.defaultInstance().constructCollectionType(collectionType, valueType));
        } catch (IOException e) {
            throw warpException(e);
        }
    }

    public static <K, V, T extends Map<K, V>> T fromJSON(String json, Class<? extends Map> mapType, Class<K> keyType, Class<V> valueType) {
        if (StringUtils.isEmpty(json)) {
            json = EMPTY_JSON;
        }

        try {
            return MAPPER.readValue(json, TypeFactory.defaultInstance().constructMapType(mapType, keyType, valueType));
        } catch (IOException e) {
            throw warpException(e);
        }
    }

    public static <K, V, T extends Map<K, V>> T fromJSON(byte[] bytes, Class<? extends Map> mapType, Class<K> keyType, Class<V> valueType) {
        try {
            return MAPPER.readValue(bytes, TypeFactory.defaultInstance().constructMapType(mapType, keyType, valueType));
        } catch (IOException e) {
            throw warpException(e);
        }
    }

    public static <K, V, T extends Map<K, V>> T fromJSON(InputStream inputStream, Class<? extends Map> mapType, Class<K> keyType, Class<V> valueType) {
        try {
            return MAPPER.readValue(inputStream, TypeFactory.defaultInstance().constructMapType(mapType, keyType, valueType));
        } catch (IOException e) {
            throw warpException(e);
        }
    }

    public static <T> T value(Object rawValue, Class<T> type) {
        return MAPPER.convertValue(rawValue, type);
    }

    public static <T> T value(Object rawValue, TypeReference<T> type) {
        return MAPPER.convertValue(rawValue, type);
    }

    public static <T> T value(Object rawValue, JavaType type) {
        return MAPPER.convertValue(rawValue, type);
    }

    public static <T> T unwrapJsonP(String raw, Class<T> type) {
        return fromJSON(unwrapJsonP(raw), type);
    }

    public static String unwrapJsonP(String raw) {
        raw = StringUtils.trim(raw);
        raw = StringUtils.removeEnd(raw, ";");
        raw = raw.substring(raw.indexOf(40) + 1);
        raw = raw.substring(0, raw.lastIndexOf(41));
        raw = StringUtils.trim(raw);
        return raw;
    }

    public static <T> T update(T rawValue, String newProperty) {
        try {
            return MAPPER.readerForUpdating(rawValue).readValue(newProperty);
        } catch (IOException e) {
            throw warpException(e);
        }
    }

    public static boolean isJSON(String jsonStr) {
        if (StringUtils.isBlank(jsonStr)) {
            return false;
        } else {
            try {
                JsonParser parser = (new ObjectMapper().getFactory().createParser(jsonStr));
                Throwable th = null;

                try {
                    while (parser.nextToken() != null) {
                    }
                    return true;
                } catch (Throwable throwable) {
                    th = throwable;
                    throw th;
                } finally {
                    if (parser != null) {
                        if (th != null) {
                            try {
                                parser.close();
                            } catch (Throwable throwable) {
                                th.addSuppressed(throwable);
                            }
                        } else  {
                            parser.close();
                        }
                    }
                }
            } catch (Throwable e) {
                return false;
            }
        }
    }

    public static boolean isBadJSON(String jsonStr) {
        return !isJSON(jsonStr);
    }

    private static RuntimeException warpException(IOException e) {
        return e instanceof JsonProcessingException ? new UncheckedJsonProcessingException((JsonProcessingException)e) : new UncheckedIOException(e);
    }

    static {
        MAPPER = (new ObjectMapper((new JsonFactory()).disable(JsonFactory.Feature.INTERN_FIELD_NAMES)));
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        MAPPER.enable(JsonParser.Feature.ALLOW_COMMENTS);
        MAPPER.registerModule(new ParameterNamesModule());
        MAPPER.registerModule(new GuavaModule());
    }
    public ObjectMapperUtils() {
    }

}

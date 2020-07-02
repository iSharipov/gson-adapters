package io.github.isharipov.gson.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Arrays;

public class PolymorphDeserializer<T> implements JsonDeserializer<T> {

    @Override
    public T deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        try {
            Class<?> typeClass = Class.forName(type.getTypeName());
            JsonType jsonType = typeClass.getDeclaredAnnotation(JsonType.class);
            String property = json.getAsJsonObject().get(jsonType.property()).getAsString();
            JsonSubtype[] subtypes = jsonType.subtypes();
            Type subType = Arrays.stream(subtypes).filter(subtype -> subtype.name().equals(property)).findFirst().orElseThrow(IllegalArgumentException::new).clazz();
            return context.deserialize(json, subType);
        } catch (Exception e) {
            throw new JsonParseException("Failed deserialize json", e);
        }
    }
}

package org.codehaus.mojo.license.fetchlicenses.repository.json;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class JsonMappingRuleParser {

    public JsonMappingRule parseMappingRule(String json) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(JsonMappingRule.class, new MappingRuleTypeAdapter());
        return builder.create().fromJson(json, JsonMappingRule.class);
    }

    private static class MappingRuleTypeAdapter implements JsonSerializer<JsonMappingRule>, JsonDeserializer<JsonMappingRule> {
        public JsonElement serialize(JsonMappingRule src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            JsonElement serialize = context.serialize(src.versions());
            result.add("versions", serialize);
            for (Pointer pointer : src.pointers()) {
                result.addProperty(pointer.name(), pointer.path());
            }
            return result;
        }

        public JsonMappingRule deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();

            JsonArray versionsArray = object.getAsJsonArray("versions");
            Type setOfStrings = new TypeToken<Set<String>>(){}.getType();
            Set<String> versions = context.deserialize(versionsArray, setOfStrings);
            object.remove("versions");

            Set<Map.Entry<String, JsonElement>> entries = object.entrySet();

            Set<Pointer> pointers = new LinkedHashSet<Pointer>();
            for (Map.Entry<String, JsonElement> entry : entries) {
                JsonElement value = entry.getValue();

                String path = value.getAsJsonPrimitive().getAsString();
                pointers.add(new BasePointer(entry.getKey(), path));
            }
            return new JsonMappingRule(versions, pointers);
        }
    }
}
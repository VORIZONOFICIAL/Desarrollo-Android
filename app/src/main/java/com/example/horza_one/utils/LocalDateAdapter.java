package com.example.horza_one.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Adaptador para Gson que maneja la serialización/deserialización de fechas en formato String
 * Compatible con todas las versiones de Android
 */
public class LocalDateAdapter implements JsonDeserializer<String>, JsonSerializer<String> {

    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) 
            throws JsonParseException {
        // Simplemente devuelve el String tal como viene del JSON
        return json.getAsString();
    }

    @Override
    public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
        // Serializa el String tal como está
        return new JsonPrimitive(src);
    }
}

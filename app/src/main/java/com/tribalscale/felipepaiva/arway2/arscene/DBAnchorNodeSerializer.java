package com.tribalscale.felipepaiva.arway2.arscene;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

class DBAnchorNodeSerializer implements JsonSerializer<DBAnchorNode> {
    @Override
    public JsonElement serialize(DBAnchorNode src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("title", src.getTitle());

        return jsonObject;
    }
}
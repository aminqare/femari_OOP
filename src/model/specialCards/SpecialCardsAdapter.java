package model.specialCards;

import com.google.gson.*;

import java.lang.reflect.Type;

public class SpecialCardsAdapter implements JsonSerializer<specialCards>, JsonDeserializer<specialCards> {

    @Override
    public JsonElement serialize(specialCards src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", src.getClass().getSimpleName());
        jsonObject.add("data", context.serialize(src, src.getClass()));
        return jsonObject;
    }

    @Override
    public specialCards deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement typeElement = jsonObject.get("type");
        JsonElement dataElement = jsonObject.get("data");

        if (typeElement == null || dataElement == null) {
            throw new JsonParseException("Invalid specialCards JSON: missing type or data field");
        }

        String type = typeElement.getAsString();
        try {
            return context.deserialize(dataElement, Class.forName("model.specialCards." + type));
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Unknown element type: " + type, e);
        }
    }


}

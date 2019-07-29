package br.com.beblue.shared;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.Collection;
import java.util.List;

public class JsonConverter {

    private static final ObjectMapper MAPPER = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    private static final TypeFactory TYPE_FACTORY = MAPPER.getTypeFactory();

    public static String asClassToJson(Object object) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(object);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T asJsonToClass(final String json, Class<T> classe) {
        try {
            return MAPPER.readValue(json, TYPE_FACTORY.constructType(classe));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> asJsonToClassList(final String json, Class<T> classe) {
        try {
            return MAPPER.readValue(json, TYPE_FACTORY.constructCollectionType(List.class, classe));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> asJsonPageableToListOfClass(final String json, Class<T> classe) {
        try {
            JsonNode page = MAPPER.readTree(json);
            return MAPPER.readValue(page.get("content").toString(), TYPE_FACTORY.constructCollectionType(Collection.class, classe));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String asXmlToJson(String xml) {
        try {
            JSONObject jsonObject = XML.toJSONObject(xml);
            return jsonObject.toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}

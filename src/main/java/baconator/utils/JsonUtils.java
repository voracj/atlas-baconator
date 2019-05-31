package baconator.utils;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import baconator.model.BaconatorData;

public class JsonUtils {
	private static final ObjectMapper mapper = new ObjectMapper();

	private JsonUtils() {
	}

	public static List<String> readList(String json) {
		try {
			return mapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, String.class));
		} catch (IOException e) {
			throw new BaconatorException(e.getMessage(), e);
		}
	}

	public static BaconatorData readBaconatorData(String json) {
		try {
			return mapper.readValue(json, BaconatorData.class);
		} catch (IOException e) {
			throw new BaconatorException(e.getMessage(), e);
		}
	}

	public static String writeValueAsString(Object o) {
		try {
			return mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new BaconatorException(e.getMessage(), e);
		}
	}
}

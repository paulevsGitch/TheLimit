package paulevs.thelimit.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonUtil {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	public static JsonObject readJson(File file) {
		if (!file.exists()) return new JsonObject();
		try {
			return readJson(new FileInputStream(file));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return new JsonObject();
		}
	}
	
	public static JsonObject readJson(String resource) {
		return readJson(JsonUtil.class.getResourceAsStream(resource));
	}
	
	public static JsonObject readJson(InputStream stream) {
		JsonObject result;
		try {
			InputStreamReader reader = new InputStreamReader(stream);
			result = GSON.fromJson(reader, JsonObject.class);
			reader.close();
			stream.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			result = new JsonObject();
		}
		return result;
	}
	
	public static void writeJson(File file, JsonObject obj) {
		file.getParentFile().mkdirs();
		try {
			FileWriter fileWriter = new FileWriter(file);
			JsonWriter jsonWriter = GSON.newJsonWriter(fileWriter);
			jsonWriter.setIndent("\t");
			GSON.toJson(obj, jsonWriter);
			jsonWriter.flush();
			jsonWriter.close();
			fileWriter.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

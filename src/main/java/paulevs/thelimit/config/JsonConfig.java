package paulevs.thelimit.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.util.function.Supplier;

public class JsonConfig {
	private final File file;
	private boolean requireSave;
	private final JsonObject json;
	
	public JsonConfig(String name) {
		this.file = new File(FabricLoader.getInstance().getConfigDir().toString() + "/thelimit/" + name + ".json");
		this.requireSave = !file.exists();
		this.json = JsonUtil.readJson(file);
	}
	
	public void save() {
		if (!requireSave) return;
		JsonUtil.writeJson(file, json);
	}
	
	private JsonElement get(String path, Supplier<JsonElement> def) {
		String[] parts = path.split("\\.");
		JsonElement element = json;
		int last = parts.length - 1;
		for (int i = 0; i < parts.length; i++) {
			String name = parts[i];
			if (!element.getAsJsonObject().has(name)) {
				requireSave = true;
				element.getAsJsonObject().add(name, i == last ? def.get() : new JsonObject());
			}
			element = element.getAsJsonObject().get(name);
		}
		return element;
	}
	
	private void set(String path, JsonElement value) {
		String[] parts = path.split("\\.");
		JsonElement element = json;
		int last = parts.length - 1;
		for (int i = 0; i < last; i++) {
			String name = parts[i];
			if (!element.getAsJsonObject().has(name)) {
				element.getAsJsonObject().add(name, new JsonObject());
			}
			element = element.getAsJsonObject().get(name);
		}
		requireSave = true;
		element.getAsJsonObject().add(parts[last], value);
	}
	
	public int getInt(String path, int def) {
		return get(path, () -> new JsonPrimitive(def)).getAsInt();
	}
	
	public void setInt(String path, int value) {
		set(path, new JsonPrimitive(value));
	}
	
	public float getFloat(String path, float def) {
		return get(path, () -> new JsonPrimitive(def)).getAsFloat();
	}
	
	public void setFloat(String path, float value) {
		set(path, new JsonPrimitive(value));
	}
}
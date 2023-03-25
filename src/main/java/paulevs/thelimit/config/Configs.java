package paulevs.thelimit.config;

import java.util.ArrayList;
import java.util.List;

public class Configs {
	private static final List<JsonConfig> CONFIGS = new ArrayList<>();
	
	public static final JsonConfig GENERATOR = make("generator");
	
	public static void saveAll() {
		CONFIGS.forEach(JsonConfig::save);
	}
	
	private static JsonConfig make(String name) {
		JsonConfig config = new JsonConfig(name);
		CONFIGS.add(config);
		return config;
	}
}

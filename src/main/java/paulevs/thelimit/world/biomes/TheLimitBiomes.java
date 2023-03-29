package paulevs.thelimit.world.biomes;

import java.util.ArrayList;
import java.util.List;

public class TheLimitBiomes {
	public static final List<TheLimitBiome> BIOMES = new ArrayList<>();
	
	public static final TheLimitBiome STELLATA_FOREST = add(new StellataForestBiome());
	public static final TheLimitBiome VOID_LAKE = add(new VoidLakeBiome());
	
	private static <T extends TheLimitBiome> T add(T biome) {
		BIOMES.add(biome);
		return biome;
	}
}

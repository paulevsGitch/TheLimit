package paulevs.thelimit.world.dimension;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.dimension.DimensionData;
import net.minecraft.level.gen.BiomeSource;
import paulevs.thelimit.world.biomes.TheLimitBiomes;

import java.util.Arrays;

public class TheLimitBiomeSource extends BiomeSource {
	private final BiomeMap map;
	
	public TheLimitBiomeSource(long seed, DimensionData data) {
		this.map = new BiomeMap(TheLimitBiomes.BIOMES, seed, 100, data);
	}
	
	@Override
	public Biome getBiome(int x, int z) {
		return map.getBiome(x, z);
	}
	
	@Override
	public Biome[] getBiomes(Biome[] biomes, int x, int z, int dx, int dz) {
		if (biomes == null || biomes.length < dx * dz) {
			biomes = new Biome[dx * dz];
		}
		
		int index = 0;
		for (int i = 0; i < dx; i++) {
			for (int j = 0; j < dz; j++) {
				biomes[index++] = map.getBiome(x + i, z + j);
			}
		}
		
		return biomes;
	}
	
	@Override
	@Environment(value= EnvType.CLIENT)
	public double getTemperature(int x, int z) {
		return 1.0;
	}
	
	@Override
	public double[] getTemperatures(double[] temperatures, int x, int z, int dx, int dz) {
		if (temperatures == null || temperatures.length < dx * dz) {
			temperatures = new double[dx * dz];
		}
		
		Arrays.fill(temperatures, 1.0);
		
		return temperatures;
	}
}

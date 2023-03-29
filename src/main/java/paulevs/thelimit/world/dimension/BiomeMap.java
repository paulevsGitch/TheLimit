package paulevs.thelimit.world.dimension;

import paulevs.thelimit.noise.PerlinNoise;
import paulevs.thelimit.world.biomes.TheLimitBiome;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BiomeMap {
	private final Map<Long, byte[]> biomes = new HashMap<>();
	private final List<TheLimitBiome> dictionary;
	private final Random random = new Random(0);
	private final PerlinNoise[] noises;
	private final int layers;
	private final int size;
	
	public BiomeMap(List<TheLimitBiome> dictionary, long seed, int size) {
		this.dictionary = dictionary;
		this.size = size;
		
		int layers = (int) Math.round(Math.log(size) / Math.log(2));
		if (layers < 1) layers = 1;
		this.layers = layers;
		
		random.setSeed(seed);
		random.nextLong();
		noises = new PerlinNoise[4];
		for (int i = 0; i < noises.length; i++) {
			noises[i] = new PerlinNoise(random.nextInt());
		}
	}
	
	public TheLimitBiome getBiome(int x, int z) {
		float dx = 0;
		float dz = 0;
		
		double px = (double) x / size;
		double pz = (double) z / size;
		
		for (int i = 1; i <= layers; i++) {
			float size = 1.5F / i;
			double size2 = 0.5 * i;
			dx += noises[i & 3].get(px * size2, pz * size2) * size;
			dz += noises[(i + 1) & 3].get(px * size2, pz * size2) * size;
		}
		
		px += dx;
		pz += dz;
		
		int cx = (int) Math.floor(px / 64.0);
		int cz = (int) Math.floor(pz / 64.0);
		
		x = (int) Math.floor(px) & 63;
		z = (int) Math.floor(pz) & 63;
		
		long chunkPos = (long) cx << 32L | (long) cz & 0xFFFFFFFFL;
		byte[] chunk = biomes.computeIfAbsent(chunkPos, key -> {
			random.setSeed(key);
			random.nextLong();
			byte[] data = new byte[4096];
			for (int i = 0; i < data.length; i++) {
				data[i] = (byte) random.nextInt(dictionary.size());
			}
			return data;
		});
		
		int index = (x & 63) << 6 | (z & 63);
		index = chunk[index];
		return dictionary.get(index);
	}
}

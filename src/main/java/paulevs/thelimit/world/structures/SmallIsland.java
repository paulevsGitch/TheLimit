package paulevs.thelimit.world.structures;

import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.structure.Structure;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import paulevs.thelimit.blocks.TLBlocks;
import paulevs.thelimit.noise.PerlinNoise;
import paulevs.thelimit.world.biomes.TheLimitBiome;

import java.util.Random;

public class SmallIsland extends Structure {
	private final PerlinNoise noise = new PerlinNoise(12);
	private final PerlinNoise noiseX = new PerlinNoise(15);
	private final PerlinNoise noiseZ = new PerlinNoise(17);
	
	@Override
	public boolean generate(Level level, Random random, int x, int y, int z) {
		float radius = random.nextFloat() * 3 + 3;
		int height = random.nextInt(20) + 10;
		int height2 = random.nextInt(5) + 5;
		fillCone(level, 0, radius, height, x, y - height, z);
		fillCone(level, radius, 0, height2, x, y, z);
		TheLimitBiome biome = (TheLimitBiome) level.dimension.biomeSource.getBiome(x, z);
		cover(level, random, (int) (radius + 4), height2, x, y, z, biome);
		populate(level, random, x, z, biome);
		return true;
	}
	
	private void fillCone(Level level, float radius1, float radius2, int height, int x, int y, int z) {
		BlockState filler = TLBlocks.GLAUCOLIT.getDefaultState();
		float intensity = Math.max(radius1, radius2) / 50F * 20;
		for (int i = 0; i < height; i++) {
			float r = MathHelper.lerp((float) i / height, radius1, radius2);
			int dr = (int) (r + 3);
			r *= r;
			int py = y + i;
			for (int dx = -dr; dx <= dr; dx++) {
				int px = x + dx;
				for (int dz = -dr; dz <= dr; dz++) {
					int pz = z + dz;
					
					float x2 = dx + noiseX.get(px * 0.3, py * 0.3, pz * 0.3) * 3;
					float z2 = dz + noiseZ.get(px * 0.3, py * 0.3, pz * 0.3) * 3;
					x2 *= x2;
					z2 *= z2;
					
					if (x2 + z2 < r - noise.get(px * 0.1, py * 0.1, pz * 0.1) * intensity) {
						level.setBlockState(px, py, pz, filler);
					}
				}
			}
		}
	}
	
	private void cover(Level level, Random random, int radius, int height, int x, int y, int z, TheLimitBiome biome) {
		for (int dx = -radius; dx <= radius; dx++) {
			int px = x + dx;
			for (int dz = -radius; dz <= radius; dz++) {
				int pz = z + dz;
				for (int dy = 0; dy < height; dy++) {
					int py = y + dy;
					if (!level.getBlockState(px, py, pz).isOf(TLBlocks.GLAUCOLIT)) continue;
					if (level.getBlockState(px, py + 1, pz).isOf(TLBlocks.GLAUCOLIT)) continue;
					BlockState state = biome.getGround(level, random, px, py, pz);
					if (state == null) continue;
					level.setBlockState(px, py, pz, state);
					break;
				}
			}
		}
	}
	
	private void populate(Level level, Random random, int x, int z, TheLimitBiome biome) {
		Chunk chunk = level.getChunkFromCache(x >> 4, z >> 4);
		biome.populate(level, chunk, random, x & 0xFFFFFFF0, z & 0xFFFFFFF0);
	}
}

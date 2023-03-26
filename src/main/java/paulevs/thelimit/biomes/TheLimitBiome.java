package paulevs.thelimit.biomes;

import net.minecraft.level.Level;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.chunk.Chunk;
import net.modificationstation.stationapi.api.block.BlockState;
import paulevs.thelimit.structures.StructurePlacer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class TheLimitBiome extends Biome {
	private final List<StructurePlacer> placers = new ArrayList<>();
	
	protected void addStructure(StructurePlacer placer) {
		placers.add(placer);
	}
	
	public void populate(Level level, Chunk chunk, Random random, int wx, int wz) {
		placers.forEach(placer -> placer.place(level, chunk, random, wx, wz));
	}
	
	public abstract BlockState getGround(Level level, Random random, int x, int y, int z);
}

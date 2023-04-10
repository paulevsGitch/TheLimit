package paulevs.thelimit.world.dimension;

import net.minecraft.level.Level;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.source.LevelSource;
import net.minecraft.util.ProgressListener;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.BlockPos;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import net.modificationstation.stationapi.impl.level.chunk.ChunkSection;
import net.modificationstation.stationapi.impl.level.chunk.StationFlatteningChunk;
import paulevs.thelimit.blocks.TLBlocks;
import paulevs.thelimit.noise.PerlinNoise;
import paulevs.thelimit.world.biomes.TheLimitBiome;
import paulevs.thelimit.world.structures.TheLimitStructures;

import java.util.Random;
import java.util.stream.IntStream;

public class TheLimitWorldgen implements LevelSource {
	private final IslandLayer layer1;
	private final IslandLayer layer2;
	private final IslandLayer layer3;
	
	private final Biome[] biomes = new Biome[256];
	private final InterpolationCell cell1;
	private final InterpolationCell cell2;
	private final Level level;
	
	public TheLimitWorldgen(Level level) {
		this.level = level;
		cell1 = new InterpolationCell(this::getDensity, 16, 0);
		cell2 = new InterpolationCell(this::getDensity, 16, 8);
		Random random = new Random(level.getSeed());
		layer1 = new IslandLayer(random.nextInt(), 120, 200, 1.0F);
		layer2 = new IslandLayer(random.nextInt(), 80, 200, 0.75F);
		layer3 = new IslandLayer(random.nextInt(), 160, 200, 0.75F);
		TheLimitStructures.updateDensity(level.getSeed());
	}
	
	@Override
	public boolean isChunkLoaded(int x, int z) {
		return true;
	}
	
	@Override
	public Chunk getChunk(int x, int z) {
		return generateChunk(x, z);
	}
	
	@Override
	public Chunk loadChunk(int x, int z) {
		return generateChunk(x, z);
	}
	
	@Override
	public void decorate(LevelSource level, int cx, int cz) {
		final int wx = cx << 4;
		final int wz = cz << 4;
		Chunk chunk = this.level.getChunkFromCache(cx, cz);
		
		random.setSeed(MathHelper.hashCode(cx, (int) this.level.getSeed(), cz));
		
		TheLimitStructures.SMALL_ISLAND_PLACER.place(this.level, chunk, random, wx, wz);
		
		for (short i = 0; i < 256; i++) {
			int px = i >> 4;
			int pz = i & 15;
			
			TheLimitBiome biome = (TheLimitBiome) this.level.dimension.biomeSource.getBiome(px | wx, pz | wz);
			BlockState terrain = chunk.getBlockState(px, 0, pz);
			int height = chunk.getHeight(px, pz);
			
			for (short py = 0; py <= height; py++) {
				BlockState above = chunk.getBlockState(px, py + 1, pz);
				if (!TLBlocks.isStone(terrain) || TLBlocks.isStone(above)) {
					terrain = above;
					continue;
				}
				terrain = above;
				BlockState state = biome.getGround(this.level, random, px | wx, py, pz | wz);
				if (state == null) continue;
				chunk.setBlockState(px, py, pz, state);
			}
		}
		
		TheLimitBiome biome = (TheLimitBiome) this.level.dimension.biomeSource.getBiome(8 | wx, 8 | wz);
		biome.populate(this.level, chunk, random, wx, wz);
	}
	
	@Override
	public boolean deleteCacheCauseClientCantHandleThis(boolean bl, ProgressListener arg) {
		return true;
	}
	
	@Override
	public boolean method_1801() {
		return false;
	}
	
	@Override
	public boolean method_1805() {
		return true;
	}
	
	@Override
	public String toString() {
		return "TheLimitWorldgen";
	}
	
	final PerlinNoise terrainNoise = new PerlinNoise(0);
	final Random random = new Random(0);
	
	private Chunk generateChunk(int x, int z) {
		StationFlatteningChunk chunk = new StationFlatteningChunk(level, x, z);
		BlockState glaucolit = TLBlocks.GLAUCOLIT.getDefaultState();
		BlockState vitilit = TLBlocks.VITILIT.getDefaultState();
		ChunkSection[] sections = chunk.sections;
		
		final int wx = x << 4;
		final int wz = z << 4;
		Biome[] biomes = new Biome[256];
		this.level.dimension.biomeSource.getBiomes(biomes, wx, wz, 16, 16);
		
		cell1.update(wx, wz);
		cell2.update(wx, wz);
		random.setSeed(MathHelper.hashCode(x, (int) this.level.getSeed(), z));
		IntStream.range(0, sections.length).forEach(index -> {
			ChunkSection section = new ChunkSection(index);
			sections[index] = section;
			int wy = index << 4;
			for (int i = 0; i < 4096; i++) {
				int dx = (i >> 4) & 15;
				int dy = i >> 8;
				int dz = i & 15;
				int py = dy | wy;
				float density = MathHelper.lerp(0.5F, cell1.get(dx, py, dz), cell2.get(dx, py, dz));
				if (density < 0.5F) continue;
				TheLimitBiome biome = (TheLimitBiome) biomes[i & 255];
				section.setBlockState(dx, dy, dz, biome.getFiller(this.level, random, x | wx, py, z | wz));
			}
		});
		
		chunk.generateHeightmap();
		
		// Custom unpopulated chunk mark
		// Used to solve cascade worldgen bug with custom populator
		//sections[0].setBlockState(0, 0, 0, BlockBase.BEDROCK.getDefaultState());
		//chunk.decorated = true;
		
		return chunk;
	}
	
	private float getDensity(BlockPos pos) {
		float density = layer1.getDensity(pos);
		if (density > 0.55F) return density;
		
		density = Math.max(density, layer2.getDensity(pos));
		if (density > 0.55F) return density;
		
		density = Math.max(density, layer3.getDensity(pos));
		return density;
	}
}

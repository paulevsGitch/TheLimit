package paulevs.thelimit.dimension;

import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.source.LevelSource;
import net.minecraft.util.ProgressListener;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.BlockPos;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import net.modificationstation.stationapi.impl.level.chunk.ChunkSection;
import net.modificationstation.stationapi.impl.level.chunk.StationFlatteningChunk;
import paulevs.thelimit.biomes.TheLimitBiomes;
import paulevs.thelimit.blocks.TheLimitBlocks;
import paulevs.thelimit.noise.PerlinNoise;
import paulevs.thelimit.structures.TheLimitStructures;

import java.util.Random;
import java.util.stream.IntStream;

public class TheLimitWorldgen implements LevelSource {
	public static TheLimitWorldgen instance;
	
	private final IslandLayer layer1;
	private final IslandLayer layer2;
	private final IslandLayer layer3;
	
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
		instance = this;
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
		/*if (!level.isChunkLoaded(cx - 1, cz)) return;
		if (!level.isChunkLoaded(cx + 1, cz)) return;
		if (!level.isChunkLoaded(cx, cz - 1)) return;
		if (!level.isChunkLoaded(cx, cz + 1)) return;*/
		
		final int wx = cx << 4;
		final int wz = cz << 4;
		Chunk chunk = this.level.getChunkFromCache(cx, cz);
		
		random.setSeed(MathHelper.hashCode(cx, (int) this.level.getSeed(), cz));
		
		TheLimitStructures.SMALL_ISLAND_PLACER.place(this.level, chunk, random, wx, wz);
		
		for (short i = 0; i < 256; i++) {
			int px = i & 15;
			int pz = i >> 4;
			for (short py = 0; py < 255; py++) {
				BlockState state = chunk.getBlockState(px, py, pz);
				if (!state.isOf(TheLimitBlocks.GLAUCOLIT)) continue;
				if (chunk.getBlockState(px, py + 1, pz).isOf(TheLimitBlocks.GLAUCOLIT)) continue;
				state = TheLimitBiomes.STELLATA_FOREST.getGround(this.level, random, px | wx, py, pz | wz);
				chunk.setBlockState(px, py, pz, state);
			}
		}
		
		TheLimitBiomes.STELLATA_FOREST.populate(this.level, chunk, random, wx, wz);
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
		BlockState glaucolit = TheLimitBlocks.GLAUCOLIT.getDefaultState();
		BlockState vitilit = TheLimitBlocks.VITILIT.getDefaultState();
		ChunkSection[] sections = chunk.sections;
		
		final int wx = x << 4;
		final int wz = z << 4;
		cell1.update(wx, wz);
		cell2.update(wx, wz);
		IntStream.range(0, sections.length).forEach(index -> {
			ChunkSection section = new ChunkSection(index);
			sections[index] = section;
			int wy = index << 4;
			for (int i = 0; i < 4096; i++) {
				int dx = i & 15;
				int dy = (i >> 4) & 15;
				int dz = i >> 8;
				int py = dy | wy;
				float density = MathHelper.lerp(0.5F, cell1.get(dx, py, dz), cell2.get(dx, py, dz));
				if (density < 0.5F) continue;
				float h = terrainNoise.get((dx | wx) * 0.1, py * 0.1, (dz | wz) * 0.1) * 10 + 5;
				section.setBlockState(dx, dy, dz, py < h ? vitilit : glaucolit);
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

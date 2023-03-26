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
import paulevs.thelimit.blocks.TheLimitBlocks;
import paulevs.thelimit.noise.PerlinNoise;
import paulevs.thelimit.structures.TheLimitStructures;

import java.util.Random;
import java.util.stream.IntStream;

public class TheLimitWorldgen implements LevelSource {
	private final IslandLayer layer = new IslandLayer(0, 120, 200, 1.0F);
	private final IslandLayer layer2 = new IslandLayer(1, 80, 200, 0.75F);
	private final IslandLayer layer3 = new IslandLayer(2, 160, 200, 0.75F);
	
	private final InterpolationCell cell1;
	private final InterpolationCell cell2;
	private final Level level;
	
	public TheLimitWorldgen(Level level) {
		this.level = level;
		cell1 = new InterpolationCell(this::getDensity, 16, 0);
		cell2 = new InterpolationCell(this::getDensity, 16, 8);
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
		// Biome populator here!
		final int wx = cx << 4;
		final int wz = cz << 4;
		Chunk chunk = level.getChunk(cx, cz);
		
		BlockState hyphum = TheLimitBlocks.HYPHUM.getDefaultState();
		
		random.setSeed(System.nanoTime());
		
		for (short i = 0; i < 256; i++) {
			int px = i & 15;
			int pz = i >> 4;
			for (short py = 0; py < 255; py++) {
				BlockState state = chunk.getBlockState(px, py, pz);
				if (state.isOf(TheLimitBlocks.GLAUCOLIT)) {
					if (!chunk.getBlockState(px, py + 1, pz).isOf(TheLimitBlocks.GLAUCOLIT)) {
						chunk.setBlockState(px, py, pz, hyphum);
					}
				}
			}
		}
		
		for (byte i = 0; i < 3; i++) {
			int x = random.nextInt(16);
			int z = random.nextInt(16);
			int y = chunk.getHeight(x, z);
			TheLimitStructures.STELLATA_TREE.generate(this.level, random, x | wx, y, z | wz);
		}
		
		int x = random.nextInt(16);
		int z = random.nextInt(16);
		int y = chunk.getHeight(x, z);
		TheLimitStructures.STELLATA_TREE_SMALL.generate(this.level, random, x | wx, y, z | wz);
		
		for (byte i = 0; i < 2; i++) {
			x = random.nextInt(16);
			z = random.nextInt(16);
			y = chunk.getHeight(x, z);
			TheLimitStructures.GUTTARBA.generate(this.level, random, x | wx, y, z | wz);
		}
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
		return chunk;
	}
	
	private float getDensity(BlockPos pos) {
		float density = layer.getDensity(pos);
		if (density > 0.55F) return density;
		
		density = Math.max(density, layer2.getDensity(pos));
		if (density > 0.55F) return density;
		
		density = Math.max(density, layer3.getDensity(pos));
		return density;
	}
}

package paulevs.thelimit.dimension;

import net.minecraft.block.BlockBase;
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
import paulevs.thelimit.noise.VoronoiNoise;

import java.util.Random;
import java.util.stream.IntStream;

public class TheLimitWorldgen implements LevelSource {
	private final InterpolationCell cell1;
	//private final InterpolationCell cell2;
	private final Level level;
	
	public TheLimitWorldgen(Level level) {
		this.level = level;
		cell1 = new InterpolationCell(
			this::getDensity,
			16 / 8 + 1,
			256 / 8 + 1,
			8, 8
		);
		/*cell2 = new InterpolationCell(
			this::getDensity,
			16 / 8 + 1,
			256 / 8 + 1,
			8, 8
		);*/
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
		// final int wx = cx << 4;
		// final int wz = cz << 4;
		Chunk chunk = level.getChunk(cx, cz);
		
		BlockState hyphum = TheLimitBlocks.HYPHUM.getDefaultState();
		//BlockState vitilit = TheLimitBlocks.VITILIT.getDefaultState();
		
		for (short i = 0; i < 256; i++) {
			int px = i & 15;
			int pz = i >> 4;
			for (short py = 0; py < 255; py++) {
				BlockState state = chunk.getBlockState(px, py, pz);
				if (state.isOf(TheLimitBlocks.GLAUCOLIT)) {
					if (chunk.getBlockState(px, py + 1, pz).isAir()) {
						chunk.setBlockState(px, py, pz, hyphum);
					}
					/*else if (chunk.getBlockState(px, py - 3, pz).isAir()) {
						chunk.setBlockState(px, py, pz, vitilit);
					}*/
				}
			}
		}
		
		/*IntStream.range(0, 256).parallel().forEach(index -> {
			int px = index & 15;// | wx;
			int pz = index >> 4;// | wz;
			for (short py = 0; py < 255; py++) {
				BlockState state = chunk.getBlockState(px, py, pz);
				if (state.isOf(TheLimitBlocks.STONE) && chunk.getBlockState(px, py + 1, pz).isAir()) {
					chunk.setBlockState(px, py, pz)
				}
			}
		});*/
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
	final VoronoiNoise islandNoise = new VoronoiNoise(0);
	final VoronoiNoise distortX = new VoronoiNoise(1);
	final VoronoiNoise distortY = new VoronoiNoise(2);
	final Random random = new Random(0);
	
	private Chunk generateChunk(int x, int z) {
		StationFlatteningChunk chunk = new StationFlatteningChunk(level, x, z);
		BlockState glaucolit = TheLimitBlocks.GLAUCOLIT.getDefaultState();
		BlockState glowstone = BlockBase.GLOWSTONE.getDefaultState();
		BlockState vitilit = TheLimitBlocks.VITILIT.getDefaultState();
		ChunkSection[] sections = chunk.sections;
		
		final int wx = x << 4;
		final int wz = z << 4;
		cell1.update(wx, wz);
		IntStream.range(0, sections.length).forEach(index -> {
			ChunkSection section = new ChunkSection(index);
			sections[index] = section;
			int wy = index << 4;
			for (int i = 0; i < 4096; i++) {
				int dx = i & 15;
				int dy = (i >> 4) & 15;
				int dz = i >> 8;
				int py = dy | wy;
				if (cell1.get(dx, py, dz) > 0.5F) {
					int type = random.nextInt(64);
					float h = terrainNoise.get((dx | wx) * 0.1, py * 0.1, (dz | wz) * 0.1) * 10 + 5;
					if (py < h) section.setBlockState(dx, dy, dz, type == 0 ? glowstone : vitilit);
					else section.setBlockState(dx, dy, dz, type == 0 ? glowstone : glaucolit);
				}
			}
		});
		
		chunk.generateHeightmap();
		return chunk;
	}
	
	private float getGradient(float y, float level, float bottom, float middle, float top) {
		if (y < level) {
			return MathHelper.lerp(y / level, bottom, middle);
		}
		else {
			return MathHelper.lerp((y - level) / (255 - level), middle, top);
		}
	}
	
	private float getDensity(BlockPos pos) {
		double px = pos.getX() * 0.01;
		double pz = pos.getZ() * 0.01;
		
		// Distortion
		float dx = distortX.get(px, pz);
		float dz = distortY.get(px, pz);
		px += dx;
		pz += dz;
		
		long seed = islandNoise.getID(px, pz);
		random.setSeed(seed);
		
		float scale = MathHelper.lerp(random.nextFloat(), 1F, 1.5F);
		float density = 0.9F - islandNoise.get(px, pz) * scale;
		
		float height = MathHelper.lerp(terrainNoise.get(px, pz), random.nextFloat(), 0.3F);
		height = MathHelper.lerp(height, -30, 30);//terrainNoise.get(pos.getX() * 0.01, pos.getZ() * 0.01);
		
		density += getGradient(pos.getY() + height, 125, -1 * scale, 0, -4);
		
		// Big details
		density -= terrainNoise.get(pos.getX() * 0.05, pos.getY() * 0.05, pos.getZ() * 0.05) * 0.1F;
		
		// Small details
		//density += terrainNoise.get(pos.getX() * 0.1, pos.getY() * 0.1, pos.getZ() * 0.1) * 0.01F;
		
		return density;
	}
}

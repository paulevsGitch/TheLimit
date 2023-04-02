package paulevs.thelimit.world.dimension;

import net.modificationstation.stationapi.api.util.math.BlockPos;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import paulevs.thelimit.noise.PerlinNoise;
import paulevs.thelimit.noise.VoronoiNoise;

import java.util.Random;

public class IslandLayer {
	private final PerlinNoise terrainNoise;
	private final VoronoiNoise islandNoise;
	private final VoronoiNoise distortX;
	private final VoronoiNoise distortY;
	private final Random random = new Random(0);
	private final int height;
	private final float distance;
	private final float coverage;
	
	public IslandLayer(int seed, int height, float distance, float coverage) {
		random.setSeed(seed);
		terrainNoise = new PerlinNoise(random.nextInt());
		islandNoise = new VoronoiNoise(random.nextInt());
		distortX = new VoronoiNoise(random.nextInt());
		distortY = new VoronoiNoise(random.nextInt());
		this.height = height;
		this.distance = 1F / distance;
		this.coverage = coverage;
	}
	
	public float getDensity(BlockPos pos) {
		double px = pos.getX() * distance;
		double pz = pos.getZ() * distance;
		
		// Distortion
		float dx = distortX.get(px, pz);
		float dz = distortY.get(px, pz);
		px += dx * 0.5F;
		pz += dz * 0.5F;
		
		long seed = islandNoise.getID(px, pz);
		random.setSeed(seed);
		
		float scale = MathHelper.lerp(random.nextFloat(), 1F, 1.5F);
		float density = coverage - islandNoise.get(px, pz) * scale;
		
		float height = 0;
		
		density += getGradient(pos.getY() + height, -1 * scale, 0, -6 * scale);
		density += terrainNoise.get(pos.getX() * 0.1, pos.getZ() * 0.1) * 0.3F - 0.1F;
		
		return density;
	}
	
	private float getGradient(float y, float bottom, float middle, float top) {
		float delta = Math.abs(y - height) / 120F;
		delta = MathHelper.lerp(0.75, delta, smoothStep(delta));
		return y < height ? MathHelper.lerp(delta, middle, bottom) : MathHelper.lerp(delta, middle, top);
	}
	
	private float smoothStep(float x) {
		return x * x * x * (x * (x * 6 - 15) + 10);
	}
}

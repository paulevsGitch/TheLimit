package paulevs.thelimit.noise;

import net.minecraft.util.maths.MathHelper;

import java.util.Arrays;
import java.util.Random;

public class VoronoiNoise {
	private final Random random = new Random(0);
	private final float[] buffer = new float[27];
	private final int seed;
	
	public VoronoiNoise(int seed) {
		this.seed = seed;
	}
	
	public float getF1F3(double x, double y, double z) {
		get(x, y, z);
		return buffer[0] / buffer[2];
	}
	
	public long getID(double x, double z) {
		int x1 = MathHelper.floor(x);
		int z1 = MathHelper.floor(z);
		float sdx = (float) (x - x1);
		float sdz = (float) (z - z1);
		
		byte index = 0;
		float[] point = new float[2];
		float minDist = 10000;
		int px = 0;
		int py = 0;
		
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				getPoint(seed, x1 + i, z1 + j, random, point);
				float dx = point[0] + i - sdx;
				float dz = point[1] + j - sdz;
				float distance = MathHelper.sqrt(dx * dx + dz * dz);
				if (distance < minDist) {
					minDist = distance;
					px = i;
					py = j;
				}
			}
		}
		
		px += x1;
		py += z1;
		
		return net.modificationstation.stationapi.api.util.math.MathHelper.hashCode(px, seed, py);
	}
	
	public float get(double x, double y, double z) {
		int x1 = MathHelper.floor(x);
		int y1 = MathHelper.floor(y);
		int z1 = MathHelper.floor(z);
		float sdx = (float) (x - x1);
		float sdy = (float) (y - y1);
		float sdz = (float) (z - z1);
		
		byte index = 0;
		float[] point = new float[3];
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				for (int k = -1; k < 2; k++) {
					getPoint(seed, x1 + i, y1 + j, z1 + k, random, point);
					float dx = point[0] + i - sdx;
					float dy = point[1] + j - sdy;
					float dz = point[2] + k - sdz;
					float distance = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
					buffer[index++] = distance;
				}
			}
		}
		Arrays.sort(buffer);
		return buffer[0];
	}
	
	public float get(double x, double y) {
		int x1 = MathHelper.floor(x);
		int y1 = MathHelper.floor(y);
		float sdx = (float) (x - x1);
		float sdy = (float) (y - y1);
		
		byte index = 0;
		float[] point = new float[2];
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				getPoint(seed, x1 + i, y1 + j, random, point);
				float dx = point[0] + i - sdx;
				float dy = point[1] + j - sdy;
				float distance = MathHelper.sqrt(dx * dx + dy * dy);
				buffer[index++] = distance;
			}
		}
		Arrays.sort(buffer);
		return buffer[0];
	}
	
	private void getPoint(int seed, int x, int y, int z, Random random, float[] point) {
		random.setSeed(hash(x, y + seed, z));
		point[0] = random.nextFloat() * 0.7F;
		point[1] = random.nextFloat() * 0.7F;
		point[2] = random.nextFloat() * 0.7F;
	}
	
	private void getPoint(int seed, int x, int z, Random random, float[] point) {
		random.setSeed(hash(x, seed, z));
		point[0] = random.nextFloat() * 0.7F;
		point[1] = random.nextFloat() * 0.7F;
	}
	
	private long hash(int x, int y, int z) {
		return net.modificationstation.stationapi.api.util.math.MathHelper.hashCode(x, y, z);
	}
}

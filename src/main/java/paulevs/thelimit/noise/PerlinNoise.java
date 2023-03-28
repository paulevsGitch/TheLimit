package paulevs.thelimit.noise;

import net.modificationstation.stationapi.api.util.math.BlockPos;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import net.modificationstation.stationapi.api.util.math.Vec3f;

import java.util.Random;

public class PerlinNoise {
	private final Vec3f[] cell = new Vec3f[] {
		new Vec3f(), new Vec3f(), new Vec3f(), new Vec3f(),
		new Vec3f(), new Vec3f(), new Vec3f(), new Vec3f()
	};
	private final boolean[] cached = new boolean[2];
	private final Random random = new Random();
	private final int[] lastPos = new int[5];
	private final Vec3f dir = new Vec3f();
	private final int seed;
	
	public PerlinNoise(int seed) {
		this.seed = seed;
	}
	
	public float get(BlockPos pos, double scale) {
		return get(pos.getX() * scale, pos.getY() * scale, pos.getZ() * scale);
	}
	
	public float get(double x, double y, double z) {
		int ix = (int) Math.floor(x);
		int iy = (int) Math.floor(y);
		int iz = (int) Math.floor(z);
		
		if (!cached[0] || lastPos[0] != ix || lastPos[1] != iy || lastPos[2] != iz) {
			lastPos[0] = ix;
			lastPos[1] = iy;
			lastPos[2] = iz;
			cached[0] = true;
			
			fillVector(cell[0], ix, iy, iz);
			fillVector(cell[1], ix + 1, iy, iz);
			fillVector(cell[2], ix, iy + 1, iz);
			fillVector(cell[3], ix + 1, iy + 1, iz);
			fillVector(cell[4], ix, iy, iz + 1);
			fillVector(cell[5], ix + 1, iy, iz + 1);
			fillVector(cell[6], ix, iy + 1, iz + 1);
			fillVector(cell[7], ix + 1, iy + 1, iz + 1);
		}
		
		float dx = (float) (x - ix);
		float dy = (float) (y - iy);
		float dz = (float) (z - iz);
		
		float a = cell[0].dot(set(dx, dy, dz));
		float b = cell[1].dot(set(dx - 1, dy, dz));
		float c = cell[2].dot(set(dx, dy - 1, dz));
		float d = cell[3].dot(set(dx - 1, dy - 1, dz));
		float e = cell[4].dot(set(dx, dy, dz - 1));
		float f = cell[5].dot(set(dx - 1, dy, dz - 1));
		float g = cell[6].dot(set(dx, dy - 1, dz - 1));
		float h = cell[7].dot(set(dx - 1, dy - 1, dz - 1));
		
		dx = smoothStep(dx);
		dy = smoothStep(dy);
		dz = smoothStep(dz);
		
		a = MathHelper.lerp(dx, a, b);
		b = MathHelper.lerp(dx, c, d);
		c = MathHelper.lerp(dx, e, f);
		d = MathHelper.lerp(dx, g, h);
		
		a = MathHelper.lerp(dy, a, b);
		b = MathHelper.lerp(dy, c, d);
		
		a = MathHelper.lerp(dz, a, b);
		
		return MathHelper.clamp(a / 1.732F + 0.5F, 0.0F, 1.0F);
	}
	
	public float get(double x, double y) {
		int ix = (int) Math.floor(x);
		int iy = (int) Math.floor(y);
		
		if (!cached[1] || lastPos[3] != ix || lastPos[4] != iy) {
			lastPos[3] = ix;
			lastPos[4] = iy;
			cached[1] = true;
			
			fillVector(cell[0], ix, iy);
			fillVector(cell[1], ix + 1, iy);
			fillVector(cell[2], ix, iy + 1);
			fillVector(cell[3], ix + 1, iy + 1);
		}
		
		float dx = (float) (x - ix);
		float dy = (float) (y - iy);
		
		float a = cell[0].dot(set(dx, dy, 0));
		float b = cell[1].dot(set(dx - 1, dy, 0));
		float c = cell[2].dot(set(dx, dy - 1, 0));
		float d = cell[3].dot(set(dx - 1, dy - 1, 0));
		
		dx = smoothStep(dx);
		dy = smoothStep(dy);
		
		a = MathHelper.lerp(dx, a, b);
		b = MathHelper.lerp(dx, c, d);
		
		a = MathHelper.lerp(dy, a, b);
		
		return MathHelper.clamp(a / 1.414F + 0.5F, 0.0F, 1.0F);
	}
	
	private Vec3f set(float x, float y, float z) {
		dir.set(x, y, z);
		return dir;
	}
	
	private void fillVector(Vec3f pos, int x, int y, int z) {
		random.setSeed(MathHelper.hashCode(x, y + seed, z));
		pos.set(
			random.nextFloat() - 0.5F,
			random.nextFloat() - 0.5F,
			random.nextFloat() - 0.5F
		);
		pos.normalize();
	}
	
	private void fillVector(Vec3f pos, int x, int y) {
		random.setSeed(MathHelper.hashCode(x, y, seed));
		pos.set(random.nextFloat() - 0.5F, random.nextFloat() - 0.5F, 0);
		pos.normalize();
	}
	
	private float smoothStep(float x) {
		return x * x * x * (x * (x * 6 - 15) + 10);
	}
}

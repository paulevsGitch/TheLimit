package paulevs.thelimit.dimension;

import net.modificationstation.stationapi.api.util.math.BlockPos;
import net.modificationstation.stationapi.api.util.math.MathHelper;

import java.util.function.Function;

public class InterpolationCell {
	private final BlockPos.Mutable origin = new BlockPos.Mutable();
	private final Function<BlockPos, Float> generator;
	private final float[][][] data;
	private final short width;
	private final short height;
	private final short dxz;
	private final short dy;
	
	public InterpolationCell(Function<BlockPos, Float> generator, int width, int height, int dxz, int dy) {
		this.data = new float[width][width][height];
		this.generator = generator;
		this.height = (short) height;
		this.width = (short) width;
		this.dxz = (short) dxz;
		this.dy = (short) dy;
	}
	
	public void update(int wx, int wz) {
		BlockPos.Mutable pos = new BlockPos.Mutable();
		for (int x = 0; x < width; x++) {
			pos.setX(wx + x * dxz);
			for (int z = 0; z < width; z++) {
				pos.setZ(wz + z * dxz);
				for (int y = 0; y < height; y++) {
					pos.setY(y * dy);
					data[x][z][y] = generator.apply(pos);
				}
			}
		}
	}
	
	public float get(int x, int y, int z) {
		int x1 = x / dxz;
		int y1 = y / dy;
		int z1 = z / dxz;
		
		int x2 = x1 + 1;
		int y2 = y1 + 1;
		int z2 = z1 + 1;
		
		float dx = (float) x / dxz - x1;
		float dy = (float) y / this.dy - y1;
		float dz = (float) z / dxz - z1;
		
		float a = MathHelper.lerp(dx, data[x1][z1][y1], data[x2][z1][y1]);
		float b = MathHelper.lerp(dx, data[x1][z1][y2], data[x2][z1][y2]);
		float c = MathHelper.lerp(dx, data[x1][z2][y1], data[x2][z2][y1]);
		float d = MathHelper.lerp(dx, data[x1][z2][y2], data[x2][z2][y2]);
		
		a = MathHelper.lerp(dy, a, b);
		b = MathHelper.lerp(dy, c, d);
		
		return MathHelper.lerp(dz, a, b);
	}
}

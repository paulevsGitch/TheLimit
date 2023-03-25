package paulevs.thelimit.dimension;

import net.minecraft.class_467;
import net.minecraft.entity.EntityBase;
import net.minecraft.level.Level;
import paulevs.thelimit.TheLimit;

public class DimensionTraveler extends class_467 {
	public void method_1530(Level level, EntityBase entity) {
		int x = (int) (entity.x);
		int z = (int) (entity.z);
		for (int i = 0; i < 100; i++) {
			int px = x + level.rand.nextInt(257) - 128;
			int pz = z + level.rand.nextInt(257) - 128;
			if (level.dimension.canSpawnOn(px, pz)) {
				int py = TheLimit.getHeight(level, px, pz);
				entity.setPosition(px + 0.5, py + 3.5, pz + 0.5);
			}
		}
	}
}

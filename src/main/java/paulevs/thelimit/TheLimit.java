package paulevs.thelimit;

import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;
import paulevs.thelimit.world.dimension.TheLimitDimension;

public class TheLimit {
	private static final ModID ID = ModID.of("thelimit");
	
	public static Identifier id(String name) {
		return ID.id(name);
	}
	
	public static boolean isTheLimit(Level level) {
		return level.dimension instanceof TheLimitDimension;
	}
	
	public static int getHeight(Level level, int x, int z) {
		Chunk chunk = level.getChunkFromCache(x >> 4, z >> 4);
		if (chunk == null) return 0;
		for (short y = 255; y > 0; y--) {
			BlockState state = chunk.getBlockState(x & 15, y, z & 15);
			if (state.isOpaque()) return y;
		}
		return 0;
	}
	
	public static boolean isReplaceable(BlockState state) {
		return state.isAir() || state.getMaterial().isReplaceable() || state.getMaterial() == Material.PLANT;
	}
}

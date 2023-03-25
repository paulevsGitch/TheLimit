package paulevs.thelimit.rendering;

import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.util.math.BlockPos;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import paulevs.thelimit.TheLimit;
import paulevs.thelimit.noise.PerlinNoise;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class EmissiveFunctions {
	private static final Map<Identifier, Function<BlockPos, Float>> FUNCTIONS = new HashMap<>();
	
	public static Function<BlockPos, Float> get(Identifier id) {
		return FUNCTIONS.get(id);
	}
	
	static {
		PerlinNoise noise = new PerlinNoise(0);
		//VoronoiNoise noise = new VoronoiNoise(0);
		FUNCTIONS.put(
			TheLimit.id("block/blob_grass_e"),
			//pos -> noise.get(pos.getX() * 0.5, pos.getY() * 0.5, pos.getZ() * 0.5)
			pos -> {
				float value = noise.get(pos.getX() * 0.2, pos.getY() * 0.2, pos.getZ() * 0.2) * 4F - 2F;
				value += (MathHelper.hashCode(pos.getX(), pos.getY(), pos.getZ()) & 15) / 15F;
				return MathHelper.clamp(value, 0, 1);
			}
		);
	}
}

package paulevs.thelimit.rendering;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.util.math.BlockPos;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import paulevs.thelimit.TheLimit;
import paulevs.thelimit.blocks.DoublePlantBlock;
import paulevs.thelimit.blocks.TLBlockProperties;
import paulevs.thelimit.blocks.TLBlockProperties.TopBottom;
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
		Function<BlockPos, Float> grass = pos -> {
			float value = noise.get(pos, 0.2) * 4F - 2F;
			value += (MathHelper.hashCode(pos.getX(), pos.getY(), pos.getZ()) & 15) / 32F;
			return MathHelper.clamp(value, 0, 1);
		};
		
		FUNCTIONS.put(TheLimit.id("block/guttarba_e"), grass);
		FUNCTIONS.put(TheLimit.id("block/guttarba_small_e"), grass);
		
		grass = pos -> {
			Minecraft mc = (Minecraft) FabricLoader.getInstance().getGameInstance();
			BlockState state = mc.level.getBlockState(pos);
			
			int y = pos.getY();
			if (state.getBlock() instanceof DoublePlantBlock && state.get(TLBlockProperties.TOP_BOTTOM) == TopBottom.TOP) {
				y -= 1;
			}
			
			float value = noise.get(pos.getX() * 0.2, y * 0.2, pos.getZ() * 0.2) * 4F - 2F;
			value += (MathHelper.hashCode(pos.getX(), y, pos.getZ()) & 15) / 32F;
			return MathHelper.clamp(value, 0, 1);
		};
		
		FUNCTIONS.put(TheLimit.id("block/guttarba_tall_bottom_e"), grass);
		FUNCTIONS.put(TheLimit.id("block/guttarba_tall_top_e"), grass);
	}
}

package paulevs.thelimit.world.biomes;

import net.minecraft.level.Level;
import net.minecraft.level.gen.BiomeSource;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;
import paulevs.thelimit.blocks.TLBlocks;
import paulevs.thelimit.world.structures.TheLimitStructures;

import java.util.Random;

public class VoidLakeBiome extends TheLimitBiome {
	private static final BlockState LIQUID = TLBlocks.VOID_FLUID.getDefaultState();
	private static final BlockState VITILIT = TLBlocks.VITILIT.getDefaultState();
	
	public VoidLakeBiome() {
		setName("Void Lake");
		addStructure(TheLimitStructures.CALABELLUM_GROUP_PLACER);
	}
	
	@Override
	public BlockState getGround(Level level, Random random, int x, int y, int z) {
		BiomeSource biomeSource = level.dimension.biomeSource;
		for (byte i = 0; i < 4; i++) {
			Direction dir = Direction.fromHorizontal(i);
			if (biomeSource.getBiome(x + dir.getOffsetX() * 3, z + dir.getOffsetZ() * 3) != this) {
				return VITILIT;
			}
			//BlockState state = level.getBlockState(x + dir.getOffsetX() * 2, y, z + dir.getOffsetZ() * 2);
			BlockState state = level.getBlockState(x + dir.getOffsetX(), y, z + dir.getOffsetZ());
			if (state.isAir()) return VITILIT;
			if (!state.isOf(TLBlocks.VOID_FLUID) && !state.getBlock().isFullCube()) return VITILIT;
		}
		return LIQUID;
	}
	
	public BlockState getFiller(Level level, Random random, int x, int y, int z) {
		return VITILIT;
	}
}

package paulevs.thelimit.world.structures.scatters;

import net.minecraft.level.Level;
import net.minecraft.level.structure.Structure;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.BlockPos;

import java.util.Random;
import java.util.function.Function;

public class FeatureScatter extends ScatterStructure {
	private final Function<BlockState, Boolean> groundFunction;
	private final Function<BlockState, Boolean> replaceFunction;
	private final Structure structure;
	
	public FeatureScatter(int radius, int count, Structure structure, Function<BlockState, Boolean> replaceFunction, Function<BlockState, Boolean> groundFunction) {
		super(radius, count);
		this.structure = structure;
		this.groundFunction = groundFunction;
		this.replaceFunction = replaceFunction;
	}
	
	@Override
	public boolean generate(Level level, Random random, int x, int y, int z) {
		BlockPos.Mutable pos = new BlockPos.Mutable();
		BlockPos center = new BlockPos(x, y, z);
		for (int i = 0; i < count; i++) {
			pos.setX(x + random.nextInt(radius2) - radius);
			pos.setZ(z + random.nextInt(radius2) - radius);
			pos.setY(y + 5);
			BlockState state = level.getBlockState(pos.getX(), pos.getY() - 1, pos.getZ());
			for (int j = 0; j < 11; j++) {
				BlockState below = level.getBlockState(pos.getX(), pos.getY() - 1, pos.getZ());
				if (replaceFunction.apply(state) && groundFunction.apply(below)) {
					place(level, random, pos, center);
					break;
				}
				state = below;
				pos.setY(pos.getY() - 1);
			}
		}
		return true;
	}
	
	@Override
	protected void place(Level level, Random random, BlockPos pos, BlockPos center) {
		if (!level.getBlockState(pos.getX(), pos.getY() - 1, pos.getZ()).getBlock().isFullCube()) return;
		structure.generate(level, random, pos.getX(), pos.getY(), pos.getZ());
	}
}

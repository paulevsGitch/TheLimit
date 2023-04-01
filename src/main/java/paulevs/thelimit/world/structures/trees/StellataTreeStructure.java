package paulevs.thelimit.world.structures.trees;

import net.minecraft.level.Level;
import net.minecraft.level.structure.Structure;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.BlockPos;
import net.modificationstation.stationapi.api.util.math.Direction;
import paulevs.thelimit.TheLimit;
import paulevs.thelimit.blocks.TLBlockProperties;
import paulevs.thelimit.blocks.TheLimitBlocks;

import java.util.Random;

public class StellataTreeStructure extends Structure {
	@Override
	public boolean generate(Level level, Random random, int x, int y, int z) {
		if (level.getBlockState(x, y - 1, z).isAir()) return false;
		int height = random.nextInt(4) + 8;
		makeTrunk(level, height, x, y, z);
		makeRoots(level, random, x, y, z);
		makeBranches(level, random, x, y, z, height);
		return true;
	}
	
	private void makeTrunk(Level level, int height, int x, int y, int z) {
		BlockState log = TheLimitBlocks.STELLATA_LOG.getDefaultState();
		BlockState top = TheLimitBlocks.STELLATA_BARK.getDefaultState();
		height--;
		for (int i = 0; i < height; i++) {
			level.setBlockState(x, y + i, z, log);
		}
		level.setBlockState(x, y + height, z, top);
	}
	
	private void makeRoots(Level level, Random random, int x, int y, int z) {
		BlockState stem = TheLimitBlocks.STELLATA_STEM.getDefaultState();
		BlockState branch = TheLimitBlocks.STELLATA_BRANCH.getDefaultState();
		branch = branch.with(TLBlockProperties.getFaceProp(Direction.DOWN), true);
		BlockPos.Mutable pos = new BlockPos.Mutable();
		
		for (byte i = 0; i < 4; i++) {
			Direction dir = Direction.fromHorizontal(i);
			pos.set(x, y + random.nextInt(3), z).move(dir);
			if (TheLimit.isReplaceable(level.getBlockState(pos))) {
				BlockState side = branch.with(TLBlockProperties.getFaceProp(dir.getOpposite()), true);
				level.setBlockState(pos.getX(), pos.getY(), pos.getZ(), side);
				for (byte j = 0; j < 4; j++) {
					pos.setY(pos.getY() - 1);
					if (!TheLimit.isReplaceable(level.getBlockState(pos))) break;
					level.setBlockState(pos.getX(), pos.getY(), pos.getZ(), stem);
				}
			}
		}
	}
	
	private void makeBranches(Level level, Random random, int x, int y, int z, int height) {
		BlockState stem = TheLimitBlocks.STELLATA_STEM.getDefaultState();
		BlockState branch = TheLimitBlocks.STELLATA_BRANCH.getDefaultState();
		BlockState flower = TheLimitBlocks.STELLATA_FLOWER.getDefaultState();
		branch = branch.with(TLBlockProperties.getFaceProp(Direction.UP), true);
		BlockPos.Mutable pos = new BlockPos.Mutable();
		
		for (byte i = 0; i < 4; i++) {
			Direction dir = Direction.fromHorizontal(i);
			pos.set(x, y + height - random.nextInt(5) - 1, z).move(dir);
			if (TheLimit.isReplaceable(level.getBlockState(pos))) {
				BlockState side = branch.with(TLBlockProperties.getFaceProp(dir.getOpposite()), true);
				level.setBlockState(pos.getX(), pos.getY(), pos.getZ(), side);
				int branchHeight = random.nextInt(5);
				for (byte j = 0; j < branchHeight; j++) {
					pos.setY(pos.getY() + 1);
					if (!TheLimit.isReplaceable(level.getBlockState(pos))) break;
					level.setBlockState(pos.getX(), pos.getY(), pos.getZ(), stem);
				}
				pos.setY(pos.getY() + 1);
				level.setBlockState(pos.getX(), pos.getY(), pos.getZ(), flower);
			}
		}
		
		pos.set(x, y + height, z);
		if (TheLimit.isReplaceable(level.getBlockState(pos))) {
			int branchHeight = random.nextInt(3) + 2;
			for (byte j = 0; j < branchHeight; j++) {
				if (!TheLimit.isReplaceable(level.getBlockState(pos))) break;
				level.setBlockState(pos.getX(), pos.getY(), pos.getZ(), stem);
				pos.setY(pos.getY() + 1);
			}
			level.setBlockState(pos.getX(), pos.getY(), pos.getZ(), flower);
		}
	}
}

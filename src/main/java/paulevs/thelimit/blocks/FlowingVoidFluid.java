package paulevs.thelimit.blocks;

import net.minecraft.block.BlockBase;
import net.minecraft.block.Door;
import net.minecraft.block.Fluid;
import net.minecraft.block.Ladder;
import net.minecraft.block.Sign;
import net.minecraft.block.SugarCane;
import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.States;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateFluid;

import java.util.Random;
import java.util.function.Supplier;

public class FlowingVoidFluid extends TemplateFluid {
	private final boolean[] flowArrayBool = new boolean[4];
	private final int[] flowArrayInt = new int[4];
	private Supplier<BlockState> stillFluid;
	private int zeroCount = 0;
	
	protected FlowingVoidFluid(Identifier id) {
		super(id, Material.WATER);
		texture = 1;
	}
	
	public void setStillFluid(Supplier<BlockState> stillFluid) {
		this.stillFluid = stillFluid;
	}
	
	@Override
	public void onScheduledTick(Level level, int x, int y, int z, Random random) {
		int meta = this.method_1220(level, x, y, z);
		int meta2;
		
		int type = 1;
		if (this.material == Material.LAVA && !level.dimension.evaporatesWater) {
			type = 2;
		}
		
		boolean setStill = true;
		if (meta > 0) {
			int meta3 = -100;
			this.zeroCount = 0;
			
			meta3 = this.checkMeta(level, x - 1, y, z, meta3);
			meta3 = this.checkMeta(level, x + 1, y, z, meta3);
			meta3 = this.checkMeta(level, x, y, z - 1, meta3);
			
			meta2 = (meta3 = this.checkMeta(level, x, y, z + 1, meta3)) + type;
			if (meta2 >= 8 || meta3 < 0) meta2 = -1;
			
			if (this.method_1220(level, x, y + 1, z) >= 0) {
				int n5 = this.method_1220(level, x, y + 1, z);
				meta2 = n5 >= 8 ? n5 : n5 + 8;
			}
			
			if (this.zeroCount >= 2 && this.material == Material.WATER) {
				if (level.getMaterial(x, y - 1, z).isSolid()) {
					meta2 = 0;
				}
				else if (level.getMaterial(x, y - 1, z) == this.material && level.getTileMeta(x, y, z) == 0) {
					meta2 = 0;
				}
			}
			
			if (this.material == Material.LAVA && meta2 < 8 && meta2 > meta && random.nextInt(4) != 0) {
				meta2 = meta;
				setStill = false;
			}
			
			if (meta2 != meta) {
				meta = meta2;
				if (meta < 0) level.setBlockState(x, y, z, States.AIR.get());
				else {
					level.setTileMeta(x, y, z, meta);
					level.method_216(x, y, z, this.id, this.getTickrate());
					level.updateAdjacentBlocks(x, y, z, this.id);
				}
			}
			else if (setStill) this.setStill(level, x, y, z);
		}
		else this.setStill(level, x, y, z);
		
		if (this.canReplace(level, x, y - 1, z)) {
			level.setBlockState(x, y - 1, z, getDefaultState());
			if (meta >= 8) level.setTileMeta(x, y - 1, z, meta);
			else level.setTileMeta(x, y - 1, z, meta + 8);
		}
		else if (meta >= 0 && (meta == 0 || this.cantReplace(level, x, y - 1, z))) {
			boolean[] flowArray = this.getFlowArray(level, x, y, z);
			meta2 = meta + type;
			if (meta >= 8) meta2 = 1;
			if (meta2 >= 8) return;
			if (flowArray[0]) this.replaceBlock(level, x - 1, y, z, meta2);
			if (flowArray[1]) this.replaceBlock(level, x + 1, y, z, meta2);
			if (flowArray[2]) this.replaceBlock(level, x, y, z - 1, meta2);
			if (flowArray[3]) this.replaceBlock(level, x, y, z + 1, meta2);
		}
	}
	
	@Override
	public void onBlockPlaced(Level level, int x, int y, int z) {
		super.onBlockPlaced(level, x, y, z);
		if (level.getBlockState(x, y, z).isOf(this)) {
			level.method_216(x, y, z, this.id, this.getTickrate());
		}
	}
	
	private void setStill(Level level, int x, int z, int y) {
		level.setBlockStateWithNotify(x, y, z, stillFluid.get());
		level.method_202(x, z, y, x, z, y);
		level.method_243(x, z, y);
	}
	
	private void replaceBlock(Level level, int x, int y, int z, int meta) {
		if (this.canReplace(level, x, y, z)) {
			BlockState state = level.getBlockState(x, y, z);
			state.getBlock().drop(level, x, y, z, level.getTileMeta(x, y, z));
			level.setBlockState(x, y, z, getDefaultState());
			level.setTileMeta(x, y, z, meta);
		}
	}
	
	private int getFlow(Level level, int x, int y, int z, int depth, int meta) {
		int n = 1000;
		
		for (int side = 0; side < 4; ++side) {
			if (side == 0 && meta == 1 || side == 1 && meta == 0 || side == 2 && meta == 3 || side == 3 && meta == 2) continue;
			
			int dx = x;
			int dz = z;
			
			switch (side) {
				case 0 -> --dx;
				case 1 -> ++dx;
				case 2 -> --dz;
				case 3 -> ++dz;
			}
			
			if (this.cantReplace(level, dx, y, dz) || level.getMaterial(dx, y, dz) == this.material && level.getTileMeta(dx, y, dz) == 0) continue;
			if (!this.cantReplace(level, dx, y - 1, dz)) return depth;
			
			int n2;
			if (depth >= 4 || (n2 = this.getFlow(level, dx, y, dz, depth + 1, side)) >= n) continue;
			n = n2;
		}
		
		return n;
	}
	
	private boolean[] getFlowArray(Level level, int x, int y, int z) {
		for (byte i = 0; i < 4; ++i) {
			this.flowArrayInt[i] = 1000;
			int dx = x;
			int dz = z;
			
			switch (i) {
				case 0 -> --dx;
				case 1 -> ++dx;
				case 2 -> --dz;
				case 3 -> ++dz;
			}
			
			if (this.cantReplace(level, dx, y, dz) || level.getMaterial(dx, y, dz) == this.material && level.getTileMeta(dx, y, dz) == 0) continue;
			this.flowArrayInt[i] = !this.cantReplace(level, dx, y - 1, dz) ? 0 : this.getFlow(level, dx, y, dz, 1, i);
		}
		
		int val = this.flowArrayInt[0];
		
		for (byte i = 1; i < 4; ++i) {
			if (this.flowArrayInt[i] >= val) continue;
			val = this.flowArrayInt[i];
		}
		
		for (byte i = 0; i < 4; ++i) {
			this.flowArrayBool[i] = this.flowArrayInt[i] == val;
		}
		
		return this.flowArrayBool;
	}
	
	protected int checkMeta(Level level, int x, int y, int z, int meta) {
		int meta2 = this.method_1220(level, x, y, z);
		if (meta2 < 0) return meta;
		if (meta2 == 0) ++this.zeroCount;
		if (meta2 >= 8) meta2 = 0;
		return meta < 0 || meta2 < meta ? meta2 : meta;
	}
	
	private boolean canReplace(Level level, int x, int y, int z) {
		//Material material = level.getMaterial(x, y, z);
		//if (material == this.material) return false;
		//if (material == Material.LAVA) return false;
		return !this.cantReplace(level, x, y, z);
	}
	
	private boolean cantReplace(Level level, int x, int y, int z) {
		BlockBase block = level.getBlockState(x, y, z).getBlock();
		if (block instanceof Fluid) return true;
		if (block instanceof Door) return true;
		if (block instanceof Sign) return true;
		if (block instanceof Ladder) return true;
		if (block instanceof SugarCane) return true;
		return block.material.blocksMovement();
	}
}

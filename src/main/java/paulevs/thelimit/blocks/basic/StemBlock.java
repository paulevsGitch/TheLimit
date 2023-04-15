package paulevs.thelimit.blocks.basic;

import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction.Axis;
import paulevs.thelimit.blocks.TLBlockProperties;

import java.util.ArrayList;

public class StemBlock extends LogBlock {
	public StemBlock(Identifier identifier) {
		super(identifier);
	}
	
	@Override
	public boolean isFullOpaque() {
		return false;
	}
	
	@Override
	public boolean isFullCube() {
		return false;
	}
	
	@Override
	public void updateBoundingBox(BlockView view, int x, int y, int z) {
		if (view instanceof Level) {
			BlockState state = ((Level) view).getBlockState(x, y, z);
			if (state.isOf(this)) {
				setBoundingBox(state);
				return;
			}
		}
		this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
	}
	
	@Override
	public void doesBoxCollide(Level level, int x, int y, int z, Box box, ArrayList list) {
		BlockState state = level.getBlockState(x, y, z);
		if (state.isOf(this)) {
			setBoundingBox(state);
		}
		super.doesBoxCollide(level, x, y, z, box, list);
		this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
	}
	
	private void setBoundingBox(BlockState state) {
		Axis axis = state.get(TLBlockProperties.AXIS);
		switch (axis) {
			case X -> this.setBoundingBox(0.0F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
			case Y -> this.setBoundingBox(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
			case Z -> this.setBoundingBox(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 1.0F);
		}
	}
}

package paulevs.thelimit.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager.Builder;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import paulevs.thelimit.blocks.TLBlockProperties.TriplePart;
import paulevs.thelimit.rendering.VoidFluidRenderer;

import java.util.ArrayList;

public class CalabellumPlant extends TemplateBlockBase {
	public CalabellumPlant(Identifier identifier) {
		super(identifier, Material.WOOD);
		setSounds(WOOD_SOUNDS);
		setHardness(0.5F);
		setDefaultState(getDefaultState().with(TLBlockProperties.VOIDLOGGED, false));
	}
	
	@Override
	public void appendProperties(Builder<BlockBase, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(TLBlockProperties.TRIPLE_PART, TLBlockProperties.VOIDLOGGED);
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
	@Environment(value= EnvType.CLIENT)
	public int getRenderPass() {
		if (VoidFluidRenderer.renderPass > 1) return 0;
		return VoidFluidRenderer.renderPass;
	}
	
	@Override
	@Environment(value=EnvType.CLIENT)
	public int getRenderType() {
		return VoidFluidRenderer.renderPass < 1 ? 0 : 4;
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
	
	/*@Override
	@Environment(value= EnvType.CLIENT)
	public int getRenderPass() {
		return 1;
	}*/
	
	private void setBoundingBox(BlockState state) {
		TriplePart part = state.get(TLBlockProperties.TRIPLE_PART);
		if (part == TriplePart.TOP) {
			setBoundingBox(0.375f, 0.0f, 0.375f, 0.625f, 0.25f, 0.625f);
		}
		else {
			setBoundingBox(0.375f, 0.0f, 0.375f, 0.625f, 1.0f, 0.625f);
		}
	}
}

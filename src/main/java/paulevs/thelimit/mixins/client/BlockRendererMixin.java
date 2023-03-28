package paulevs.thelimit.mixins.client;

import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.render.block.BlockRenderer;
import net.minecraft.level.BlockView;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.BlockPos;
import net.modificationstation.stationapi.api.world.BlockStateView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.thelimit.blocks.TheLimitBlocks;

@Mixin(BlockRenderer.class)
public class BlockRendererMixin {
	@Shadow private BlockView blockView;
	
	@Unique private final BlockPos.Mutable thelimit_liquidPos = new BlockPos.Mutable();
	@Unique private BlockPos[] thelimit_offsets;
	@Unique private boolean thelimit_apply;
	@Unique private byte thelimit_index;
	
	@Inject(method = "<init>*", at = @At("TAIL"))
	private void thelimit_onInit(CallbackInfo info) {
		thelimit_offsets = new BlockPos[] {
			new BlockPos(0, 0, -1), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(1, 0, 0)
		};
	}
	
	@Inject(method = "renderFluid", at = @At("HEAD"))
	private void thelimit_renderFluid(BlockBase block, int x, int y, int z, CallbackInfoReturnable<Boolean> info) {
		thelimit_apply = block == TheLimitBlocks.VOID_FLUID;
		if (thelimit_apply) {
			thelimit_liquidPos.set(x, y, z);
			thelimit_index = 0;
		}
	}
	
	@Inject(method = "method_43", at = @At("HEAD"), cancellable = true)
	private void thelimit_getFluidHeight(int x, int y, int z, Material material, CallbackInfoReturnable<Float> info) {
		if (!thelimit_apply) return;
		if (!(this.blockView instanceof BlockStateView view)) return;
		
		BlockState state = view.getBlockState(
			thelimit_liquidPos.getX(),
			thelimit_liquidPos.getY() + 1,
			thelimit_liquidPos.getZ()
		);
		
		if (state.isOf(TheLimitBlocks.VOID_FLUID)) {
			info.setReturnValue(1.0F);
			return;
		}
		
		/*state = view.getBlockState(
			thelimit_liquidPos.getX(),
			thelimit_liquidPos.getY() - 1,
			thelimit_liquidPos.getZ()
		);*/
		
		BlockPos offset1 = thelimit_offsets[thelimit_index];
		thelimit_index = (byte) ((thelimit_index + 1) & 3);
		BlockPos offset2 = thelimit_offsets[thelimit_index];
		
		BlockState state1 = view.getBlockState(
			thelimit_liquidPos.getX() + offset1.getX(),
			thelimit_liquidPos.getY(),
			thelimit_liquidPos.getZ() + offset1.getZ()
		);
		
		BlockState state2 = view.getBlockState(
			thelimit_liquidPos.getX() + offset1.getX(),
			thelimit_liquidPos.getY() + 1,
			thelimit_liquidPos.getZ() + offset1.getZ()
		);
		
		BlockState state3 = view.getBlockState(
			thelimit_liquidPos.getX() + offset2.getX(),
			thelimit_liquidPos.getY(),
			thelimit_liquidPos.getZ() + offset2.getZ()
		);
		
		BlockState state4 = view.getBlockState(
			thelimit_liquidPos.getX() + offset2.getX(),
			thelimit_liquidPos.getY() + 1,
			thelimit_liquidPos.getZ() + offset2.getZ()
		);
		
		//float value = 0.4375F;
		float value = 0.25F;
		if (state1.isOf(TheLimitBlocks.VOID_FLUID) ^ state1.getBlock().isFullCube()) {
			value = state2.isOf(TheLimitBlocks.VOID_FLUID) ? 1 : 0.9375F;
		}
		if (state3.isOf(TheLimitBlocks.VOID_FLUID) ^ state4.getBlock().isFullCube()) {
			value = Math.max(value, state4.isOf(TheLimitBlocks.VOID_FLUID) ? 1 : 0.9375F);
		}
		info.setReturnValue(value);
		
		/*if (state.isOf(TheLimitBlocks.VOID_FLUID)) {
			state = view.getBlockState(x, y, z);
			info.setReturnValue(state.isAir() ? 0.9375F : 0.4375F);
			return;
		}
		
		info.setReturnValue(0.9375F);*/
		
		/*state = view.getBlockState(x, y, z);
		if (state.isAir() || state.isOf(TheLimitBlocks.VOID_FLUID) || state.getBlock().isFullCube()) {
			info.setReturnValue(0.9375F);
		}
		else {
			info.setReturnValue(0.4375F);
		}*/
		
		//info.setReturnValue(state.isOf(TheLimitBlocks.VOID_FLUID) ? 1.0F : 0.5F);
	}
}

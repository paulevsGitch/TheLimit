package paulevs.thelimit.mixins.common;

import net.minecraft.level.Level;
import net.minecraft.level.LightType;
import net.minecraft.level.chunk.Chunk;
import net.modificationstation.stationapi.impl.level.chunk.ChunkSection;
import net.modificationstation.stationapi.impl.level.chunk.StationFlatteningChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = StationFlatteningChunk.class, remap = false)
public abstract class StationFlatteningChunkMixin extends Chunk {
	@Shadow protected abstract ChunkSection getSection(int y);
	
	public StationFlatteningChunkMixin(Level arg, int i, int j) {
		super(arg, i, j);
	}
	
	@ModifyVariable(method = "getOrCreateSection", at = @At("HEAD"), argsOnly = true)
	private boolean thelimit_disableSkylight(boolean fillSkyLight) {
		return !level.dimension.halvesMapping;
	}
	
	@Inject(method = "method_864", at = @At("HEAD"), cancellable = true)
	private void thelimit_disableSkylight1(LightType type, int x, int y, int z, CallbackInfoReturnable<Integer> info) {
		if (level.dimension.halvesMapping && type == LightType.field_2757) {
			info.setReturnValue(0);
		}
	}
	
	@Inject(method = "method_880", at = @At("HEAD"), cancellable = true)
	private void thelimit_disableSkylight2(int x, int y, int z, int light, CallbackInfoReturnable<Integer> info) {
		if (level.dimension.halvesMapping) {
			int lightLevel = -light;
			ChunkSection section = getSection(y);
			int blockLight = section == null ? 0 : section.getLight(LightType.field_2758, x, y & 15, z);
			if (blockLight > lightLevel) {
				lightLevel = blockLight;
			}
			info.setReturnValue(lightLevel);
		}
	}
}

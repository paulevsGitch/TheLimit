package paulevs.thelimit.mixins.common;

import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.chunk.ServerChunkCache;
import net.minecraft.level.source.LevelSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerChunkCache.class)
public abstract class ServerChunkCacheMixin {
	@Shadow public abstract Chunk getChunk(int i, int j);
	
	@Shadow private LevelSource levelSource;
	
	/*@Inject(method = "decorate", at = @At("HEAD"), cancellable = true)
	public void thelimit_decorate(LevelSource level, int cx, int cz, CallbackInfo info) {
		info.cancel();
		if (this.levelSource == null) return;
		if (!level.isChunkLoaded(cx - 1, cz)) return;
		if (!level.isChunkLoaded(cx + 1, cz)) return;
		if (!level.isChunkLoaded(cx, cz - 1)) return;
		if (!level.isChunkLoaded(cx, cz + 1)) return;
		Chunk chunk = this.getChunk(cx, cz);
		if (chunk.decorated) return;
		chunk.decorated = true;
		this.levelSource.decorate(level, cx, cz);
		chunk.method_885();
	}
	
	@ModifyVariable(method = "loadChunk", at = @At("STORE"))
	private Chunk thelimit_loadChunk(Chunk chunk) {
		if (chunk != null && !chunk.decorated) {
			int cx = chunk.x;
			int cz = chunk.z;
			if (!this.levelSource.isChunkLoaded(cx - 1, cz)) return chunk;
			if (!this.levelSource.isChunkLoaded(cx + 1, cz)) return chunk;
			if (!this.levelSource.isChunkLoaded(cx, cz - 1)) return chunk;
			if (!this.levelSource.isChunkLoaded(cx, cz + 1)) return chunk;
			chunk.decorated = true;
			this.levelSource.decorate(this.levelSource, cx, cz);
			chunk.method_885();
		}
		return chunk;
	}*/
}

package paulevs.thelimit.mixins.common;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.level.Level;
import net.minecraft.level.LevelProperties;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.dimension.Dimension;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import paulevs.thelimit.TheLimit;
import paulevs.thelimit.dimension.TheLimitWorldgen;

import java.util.Random;

@Mixin(Level.class)
public abstract class LevelMixin {
	@Unique private boolean thelimit_populate;
	
	@Shadow protected LevelProperties properties;
	@Shadow public boolean field_221;
	@Shadow @Final public Dimension dimension;
	@Shadow public Random rand;
	
	@Shadow public abstract int getTopBlockAboveSeaLevel(int i, int j);
	
	@Inject(method = "method_212", at = @At("HEAD"), cancellable = true)
	private void thelimit_changeInitialSpawn(CallbackInfo info) {
		if (!TheLimit.isTheLimit(Level.class.cast(this))) return;
		info.cancel();
		
		this.field_221 = true;
		
		int x = 0;
		int z = 0;
		
		for (int i = 0; i < 100; i++) {
			x = this.rand.nextInt(129) - 64;
			z = this.rand.nextInt(129) - 64;
			if (this.dimension.canSpawnOn(x, z)) break;
		}
		
		int y = TheLimit.getHeight(Level.class.cast(this), x, z) + 1;
		if (y < 0) y = 128;
		
		this.properties.setSpawnPosition(x, y, z);
		
		this.field_221 = false;
	}
	
	@Environment(value= EnvType.CLIENT)
	@Inject(method = "method_283", at = @At("HEAD"), cancellable = true)
	private void thelimit_changeClientSpawn(CallbackInfo info) {
		if (!TheLimit.isTheLimit(Level.class.cast(this))) return;
		info.cancel();
		
		if (this.properties.getSpawnY() <= 0) {
			this.properties.setSpawnY(128);
		}
		
		int x = 0;
		int z = 0;
		
		for (int i = 0; i < 100; i++) {
			x = this.properties.getSpawnX() + this.rand.nextInt(129) - 64;
			z = this.properties.getSpawnZ() + this.rand.nextInt(129) - 64;
			if (this.dimension.canSpawnOn(x, z)) break;
		}
		
		int y = TheLimit.getHeight(Level.class.cast(this), x, z) + 1;
		if (y < 0) y = 128;
		
		this.properties.setSpawnPosition(x, y, z);
	}
	
	//method_248
	
	/*@Inject(method = "method_248", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/level/Level;getChunkFromCache(II)Lnet/minecraft/level/chunk/Chunk;",
		shift = Shift.AFTER
	), locals = LocalCapture.PRINT)
	private void thelimit_customPopulator(CallbackInfo info) {
	
	}*/
	
	/*@Inject(method = "method_248", at = @At("HEAD"))
	private void thelimit_customPopulator(CallbackInfo info) {
		thelimit_populate = true;
	}
	
	@ModifyVariable(method = "method_248", at = @At("STORE"))
	private Chunk thelimit_customPopulator(Chunk chunk) {
		if (!thelimit_populate || chunk == null || chunk.method_875(0, 0, 0) == 0) return chunk;
		System.out.println("Need population!");
		TheLimitWorldgen.instance.decorate(TheLimitWorldgen.instance, chunk.x, chunk.z);
		chunk.method_876(0, 0, 0, 0);
		return chunk;
	}*/
}

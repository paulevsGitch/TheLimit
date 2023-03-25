package paulevs.thelimit.mixins.client;

import net.minecraft.client.render.WorldRenderer;
import net.minecraft.level.Level;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.thelimit.TheLimit;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	@Shadow private int field_1820;
	
	@Unique private boolean isTheLimit = false;
	
	@Inject(method = "method_1552", at = @At("HEAD"), cancellable = true)
	private void thelimit_disableWeatherRendering(float delta, CallbackInfo info) {
		if (isTheLimit) info.cancel();
	}
	
	@Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
	public void thelimit_renderSky(float delta, CallbackInfo info) {
		if (!isTheLimit) return;
		info.cancel();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glColor3f(0, 0, 0);
		
		GL11.glCallList(this.field_1820);
		
		GL11.glPushMatrix();
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glCallList(this.field_1820);
		GL11.glPopMatrix();
		
		GL11.glDisable(GL11.GL_FOG);
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	@Inject(method = "method_1546", at = @At("HEAD"))
	public void setClientLevel(Level level, CallbackInfo info) {
		isTheLimit = TheLimit.isTheLimit(level);
	}
}

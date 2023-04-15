package paulevs.thelimit.events;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.client.texture.atlas.ExpandableAtlas;
import paulevs.thelimit.TheLimit;
import paulevs.thelimit.blocks.TLBlocks;
import paulevs.thelimit.rendering.AutoTextureBlock;
import paulevs.thelimit.rendering.VoidFluidRenderer;

public class ClientEvents {
	@EventListener
	public void registerTextures(TextureRegisterEvent event) {
		ExpandableAtlas atlas = Atlases.getTerrain();
		TLBlocks.BLOCKS.forEach(block -> {
			if (block instanceof AutoTextureBlock) {
				((AutoTextureBlock) block).registerTextures(atlas);
			}
		});
		VoidFluidRenderer.voidFluid = atlas.addTexture(TheLimit.id("block/void_fluid")).index;
		VoidFluidRenderer.voidFluidEmission = atlas.addTexture(TheLimit.id("block/void_fluid_e")).index;
	}
}

package paulevs.thelimit.events;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.client.texture.atlas.ExpandableAtlas;
import paulevs.thelimit.TheLimit;
import paulevs.thelimit.blocks.TLBlocks;
import paulevs.thelimit.items.TLItems;
import paulevs.thelimit.rendering.AutoTextured;
import paulevs.thelimit.rendering.VoidFluidRenderer;

public class ClientEvents {
	@EventListener
	public void registerTextures(TextureRegisterEvent event) {
		final ExpandableAtlas blockAtlas = Atlases.getTerrain();
		TLBlocks.BLOCKS.forEach(block -> {
			if (block instanceof AutoTextured) {
				((AutoTextured) block).registerTextures(blockAtlas);
			}
		});
		
		VoidFluidRenderer.voidFluid = blockAtlas.addTexture(TheLimit.id("block/void_fluid")).index;
		VoidFluidRenderer.voidFluidEmission = blockAtlas.addTexture(TheLimit.id("block/void_fluid_e")).index;
		
		final ExpandableAtlas itemAtlas = Atlases.getGuiItems();
		TLItems.ITEMS.forEach(item -> {
			if (item instanceof AutoTextured) {
				((AutoTextured) item).registerTextures(itemAtlas);
			}
		});
	}
}

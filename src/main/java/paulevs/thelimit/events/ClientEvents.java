package paulevs.thelimit.events;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.client.texture.atlas.ExpandableAtlas;
import paulevs.thelimit.blocks.TLBlocks;
import paulevs.thelimit.rendering.AutoTextureBlock;

public class ClientEvents {
	@EventListener
	public void registerTextures(TextureRegisterEvent event) {
		ExpandableAtlas atlas = Atlases.getTerrain();
		TLBlocks.BLOCKS.forEach(block -> {
			if (block instanceof AutoTextureBlock) {
				((AutoTextureBlock) block).registerTextures(atlas);
			}
		});
	}
}

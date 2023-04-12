package paulevs.thelimit.events;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.item.tool.Pickaxe;
import net.minecraft.item.tool.ToolBase;
import net.minecraft.level.dimension.Dimension;
import net.modificationstation.stationapi.api.event.mod.PostInitEvent;
import net.modificationstation.stationapi.api.event.registry.AfterBlockAndItemRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.DimensionRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.registry.DimensionContainer;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import paulevs.thelimit.TheLimit;
import paulevs.thelimit.blocks.TLBlocks;
import paulevs.thelimit.config.Configs;
import paulevs.thelimit.items.TLItems;
import paulevs.thelimit.mixins.common.ToolBaseAccessor;
import paulevs.thelimit.world.dimension.TheLimitDimension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitEvents {
	@EventListener
	public void registerDimension(DimensionRegistryEvent event) {
		event.registry.register(TheLimit.id("the_limit"), new DimensionContainer<Dimension>(TheLimitDimension::new));
	}
	
	@EventListener
	public void registerBlocks(BlockRegistryEvent event) {
		TLBlocks.init();
	}
	
	@EventListener
	public void registerItems(ItemRegistryEvent event) {
		TLItems.init();
	}
	
	@EventListener
	public void applyTagFix(AfterBlockAndItemRegisterEvent event) {
		List<BlockBase> pickaxeBlocks = new ArrayList<>();
		
		TLBlocks.BLOCKS.forEach(block -> {
			if (block.material == Material.STONE || block.material == Material.METAL) pickaxeBlocks.add(block);
		});
		
		ItemRegistry.INSTANCE.forEach(item -> {
			if (item instanceof Pickaxe) {
				addToolList((ToolBase) item, pickaxeBlocks);
			}
		});
	}
	
	@EventListener
	public void postInit(PostInitEvent event) {
		Configs.saveAll();
	}
	
	private void addToolList(ToolBase tool, List<BlockBase> blocks) {
		ToolBaseAccessor accessor = (ToolBaseAccessor) tool;
		blocks.addAll(Arrays.asList(accessor.getEffectiveBlocks()));
		accessor.setEffectiveBlocks(blocks.toArray(BlockBase[]::new));
	}
}

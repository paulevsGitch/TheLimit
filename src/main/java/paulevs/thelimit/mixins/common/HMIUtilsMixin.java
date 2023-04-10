package paulevs.thelimit.mixins.common;

import net.minecraft.item.ItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.thelimit.blocks.TLBlocks;
import paulevs.thelimit.items.TLItems;

import java.util.ArrayList;

@Mixin(targets = "net.glasslauncher.hmifabric.TabUtils", remap = false)
public class HMIUtilsMixin {
	@Inject(method = "addHiddenModItems", at = @At("HEAD"))
	private static void thelimit_addHiddenModItems(ArrayList<ItemInstance> list, CallbackInfo info) {
		list.addAll(TLBlocks.BLOCKS.stream().map(ItemInstance::new).toList());
		list.addAll(TLItems.ITEMS.stream().map(ItemInstance::new).toList());
	}
}

package paulevs.thelimit.mixins.common;

import net.glasslauncher.hmifabric.Utils;
import net.minecraft.item.ItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.thelimit.blocks.TheLimitBlocks;

import java.util.ArrayList;

@Mixin(value = Utils.class, remap = false)
public class HMIUtilsMixin {
	// Don't work
	@Inject(method = "itemList", at = @At("RETURN"))
	private static void thelimit_itemList(CallbackInfoReturnable<ArrayList<ItemInstance>> info) {
		ArrayList<ItemInstance> list = info.getReturnValue();
		list.addAll(TheLimitBlocks.BLOCKS.stream().map(ItemInstance::new).toList());
	}
}
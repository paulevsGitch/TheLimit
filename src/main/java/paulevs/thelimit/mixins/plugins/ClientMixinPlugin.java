package paulevs.thelimit.mixins.plugins;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class ClientMixinPlugin implements IMixinConfigPlugin {
	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		if ("net.mine_diver.glsl.Shaders".equals(targetClassName)) {
			return FabricLoader.getInstance().isModLoaded("glsl");
		}
		else if ("net.minecraft.client.Minecraft".equals(targetClassName)) {
			return FabricLoader.getInstance().isDevelopmentEnvironment();
		}
		return true;
	}
	
	@Override
	public void onLoad(String mixinPackage) {}
	
	@Override
	public String getRefMapperConfig() {
		return null;
	}
	
	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}
	
	@Override
	public List<String> getMixins() {
		return null;
	}
	
	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
	
	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}

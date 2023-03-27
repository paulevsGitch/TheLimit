package paulevs.thelimit.blocks;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.block.BlockBase;
import net.minecraft.client.resource.language.TranslationStorage;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.mixin.lang.TranslationStorageAccessor;
import paulevs.thelimit.TheLimit;
import paulevs.thelimit.config.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

public class TheLimitBlocks {
	public static final List<BlockBase> BLOCKS = new ArrayList<>();
	
	public static final BlockBase GLAUCOLIT = make("glaucolit", StoneBlock::new);
	public static final BlockBase VITILIT = make("vitilit", StoneBlock::new);
	public static final BlockBase HYPHUM = make("hyphum", StoneBlock::new);
	
	public static final BlockBase STELLATA_LOG = make("stellata_log", LogBlock::new);
	public static final BlockBase STELLATA_BARK = make("stellata_bark", LogBlock::new);
	public static final BlockBase STELLATA_STEM = make("stellata_stem", StemBlock::new);
	public static final BlockBase STELLATA_BRANCH = make("stellata_branch", BranchBlock::new);
	public static final BlockBase STELLATA_FLOWER = make("stellata_flower", StellataFlowerBlock::new);
	
	public static final BlockBase STELLATA_PLANKS = make("stellata_planks", PlanksBlock::new);
	
	public static final BlockBase GUTTARBA_SHORT = make("guttarba_short", PlantBlock::new);
	public static final BlockBase GUTTARBA_NORMAL = make("guttarba_normal", PlantBlock::new);
	public static final BlockBase GUTTARBA_TALL = make("guttarba_tall", DoublePlantBlock::new);
	
	public static final BlockBase GLOW_PLANT = make("glow_plant", GlowingPlant::new);
	
	private static BlockBase make(String name, Function<Identifier, BlockBase> constructor) {
		Identifier id = TheLimit.id(name);
		BlockBase block = constructor.apply(id);
		block.setTranslationKey(name);
		BLOCKS.add(block);
		return block;
	}
	
	public static void init() {
		Properties translations = ((TranslationStorageAccessor) TranslationStorage.getInstance()).getTranslations();
		JsonObject translation = JsonUtil.readJson("/assets/thelimit/stationapi/lang/en_us.json");
		BLOCKS.forEach(block -> {
			String key = block.getTranslationKey();
			JsonElement element = translation.get(key);
			if (element == null) return;
			translations.put(key, element.getAsString());
		});
	}
}

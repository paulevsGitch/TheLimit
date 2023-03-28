package paulevs.thelimit.events;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.level.dimension.Dimension;
import net.modificationstation.stationapi.api.event.mod.PostInitEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.DimensionRegistryEvent;
import net.modificationstation.stationapi.api.registry.DimensionContainer;
import net.modificationstation.stationapi.api.util.math.BlockPos;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import paulevs.thelimit.TheLimit;
import paulevs.thelimit.blocks.TheLimitBlocks;
import paulevs.thelimit.config.Configs;
import paulevs.thelimit.dimension.InterpolationCell;
import paulevs.thelimit.dimension.IslandLayer;
import paulevs.thelimit.dimension.TheLimitDimension;
import paulevs.thelimit.noise.PerlinNoise;
import paulevs.thelimit.noise.VoronoiNoise;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import java.util.Random;

public class InitEvents {
	@EventListener
	public void registerDimension(DimensionRegistryEvent event) {
		event.registry.register(TheLimit.id("the_limit"), new DimensionContainer<Dimension>(TheLimitDimension::new));
	}
	
	@EventListener
	public void registerBlocks(BlockRegistryEvent event) {
		TheLimitBlocks.init();
	}
	
	@EventListener
	public void postInit(PostInitEvent event) {
		Configs.saveAll();
		//TheLimitBlocks.VOID_FLUID.initialised = true;
		//showIslands2();
		/*Properties translations = ((TranslationStorageAccessor) TranslationStorage.getInstance()).getTranslations();
		JsonObject translation = JsonUtil.readJson("/assets/thelimit/stationapi/lang/en_us.json");
		translation.entrySet().forEach(entry -> {
			String key = entry.getKey();
			String value = entry.getValue().getAsString();
			translations.put(key, value);
		});*/
		
		//Atlases.getTerrain().idToTex.keySet().forEach(System.out::println);
	}
	
	/*@EventListener
	public void textureInit(ResourcesReloadEvent event) {
		ExpandableAtlas atlas = Atlases.getTerrain();
		if (atlas == null) return;
		atlas.idToTex.keySet().forEach(System.out::println);
	}*/
	
	final PerlinNoise terrainNoise = new PerlinNoise(0);
	final VoronoiNoise islandNoise = new VoronoiNoise(0);
	final VoronoiNoise distortX = new VoronoiNoise(1);
	final VoronoiNoise distortY = new VoronoiNoise(2);
	final Random random = new Random(0);
	
	private void showIslands() {
		BufferedImage img = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
		int cx = img.getWidth() >> 1;
		int cy = img.getHeight() >> 1;
		
		int side = 16;
		InterpolationCell cell = new InterpolationCell(
			this::getDensity,
			1024 / side + 1,
			256 / side + 1,
			side, side, 0
		);
		cell.update(-512, -512);
		
		//side = 32;
		InterpolationCell cell2 = new InterpolationCell(
			this::getDensity,
			1024 / side + 1,
			256 / side + 1,
			side, side, 4
		);
		cell2.update(-512, -512);
		
		for (int x = 0; x < 1024; x ++) {
			int dx = x - 512;
			for (int z = 0; z < 1024; z ++) {
				int dz = z - 512;
				for (int y = 0; y < 256; y ++) {
					int dy = y - 128;
					int px = ((dz - dx) >> 1) + cx;
					int py = (-dy >> 1) + ((dx + dz) >> 2) + cy;
					
					if (px < 0 || py < 0 || px >= img.getWidth() || py >= img.getHeight()) continue;
					
					//float density = MathHelper.lerp(0.25F, cell.get(x, y, z), cell2.get(x, y, z));
					//float density = Math.max(cell.get(x, y, z), cell2.get(x, y, z));
					//float density = cell.get(x, y, z);
					//density += cell2.get(x, y, z);
					
					//if (density < 0.5F) continue;
					float density = MathHelper.lerp(0.5F, cell.get(x, y, z), cell2.get(x, y, z));
					
					if (density < 0.5F) continue;
					//if (cell2.get(x, y, z) < 0.45F) continue;
					
					int rgb = y;// << 1;
					rgb = 255 << 24 | rgb << 16 | ((y & 1) * 64) << 8 | (255 - rgb);
					
					img.setRGB(px, py, rgb);
				}
			}
		}
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new JLabel(new ImageIcon(img)));
		frame.pack();
		frame.setVisible(true);
	}
	
	private void showIslands2() {
		BufferedImage img = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
		int cx = img.getWidth() >> 1;
		int cy = img.getHeight() >> 1;
		
		InterpolationCell cell1 = new InterpolationCell(this::getDensity, 16, 0);
		InterpolationCell cell2 = new InterpolationCell(this::getDensity, 16, 8);
		
		boolean update = false;
		int chx = 100;
		int chz = 100;
		
		for (int x = 0; x < 1024; x ++) {
			int dx = x - 512;
			
			int ncx = x >> 4;
			if (ncx != chx) {
				update = true;
				chx = ncx;
			}
			
			for (int z = 0; z < 1024; z ++) {
				int dz = z - 512;
				
				int ncz = z >> 4;
				if (ncz != chz) {
					update = true;
					chz = ncz;
				}
				
				for (int y = 0; y < 256; y ++) {
					int dy = y - 128;
					int px = ((dz - dx) >> 1) + cx;
					int py = (-dy >> 1) + ((dx + dz) >> 2) + cy;
					
					if (px < 0 || py < 0 || px >= img.getWidth() || py >= img.getHeight()) continue;
					
					if (update) {
						update = false;
						cell1.update(chx << 4, chz << 4);
						cell2.update(chx << 4, chz << 4);
					}
					
					float density = MathHelper.lerp(0.5F, cell1.get(x & 15, y, z & 15), cell2.get(x & 15, y, z & 15));
					if (density < 0.5F) continue;
					int rgb = y;
					rgb = 255 << 24 | rgb << 16 | ((y & 1) * 64) << 8 | (255 - rgb);
					
					img.setRGB(px, py, rgb);
				}
			}
		}
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new JLabel(new ImageIcon(img)));
		frame.pack();
		frame.setVisible(true);
	}
	
	IslandLayer layer = new IslandLayer(random.nextInt(), 120, 200, 1.0F);
	IslandLayer layer2 = new IslandLayer(random.nextInt(), 80, 200, 0.75F);
	IslandLayer layer3 = new IslandLayer(random.nextInt(), 160, 200, 0.75F);
	
	private float getDensity(BlockPos pos) {
		float density = layer.getDensity(pos);
		if (density > 0.55F) return density;
		
		density = Math.max(density, layer2.getDensity(pos));
		if (density > 0.55F) return density;
		
		density = Math.max(density, layer3.getDensity(pos));
		return density;
	}
}

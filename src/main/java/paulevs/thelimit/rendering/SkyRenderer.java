package paulevs.thelimit.rendering;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.glsl.Shaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.maths.MathHelper;
import net.modificationstation.stationapi.api.util.math.Vec3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class SkyRenderer {
	private static final boolean USE_SHADERS = FabricLoader.getInstance().isModLoaded("glsl");
	private static final Vec3f FOG_COLOR = new Vec3f();
	private static final int GRADIENT_1;
	private static final int GRADIENT_2;
	private static final int STARS_1;
	private static final int STARS_2;
	private static final int SKY_BACK;
	private static final int SKY_GRADIENT;
	private static final int SKY_STRIPES;
	
	public static void render() {
		disable(GL11.GL_TEXTURE_2D);
		disable(GL11.GL_FOG);
		GL11.glDepthMask(false);
		
		GL11.glColor3f(FOG_COLOR.getX() * 0.1F, FOG_COLOR.getY() * 0.1F, FOG_COLOR.getZ() * 0.1F);
		GL11.glCallList(SKY_BACK);
		
		GL11.glColor3f(1, 0.5F, 1);
		GL11.glCallList(STARS_1);
		
		GL11.glColor3f(1, 0.8F, 1);
		GL11.glCallList(STARS_2);
		
		enable(GL11.GL_TEXTURE_2D);
		enable(GL11.GL_BLEND);
		
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, GRADIENT_1);
		GL11.glColor3f(1, 0.5F, 1);
		GL11.glColor3f(FOG_COLOR.getX(), FOG_COLOR.getY(), FOG_COLOR.getZ());
		GL11.glCallList(SKY_GRADIENT);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, GRADIENT_2);
		GL11.glColor4f(FOG_COLOR.getX() * 1.2F, FOG_COLOR.getY() * 1.2F, FOG_COLOR.getZ() * 1.2F, 0.1F);
		GL11.glCallList(SKY_STRIPES);
		
		disable(GL11.GL_BLEND);
		
		GL11.glDepthMask(true);
		enable(GL11.GL_TEXTURE_2D);
	}
	
	public static void setFogColor(float r, float g, float b) {
		FOG_COLOR.set(r, g, b);
	}
	
	private static void enable(int target) {
		if (USE_SHADERS) Shaders.glEnableWrapper(target);
		else GL11.glEnable(target);
	}
	
	private static void disable(int target) {
		if (USE_SHADERS) Shaders.glDisableWrapper(target);
		else GL11.glDisable(target);
	}
	
	private static int makeStars(int count, float size, int seed) {
		Tessellator tessellator = Tessellator.INSTANCE;
		
		Random random = new Random(seed);
		float pi2 = (float) (Math.PI * 2);
		
		int list = GL11.glGenLists(1);
		GL11.glNewList(list, GL11.GL_COMPILE);
		
		tessellator.start();
		
		for (short i = 0; i < count; ++i) {
			float preX = random.nextFloat() - 0.5F;
			float preY = random.nextFloat() - 0.5F;
			float preZ = random.nextFloat() - 0.5F;
			
			float scale = size * (1 + random.nextFloat());
			float dist = preX * preX + preY * preY + preZ * preZ;
			if (dist < 0.01F) continue;
			
			dist = 100F / MathHelper.sqrt(dist);
			preX *= dist;
			preY *= dist;
			preZ *= dist;
			
			float angle = (float) Math.atan2(preX, preZ);
			float sin1 = (float) Math.sin(angle);
			float cos1 = (float) Math.cos(angle);
			
			angle = (float) Math.atan2(Math.sqrt(preX * preX + preZ * preZ), preY);
			float sin2 = (float) Math.sin(angle);
			float cos2 = (float) Math.cos(angle);
			
			angle = random.nextFloat() * pi2;
			float sin3 = (float) Math.sin(angle);
			float cos3 = (float) Math.cos(angle);
			
			for (byte index = 0; index < 4; ++index) {
				float sx = ((index & 2) - 1) * scale;
				float sz = ((index + 1 & 2) - 1) * scale;
				
				float d23 = sx * cos3 - sz * sin3;
				float d24 = sz * cos3 + sx * sin3;
				float d26 = -d23 * cos2;
				
				float dx = d26 * sin1 - d24 * cos1;
				float dy = d23 * sin2;
				float dz = d24 * sin1 + d26 * cos1;
				
				tessellator.addVertex(preX + dx, preY + dy, preZ + dz);
			}
		}
		
		tessellator.draw();
		GL11.glEndList();
		
		return list;
	}
	
	private static int makeBox() {
		int list = GL11.glGenLists(1);
		GL11.glNewList(list, GL11.GL_COMPILE);
		
		Tessellator tessellator = Tessellator.INSTANCE;
		tessellator.start();
		
		tessellator.addVertex(-100,  100, -100);
		tessellator.addVertex( 100,  100, -100);
		tessellator.addVertex( 100,  100,  100);
		tessellator.addVertex(-100,  100,  100);
		
		tessellator.addVertex(-100, -100,  100);
		tessellator.addVertex( 100, -100,  100);
		tessellator.addVertex( 100, -100, -100);
		tessellator.addVertex(-100, -100, -100);
		
		tessellator.addVertex( 100, -100,  100);
		tessellator.addVertex( 100,  100,  100);
		tessellator.addVertex( 100,  100, -100);
		tessellator.addVertex( 100, -100, -100);
		
		tessellator.addVertex(-100, -100, -100);
		tessellator.addVertex(-100,  100, -100);
		tessellator.addVertex(-100,  100,  100);
		tessellator.addVertex(-100, -100,  100);
		
		tessellator.addVertex(-100,  100,  100);
		tessellator.addVertex( 100,  100,  100);
		tessellator.addVertex( 100, -100,  100);
		tessellator.addVertex(-100, -100,  100);
		
		tessellator.addVertex(-100, -100, -100);
		tessellator.addVertex( 100, -100, -100);
		tessellator.addVertex( 100,  100, -100);
		tessellator.addVertex(-100,  100, -100);
		
		tessellator.draw();
		GL11.glEndList();
		
		return list;
	}
	
	private static int makeSkyGradient() {
		float pi2 = (float) (Math.PI * 2);
		
		int list = GL11.glGenLists(1);
		GL11.glNewList(list, GL11.GL_COMPILE);
		Tessellator tessellator = Tessellator.INSTANCE;
		tessellator.start();
		
		final int count = 16;
		float sin1 = MathHelper.sin(0) * 100;
		float cos1 = MathHelper.cos(0) * 100;
		
		for (byte i = 1; i <= count; i++) {
			float angle = (float) i / count * pi2;
			float sin2 = MathHelper.sin(angle) * 100;
			float cos2 = MathHelper.cos(angle) * 100;
			
			tessellator.vertex(sin1, -50, cos1, 0, 0);
			tessellator.vertex(sin1,  50, cos1, 0, 1);
			tessellator.vertex(sin2,  50, cos2, 1, 1);
			tessellator.vertex(sin2, -50, cos2, 1, 0);
			
			sin1 = sin2;
			cos1 = cos2;
		}
		
		tessellator.draw();
		GL11.glEndList();
		
		return list;
	}
	
	private static int makeSkyStripes() {
		float pi2 = (float) (Math.PI * 2);
		Random random = new Random(0);
		
		int list = GL11.glGenLists(1);
		GL11.glNewList(list, GL11.GL_COMPILE);
		Tessellator tessellator = Tessellator.INSTANCE;
		tessellator.start();
		
		for (short i = 0; i < 200; i++) {
			float angle = random.nextFloat() * pi2;
			float height = random.nextFloat() * 25 + 25;
			float width = random.nextFloat() * 0.02F + 0.05F;
			
			float sin1 = MathHelper.sin(angle) * 100;
			float cos1 = MathHelper.cos(angle) * 100;
			float sin2 = MathHelper.sin(angle + width) * 100;
			float cos2 = MathHelper.cos(angle + width) * 100;
			
			tessellator.vertex(sin1, -height, cos1, 0, 0);
			tessellator.vertex(sin1,  height, cos1, 0, 1);
			tessellator.vertex(sin2,  height, cos2, 1, 1);
			tessellator.vertex(sin2, -height, cos2, 1, 0);
		}
		
		tessellator.draw();
		GL11.glEndList();
		
		return list;
	}
	
	static {
		STARS_1 = makeStars(1500, 0.125F, 0);
		STARS_2 = makeStars(800, 0.1F, 1);
		SKY_BACK = makeBox();
		SKY_GRADIENT = makeSkyGradient();
		SKY_STRIPES = makeSkyStripes();
		
		TextureManager textureManager = ((Minecraft) FabricLoader.getInstance().getGameInstance()).textureManager;
		GRADIENT_1 = textureManager.getTextureId("/assets/thelimit/stationapi/textures/environment/gradient_1.png");
		GRADIENT_2 = textureManager.getTextureId("/assets/thelimit/stationapi/textures/environment/gradient_2.png");
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, GRADIENT_1);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL11.GL_FALSE);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, GRADIENT_2);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL11.GL_FALSE);
	}
}

package globj.objects.textures;


import java.nio.ByteBuffer;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL42;

import annotations.GLVersion;
import globj.core.Context;
import globj.core.Window;
import globj.objects.BindTracker;
import globj.objects.framebuffers.FBOAttachable;
import globj.objects.framebuffers.values.FBOAttachment;
import globj.objects.textures.values.CubemapTarget;
import globj.objects.textures.values.ImageDataType;
import globj.objects.textures.values.ImageFormat;
import globj.objects.textures.values.TextureFormat;
import globj.objects.textures.values.TextureTarget;
import lwjgl.debug.GLDebug;



@NonNullByDefault
public final class TextureCubemap extends GLTexture2D implements FBOAttachable {
	
	private int		s, basemap, maxmap;
	private boolean	seamless;
	
	private TextureCubemap(String name, TextureFormat texformat) {
		super(name, texformat, TextureTarget.TEXTURE_CUBEMAP);
	}
	
	@Nullable
	protected static TextureCubemap create(String name, TextureFormat texformat, int size, int mipmaps) {
		return create(name, texformat, size, 0, mipmaps - 1);
	}
	
	@Nullable
	protected static TextureCubemap create(String name, TextureFormat texformat, int size, int basemap, int maxmap) {
		TextureCubemap tex = new TextureCubemap(name, texformat);
		if (tex.id == 0) {
			GLDebug.glObjError(TextureCubemap.class, name, "Cannot create", "No ID could be allocated");
			return null;
		}
		
		int max = Context.intConst(GL13.GL_MAX_CUBE_MAP_TEXTURE_SIZE);
		if (!checkBounds(new int[] { size, size }, new int[] { max, max }, tex))
			return null;
			
		tex.s = size;
		tex.basemap = Math.min(Math.max(0, basemap), levels(size));
		tex.maxmap = Math.min(Math.max(tex.basemap, maxmap), levels(size));
		
		tex.bind();
		setMipmaps(tex.target, tex.basemap, tex.maxmap);
		if (Window.versionCheck(4, 2)) {
			GL42.glTexStorage2D(tex.target.value(), tex.maxmap + 1, texformat.value(), size, size);
		}
		else {
			int s = Math.max(1, size >> tex.basemap);
			for (int i = tex.basemap; i <= tex.maxmap; i++) {
				for (CubemapTarget target : CubemapTarget.values())
					GL11.glTexImage2D(target.value(), i, texformat.value(), s, s, 0, texformat.base(), ImageDataType.UBYTE.value(), (ByteBuffer) null);
				s = Math.max(1, s / 2);
			}
		}
		tex.undobind();
		return tex;
	}
	
	/**************************************************
	 ********************** Bind **********************
	 **************************************************/
	
	private static final BindTracker bindTracker = new BindTracker();
	
	@Override
	protected BindTracker bindingTracker() {
		return bindTracker;
	}
	
	/**************************************************/
	
	/*
	 * XXX When is this core? Latest 4.4?
	 */
	@GLVersion({ 4, 4, })
	public void makeSeamless(boolean seamless) {
		if (Window.versionCheck(4, 4)) {
			bind();
			GL11.glTexParameteri(target.value(), GL32.GL_TEXTURE_CUBE_MAP_SEAMLESS, (this.seamless = seamless) ? 1 : 0);
			undobind();
		}
		else
			GLDebug.glWarning("Cannot use per texture seamless cubemaps. Version 4.4 required.");
	}
	
	/**
	 * Sets the texel data in specified rectangle of mipmap level. Texture needs to be initialized with
	 * {@link #initializeTexture(int, int, int, TextureFormat)}. Rectangle must be within the bounds of the texture.
	 * [GL_TEXTURE_BASE_LEVEL + map].
	 */
	public void setData(int x, int y, int w, int h, int map, ImageFormat format, ImageDataType type, ByteBuffer data) {
		bind();
		GL11.glTexSubImage2D(target.value(), map, x, y, w, h, format.value(), type.value(), data);
		undobind();
	}
	
	/**************************************************
	 ****************** FBOAttachable *****************
	 **************************************************/
	
	/**
	 * @param level
	 *            mipmap level.
	 * @param layer
	 *            cubemap face. Use {@link CubemapTarget#layer}.
	 */
	@Override
	public void attachToFBO(FBOAttachment attachment, int level, int layer) {
		GL30.glFramebufferTexture2D(GL30.GL_DRAW_FRAMEBUFFER, attachment.value(), target.value(), id, level);
	}
	
	/**************************************************
	 ********************** Debug *********************
	 **************************************************/
	
	@Override
	public void debug() {
		GLDebug.writef(GLDebug.ATTRIB_STRING + "\t(%d x %d)", target + ":", name, s, s);
		//#formatter:off
		GLDebug.indent();
			GLDebug.writef(GLDebug.ATTRIB_STRING, "Texture Format:", texformat);
			if (Window.versionCheck(4, 4))
				GLDebug.writef(GLDebug.ATTRIB_STRING, "Seamless Cubemap:", seamless);
			if (minFilter.mipmaps() && maxmap > 0)
				GLDebug.writef(GLDebug.ATTRIB + "[%d, %d]", "Mipmap Range:", basemap, maxmap);
			super.debug();
		GLDebug.unindent();
		//#formatter:on
	}
	
	@Override
	public void debugQuery() {
		GLDebug.flushErrors();
		GLDebug.writef(GLDebug.ATTRIB_STRING+ "\t(%d x %d)", target + ":", name, GL11.glGetTexLevelParameteri(target.value(), 0, GL11.GL_TEXTURE_WIDTH),
						GL11.glGetTexParameteri(target.value(), GL11.GL_TEXTURE_HEIGHT));
		//#formatter:off
		GLDebug.indent();
			GLDebug.writef(GLDebug.ATTRIB_STRING, "Texture Format:", TextureFormat.get(GL11.glGetTexLevelParameteri(target.value(), 0, GL11.GL_TEXTURE_INTERNAL_FORMAT)));
			if (Window.versionCheck(4, 4))
				GLDebug.writef(GLDebug.ATTRIB_STRING, "Seamless Cubemap:", GL11.glGetTexParameteri(target.value(), GL32.GL_TEXTURE_CUBE_MAP_SEAMLESS));
			if (minFilter.mipmaps() && maxmap > 0)
				GLDebug.writef(GLDebug.ATTRIB + "[%d, %d]", "Mipmap Range:", GL11.glGetTexParameteri(target.value(), GL12.GL_TEXTURE_BASE_LEVEL), 
				               GL11.glGetTexParameteri(target.value(), GL12.GL_TEXTURE_MAX_LEVEL));
			super.debugQuery();
		GLDebug.unindent();
		//#formatter:on
		GLDebug.flushErrors();
	}
	
}

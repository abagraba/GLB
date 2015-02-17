package globj.objects.textures;

import globj.objects.textures.values.TextureFormat;
import globj.objects.textures.values.TextureTarget;
import globj.objects.textures.values.TextureWrap;

import org.lwjgl.opengl.GL11;

import lwjgl.core.states.State;

public abstract class GLTexture2D extends GLTexture{
	
	protected GLTexture2D(String name, TextureFormat texformat, TextureTarget target) {
		super(name, texformat, target);
	}

	protected final State<TextureWrap> sWrap = new State<TextureWrap>("S Wrap", TextureWrap.REPEAT);
	protected final State<TextureWrap> tWrap = new State<TextureWrap>("T Wrap", TextureWrap.REPEAT);

	@Override
	protected void resolveStates(){
		super.resolveStates();
		if (!sWrap.resolved()){
			GL11.glTexParameteri(target.value, GL11.GL_TEXTURE_WRAP_S, sWrap.state().value);
			sWrap.resolve();
		}
		if (!tWrap.resolved()){
			GL11.glTexParameteri(target.value, GL11.GL_TEXTURE_WRAP_T, tWrap.state().value);
			tWrap.resolve();
		}
	}
	
	/**
	 * Sets the wrap mode for each axis of the texture. Excess arguments are
	 * ignored.
	 * 
	 * @param s
	 *            texture edge wrap mode on the s-axis. Mandatory.
	 * @param t
	 *            texture edge wrap mode on the t-axis. Can be null.
	 */
	public void setWrap(TextureWrap s, TextureWrap t) {
		sWrap.setState(s);
		tWrap.setState(t);
	}

	
}
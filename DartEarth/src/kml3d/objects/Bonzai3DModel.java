package kml3d.objects;

import gov.nasa.worldwind.Movable;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Quaternion;
import gov.nasa.worldwind.geom.Vec4;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.render.DrawContext;
import gov.nasa.worldwind.render.Renderable;
import gov.nasa.worldwind.util.Logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.nio.FloatBuffer;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import jouvieje.engine.BonzaiEngine;
import jouvieje.engine.WorkerDispatcher;
import jouvieje.io.IStreamCallback;
import jouvieje.io.Stream;
import jouvieje.model.Model;
import jouvieje.model.asset.AssetManager;
import jouvieje.model.asset.AsyncModel;
import jouvieje.model.reader.LightingMode;
import jouvieje.model.reader.ModelReaderSettings;
import jouvieje.model.util.Optimizer;
import jouvieje.opengl.GLc;
import jouvieje.opengl.jsr231.Jsr231Api;
import jouvieje.renderer.RenderOptions;
import jouvieje.renderer.RendererPipeline;
import jouvieje.texture.TextureList;
import jouvieje.texture.TextureLoader;
import jouvieje.texture.TextureParam;
import kml3d.ogc.kml.SimpleNamespaceContext;

import org.xml.sax.InputSource;

/**
 * @author R.Wathelet, most of the code is from RodgersGB Model3DLayer class see
 *         https://joglutils.dev.java.net/ modified by eterna2 modified by
 *         R.Wathelet adding the Adjustable
 */
public class Bonzai3DModel implements Renderable, Movable,
		Cloneable {
	private static Jsr231Api opengl;
	private static GLAutoDrawable drawable;
	
	public static void init(GLAutoDrawable drawable) {
		Bonzai3DModel.drawable = drawable;
		opengl = new Jsr231Api(null, null);
		
		BonzaiEngine.get().setReaderDaeFixBadsidedNormals(true);
		if(BonzaiEngine.get().getWorker() == null) {
			// Set number of thread Bonzai Engine will use internally
			BonzaiEngine.get().setWorker(new WorkerDispatcher(2));
		}
	}

	/* Model */
	protected AssetManager assets;
	private AsyncModel asyncModel;
	private final RenderOptions renderOptions;
	private ModelReaderSettings settings;
	
	private Position position;
	private double yaw = 0.0;
	private double roll = 0.0;
	private double pitch = 0.0;
	private boolean keepConstantSize = true;
	private Vec4 referenceCenterPoint;
	private Globe globe;
	private double size = 1.0;
	private double scalex = 1.0;
	private double scaley = 1.0;
	private double scalez = 1.0;
	private int altitudeMode = 0;
	private double elevation;

	private double unitScale = 1.0;

	private double distanceToModel = 500000;

	public final static int ABSOLUTE_MODE = 1;
	public final static int CLAMP_TO_GROUND_MODE = 2;
	public final static int RELATIVE_TO_GROUND_MODE = 3;

	@Override
	public Object clone() throws CloneNotSupportedException {
		Bonzai3DModel clone = new Bonzai3DModel();
		clone.assets = assets;
		clone.asyncModel = asyncModel;
		clone.settings = settings;
		clone.globe = globe;
		clone.unitScale = unitScale;
		return clone;
	}
	private Bonzai3DModel() {
		renderOptions = new RenderOptions();
	}

	public Bonzai3DModel(String path, Position pos) {
		settings = new ModelReaderSettings();
		settings.stream = new Stream(path);
		settings.lighting = LightingMode.Default;
		settings.optimizer = Optimizer.getBestOptions(settings.animated);

		String ext = settings.stream.getPath().substring(settings.stream.getPath()
				.lastIndexOf('.') + 1);
		if (ext.equalsIgnoreCase("dae")) {
			parseUnitScale();
		}
		settings.modelFormatExt = ext;
		
		// Manage model and texture asynchronous loading
		assets = new AssetManager();
		assets.textures = new TextureList(new TextureLoader());
		assets.setReadTextureAsync(true); // Auto asynchronous texture load
		initStreamCallback();
		
		TextureParam textureParam = new TextureParam();
		textureParam.setMipmap(true);
		textureParam.setAnisotropicDegree(4);
		assets.textures.setDefaultTextureParam(textureParam);
		
		asyncModel = new AsyncModel(assets, settings);
		
		renderOptions = new RenderOptions();
		
		this.setPosition(pos);
	}

	public Bonzai3DModel(String path, Position pos, double size) {
		this(path, pos);
		this.setSize(size);
	}
	
	public double getScalex() {
		return scalex;
	}

	public void setScalex(double scalex) {
		this.scalex = scalex;
	}

	public double getScaley() {
		return scaley;
	}

	public void setScaley(double scaley) {
		this.scaley = scaley;
	}

	public double getScalez() {
		return scalez;
	}

	public void setScalez(double scalez) {
		this.scalez = scalez;
	}

	private void parseUnitScale() {
		XPathFactory xpFactory = XPathFactory.newInstance();
		XPath xpath = xpFactory.newXPath();

		SimpleNamespaceContext nsc = new SimpleNamespaceContext();

		xpath.setNamespaceContext(nsc);

		InputSource source;
		try {
			System.out.println("3D model file : " + settings.stream);
			source = new InputSource(new FileReader(settings.stream.getPath()));

			String result = (String) xpath.evaluate(
					"//col:asset/col:unit/@meter", source,
					XPathConstants.STRING);
			if (result != null && result.length()>0) {
				System.out.println("3D model unitScale : " + result);
				unitScale = Double.parseDouble(result);
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void render(DrawContext dc) {
		opengl.update(drawable);
		
		// Move texture read in cpu to memory to gpu
		if(assets.textures.hasPendingLoading(opengl.getGL())) {
			assets.textures.loadTextures(opengl.getGL());
		}

		this.globe = dc.getGlobe();

		Vec4 loc = computeLoc(dc);

		if ((distanceToModel*scalez > 0 && dc.getView().getEyePoint().distanceTo3(loc) < distanceToModel*scalez)) {
			
			boolean renderTexture = false;
			if (distanceToModel > 0 && dc.getView().getEyePoint().distanceTo3(loc) < distanceToModel/8) {
				renderTexture = true;
			}
			
			// Asynchronous request of the Model
			final Model model = asyncModel.get();
			if(model != null) {
				try {
					beginDraw(dc);
					
					draw(dc, model, renderTexture);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					endDraw(dc);
				}
			}
		}
	}

	protected void initStreamCallback() {
//		System.out.println("fail!!!!");
		assets.getTextureFinder().setStreamCallback(new IStreamCallback() {
			public Stream open(StreamOpenState state,
					Stream stream) {
//				System.out.println("open");
				switch (state) {
				case NotFound:
					// In case the texture has not been found,
					// search for a file with another extension
//					try {
//						System.out.println("NotFound "
//								+ stream.buildURL());
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
					String path = stream.getPath();
//					System.out.println("path " + path);
					if (path == null)
						return stream;

					int index2 = path
							.lastIndexOf(File.separator);
					String textureName = path.substring(
							index2 + 1, path.length());
					String base2 = path.substring(0, index2);

					int index3 = base2
							.lastIndexOf(File.separator);
					String base3 = base2.substring(0, index3);
					File f = new File(base2);
					
					String fixedName = base3 + File.separator
					+ "images" + File.separator
					+ textureName;
//					System.out.println("fixedName : "
//							+ fixedName);
					Stream fixedStream = new Stream(fixedName);
					if (fixedStream.exists()) {
						return fixedStream;
					}
					
					String [] subDirList = f.list(new FilenameFilter() {
						
						@Override
						public boolean accept(File dir, String name) {
							File fi = new File(dir+File.separator+name);
							return fi.isDirectory();
						}
					});
					
					for(String dir : subDirList) {
						fixedName = base2 + File.separator
						+ dir + File.separator
						+ textureName;
						System.out.println("fixedName : "
								+ fixedName);
						fixedStream = new Stream(fixedName);
						if (fixedStream.exists()) {
							return fixedStream;
						}
					}

					int index = path.lastIndexOf(".");
					if (index <= 0)
						return stream;

					final String base = path.substring(0,
							index + 1);
					for (String ext : ImageIO
							.getReaderFormatNames()) {
						fixedName = base + ext;
						fixedStream = new Stream(fixedName);
						if (fixedStream.exists()) {
							return fixedStream;
						}
					}
					System.out.println("fail!!!!");
					return stream;

				case Opening:
//					try {
//						System.out.println("Opening "
//								+ stream.buildURL());
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
					return stream;

				case OpenFailed:
//					try {
//						System.out.println("OpenFailed "
//								+ stream.buildURL());
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
					System.out.println("fail!!!!");
					return stream;

				case Opened:
//					try {
//						System.out.println("Opened "
//								+ stream.buildURL());
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
					return stream;

				default:
					return stream;
				}
			}

			@Override
			public void close(StreamCloseState state,
					Stream stream) {
				switch (state) {
				case CloseFailed:
//					try {
//						System.out.println("CloseFailed "
//								+ stream.buildURL());
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
				case Closed:
//					try {
//						System.out.println("Closed "
//								+ stream.buildURL());
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
				}

			}
		});
	}
	
	private void draw(DrawContext dc, Model model, boolean renderTexture) {
		GL gl = dc.getGL();
		this.globe = dc.getGlobe();
		
		Vec4 loc = computeLoc(dc);
		
		double localSize = this.computeSize(dc, loc) * unitScale;
		this.computeReferenceCenter(dc);

		dc.getView().pushReferenceCenter(dc, loc);
		gl.glRotated(position.getLongitude().degrees, 0, 1, 0);
		gl.glRotated(-position.getLatitude().degrees, 1, 0, 0);
		gl.glRotated(pitch, 1, 0, 0);
		gl.glRotated(roll, 0, 1, 0);
		gl.glRotated(yaw, 0, 0, 1);
		gl.glScaled(localSize*scalex, localSize*scaley, localSize*scalez);
		this.renderModel(dc, model, renderTexture);
		dc.getView().popReferenceCenter(dc);
	}

	private void renderModel(DrawContext dc, Model model, boolean renderTexture) {
		long timePassedMillis = 0; // For animated models
		model.update(timePassedMillis);
		GL gl = dc.getGL();
		
		boolean previousGL_LIGHTING = gl.glIsEnabled(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_LIGHTING);
		float global_ambient [] = { 0.5f, 0.5f, 0.5f, 1.0f };
		gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(global_ambient));
		boolean previousGL_LIGHT0 = dc.getGL().glIsEnabled(GL.GL_LIGHT0);
		dc.getGL().glEnable(GL.GL_LIGHT0);
		
		if(renderTexture) {
			renderOptions.setRenderMode(RenderOptions.Solid);
		} else {
			renderOptions.setRenderMode(RenderOptions.Wired);
		}
		/*
		 * Bonzai engine does not used OpenGL matrix stack (deprecated in recent version of OpenGL).
		 * WWJ uses OpenGL matrix stack, and therefore matrices must be sync'ed using:
		 * 		glc.syncFromContext(opengl)
		 * or better with:
		 * 		glc.syncFromMatrices(projectionMatrix, modelviewMatrix)
		 * 
		 * Note: Above code is not needed when using GLc.getModelview() and GLc.getProjection() stacks.
		 */
		final GLc glc = opengl.getGL().getGLc();
		glc.syncFromContext(opengl.getGL());
		
		RendererPipeline.render(opengl, model, renderOptions);
		
		if(!previousGL_LIGHT0) dc.getGL().glDisable(GL.GL_LIGHT0);	
		if(!previousGL_LIGHTING) dc.getGL().glDisable(GL.GL_LIGHTING);	
	}

	private Vec4 computeLoc(DrawContext dc) {
		Angle lat = this.getPosition().getLatitude();
		Angle lon = this.getPosition().getLongitude();
		
		switch(altitudeMode) {
			case ABSOLUTE_MODE :
				return dc.getGlobe().computePointFromPosition(
						lat,
						lon,
						elevation);
			case RELATIVE_TO_GROUND_MODE:
				return dc.getGlobe().computePointFromPosition(
						lat,
						lon,
						dc.getGlobe().getElevation(lat, lon)
								* dc.getVerticalExaggeration()+elevation);
			case CLAMP_TO_GROUND_MODE:
			default:
				return dc.getGlobe().computePointFromPosition(
						lat,
						lon,
						dc.getGlobe().getElevation(lat, lon)
								* dc.getVerticalExaggeration());
		}
	}

	private void computeReferenceCenter(DrawContext dc) {
		this.referenceCenterPoint = this
				.computeTerrainPoint(dc, this.getPosition().getLatitude(), this
						.getPosition().getLongitude());
	}

	private Vec4 computeTerrainPoint(DrawContext dc, Angle lat, Angle lon) {
		Vec4 p = dc.getSurfaceGeometry().getSurfacePoint(lat, lon);
		if (p == null) {
			p = dc.getGlobe().computePointFromPosition(
					lat,
					lon,
					dc.getGlobe().getElevation(lat, lon)
							* dc.getVerticalExaggeration());
		}
		return p;
	}

	private double computeSize(DrawContext dc, Vec4 loc) {
		if (this.keepConstantSize) {
			return size;
		}
		if (loc == null) {
			System.err.println("Null location when computing size of model");
			return 1;
		}
		double d = loc.distanceTo3(dc.getView().getEyePoint());
		double newSize = 60 * dc.getView().computePixelSizeAtDistance(d);
		if (newSize < 2) {
			newSize = 2;
		}
		return newSize;
	}

	// puts opengl in the correct state for this layer
	protected void beginDraw(DrawContext dc) {
		GL gl = dc.getGL();
		Vec4 cameraPosition = dc.getView().getEyePoint();
		gl.glPushAttrib(GL.GL_TEXTURE_BIT | GL.GL_COLOR_BUFFER_BIT
				| GL.GL_DEPTH_BUFFER_BIT | GL.GL_HINT_BIT | GL.GL_POLYGON_BIT
				| GL.GL_ENABLE_BIT | GL.GL_CURRENT_BIT | GL.GL_LIGHTING_BIT
				| GL.GL_TRANSFORM_BIT);
		// float[] lightPosition = {0F, 100000000f, 0f, 0f};
		float[] lightPosition = { (float) (cameraPosition.x + 1000),
				(float) (cameraPosition.y + 1000),
				(float) (cameraPosition.z + 1000), 1.0f };
		/** Ambient light array */
		float[] lightAmbient = { 0.4f, 0.4f, 0.4f, 0.4f };
		/** Diffuse light array */
		float[] lightDiffuse = { 1.0f, 1.0f, 1.0f, 1.0f };
		/** Specular light array */
		float[] lightSpecular = { 1.0f, 1.0f, 1.0f, 1.0f };
		float[] model_ambient = { 0.5f, 0.5f, 0.5f, 1.0f };
		gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT, model_ambient, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPosition, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, lightDiffuse, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, lightAmbient, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, lightSpecular, 0);
		gl.glDisable(GL.GL_LIGHT0);
		gl.glEnable(GL.GL_LIGHT1);
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_NORMALIZE);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glPushMatrix();
	}

	// resets opengl state
	protected void endDraw(DrawContext dc) {
		GL gl = dc.getGL();
		gl.glMatrixMode(javax.media.opengl.GL.GL_MODELVIEW);
		gl.glPopMatrix();
		gl.glPopAttrib();
	}

	public Position getReferencePosition() {
		return this.getPosition();
	}

	public void move(Position delta) {
		if (delta == null) {
			String msg = Logging.getMessage("nullValue.PositionDeltaIsNull");
			Logging.logger().severe(msg);
			throw new IllegalArgumentException(msg);
		}
		this.moveTo(this.getReferencePosition().add(delta));
	}

	public void moveTo(Position position) {
		if (position == null) {
			String msg = Logging.getMessage("nullValue.PositionIsNull");
			Logging.logger().severe(msg);
			throw new IllegalArgumentException(msg);
		}
		Vec4 newRef = this.globe.computePointFromPosition(position);
		Angle distance = LatLon.greatCircleDistance(this.getPosition()
				, position);
		Vec4 axis = this.referenceCenterPoint.cross3(newRef).normalize3();
		Vec4 p = this.globe.computePointFromPosition(this.getPosition());
		p = p.transformBy3(Quaternion.fromAxisAngle(distance, axis));
		this.position = this.globe.computePositionFromPoint(p);
	}

	public boolean isConstantSize() {
		return keepConstantSize;
	}

	public void setKeepConstantSize(boolean val) {
		this.keepConstantSize = val;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public AsyncModel getModel() {
		return asyncModel;
	}

	public double getYaw() {
		return yaw;
	}

	public void setYaw(double val) {
		this.yaw = val;
	}

	public double getRoll() {
		return roll;
	}

	public void setRoll(double val) {
		this.roll = val;
	}

	public double getPitch() {
		return pitch;
	}

	public void setPitch(double val) {
		this.pitch = val;
	}

	public int getAltitudeMode() {
		return altitudeMode;
	}

	public void setAltitudeMode(int altitudeMode) {
		this.altitudeMode = altitudeMode;
	}

	public double getElevation() {
		return elevation;
	}

	public void setElevation(double elevation) {
		this.elevation = elevation;
	}
	
	public double getDistanceToModel() {
		return distanceToModel;
	}

	public void setDistanceToModel(double distanceToModel) {
		this.distanceToModel = distanceToModel;
	}
}

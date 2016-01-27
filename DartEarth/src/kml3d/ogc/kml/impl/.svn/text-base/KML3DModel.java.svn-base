package kml3d.ogc.kml.impl;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.ogc.kml.KMLModel;
import gov.nasa.worldwind.ogc.kml.KMLRoot;
import gov.nasa.worldwind.ogc.kml.impl.KMLRenderable;
import gov.nasa.worldwind.ogc.kml.impl.KMLTraversalContext;
import gov.nasa.worldwind.ogc.kml.impl.KMLUtil;
import gov.nasa.worldwind.render.DrawContext;

import java.io.File;
import java.io.FilenameFilter;

import javax.imageio.ImageIO;

import jouvieje.io.IStreamCallback;
import jouvieje.io.Stream;
import kml3d.objects.Bonzai3DModel;

public class KML3DModel extends Bonzai3DModel implements KMLRenderable {

	private KMLRoot root;

	public KML3DModel(KMLTraversalContext tc, KMLModel model) {
		super(getPath(tc,model), getPos(tc,model));
		this.setAltitudeMode(KMLUtil.convertAltitudeMode(model.getAltitudeMode()));
		this.setScalex(model.getScale().getX());
		this.setScaley(model.getScale().getY());
		this.setScalez(model.getScale().getZ());
		this.setPitch(model.getOrientation().getTilt());
		this.setRoll(model.getOrientation().getRoll());
		this.setYaw(model.getOrientation().getHeading());
		this.setElevation(model.getLocation().getAltitude());
		this.root =  model.getRoot();
		
//		if(model.getParent() instanceof KMLPlacemark) {
//			((KMLPlacemark)model.getParent()).get
//		}
//		System.out.println("RANGE : "+range);
//		if(range>0) {
//			currentModel.setDistanceToModel(range*50);
//		}
	}

	private static Position getPos(KMLTraversalContext tc, KMLModel model) {
		return Position.fromDegrees(model.getLocation().getLatitude(), model.getLocation().getLongitude(), model.getLocation().getAltitude());
	}

	private static String getPath(KMLTraversalContext tc, KMLModel model) {
		String href = model.getLink().getHref();
//        String localAddress = (String) model.getRoot().resolveLocalReference(href, null);
		String localAddress = (String) model.getRoot().resolveReference(href);
		System.out.println(localAddress);
		return localAddress;
	}
	
	@Override
	protected void initStreamCallback() {
		this.assets.getTextureFinder().setStreamCallback(new IStreamCallback() {
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
					
					String localAddress = (String) root.resolveReference(path);
					
					Stream resolvedStream = new Stream(localAddress);
					if (resolvedStream.exists()) {
						return resolvedStream;
					}

					int index2 = path
							.lastIndexOf(File.separator);
					String textureName = path.substring(
							index2 + 1, path.length());
					String base2 = path.substring(0, index2);

					int index3 = base2
							.lastIndexOf(File.separator);
					String base3 = base2.substring(0, index3);
					File f = new File(base2);
					
					String fixedName = 
					"images" + "/"
					+ textureName;
//					System.out.println("fixedName : "
//							+ fixedName);
					Stream fixedStream = new Stream(fixedName);
					if (fixedStream.exists()) {
						return fixedStream;
					} else {
						localAddress = (String) root.resolveReference(fixedName);
						resolvedStream = new Stream(localAddress);
						if (resolvedStream.exists()) {
							return resolvedStream;
						} else  {
							fixedName = 
								"files" + "/"
								+ textureName;
							localAddress = (String) root.resolveReference(fixedName);
							resolvedStream = new Stream(localAddress);
							if (resolvedStream.exists()) {
								return resolvedStream;
							}
						}
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
//					System.out.println("fail!!!!");
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

	@Override
	public void preRender(KMLTraversalContext tc, DrawContext dc) {
		
	}

	@Override
	public void render(KMLTraversalContext tc, DrawContext dc) {
		this.render(dc);
	}

}

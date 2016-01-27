package org.gfg.dartearth.layer.kml3d;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.cache.BasicDataFileStore;
import gov.nasa.worldwind.cache.FileStore;
import gov.nasa.worldwind.exception.WWRuntimeException;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.ogc.kml.impl.KMLController;
import gov.nasa.worldwind.util.WWIO;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import kml3d.event.ViewMoveEvent;
import kml3d.event.ViewMoveListener;
import kml3d.objects.Bonzai3DModel;

import org.gfg.dartearth.core.ApplicationTemplate.AppFrame;

public class Kml3DLayer extends RenderableLayer {

	private AppFrame appFrame;
	private final Object fileLock = new Object();
	private double deltDegree = 36;
	private int trimLevel = 8;
	// protected HotSpotController hotSpotController;
	// protected KMLApplicationController kmlAppController;
	// protected BalloonController balloonController;

	// private HashMap<String, ModelTile> tileCache = new HashMap<String,
	// ModelTile>();
	// protected final LevelSet levels;
	protected String tileCountName;
	private ExecutorService threadPool = Executors.newSingleThreadExecutor();
	private Kml3DDataFileStore filestore = new Kml3DDataFileStore(this);

	// private HashSet<KMLController> kmlControllers;

	private HashMap<Object, KMLController> kmlControllerMap = new HashMap<Object, KMLController>();

	private Timer timer = new Timer();

	// protected ArrayList<TextureTile> topLevels;

	// public KmlLayer(LevelSet levelSet)
	// {
	// if (levelSet == null)
	// {
	// String message = Logging.getMessage("nullValue.LevelSetIsNull");
	// Logging.logger().severe(message);
	// throw new IllegalArgumentException(message);
	// }
	//
	// this.levels = new LevelSet(levelSet); // the caller's levelSet may change
	// internally, so we copy it.
	//
	// this.setPickEnabled(false); // textures are assumed to be terrain unless
	// specifically indicated otherwise.
	// this.tileCountName = this.getName() + " Tiles";
	// }

	public Kml3DLayer(final AppFrame appFrame) {
		this.addRenderables(kmlControllerMap.values());
		this.appFrame = appFrame;
		this.setMaxActiveAltitude(4000d);

		Bonzai3DModel.init(appFrame.getWwd());

		final long delay = 100l;
		final long period = 10000l;
		// this.addRenderables(currentTiles);
		ViewMoveListener viewMoveListener = new ViewMoveListener() {

			@Override
			public void moved(ViewMoveEvent event) {
				timer.cancel();
				timer = new Timer();
				TimerTask timertask = new TimerTask() {

					@Override
					public void run() {
						final Position currentPosition = appFrame.getWwd()
								.getView().getCurrentEyePosition();
						refresh(currentPosition);
					}
				};
				timer.scheduleAtFixedRate(timertask, delay, period);
			}
		};
		appFrame.getWwd().addViewMoveListener(viewMoveListener);

	}

	public void addKmlController(Object key, KMLController kmlController) {
		this.kmlControllerMap.put(key, kmlController);
		this.addRenderable(kmlController);
		// this.addRenderables(kmlControllerMap.values());
	}

	public synchronized void removeKmlController(Object key) {
		KMLController kmlController = this.kmlControllerMap.get(key);
		// this.addRenderables(kmlControllerMap.values());
		if (kmlController != null) {
			this.removeRenderable(kmlController);
			this.kmlControllerMap.remove(key);
		}
	}

	public KMLController getKmlController(Object key) {
		return this.kmlControllerMap.get(key);
	}

	public void setName(String name) {
		super.setName(name);
		this.tileCountName = this.getName() + " Tiles";
	}

	private static class DownloadPostProcessor {
		// TODO: Rewrite this inner class, factoring out the generic parts.
		private final ModelTile tile;
		private final Kml3DLayer layer;

		public DownloadPostProcessor(ModelTile tile, Kml3DLayer layer) {
			this.tile = tile;
			this.layer = layer;
		}

		public ByteBuffer run(String urlStr) {

			try {
				URL url = new URL(urlStr);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				int contentLength = connection.getContentLength();
				ByteBuffer byteBuffer = ByteBuffer.allocate(contentLength);
				InputStream inputStream = connection.getInputStream();

				tile.setModelData(inputStream);

				ReadableByteChannel channel = Channels.newChannel(inputStream);
				int numBytesRead = 0;
				AtomicInteger contentLengthRead = new AtomicInteger(0);
				while (!this.interrupted() && numBytesRead >= 0
						&& numBytesRead < byteBuffer.limit()) {
					int count = channel.read(byteBuffer);
					if (count > 0)
						contentLengthRead.getAndAdd(numBytesRead += count);
					if (count < 0)
						throw new WWRuntimeException(
								"Premature end of stream from server.");
				}

				if (byteBuffer != null)
					byteBuffer.flip();

				final File outFile = this.layer.getDataFileStore().newFile(
						this.tile.getPath());

				this.layer.saveBuffer(byteBuffer, outFile);

				return byteBuffer;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected boolean interrupted() {
			if (Thread.currentThread().isInterrupted()) {
				// this.setState(RETRIEVER_STATE_INTERRUPTED);
				// String message =
				// Logging.getMessage("URLRetriever.RetrievalInterruptedFor",
				// this.url.toString());
				// Logging.logger().fine(message);
				return true;
			}
			return false;
		}

	}

	private void saveBuffer(java.nio.ByteBuffer buffer, java.io.File outFile)
			throws java.io.IOException {
		synchronized (this.fileLock) // sychronized with read of file in
										// RequestTask.run()
		{
			WWIO.saveBuffer(buffer, outFile);
		}
	}

	public void refresh(Position position) {

		double longitude = position.getLongitude().getDegrees();
		double latitude = position.getLatitude().getDegrees();

		int col = (int) Math.floor(longitudeToColumn(longitude));
		int row = (int) Math.floor(latitudeToRow(latitude));

		loadLocalKmls("D:/RearrageGoogle3D", row, col);
		removeModelTile();

	}

	private void removeModelTile() {

	}

	private HashSet<String> getUrls(int row, int col) {

		HashSet<String> res = new HashSet<String>();

		// TODO
		return res;
	}

	private double latitudeToRow(double latitude) {
		return (latitude + 90) / deltDegree * Math.pow(2, trimLevel);
	}

	private double longitudeToColumn(double longitude) {
		return (longitude + 180) / deltDegree * Math.pow(2, trimLevel);
	}

	private void loadNetKmls(int row, int col) {
		HashSet<String> urls = getUrls(row, col);

		for (String url : urls) {
			String fileName = url.substring(2);// TODO

			ModelTile modelTile = new ModelTile(row, col, fileName);
			if (ModelTile.getMemoryCache().contains(modelTile.getTileKey())) {
				return;
			}

			// tileCache.put(modelTile.getTileKey(), modelTile);
			// tileStore.put(modelTile.getTileKey(), null);
			DownloadPostProcessor downloadPostProcessor = new DownloadPostProcessor(
					modelTile, this);
			downloadPostProcessor.run(url);
			modelTile.updateMemoryCache();

			String ctrlKey = modelTile.getTileKey();
			Thread kmlThread = new WorkerThread(ctrlKey,
					modelTile.getModelData(), appFrame, this);

			kmlThread.start();

		}
	}

	private void loadLocalKmls(String address, int row, int col) {

		List<File> kmlList = getKmls(address, row, col);

		for (File kml : kmlList) {
			String fileName = kml.getName();

			// System.out.println(fileName);
			ModelTile modelTile = new ModelTile(row, col, fileName);
			
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@size:"
					+ kmlControllerMap.keySet().size());

			if (this.kmlControllerMap.containsKey(modelTile.getTileKey())) {
				continue;
			}

			modelTile.setSize(10000);
			File file = filestore.newFileAndCache(modelTile.getPath(),
					modelTile);
			try {
				FileInputStream fis = new FileInputStream(kml);
				FileOutputStream fos = new FileOutputStream(file);
				int len;
				long size = 0;
				byte[] buff = new byte[1024 * 4];
				while ((len = fis.read(buff)) != -1) {
					fos.write(buff, 0, len);
					size += len;
				}

				fis.close();
				fos.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// filestore.requestFile(kml, true);

			String ctrlKey = modelTile.getTileKey();
			Thread kmlThread = new WorkerThread(ctrlKey, kml, appFrame, this);
			threadPool.execute(kmlThread);
		}

	}

	private List<File> getKmls(String address, int row, int col) {

		List<File> kmlList = new ArrayList<File>();
		File addr = new File(address);
		File[] files = null;
		// addr.listFiles();
		
		File[] fileXs = getFitDirs(addr, col);
		
		if(fileXs==null) {
			return kmlList;
		}
		for (File fileX : fileXs) {
			File[] fileYs = getFitDirs(fileX, row);
			if(fileYs==null) {
				return kmlList;
			}
			for (File fileY : fileYs) {
				if (fileY.isDirectory()) {
					FileFilter filter = new FileFilter() {

						@Override
						public boolean accept(File arg0) {
							String filename = arg0.getAbsolutePath();
							if (arg0.isFile()
									&& (filename.endsWith(".kml") || filename
											.endsWith(".kmz"))) {
								return true;
							} else {
								return false;
							}

						}
					};
					files = fileY.listFiles(filter);
				}
			}
		}

		if (files != null) {
			for (File file : files) {
				// InputStream inputStream = new FileInputStream(file);
				// String filestr = file.getAbsolutePath();
				if (file.exists()) {
					kmlList.add(file);
					// System.out.println(file.getAbsolutePath());
					// System.out.println(filestr);
				}
			}
		}

		return kmlList;
	}

	private File[] getFitDirs(File addr, final int fitNum) {

		if (addr.isDirectory()) {
			FileFilter filter = new FileFilter() {

				@Override
				public boolean accept(File arg0) {
					String filename = arg0.getAbsolutePath().replace("\\", "/");
					String[] splitStr = filename.split("/");
					int num;
					try {
						num = Integer.parseInt(splitStr[splitStr.length - 1]);
					} catch (Exception e) {
						return false;
					}

					if (arg0.isDirectory() && num == fitNum) {
						return true;
					} else {
						return false;
					}

				}
			};
			return addr.listFiles(filter);
		} else {
			return null;
		}

	}

}

package org.gfg.dartearth.layer.kml3d;

import java.io.File;

import gov.nasa.worldwind.cache.BasicDataFileStore;
import gov.nasa.worldwind.cache.MemoryCache;
import gov.nasa.worldwind.cache.MemoryCache.CacheListener;
import gov.nasa.worldwind.ogc.kml.impl.KMLController;
import gov.nasa.worldwind.util.Logging;

public class Kml3DDataFileStore extends BasicDataFileStore {
	
	private Kml3DLayer layer;
	
	public Kml3DDataFileStore(final Kml3DLayer layer) {
		super();
		this.layer = layer;
//		this.db.contains(key)
		MemoryCache.CacheListener listener = new CacheListener() {
			
			@Override
			public void removalException(Throwable exception, Object key,
					Object clientObject) {
				
//				String message = Logging.getMessage("BasicMemoryCache.removeCacheException");
//	            Logging.logger().warning(message);
//	            throw new IllegalArgumentException(message);
				System.out.println("removalException");
				
			}
			
			@Override
			public void entryRemoved(Object key, Object clientObject) {
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
//				KMLController kmlController = layer.getKmlController(key);
				layer.removeKmlController(key);
			}
		};
		this.addCacheListener(listener);
	}	

	public File newFileAndCache(String fileName, ModelTile modelTile) {
		// DBEntry entry = new DBEntry(fileName);
		this.db.add(fileName, modelTile);
		return super.newFile(fileName);
	}

	@Override
	public synchronized void removeFile(String address) {
		super.removeFile(address);
	}

	public void addCacheListener(MemoryCache.CacheListener listener) {
		this.db.addCacheListener(listener);
	}

	public void removeCacheListener(MemoryCache.CacheListener listener) {
		this.db.removeCacheListener(listener);
	}

}

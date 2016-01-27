package org.gfg.dartearth.layer.kml3d;

import gov.nasa.worldwind.cache.Cacheable;
import gov.nasa.worldwind.cache.MemoryCache;

public class Kml3DCache implements MemoryCache {

	private String name;
	private CacheListener listener;
	private Long capacity = 0l;
	private Long usedCapacity = 0l;
	private int currentRow = 0;
	private int currentColumn = 0;


	public int getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	public int getCurrentColumn() {
		return currentColumn;
	}

	public void setCurrentColumn(int currentColumn) {
		this.currentColumn = currentColumn;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCacheListener(CacheListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeCacheListener(CacheListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean add(Object key, Object clientObject, long objectSize) {
//		Kml3DKey kml3DKey = (Kml3DKey) key;
//		this.cacheData.put(kml3DKey, clientObject);
//		this.usedCapacity += objectSize;
		return false;
	}

	@Override
	public boolean add(Object key, Cacheable clientObject) {
//		Kml3DKey kml3DKey = (Kml3DKey) key;
//		this.cacheData.put(kml3DKey, clientObject);
		this.usedCapacity += clientObject.getSizeInBytes();
		return false;
	}

	@Override
	public void remove(Object key) {
//		cacheData.remove(key);

	}

	@Override
	public Object getObject(Object key) {
		return null;
//		return this.cacheData.get(key);
	}

	@Override
	public void clear() {
//		this.cacheData.clear();
		this.usedCapacity = 0l;
	}

	@Override
	public int getNumObjects() {
		return 0;
//		return this.cacheData.size();
	}

	@Override
	public long getCapacity() {
		return this.capacity;
	}

	@Override
	public long getUsedCapacity() {
		return this.usedCapacity;
	}

	@Override
	public long getFreeCapacity() {
		return this.capacity - this.usedCapacity;
	}

	@Override
	public long getLowWater() {
//		this.cacheData
		
		return 0;
	}

	@Override
	public void setLowWater(long loWater) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCapacity(long capacity) {
		this.capacity = capacity;

	}
	
	public static void main(String[] args) {
		System.out.println((long) 3e5);
		System.out.println((long) 5e5);
		int i =0;
		System.out.println(i++);
	}
}

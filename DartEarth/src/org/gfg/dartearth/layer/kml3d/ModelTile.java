/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package org.gfg.dartearth.layer.kml3d;

import com.sun.opengl.util.texture.*;
import com.sun.xml.internal.bind.v2.TODO;

import gov.nasa.worldwind.*;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.cache.*;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.ogc.kml.impl.KMLController;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwind.util.*;

import javax.media.opengl.*;

import java.io.InputStream;
import java.util.*;

import jouvieje.io.Stream;

/**
 * This class manages the conversion and timing of image data to a JOGL Texture, and provides an interface for binding
 * the texture and applying any texture transforms to align the texture and texture coordinates.
 * <p/>
 *
 * @author tag
 * @version $Id: TextureTile.java 1 2011-07-16 23:22:47Z dcollins $
 */
public class ModelTile implements Cacheable
{
	
	private String basePath = "/Kml3dModel";
	private KMLController kmlController;
    private volatile InputStream modelData; // if non-null, then must be converted to a Texture
//    private ModelTile fallbackTile = null; // holds texture to use if own texture not available
    protected boolean hasMipmapData = false;
    protected long updateTime = 0;
    protected static String MODEL_CACHE_SIZE = "gov.nasa.worldwind.ModelCacheSize";
    
    private int row;
	private int column;
//	private Sector sector;
    private String fileName;
    
    private long size;
    
//    public Sector getSector() {
//		return sector;
//	}
//
//	public void setSector(Sector sector) {
//		this.sector = sector;
//	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
    
    public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
    
    /**
     * Returns the memory cache used to cache tiles for this class and its subclasses, initializing the cache if it
     * doesn't yet exist.
     *
     * @return the memory cache associated with the tile.
     */
    public static synchronized MemoryCache getMemoryCache()
    {
        if (!WorldWind.getMemoryCacheSet().containsCache(ModelTile.class.getName()))
        {
            long size = Configuration.getLongValue(MODEL_CACHE_SIZE, 3000000L);
            MemoryCache cache = new BasicMemoryCache((long) (0.85 * size), size);
            cache.setName("Model Tiles");
            WorldWind.getMemoryCacheSet().addCache(ModelTile.class.getName(), cache);
        }

        return WorldWind.getMemoryCacheSet().getCache(ModelTile.class.getName());
    }

    public ModelTile()
    {
//        super(sector);
//    	this.sector = sector;
    }

    public ModelTile(int row, int col)
    {
//        super(sector, level, row, col);
//    	this.sector = sector;
    	this.row = row;
    	this.column = col;
    }

    public ModelTile(int row, int column, String fileName)
    {
//        super(sector, level, row, column, cacheName);
//    	this.sector = sector;
    	this.row = row;
    	this.column = column;
    	this.fileName = fileName;
    }

    public long getSizeInBytes()
    {
        // Return just an approximate size
        long size = 0;

//        if (this.sector != null)
//            size += this.sector.getSizeInBytes();

        if (this.basePath != null)
            size += this.getPath().length();

        size += 32; // to account for the references and the TileKey size

        size += this.size;
//        TODO
//        if (this.modelData != null)
//            size += this.modelData.();

        return size;
    }

//    public List<? extends LatLon> getCorners()
//    {
//        ArrayList<LatLon> list = new ArrayList<LatLon>(4);
//        for (LatLon ll : this.getSector())
//        {
//            list.add(ll);
//        }
//
//        return list;
//    }

//    public ModelTile getFallbackTile()
//    {
//        return this.fallbackTile;
//    }
//
//    public void setFallbackTile(ModelTile fallbackTile)
//    {
//        this.fallbackTile = fallbackTile;
//    }

    /**
     * Returns the texture data most recently specified for the tile. New texture data is typically specified when a new
     * image is read, either initially or in response to image expiration.
     * <p/>
     * If texture data is non-null, a new texture is created from the texture data when the tile is next bound or
     * otherwise initialized. The texture data field is then set to null. Subsequently setting texture data to be
     * non-null causes a new texture to be created when the tile is next bound or initialized.
     *
     * @return the texture data, which may be null.
     */
    public InputStream getModelData()
    {
        return this.modelData;
    }

    /**
     * Specifies new texture data for the tile. New texture data is typically specified when a new image is read, either
     * initially or in response to image expiration.
     * <p/>
     * If texture data is non-null, a new texture is created from the texture data when the tile is next bound or
     * otherwise initialized. The texture data field is then set to null. Subsequently setting texture data to be
     * non-null causes a new texture to be created when the tile is next bound or initialized.
     * <p/>
     * When a texture is created from the texture data, the texture data field is set to null to indicate that the data
     * has been converted to a texture and its resources may be released.
     *
     * @param textureData the texture data, which may be null.
     */
    public void setModelData(InputStream modelData)
    {
        this.modelData = modelData;
//        if (modelData..getMipmapData() != null)
//            this.hasMipmapData = true;
    }

    public Texture getTexture(GpuResourceCache tc)
    {
        if (tc == null)
        {
            String message = Logging.getMessage("nullValue.TextureCacheIsNull");
            Logging.logger().severe(message);
            throw new IllegalStateException(message);
        }

        return tc.getTexture(this.getTileKey());
    }

    public boolean isTextureInMemory(GpuResourceCache tc)
    {
        if (tc == null)
        {
            String message = Logging.getMessage("nullValue.TextureCacheIsNull");
            Logging.logger().severe(message);
            throw new IllegalStateException(message);
        }

        return this.getTexture(tc) != null || this.getModelData() != null;
    }

//    public boolean isTextureExpired()
//    {
//        return this.isTextureExpired(this.getLevel().getExpiryTime());
//    }

//    public boolean isTextureExpired(long expiryTime)
//    {
//        return this.updateTime > 0 && this.updateTime < expiryTime;
//    }

//    public void setTexture(GpuResourceCache tc, Texture texture)
//    {
//        if (tc == null)
//        {
//            String message = Logging.getMessage("nullValue.TextureCacheIsNull");
//            Logging.logger().severe(message);
//            throw new IllegalStateException(message);
//        }
//
//        tc.put(this.getTileKey(), texture);
//        this.updateTime = System.currentTimeMillis();
//
//        // No more need for texture data; allow garbage collector and memory cache to reclaim it.
//        // This also signals that new texture data has been converted.
//        this.modelData = null;
//        this.updateMemoryCache();
//    }

//    public Vec4 getCentroidPoint(Globe globe)
//    {
//        if (globe == null)
//        {
//            String msg = Logging.getMessage("nullValue.GlobeIsNull");
//            Logging.logger().severe(msg);
//            throw new IllegalArgumentException(msg);
//        }
//
//        return globe.computePointFromLocation(this.getSector().getCentroid());
//    }

//    public Extent getExtent(DrawContext dc)
//    {
//        if (dc == null)
//        {
//            String msg = Logging.getMessage("nullValue.DrawContextIsNull");
//            Logging.logger().severe(msg);
//            throw new IllegalArgumentException(msg);
//        }
//
//        return Sector.computeBoundingBox(dc.getGlobe(), dc.getVerticalExaggeration(), this.getSector());
//    }


    protected ModelTile getTileFromMemoryCache(String tileKey)
    {
        return (ModelTile) getMemoryCache().getObject(tileKey);
    }

    protected void updateMemoryCache()
    {
        if (this.getTileFromMemoryCache(this.getTileKey()) != null)
            getMemoryCache().add(this.getTileKey(), this);
        Object o = ModelTile.getMemoryCache().getObject(this.getTileKey());
        updateTime = System.currentTimeMillis();
    }

//    protected Texture initializeTexture(DrawContext dc)
//    {
//        if (dc == null)
//        {
//            String message = Logging.getMessage("nullValue.DrawContextIsNull");
//            Logging.logger().severe(message);
//            throw new IllegalStateException(message);
//        }
//
//        Texture t = this.getTexture(dc.getTextureCache());
//        // Return texture if found and there is no new texture data
//        if (t != null && this.getModelData() == null)
//            return t;
//
//        if (this.getModelData() == null) // texture not in cache yet texture data is null, can't initialize
//        {
//            String msg = Logging.getMessage("nullValue.TextureDataIsNull");
//            Logging.logger().severe(msg);
//            throw new IllegalStateException(msg);
//        }
//
//        try
//        {
//            t = TextureIO.newTexture(this.getTexture());
//        }
//        catch (Exception e)
//        {
//            String msg = Logging.getMessage("layers.TextureLayer.ExceptionAttemptingToReadTextureFile", "");
//            Logging.logger().log(java.util.logging.Level.SEVERE, msg, e);
//            return null;
//        }
//
//        this.setTexture(dc.getTextureCache(), t);
//        t.bind();
//
//        this.setTextureParameters(dc, t);
//
//        return t;
//    }

//    protected void setTextureParameters(DrawContext dc, Texture t)
//    {
//        if (dc == null)
//        {
//            String message = Logging.getMessage("nullValue.DrawContextIsNull");
//            Logging.logger().severe(message);
//            throw new IllegalStateException(message);
//        }
//
//        GL gl = dc.getGL();
//
//        // Use a mipmap minification filter when either of the following is true:
//        // a. The texture has mipmap data. This is typically true for formats with embedded mipmaps, such as DDS.
//        // b. The texture is setup to have GL automatically generate mipmaps. This is typically true when a texture is
//        //    loaded from a standard image type, such as PNG or JPEG, and the caller instructed JOGL to generate
//        //     mipmaps.
//        // Additionally, the texture must be in the latitude range (-80, 80). We do this to prevent seams that appear
//        // between textures near the poles.
//        //
//        // TODO: remove the latitude range restriction if a better tessellator fixes the problem.
//
//        boolean useMipmapFilter = (this.hasMipmapData || t.isUsingAutoMipmapGeneration())
//            && this.getSector().getMaxLatitude().degrees < 80d && this.getSector().getMinLatitude().degrees > -80;
//
//        // Set the texture minification filter. If the texture qualifies for mipmaps, apply a minification filter that
//        // will access the mipmap data using the highest quality algorithm. If the anisotropic texture filter is
//        // available, we will enable it. This will sharpen the appearance of the mipmap filter when the textured
//        // surface is at a high slope to the eye.
//        if (useMipmapFilter)
//        {
//            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR_MIPMAP_LINEAR);
//
//            // If the maximum degree of anisotropy is 2.0 or greater, then we know this graphics context supports
//            // the anisotropic texture filter.
//            double maxAnisotropy = dc.getGLRuntimeCapabilities().getMaxTextureAnisotropy();
//            if (dc.getGLRuntimeCapabilities().isUseAnisotropicTextureFilter() && maxAnisotropy >= 2.0)
//            {
//                gl.glTexParameterf(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAX_ANISOTROPY_EXT, (float) maxAnisotropy);
//            }
//        }
//        // If the texture does not qualify for mipmaps, then apply a linear minification filter.
//        else
//        {
//            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
//        }
//
//        // Set the texture magnification filter to a linear filter. This will blur the texture as the eye gets very
//        // near, but this is still a better choice than nearest neighbor filtering.
//        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
//
//        // Set the S and T wrapping modes to clamp to the texture edge. This way no border pixels will be sampled by
//        // either the minification or magnification filters.
//        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP_TO_EDGE);
//        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP_TO_EDGE);
//    }

//    public boolean bind(DrawContext dc)
//    {
//        if (dc == null)
//        {
//            String message = Logging.getMessage("nullValue.DrawContextIsNull");
//            Logging.logger().severe(message);
//            throw new IllegalStateException(message);
//        }
//
//        // Reinitialize texture if new texture data
//        if (this.getTextureData() != null)
//        {
//            Texture t = this.initializeTexture(dc);
//            if (t != null)
//                return true; // texture was bound during initialization.
//        }
//
//        Texture t = this.getTexture(dc.getTextureCache());
//
//        if (t == null && this.getFallbackTile() != null)
//        {
//            ModelTile resourceTile = this.getFallbackTile();
//            t = resourceTile.getTexture(dc.getTextureCache());
//            if (t == null)
//            {
//                t = resourceTile.initializeTexture(dc);
//                if (t != null)
//                    return true; // texture was bound during initialization.
//            }
//        }
//
//        if (t != null)
//            t.bind();
//
//        return t != null;
//    }

//    public void applyInternalTransform(DrawContext dc, boolean textureIdentityActive)
//    {
//        if (dc == null)
//        {
//            String message = Logging.getMessage("nullValue.DrawContextIsNull");
//            Logging.logger().severe(message);
//            throw new IllegalStateException(message);
//        }
//
//        Texture t;
//        if (this.getTextureData() != null) // Reinitialize if new texture data
//            t = this.initializeTexture(dc);
//        else
//            t = this.getTexture(dc.getTextureCache()); // Use the tile's texture if available
//
//        if (t != null)
//        {
//            if (t.getMustFlipVertically())
//            {
//                GL gl = GLContext.getCurrent().getGL();
//                if (!textureIdentityActive)
//                {
//                    gl.glMatrixMode(GL.GL_TEXTURE);
//                    gl.glLoadIdentity();
//                }
//                gl.glScaled(1, -1, 1);
//                gl.glTranslated(0, -1, 0);
//            }
//            return;
//        }
//
//        // Use the tile's fallback texture if its primary texture is not available.
//        ModelTile resourceTile = this.getFallbackTile();
//        if (resourceTile == null) // no fallback specified
//            return;
//
//        t = resourceTile.getTexture(dc.getTextureCache());
//        if (t == null && resourceTile.getTextureData() != null)
//            t = resourceTile.initializeTexture(dc);
//
//        if (t == null) // was not able to initialize the fallback texture
//            return;
//
//        // Apply necessary transforms to the fallback texture.
//        GL gl = GLContext.getCurrent().getGL();
//        if (!textureIdentityActive)
//        {
//            gl.glMatrixMode(GL.GL_TEXTURE);
//            gl.glLoadIdentity();
//        }
//
//        if (t.getMustFlipVertically())
//        {
//            gl.glScaled(1, -1, 1);
//            gl.glTranslated(0, -1, 0);
//        }
//
//        this.applyResourceTextureTransform(dc);
//    }

//    protected void applyResourceTextureTransform(DrawContext dc)
//    {
//        if (dc == null)
//        {
//            String message = Logging.getMessage("nullValue.DrawContextIsNull");
//            Logging.logger().severe(message);
//            throw new IllegalStateException(message);
//        }
//
//        if (this.getLevel() == null)
//            return;
//
//        int levelDelta = this.getLevelNumber() - this.getFallbackTile().getLevelNumber();
//        if (levelDelta <= 0)
//            return;
//
//        double twoToTheN = Math.pow(2, levelDelta);
//        double oneOverTwoToTheN = 1 / twoToTheN;
//
//        double sShift = oneOverTwoToTheN * (this.getColumn() % twoToTheN);
//        double tShift = oneOverTwoToTheN * (this.getRow() % twoToTheN);
//
//        dc.getGL().glTranslated(sShift, tShift, 0);
//        dc.getGL().glScaled(oneOverTwoToTheN, oneOverTwoToTheN, 1);
//    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final ModelTile tile = (ModelTile) o;

        return !(this.getTileKey() != null ? !this.getTileKey().equals(tile.getTileKey()) : tile.getTileKey() != null);
    }

    @Override
    public int hashCode()
    {
        return (this.getTileKey() != null ? this.getTileKey().hashCode() : 0);
    }

//    @Override
//    public String toString()
//    {
//        return this.getSector().toString();
//    }

	public void setKmlController(KMLController kmlController) {
		this.kmlController = kmlController;
	}

	public KMLController getKmlController() {
		return kmlController;
	}
	
    public String getPath()
    {
        String path = this.basePath + "/" + this.column + "/" + this.row + "/" + this.fileName;
        return path;
    }
    
    public String getTileKey() {
    	return getPath();
    }
    
}

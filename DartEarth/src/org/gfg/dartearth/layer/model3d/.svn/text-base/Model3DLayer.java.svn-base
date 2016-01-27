/*
 * Model3DLayer.java
 *
 * Created on February 12, 2008, 10:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.gfg.dartearth.layer.model3d;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Vec4;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.DrawContext;

import javax.media.opengl.GL;

/**
 *
 * @author RodgersGB
 */
public class Model3DLayer extends RenderableLayer {
    private WWModel3D model;
    private boolean maitainConstantSize = false;
    private double size = 1;
    private Model3DPanel panel;
    
    /** Creates a new instance of Model3DLayer */
    public Model3DLayer() {
       // list = new Vector<WWModel3D>();
    }

//    public void addModel(WWModel3D model) {
//        list.add(model);
//    }
//    
//    public void removeModel(WWModel3D model) {
//        list.remove(model);
//    }
    
    
    protected void doRender(DrawContext dc) {
    	super.doRender(dc);
        try {
            beginDraw(dc);
          //  Iterator<WWModel3D> it = list.iterator();
        //    while (it.hasNext())
                draw(dc, model);
        }
        // handle any exceptions
        catch (Exception e) {
            // handle
            e.printStackTrace();
        }
        // we must end drawing so that opengl
        // states do not leak through.
        finally {
            endDraw(dc);
        }
    }
    
    public WWModel3D getModel() {
		return model;
	}

	public void setModel(WWModel3D model) {
		this.model = model;
	}

	// draw this layer
    protected void draw(DrawContext dc, WWModel3D model) {
        GL gl = dc.getGL();
        Position pos = model.getPosition();
        Vec4 loc = dc.getGlobe().computePointFromPosition(pos);
        double size = this.computeSize(dc, loc);
        
        if (dc.getView().getFrustumInModelCoordinates().contains(loc)) {
            dc.getView().pushReferenceCenter(dc, loc);
            gl.glRotated(pos.getLongitude().degrees, 0,1,0);
            gl.glRotated(-pos.getLatitude().degrees, 1,0,0);
            gl.glScaled(size, size, size);
            gl.glCallList(Model3DisplayListFactory.getModel(gl, model.getPath()));
            dc.getView().popReferenceCenter(dc);
        }
    }
    
    // puts opengl in the correct state for this layer
    protected void beginDraw(DrawContext dc) {
        GL gl = dc.getGL();
        
        gl.glPushAttrib(GL.GL_TEXTURE_BIT |
                        GL.GL_ENABLE_BIT |
                        GL.GL_CURRENT_BIT |
                        GL.GL_TRANSFORM_BIT);
        
        if(!dc.isPickingMode()) {
            gl.glEnable(GL.GL_BLEND);
            gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        }
        
        gl.glMatrixMode(javax.media.opengl.GL.GL_MODELVIEW);
        gl.glPushMatrix();
    }
    
    // resets opengl state
    protected void endDraw(DrawContext dc) {
        GL gl = dc.getGL();
        
        gl.glMatrixMode(javax.media.opengl.GL.GL_MODELVIEW);
        gl.glPopMatrix();
        
        gl.glPopAttrib();
    }
    
    private double computeSize(DrawContext dc, Vec4 loc) {
        if (this.maitainConstantSize)
            return size;
        
        if (loc == null) {
            System.err.println("Null location when computing size of model");
            return 1;
        }
        double d = loc.distanceTo3(dc.getView().getEyePoint());
        double currentSize = 60 * dc.getView().computePixelSizeAtDistance(d);
        if (currentSize < 2)
            currentSize = 2;
        
        return currentSize;
    }

    public boolean isConstantSize() {
        return maitainConstantSize;
    }

    public void setMaitainConstantSize(boolean maitainConstantSize) {
        this.maitainConstantSize = maitainConstantSize;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

	public Model3DPanel getPanel() {
		return panel;
	}

	public void setPanel(Model3DPanel panel) {
		this.panel = panel;
	}

//	@Override
//	public Exportable getExportElem() {
//		// TODO Auto-generated method stub
//		return null;
//	}
    
}

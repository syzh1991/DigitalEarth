/*
 * Model3DRenderer.java
 *
 * Created on February 12, 2008, 10:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.gfg.dartearth.layer.model3d;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.media.opengl.GL;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureCoords;
import com.sun.opengl.util.texture.TextureIO;

/**
 * 
 * @author Brian Wood modifications made by Greg Rodgers
 */
public class Model3DisplayListFactory {
	private static DisplayListCache listCache = new DisplayListCache();

	public static int getModel(GL gl, String path) {
		int model = listCache.get(path);

		if (model < 0)
			model = initialize(gl, path);

		return model;
	}

	/* Private methods */
	private static HashMap<Integer, Texture> texture;

	private static int initialize(GL gl, String path) {
		Model3D model = Model3DFactory.createModel(path);
		int numMaterials = model.getNumberOfMaterials();

		texture = new HashMap<Integer, Texture>();
		for (int i = 0; i < numMaterials; i++) {
			Model3D.Material material = model.getMaterial(i);
			if (material.file != null && material.file.length() > 0) {
				loadTexture(gl, material.file, i);
			}
			material.textureId = i;
		}

		int listID = listCache.generateList(path, gl);
		gl.glNewList(listID, GL.GL_COMPILE);
		draw(gl, model);
		gl.glEndList();

		return listID;
	}

	private static void beginDrawing(GL gl, Model3D.ModelObject obj) {
		gl.glMatrixMode(GL.GL_TEXTURE);
		gl.glPushMatrix();

		if (obj.hasTexture && texture.get(obj.materialID) != null && texture.get(obj.materialID).getMustFlipVertically()) {
			gl.glScaled(1, -1, 1);
			gl.glTranslated(0, -1, 0);
		}

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glPushMatrix();
	}

	private static void endDrawing(GL gl) {
		gl.glMatrixMode(GL.GL_TEXTURE);
		gl.glPopMatrix();

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glPopMatrix();
	}

	private static void loadTexture(GL gl, String textureFile, int id) {
		String ext = textureFile.substring(textureFile.lastIndexOf('.') + 1, textureFile.length());
		//InputStream stream = Model3DisplayListFactory.class.getResourceAsStream(textureFile);
		
		try {
			InputStream stream=new FileInputStream(textureFile);
			texture.put(id, TextureIO.newTexture(stream, true, ext));
		} catch (IOException e) {
			System.err.println(e.getMessage() + ": " + textureFile);
		}
	}

	private static void draw(GL gl, Model3D model) {
		TextureCoords coords;

		float size = model.getSize();
		gl.glScalef(1 / size, 1 / size, 1 / size);

		int numObjects = model.getNumberOfObjects();
		for (int i = 0; i < numObjects; i++) {
			Model3D.ModelObject tempObj = model.getObject(i);
			beginDrawing(gl, tempObj);
			if (tempObj.hasTexture) {
				Texture t = texture.get(tempObj.materialID);
				if (t != null) {
					t.enable();
					t.bind();
					coords = t.getImageTexCoords();
				}
			}

			if (tempObj.faces == null)
				continue;

			gl.glBegin(GL.GL_TRIANGLES);
			for (int j = 0; j < tempObj.faces.length; j++) {
				for (int whichVertex = 0; whichVertex < 3; whichVertex++) {
					int index = tempObj.faces[j].vertIndex[whichVertex];
					int texIndex = tempObj.faces[j].coordIndex[whichVertex];
					if (tempObj.hasTexture) {
						if (tempObj.texVerts != null)
							gl.glTexCoord2f(tempObj.texVerts[texIndex].u, tempObj.texVerts[texIndex].v);
					} else {
						if (model.getNumberOfMaterials() < tempObj.materialID) {
							// Do something with the material
						}
					}
					gl.glVertex3f(tempObj.verts[index].x, tempObj.verts[index].y, tempObj.verts[index].z);
				}
			}
			gl.glEnd();

			if (tempObj.hasTexture) {
				Texture t = texture.get(tempObj.materialID);
				if (t != null)
					t.disable();
			}

			endDrawing(gl);
		}
	}

	public static class DisplayListCache {
		private HashMap<Object, Integer> listCache;

		/** Creates a new instance of WWDisplayListCache */
		private DisplayListCache() {
			listCache = new HashMap<Object, Integer>();
		}

		public void clear() {
			listCache.clear();
		}

		public int get(Object objID) {
			if (listCache.containsKey(objID))
				return listCache.get(objID);
			else
				return -1;
		}

		public void remove(Object objID, GL gl) {
			Integer list = listCache.get(objID);

			if (list != null)
				gl.glDeleteLists(list, 1);

			listCache.remove(objID);
		}

		/**
		 * Returns an integer identifier for an OpenGL display list based on the
		 * object being passed in. If the object already has a display list
		 * allocated, the existing ID is returned.
		 */
		public int generateList(Object objID, GL gl) {
			Integer list;

			list = listCache.get(objID);
			if (list == null) {
				list = new Integer(gl.glGenLists(1));
				listCache.put(objID, list);
			}

			return list;
		}
	}
}

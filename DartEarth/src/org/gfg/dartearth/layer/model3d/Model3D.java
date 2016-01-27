/*
 * Model3D.java
 *
 * Created on February 12, 2008, 10:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.gfg.dartearth.layer.model3d;

import java.awt.Color;
import java.util.Vector;

/**
 *
 * @author RodgersGB
 * Modifications made by Brian Wood
 */
public class Model3D {
    protected Vector<Material> materials = new Vector<Material>();
    protected Vector<ModelObject> objects = new Vector<ModelObject>();
    protected BoundingBox boundingBox = null;
    private String baseDir;
    private int modelType;
    private String path;
    
    public static final int MODEL_3DS = 1;
    public static final int MODEL_OBJ = 2;
    
    public Model3D() {
    }
    
    // Add material
    public void addMaterial(Material mat) {
        materials.add(mat);
    }
    
    // Add an object
    public void addObject(ModelObject obj) {
        objects.add(obj);
    }
    
    // Get material
    public Material getMaterial(int index) {
        return materials.get(index);
    }
    
    // Get an object
    public ModelObject getObject(int index) {
        return objects.get(index);
    }
    
    // Get the number of objects
    public int getNumberOfObjects() {
        return objects.size();
    }
    
    // Get the number of materials
    public int getNumberOfMaterials() {
        return materials.size();
    }
    
    public BoundingBox getBoundingBox() {
        return this.boundingBox;
    }
    
    public void setBoundingBox(BoundingBox bb) {
        this.boundingBox = bb;
    }
    
    public void setType(int type) {
        this.modelType = type;
    }
    
    public void setBaseDir(String base) {
        baseDir = base;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public float getSize() {
        BoundingBox box = this.getBoundingBox();
        Vector3f front[] = box.getFront();
        Vector3f back[] = box.getBack();
        
        return Vector3f.subtract(front[2], back[0]).magnitude();
    }
    
    public static class Material {
        public String name;
        public String file;
        public Color ambientColor;
        public Color specularColor;
        public Color diffuseColor;
        public Color emissive;
        public float shininess;
        public float shininess2;
        public float transparency;
        
        public int textureId;
        public float uTile;
        public float vTile;
        public float uOffset;
        public float vOffset;
        
        public void setEmissiveColor(Color color) {
            this.emissive = color;
        }
        
        public void setAmbientColor(Color color) {
            this.ambientColor = color;
        }
        
        public void setSpecularColor(Color color) {
            this.specularColor = color;
        }
        
        public void setDiffuseColor(Color color) {
            this.diffuseColor = color;
        }
        
        public void setShininess(float shin) {
            this.shininess = shin;
        }
    }
    
    public static class TexCoord {
        public float u,v;
        
        public TexCoord() {}
        public TexCoord(float uu, float vv) {
            this.u = uu; this.v = vv;
        }
        public TexCoord(TexCoord c) {
            this.u = c.u; this.v = c.v;
        }
    }
    
    public static class BoundingBox {
        private Vector3f front[] = new Vector3f[4];
        private Vector3f back[] = new Vector3f[4];
        
        public BoundingBox(float minx, float maxx, float miny, float maxy, float minz, float maxz) {
            front[0] = new Vector3f(minx, miny, maxz);
            front[1] = new Vector3f(minx, maxy, maxz);
            front[2] = new Vector3f(maxx, maxy, maxz);
            front[3] = new Vector3f(maxx, miny, maxz);
            
            back[0] = new Vector3f(minx, miny, minz);
            back[1] = new Vector3f(minx, maxy, minz);
            back[2] = new Vector3f(maxx, maxy, minz);
            back[3] = new Vector3f(maxx, miny, minz);
        }
        
        public Vector3f[] getFront() {
            return front;
        }
        
        public Vector3f[] getBack() {
            return back;
        }
    }
    
    public static class Vector3f {
        public float x,y,z;
        
        public Vector3f() {
        }
        
        public Vector3f(float x, float y, float z) {
            this.x = x; this.y = y; this.z = z;
        }
        
        public Vector3f(Vector3f v) {
            x = v.x; y = v.y; z = v.z;
        }
        
        // set methods
        public void set(float x, float y, float z) {
            this.x = x; this.y = y; this.z = z;
        }
        
        public void set(Vector3f v) {
            x = v.x; y = v.y; z = v.z;
        }
        
        // scale methods
        public void scale(float s) {
            this.x *= s; this.y *= s; this.z *= s;
        }
        
        // arithmetic methods
        public void subtract(Vector3f v) {
            this.x -= v.x; this.y -= v.y; this.z -= v.z;
        }
        
        public void add(Vector3f v) {
            this.x += v.x; this.y += v.y; this.z += v.z;
        }
        
        public static Vector3f subtract(Vector3f v1, Vector3f v2) {
            return new Vector3f(v1.x-v2.x, v1.y-v2.y, v1.z-v2.z);
        }
        
        public static void scale(Vector3f v, float s) {
            v.x *= s; v.y *= s; v.z *= s;
        }
        
        public Vector3f scaleNew(float s) {
            Vector3f v = new Vector3f(this);
            v.scale(s);
            return v;
        }
        
        public float magnitude() {
            return (float) Math.sqrt(x*x + y*y + z*z);
        }
        
        public void normalize() {
            this.scale(1/this.magnitude());
        }
        
        public Vector3f normalizeNew() {
            return scaleNew(this.magnitude());
        }
        
        public static void normalize(Vector3f v) {
            v.scale(v.magnitude());
        }
    }
    
    public static class Face {
        public int vertIndex[];
        public int coordIndex[];
        
        public Face() {
            vertIndex = new int[3];
            coordIndex = new int[3];
        }
        
        public Face(int vertsPerFace) {
            vertIndex = new int[vertsPerFace];
            coordIndex = new int[vertsPerFace];
        }
    }
    
    public static class Appearance {
        public Material material;
        
        void setMaterial(Material material) {
            this.material = material;
        }
    }
    
    public static class ModelObject {
        public int materialID = 0;
        public boolean hasTexture = false;
        public String name;
        public int indices = 0;
        public Vector3f verts[] = null;
        public TexCoord texVerts[] = null;
        public Face faces[] = null;
        public Vector3f normals[] = null;
        public Vector3f pivotPoint = null;
    }
}

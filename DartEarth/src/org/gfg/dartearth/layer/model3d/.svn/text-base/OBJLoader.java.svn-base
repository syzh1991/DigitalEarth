/*
 * OBJLoader.java
 *
 * Created on February 12, 2008, 10:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.gfg.dartearth.layer.model3d;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 *
 * @author Brian Wood
 */
class OBJLoader {
    private String OBJModelPath;
    private Vector<Model3D.Vector3f> vertices = new Vector<Model3D.Vector3f>();
    private Vector<Model3D.Vector3f> normals = new Vector<Model3D.Vector3f>();
    private Vector<Model3D.TexCoord> texCoords = new Vector<Model3D.TexCoord>();
    private Vector<Model3D.Face> faces = new Vector<Model3D.Face>();
    
    private int faceFormat;
    private int faceCount;
    private int polyCount = 0;
    private int materialID;
    private boolean hasTexture = false;
    private String baseDir;
    
    private int verticesRead = 0;
    private int verticesSoFar = 0;
    private int texCoordsRead = 0;
    private int texCoordsSoFar = 0;
    private int facesRead = 0;
    
    private final int RENDER_TRIANGLES =    1;
    private final int RENDER_QUADS =	    2;
    private final int RENDER_POLYGONS =	    3;
    
    Model3D theModel;
    
    public OBJLoader() {
    }
    
    //陈亮修改了这个地方 
    public Model3D load(String path) throws IOException, FileNotFoundException {
//        String tokens[] = path.split("/");
//        baseDir = "/";
//        for(int i = 0; i < tokens.length - 1; i++) {
//            baseDir += tokens[i] + "/";
//        }
    	baseDir="";
		String[] pathSplits=	path.split("\\\\");
		for(int i=0;i<pathSplits.length-1;i++){
			baseDir+=pathSplits[i]+"\\";
		}
//        
    	   FileInputStream inputStream=new FileInputStream(path);
          // dataInputStream = new DataInputStream(inputStream);
        theModel = load(inputStream);
        theModel.setBaseDir(baseDir);
        theModel.setType(Model3D.MODEL_OBJ);
        
        return theModel;
    }
    
    private Model3D load(InputStream stream) {
        if (stream == null)
            return null;
        
        theModel = new Model3D();
        Model3D.ModelObject object = null;
        
        try {
            // Open a file handle and read the models data
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String  line = null;
            while((line = br.readLine()) != null) {
                if (line.startsWith("#")) { // Read Any Descriptor Data in the File
                } else if (line.equals("")) {
                    //Ignore whitespace data
                } else if(line.startsWith("g ")) {
                    
                } else if(line.startsWith("o ")) {
                    if(object != null){
                        loadObjectData(object);
                        theModel.addObject(object);
                    }
                    object = new Model3D.ModelObject();
                    object.name = parseName(line);
                } else if (line.startsWith("v ")) { // Read in Vertex Data
                    processPointData(line, vertices);
                    verticesRead++;
                } else if (line.startsWith("vt ")) { // Read Texture Coordinates
                    processTexCoord(line, texCoords);
                    texCoordsRead++;
                } else if (line.startsWith("vn ")) { // Read Normal Coordinates
                    processPointData(line, normals);
                } else if (line.startsWith("f ")) { // Read Face Data
                    ProcessfData(line);
                } else if(line.startsWith("mtllib ")){
                    processMaterialLib(line);
                } else if(line.startsWith("usemtl ")) {
                    processMaterialType(line);
                }
                
            }
            br.close();
            if(object == null){
                object = new Model3D.ModelObject();
                loadObjectData(object);
                theModel.addObject(object);
            }
        } catch(IOException e) {
            System.out.println("Failed to find or read OBJ: " + stream);
            System.err.println(e);
        }
        
        theModel.setBoundingBox(this.constructBoundingBox());
        
        return theModel;
    }
    
    private void processPointData(String line, Vector<Model3D.Vector3f> points) {
        Model3D.Vector3f point = new Model3D.Vector3f();
        
        final String s[] = line.split("\\s+");
        
        point.x = Float.parseFloat(s[1]);
        point.y = Float.parseFloat(s[2]);
        point.z = Float.parseFloat(s[3]);
        
        points.add(point);
    }
    
    private String parseName(String line) {
        String name;
        
        final String s[] = line.split("\\s+");
        
        name = s[1];
        
        return name;
    }
    
    private Model3D.ModelObject initializeObject() {
        return new Model3D.ModelObject();
    }
    
    private void processTexCoord(String line, Vector<Model3D.TexCoord> tc) {
        Model3D.TexCoord coord = new Model3D.TexCoord();
        
        final String s[] = line.split("\\s+");
        
        coord.u = Float.parseFloat(s[1]);
        coord.v = Float.parseFloat(s[2]);
        
        tc.add(coord);
    }
    
    private float[] ProcessData(String read) {
        final String s[] = read.split("\\s+");
        return (ProcessFloatData(s)); //returns an array of processed float data
    }
    
    private float[] ProcessFloatData(String sdata[]) {
        float data[] = new float[sdata.length-1];
        for (int loop=0; loop < data.length; loop++) {
            data[loop] = Float.parseFloat(sdata[loop+1]);
        }
        return data; // return an array of floats
    }
    
    private void ProcessfData(String fread) {
        polyCount++;
        String s[] = fread.split("\\s+");
        if (fread.contains("//")) { // Pattern is present if obj has only v and vn in face data
            for (int loop=1; loop < s.length; loop++) {
                s[loop] = s[loop].replaceAll("//","/0/"); //insert a zero for missing vt data
            }
        }
        ProcessfIntData(s); // Pass in face data
    }
    
    private void ProcessfIntData(String sdata[]) {
        int vdata[] = new int[sdata.length-1];
        int vtdata[] = new int[sdata.length-1];
        int vndata[] = new int[sdata.length-1];
        Model3D.Face face = new Model3D.Face(sdata.length - 1);
        
        for (int loop = 1; loop < sdata.length; loop++) {
            String s = sdata[loop];
            String[] temp = s.split("/");
            face.vertIndex[loop-1] = Integer.valueOf(temp[0]) - 1 - verticesSoFar;
            
            if (temp.length > 1) { // we have v and vt data
                if(Integer.valueOf(temp[1]) > 0)
                    face.coordIndex[loop - 1] = Integer.valueOf(temp[1]) - 1 - texCoordsSoFar;
                else
                    face.coordIndex[loop - 1] = 0;
            }
        }
        
        faces.add(face);
    }
    
    /**
     * Loads all current mesh data into the object parameter.  The buffers are
     * cleared after the data is loaded so that subsequent data will be loaded
     * correctly into the next object
     *
     * @param object Container for 3D vertex data
     */
    private void loadObjectData(Model3D.ModelObject object) {
        object.faces = new Model3D.Face[faces.size()];
        object.normals = new Model3D.Vector3f[normals.size()];
        object.verts = new Model3D.Vector3f[vertices.size()];
        object.texVerts = new Model3D.TexCoord[texCoords.size()];
        
        if(faces.size() > 0)
            faces.toArray(object.faces);
        if(vertices.size() > 0)
            vertices.toArray(object.verts);
        if(normals.size() > 0)
            normals.toArray(object.normals);
        if(texCoords.size() > 0){
            texCoords.toArray(object.texVerts);
        }
        
        object.materialID = materialID;
        object.hasTexture = hasTexture;
        
        verticesSoFar = verticesRead;
        texCoordsSoFar = texCoordsRead;
        
        faces.clear();
        vertices.clear();
        normals.clear();
        
        theModel.addObject(object);
    }
    
    private void processMaterialLib(String mtlData) {
        String s[] = mtlData.split("\\s+");
        
        Model3D.Material mat = new Model3D.Material();
        
        InputStream stream = OBJLoader.class.getResourceAsStream(baseDir + s[1]);
        
        if(stream == null) {
            try {
                stream = new FileInputStream(baseDir + s[1]);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                return;
            }
        }
        loadMaterialFile(stream);
    }
    
    private void processMaterialType(String line) {
        String s[] = line.split("\\s+");
        
        materialID = -1;
        
        for(int i = 0; i < theModel.getNumberOfMaterials(); i++){
            Model3D.Material mat = theModel.getMaterial(i);
            
            
            if(mat.name.equals(s[1])){
                materialID = i;
                if(mat.file != null)
                    hasTexture = true;
                else
                    hasTexture = false;
                break;
            }
        }
        
        if(materialID == -1)
            materialID = 0;
    }
    
    private void setFaceRenderType() {
        final int vertexPerFace = theModel.getObject(0).faces[0].coordIndex.length;
        
        //final int temp [] = (int[]) fv.get(0);
        if ( vertexPerFace == 3) {
            faceFormat = RENDER_TRIANGLES; // The faces come in sets of 3 so we have triangular faces
            faceCount = 3;
        } else if (vertexPerFace == 4) {
            faceFormat = RENDER_QUADS; // The faces come in sets of 4 so we have quadrilateral faces
            faceCount = 4;
        } else {
            faceFormat = RENDER_POLYGONS; // Fall back to render as free form polygons
        }
    }
    
    public Model3D.BoundingBox constructBoundingBox() {
        float minX = Float.POSITIVE_INFINITY, maxX = Float.NEGATIVE_INFINITY;
        float minY = Float.POSITIVE_INFINITY, maxY = Float.NEGATIVE_INFINITY;
        float minZ = Float.POSITIVE_INFINITY, maxZ = Float.NEGATIVE_INFINITY;
        Model3D.BoundingBox boundingBox;
        
        
        Model3D.Vector3f point;
        
        for(int i = 0; i < theModel.getNumberOfObjects(); i++){
            Model3D.ModelObject object = theModel.getObject(i);
            
            if(object.verts == null){
                System.out.println("Verts should never be null");
                System.exit(1);
            }
            
            for(int j = 0; j < object.verts.length; j++){
                point = object.verts[j];
                if(point.x < minX)
                    minX = (float) point.x;
                else if(point.x > maxX)
                    maxX = (float) point.x;
                else if(point.y < minY)
                    minY = (float) point.y;
                else if(point.y > maxY)
                    maxY = (float) point.y;
                else if(point.z < minZ)
                    minZ = (float) point.z;
                else if(point.z > maxZ)
                    maxZ = (float) point.z;
            }
        }
        
        boundingBox = new Model3D.BoundingBox(minX, maxX, minY, maxY, minZ, maxZ);
        
        return boundingBox;
    }
    
    public Model3D.Material loadMaterialFile(InputStream stream) {
        Model3D.Material mat = null;
        int texId = 0;
        
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String line;
            
            while((line = br.readLine()) != null){
                
                String parts[] = line.trim().split("\\s+");
                
                if(parts[0].equals("newmtl")){
                    if(mat != null)
                        theModel.addMaterial(mat);
                    
                    mat = new Model3D.Material();
                    mat.name = parts[1];
                    mat.textureId = texId++;
                    
                } else if(parts[0].equals("Ks"))
                    mat.specularColor = parseColor(line);
                else if(parts[0].equals("Ns"))
                    mat.shininess = Float.valueOf(parts[1]);
                else if(parts[0].equals("d"))
                    ;
                else if(parts[0].equals("illum"))
                    ;
                else if(parts[0].equals("Ka"))
                    mat.ambientColor = parseColor(line);
                else if(parts[0].equals("Kd"))
                    mat.diffuseColor = parseColor(line);
                else if(parts[0].equals("map_Kd"))
                    mat.file = baseDir + parts[1];
                else if(parts[0].equals("map_Ka"))
                    mat.file = baseDir + parts[1];
            }
            
            br.close();
            theModel.addMaterial(mat);
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return mat;
    }
    
    private String parseMaterialName(String line) {
        String s[] = line.split("\\s+");
        
        return s[1];
    }
    
    private Color parseColor(String line) {
        String parts[] = line.trim().split("\\s+");
        
        Color color = new Color(Float.valueOf(parts[1]),
                Float.valueOf(parts[2]),Float.valueOf(parts[3]));
        
        return color;
    }
    
    public int getPolycount() {
        return polyCount;
    }
}
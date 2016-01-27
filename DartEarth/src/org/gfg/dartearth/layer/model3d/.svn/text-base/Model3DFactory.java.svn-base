/*
 * Model3DLoader.java
 *
 * Created on February 12, 2008, 10:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.gfg.dartearth.layer.model3d;

import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Brian Wood
 * modifications made by Greg Rodgers
 */
public class Model3DFactory {
    private static final int FILETYPE_UNKNOWN =    -1;
    private static final int FILETYPE_3DS  =	    1;
    private static final int FILETYPE_OBJ  =	    2;
    private static HashMap <Object, Model3D> cache = new HashMap<Object, Model3D>();

    /**
     * Loads a Model3D from file.  Determines which type of loader to use based
     * on the filename specified in path.  Valid file formats are 3DS and OBJ.
     *
     * @param path Relative path to file location
     * @return Model3D 3D model data
     */
    public static Model3D createModel(String path) {
	Model3D model = cache.get(path);
        
        if (model != null)
            return model;
        
	int filetype;
	
	filetype = determineFiletype(path);
	
        switch(filetype) {
            case FILETYPE_3DS:
                model = createMaxModel(path);
                break;

            case FILETYPE_OBJ:
                model = createObjModel(path);
                break;

            default:
                return null;
        }
        
        model.setPath(path);
        cache.put(path, model);
        
	return model;
    }
    
    public static Model3D createObjModel(String path) {
        Model3D model = null;
        
        try {
            model = new OBJLoader().load(path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return model;
    }
    
    public static Model3D createMaxModel(String path) {
        Model3D model = null;
        
        try {
            model = new MaxLoader().load(path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return model;
    }
    
    /**
     * Parses the file suffix to determine what file format the model is in.
     *
     * @param path File path info
     * @returns int Constant indicating file type
     */
    private static int determineFiletype(String path) {
	int type = FILETYPE_UNKNOWN;
	String tokens[] = path.split("\\.");	
	
	if(tokens[tokens.length - 1].equals("3ds"))
	    type = FILETYPE_3DS;
	else if(tokens[tokens.length - 1].equals("obj"))
	    type = FILETYPE_OBJ;
	    
	return type;
    }
}
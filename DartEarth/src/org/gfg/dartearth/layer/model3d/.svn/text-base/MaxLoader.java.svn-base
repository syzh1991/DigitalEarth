/*
 * MaxLoader.java
 *
 * Created on February 12, 2008, 10:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.gfg.dartearth.layer.model3d;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

/**
 * 
 * @author RodgersGB modifications made by Brian Wood
 */
class MaxLoader implements MaxConstants {
	private File file;
	private String baseDir;
	private boolean loaded = false;
	private FileInputStream fileInputStream;
	private DataInputStream dataInputStream;
	private Model3D.BoundingBox boundingBox = null;
	private float minx, maxx, miny, maxy, minz, maxz;

	// Global chunks
	private Chunk currentChunk, tempChunk;

	// the model
	private Model3D model = null;

	// Constructor
	public MaxLoader() {
		currentChunk = new Chunk();
		tempChunk = new Chunk();
		model = new Model3D();
	}

	// Verified
	// 陈亮修改了这个地方 ,将原来读取文件的地方进行了一定的修改
	public Model3D load(String path) throws IOException {
		String strMessage;

		// String tokens[] = path.split("/");
		// baseDir = "/";
		// for(int i = 0; i < tokens.length - 1; i++) {
		// baseDir += tokens[i] + "/";
		// }
		//
		// URL url= MaxLoader.class.getClassLoader().getResource(path);
		baseDir="";
		String[] pathSplits=	path.split("\\\\");
		for(int i=0;i<pathSplits.length-1;i++){
			baseDir+=pathSplits[i]+"\\";
		}
		FileInputStream inputStream = new FileInputStream(path);
		dataInputStream = new DataInputStream(inputStream);

		if (dataInputStream == null) {
			return null;
		}

		readChunkHeader(currentChunk);

		if (currentChunk.id != TYPE_3DS_FILE) {
			System.err.println("Unable to load PRIMARY chuck from file!");
			return null;
		}

		processNextChunk(currentChunk);

		// computeNormals();

		model.setBoundingBox(new Model3D.BoundingBox(minx, maxx, miny, maxy, minz, maxz));

		dataInputStream.close();

		return model;
	}

	private void processNextChunk(Chunk root) throws IOException {
		int version = 0;
		byte buffer[] = null;

		currentChunk = new Chunk();

		while (root.bytesRead < root.length) {
			readChunkHeader(currentChunk);

			switch (currentChunk.id) {
			case TYPE_3DS_VERSION:
				version = readInt(currentChunk);

				if (version > 0x03)
					System.err.println("This 3DS file is over version 3 so it may load incorrectly");
				break;

			case TYPE_MESH_DATA:
				readChunkHeader(tempChunk);
				readBytes(tempChunk, tempChunk.length - tempChunk.bytesRead);
				currentChunk.bytesRead += tempChunk.bytesRead;
				processNextChunk(currentChunk);
				break;

			case TYPE_MATERIAL:
				Model3D.Material m = new Model3D.Material();
		//		System.out.println(currentChunk);
				processMaterial(m, currentChunk);
				model.addMaterial(m);
				break;

			case TYPE_NAMED_OBJECT:
				Model3D.ModelObject obj = new Model3D.ModelObject();
				obj.name = readString(currentChunk);
				getObjectChunk(obj, currentChunk);
				model.addObject(obj);
				break;

			case TYPE_KEY_FRAME:
				processKeyFrame(currentChunk);
				break;

			default:
				readBytes(currentChunk, currentChunk.length - currentChunk.bytesRead);
				break;
			}
			root.bytesRead += currentChunk.bytesRead;
		}

		currentChunk = root;
	}

	private void processKeyFrame(Chunk root) throws IOException {
		currentChunk = new Chunk();

		while (root.bytesRead < root.length) {
			readChunkHeader(currentChunk);

			switch (currentChunk.id) {
			default:
				readBytes(currentChunk, currentChunk.length - currentChunk.bytesRead);
				break;
			}
			root.bytesRead += currentChunk.bytesRead;
		}
		currentChunk = root;
	}

	private void readChunkHeader(Chunk c) throws IOException {
		c.bytesRead = 0;
		c.id = this.readShort(c);
		c.length = this.readInt(c);
	}

	private void getObjectChunk(Model3D.ModelObject object, Chunk root) throws IOException {
		currentChunk = new Chunk();

		while (root.bytesRead < root.length) {
			readChunkHeader(currentChunk);

			switch (currentChunk.id) {
			case TYPE_TRIANGLE_OBJECT:
				getObjectChunk(object, currentChunk);
				break;

			case TYPE_DIRECT_LIGHT:
				readBytes(currentChunk, currentChunk.length - currentChunk.bytesRead);
				break;

			case TYPE_POINT_LIST:
				readVertices(object, currentChunk);
				break;

			case TYPE_FACE_LIST:
				readFaceList(object, currentChunk);
				break;

			case TYPE_MAT_FACE_LIST:
				readObjectMaterial(object, currentChunk);
				readBytes(currentChunk, currentChunk.length - currentChunk.bytesRead);
				break;

			case TYPE_MAT_UV:
				object.hasTexture = true;
				model.addMaterial(new Model3D.Material());
				object.materialID = 0;
				readUVCoordinates(object, currentChunk);
				break;

			default:
				readBytes(currentChunk, currentChunk.length - currentChunk.bytesRead);
				break;
			}
			root.bytesRead += currentChunk.bytesRead;
		}

		currentChunk = root;
	}

	private void processMaterial(Model3D.Material material, Chunk root) throws IOException {
		byte buffer[] = null;

		currentChunk = new Chunk();

		while (root.bytesRead < root.length) {
			readChunkHeader(currentChunk);

			switch (currentChunk.id) {
			case TYPE_MATERIAL_NAME:
				material.name = readString(currentChunk);
				readBytes(currentChunk, currentChunk.length - currentChunk.bytesRead);
				break;

			case TYPE_MAT_AMBIENT:
				material.setAmbientColor(readColor(currentChunk));
				break;

			case TYPE_MAT_SPECULAR:
				material.setSpecularColor(readColor(currentChunk));
				break;

			case TYPE_MAT_DIFFUSE:
				material.setDiffuseColor(readColor(currentChunk));
				break;

			case TYPE_MAT_SHININESS:
				float shin = 1.0f + 127.0f * readPercentage(currentChunk);
				material.setShininess(shin);
				break;

			case TYPE_MAT_SHININESS2:
				float shin2 = 1.0f + 127.0f * readPercentage(currentChunk);
				break;

			case TYPE_MAT_TRANSPARENCY:
				float trans = readPercentage(currentChunk);
				break;

			case TYPE_MAT_2_SIDED:
				readBytes(currentChunk, currentChunk.length - currentChunk.bytesRead);
				break;

			case TYPE_MAT_XPFALL:
				float xpf = readPercentage(currentChunk);
				break;

			case TYPE_MAT_REFBLUR:
				float ref = readPercentage(currentChunk);
				break;

			case TYPE_MAT_SELF_ILPCT:
				float il = readPercentage(currentChunk);
				break;

			case TYPE_MAT_SHADING:
				short shading = readShort(currentChunk);
				// Some kind of code for the rendering type: 1, 3, 4, etc.
				break;

			case TYPE_MAT_TEXMAP:
				processMaterial(material, currentChunk);
				readBytes(currentChunk, currentChunk.length - currentChunk.bytesRead);
				break;

			case TYPE_MAT_MAPNAME:
			//	System.out.println(baseDir);
				material.file = baseDir + readString(currentChunk);
				readBytes(currentChunk, currentChunk.length - currentChunk.bytesRead);
				break;

			default:
				readBytes(currentChunk, currentChunk.length - currentChunk.bytesRead);
				break;
			}

			root.bytesRead += currentChunk.bytesRead;
		}

		currentChunk = root;
	}

	private void readObjectMaterial(Model3D.ModelObject object, Chunk root) throws IOException {
		String strMaterial = null;
		byte buffer[] = null;

		strMaterial = readString(root);

		for (int i = 0; i < model.getNumberOfMaterials(); i++) {
			if (strMaterial.equals(model.getMaterial(i).name)) {
				object.materialID = i;
				Model3D.Material mat = model.getMaterial(i);
				if (mat.file != null)
					object.hasTexture = true;
				break;
			}
		}
	}

	private void readUVCoordinates(Model3D.ModelObject object, Chunk root) throws IOException {
		int numTexVertex = readShort(root);

		object.texVerts = new Model3D.TexCoord[numTexVertex];
		for (int i = 0; i < numTexVertex; i++)
			object.texVerts[i] = readPoint(root);
	}

	private void readVertices(Model3D.ModelObject object, Chunk root) throws IOException {
		int numOfVerts = readShort(root);

		object.verts = new Model3D.Vector3f[numOfVerts];
		for (int i = 0; i < numOfVerts; i++)
			object.verts[i] = readVertex(root);
	}

	private void readFaceList(Model3D.ModelObject object, Chunk root) throws IOException {
		short index = 0;

		int numOfFaces = readShort(root);

		object.faces = new Model3D.Face[numOfFaces];
		for (int i = 0; i < numOfFaces; i++) {
			object.faces[i] = new Model3D.Face();
			object.faces[i].vertIndex[0] = readShort(root);
			object.faces[i].vertIndex[1] = readShort(root);
			object.faces[i].vertIndex[2] = readShort(root);

			object.faces[i].coordIndex[0] = object.faces[i].vertIndex[0];
			object.faces[i].coordIndex[1] = object.faces[i].vertIndex[1];
			object.faces[i].coordIndex[2] = object.faces[i].vertIndex[2];

			// Read in the extra face info
			readShort(root); // Flags (?)
		}
	}

	/**
	 * Reads a color from the input file.
	 */
	protected Color readColor(Chunk c) throws IOException {
		Color color = null;

		readChunkHeader(tempChunk);
		switch (tempChunk.id) {
		case TYPE_COLOR_F:
		case TYPE_COLOR_LIN_F:
			color = new Color(readFloat(c), readFloat(c), readFloat(c));
		case TYPE_COLOR_I:
		case TYPE_COLOR_LIN_I:
			color = new Color(readUnsignedByte(c), readUnsignedByte(c), readUnsignedByte(c));
		}

		c.bytesRead += tempChunk.bytesRead;

		return color;
	}

	/**
	 * Reads a percentage value from the input file. Returns as a float between
	 * 0 and 1.
	 */
	protected float readPercentage(Chunk c) throws IOException {
		float value = 0;
		readChunkHeader(tempChunk);

		if (tempChunk.id == TYPE_PERCENT_I) {
			value = (float) readShort(c) / 100.0f;
		} else if (tempChunk.id == TYPE_PERCENT_F) {
			value = readFloat(c);
		}

		c.bytesRead += tempChunk.bytesRead;

		return value;
	}

	/**
	 * Reads a String value from the input file.
	 */
	protected String readString(Chunk c) throws IOException {
		DataInputStream in = dataInputStream;
		StringBuffer sb = new StringBuffer("");
		c.bytesRead++;
		byte ch = in.readByte();
		while (ch != (byte) 0) {
			sb.append((char) ch);
			c.bytesRead++;
			ch = in.readByte();
		}
		return sb.toString();
	}

	protected byte[] readBytes(Chunk c, int num) throws IOException {
		byte[] buffer = new byte[num];
		c.bytesRead += dataInputStream.read(buffer, 0, num);
		return buffer;
	}

	/**
	 * Reads a byte (8-bit) value from the input file.
	 */
	protected byte readByte(Chunk c) throws IOException {
		c.bytesRead++;
		return dataInputStream.readByte();
	}

	/**
	 * Reads an unsigned byte (8-bit) value from the input file.
	 */
	protected int readUnsignedByte(Chunk c) throws IOException {
		c.bytesRead++;
		return dataInputStream.readUnsignedByte();
	}

	/**
	 * Reads a short (16-bit) value from the input file.
	 */
	protected short readShort(Chunk c) throws IOException {
		c.bytesRead += 2;
		return (short) (dataInputStream.read() + (dataInputStream.read() << 8));
	}

	/**
	 * Reads an int (32-bit) value from the input file.
	 */
	private int readInt(Chunk c) throws IOException {
		DataInputStream in = dataInputStream;
		c.bytesRead += 4;
		return (int) (in.read() + (in.read() << 8) + (in.read() << 16) + (in.read() << 24));
	}

	private float readFloat(Chunk c) throws IOException {
		return Float.intBitsToFloat(readInt(c));
	}

	private Model3D.Vector3f readVertex(Chunk c) throws IOException {
		float x = readFloat(c);
		float y = readFloat(c);
		float z = readFloat(c);

		if (x < minx) {
			minx = x;
		} else if (x > maxx) {
			maxx = x;
		}
		if (y < miny) {
			miny = y;
		} else if (y > maxy) {
			maxy = y;
		}
		if (z < minz) {
			minz = z;
		} else if (z > maxz) {
			maxz = z;
		}

		return new Model3D.Vector3f(x, y, z);
	}

	private Model3D.TexCoord readPoint(Chunk c) throws IOException {
		return new Model3D.TexCoord(readFloat(c), readFloat(c));
	}

	/**
	 * Gets a type as a Hex string.
	 */
	protected static String getTypeString(short type) {
		String temp = Integer.toHexString(type);
		int length = temp.length();
		if (length > 4) {
			return temp.substring(length - 4, length);
		} else {
			return temp;
		}
	}

	private class Chunk {
		public short id = 0;
		public int length = 0;
		public int bytesRead = 0;
	}
}
package kml3d;

import gov.nasa.worldwind.ogc.kml.KMLFolder;
import gov.nasa.worldwind.ogc.kml.KMLLookAt;
import gov.nasa.worldwind.ogc.kml.KMLRoot;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

public class RearrageByLatLon {

	private String path = "D:/RearrageGoogle3D";
	private double deltDegree = 36;

	public void reArrage(int level) throws IOException, XMLStreamException {
		List<Object> kmls = KmlFactory.getKmls("D:\\DEML\\data\\google3D");
		File file = new File(path);
		file.mkdir();
		for (int i = 0; i < kmls.size(); i++) {

				KMLRoot kmlRoot = KMLRoot.create(kmls.get(i));
				kmlRoot.parse();
				KMLFolder folder = (KMLFolder) kmlRoot.getFields().getValues().toArray()[0];
				KMLLookAt ka = (KMLLookAt) folder.getFields().getValues().toArray()[3];
				double lat = ka.getLatitude();
				double lng = ka.getLongitude();
				int x = (int) ((lng + 180) / deltDegree * Math.pow(2, level));
				int y = (int) ((lat + 90) / deltDegree * Math.pow(2, level));		
				File dir = new File(path+"/"+x+"/"+y);
				if(!dir.exists()) {
					dir.mkdirs();
				}
				File oldFile = new File(kmls.get(i).toString());
				File toFile= new File(dir.getAbsolutePath() + "/" + oldFile.getName());
				this.copyFile(oldFile, toFile);
			


		}

	}

	public static void main(String arg[]) throws IOException, XMLStreamException {
		RearrageByLatLon r = new RearrageByLatLon();
		r.reArrage(8);
	}

	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);

		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);

		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();

		// 关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
	}

}

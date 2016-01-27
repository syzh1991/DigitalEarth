package test;

import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;


public class TestErrorPic {
	private String path="D:\\data";
	
	public void testErrorPic(File file){
		File[] flist=file.listFiles();
		for(File f:flist){
			if(!f.isDirectory()){
				ImageIcon img=new ImageIcon(f.getAbsolutePath());
				if(img.getIconWidth()<=0){
					f.delete();
					System.out.println(f.getName()+"已损坏!");
				}
			}
			else {
				testErrorPic(f);
			}
		}
		
	}
}

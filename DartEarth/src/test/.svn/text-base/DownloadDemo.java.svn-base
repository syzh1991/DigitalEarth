package test;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadDemo {
	private static int MAX_TRY_DOWNLOAD_TIME = 100;
	private static int BUFFER_SIZE = 1024 * 8;

	private static boolean saveToFile(String destUrl, String fileName) {
		int currentTime = 0;
		while (currentTime < MAX_TRY_DOWNLOAD_TIME) {
			try {
				FileOutputStream fos = null;
				BufferedInputStream bis = null;
				HttpURLConnection httpConnection = null;
				URL url = null;
				byte[] buf = new byte[BUFFER_SIZE];
				int size = 0;

				// 建立链接
				url = new URL(destUrl);
				// url.openConnection(arg0)
				currentTime++;

				// if (proxy != null) {
				// System.out.println(threadName + ":\t切换代理\t" +
				// proxy.address().toString());
				// } else {
				// System.out.println(threadName + ":\t使用本机IP");
				// }

				httpConnection = (HttpURLConnection) url.openConnection();

				httpConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

				httpConnection.setConnectTimeout(60000);
				httpConnection.setReadTimeout(60000);

				// 连接指定的资源
				httpConnection.connect();
				// 获取网络输入流
				bis = new BufferedInputStream(httpConnection.getInputStream());
				// 建立文件
				fos = new FileOutputStream(fileName);

				// System.out.println("正在获取链接[" + destUrl + "]的内容;将其保存为文件[" +
				// fileName + "]");

				// 保存文件
				while ((size = bis.read(buf)) != -1) {
					// System.out.println(size);
					fos.write(buf, 0, size);
				}

				fos.close();
				bis.close();
				httpConnection.disconnect();
				// currentTime = MAX_TRY_DOWNLOAD_TIME;
				break;
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		if (currentTime < MAX_TRY_DOWNLOAD_TIME) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url = "http://192.168.0.193:8000/wms?service=WMS&request=GetMap&version=1.3&srs=EPSG:4326&layers=bmng201412&styles=&transparent=TRUE&format=image/png&width=256&height=256&bbox=-180,-90,180,90";
		String filename = "D://d.png";
		saveToFile(url, filename);
		//BigInteger i = new BigInteger("1");
		BigInteger i = new BigInteger("100");
		System.out.println("-");
		while (true) {
			i = i.multiply(i);
			System.out.println(i.bitLength());
		}

	}

}

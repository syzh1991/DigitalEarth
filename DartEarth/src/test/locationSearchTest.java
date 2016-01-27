package test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class locationSearchTest {

	public static void main(String... str) {
		String pName = "中国";
		URLConnection httpsConn = null;
		URL url = null;
		try {
			url = new URL(
					"http://127.0.0.1:8080/DartEarthLocationServer/searchPlaceName.html?placeName="
							+ pName + "&pageCount=0");
			httpsConn = url.openConnection();
			httpsConn.setConnectTimeout(16000);
			httpsConn.setReadTimeout(12000);
			System.out.println(httpsConn.getHeaderField(null));
			InputStream content = (InputStream) httpsConn.getContent();
			InputStreamReader insr = new InputStreamReader(content, "UTF-8");
			BufferedReader br = new BufferedReader(insr);
			String data = "";
			String line;
			while ((line = br.readLine()) != null) {
				data += line;
			}
			JSONObject jsonObject = JSONObject.fromObject(data);
			Object bean = JSONObject.toBean(jsonObject);
			System.out.println(jsonObject.get("locations"));
			List list = (List) jsonObject.get("locations");
			Map map = (Map) list.get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package org.gfg.metadata;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.gfg.util.metadata.ReadFile;

public class ESUtil {
	private Client client;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * 预定义一个索引的mapping,使用mapping的好处是可以个性的设置某个字段等的属性
	 */
	public void buildIndex() {
		CreateIndexRequestBuilder cib = client.admin().indices()
				.prepareCreate("search");// search为索引类型
		String fileName = System.getProperty("user.dir")
				+ "/res/search-mappings.json";
		String mapping = ReadFile.readFile(fileName);
		cib.addMapping("search", mapping).execute().actionGet();
	}

	/**
	 * 该方法为增加索引记录
	 */
	public boolean addRecord(Map<String, Object> map, String key) {
		// 第一个search为上个方法中定义的索引名,第二个search为类型，key为id

		IndexResponse response = client.prepareIndex("search", "search", key)
				.setSource(map).execute().actionGet();
		return response.isCreated();
	}

	/**
	 * 得到访问es的客户端,我们使用Transport Client
	 */
	public Client buildClient() {
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("client.transport.sniff", true)
				.put("cluster.name", "search").build();
		Client client = new TransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(
						"master", 9300));
		return client;
	}

	public List<Map<String, Object>> search(
			Map<String, Object> multiRestrctionMap) {
		return null;

	}

	/**
	 * 搜索的使用
	 */

	public static void main(String[] dfd) {
		ESUtil esm = new ESUtil();
		esm.setClient(esm.buildClient());
		esm.buildIndex();

		esm.getClient().close();
	}
}

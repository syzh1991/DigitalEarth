package org.gfg.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.SearchHit;
import org.gfg.metadata.model.ESearchField;

public class ESQuery {
	//TODO 设置分页的效果
	public List<Map<String, Object>> queryByMultipleContidion(
			Map<String, Object> conditionMap) {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		FilterBuilder postFilter = convertConditionToFilter(conditionMap);
		Client client = new ESUtil().buildClient();
		SearchRequestBuilder builder = client.prepareSearch("search")
				.setTypes("search").setSearchType(SearchType.DEFAULT)
				.setPostFilter(postFilter).setSize(20).setFrom(0);
		builder.execute().actionGet();

		SearchResponse response = builder.execute().actionGet();
		long count = response.getHits().getTotalHits();
		System.out.println("the totalhits is " + count);
		for (SearchHit hit : response.getHits()) {
			System.out.println(hit.getId());
			Map<String,Object> map = hit.getSource();
			map.put( ESearchField._ID.getField(), hit.getId());
			ret.add(map);
		}
		client.close();

		return ret;
	}

	private FilterBuilder convertConditionToFilter(
			Map<String, Object> conditionMap) {
		FilterBuilder filter = FilterBuilders.matchAllFilter();
		/*
		 * satelliteName
		 */
		String satelliteName = ESearchField.SATELLITENAME.getField();
		if (conditionMap.get(satelliteName) != null) {
			filter = FilterBuilders.andFilter(
					filter,
					FilterBuilders.termFilter(satelliteName,
							conditionMap.get(satelliteName).toString()));
		}
		/*
		 * sensor
		 */
		String sensor = ESearchField.SENSOR.getField();
		if (conditionMap.get(sensor) != null) {
			filter = FilterBuilders.andFilter(filter, FilterBuilders
					.termFilter(sensor, conditionMap.get(sensor).toString()));
		}
		/*
		 * nresval
		 */
		String nresval = ESearchField.NRESVAL.getField();
		if (conditionMap.get(nresval) != null) {
			filter = FilterBuilders.andFilter(filter, FilterBuilders
					.termFilter(nresval, Double.parseDouble(conditionMap.get(
							nresval).toString())));
		}

		/*
		 * sensor
		 */
		String productLevel = ESearchField.PRODUCTLEVEL.getField();
		if (conditionMap.get(productLevel) != null) {
			filter = FilterBuilders.andFilter(
					filter,
					FilterBuilders.termFilter(productLevel,
							conditionMap.get(productLevel).toString()));
		}

		/*
		 * row
		 */
		String fromScenePath = ESearchField.FROMSCENEPATH.getField();
		String toScenePath = ESearchField.TOSCENEPATH.getField();
		if (conditionMap.get(fromScenePath) != null
				&& conditionMap.get(toScenePath) != null) {
			filter = FilterBuilders.andFilter(
					filter,
					FilterBuilders
							.rangeFilter(ESearchField.SCENEPATH.getField())
							.from(Double.parseDouble(conditionMap.get(
									fromScenePath).toString()))
							.to(Double.parseDouble(conditionMap
									.get(toScenePath).toString())));
		}
		/*
		 * ROW
		 */
		String fromSceneRow = ESearchField.FROMSCENEROW.getField();
		String toSceneRow = ESearchField.TOSCENEROW.getField();
		if (conditionMap.get(fromSceneRow) != null
				&& conditionMap.get(toSceneRow) != null) {
			filter = FilterBuilders.andFilter(
					filter,
					FilterBuilders
							.rangeFilter(ESearchField.SCENEROW.getField())
							.from(Double.parseDouble(conditionMap.get(
									fromSceneRow).toString()))
							.to(Double.parseDouble(conditionMap.get(toSceneRow)
									.toString())));
		}
		/*
		 * productId
		 */
		String productID = ESearchField.PRODUCTID.getField();
		if (conditionMap.get(productID) != null) {
			filter = FilterBuilders.andFilter(
					filter,
					FilterBuilders.termFilter(productID,
							conditionMap.get(productID).toString()));
		}

		/*
		 * sceneID
		 */
		String sceneID = ESearchField.SCENEID.getField();
		if (conditionMap.get(sceneID) != null) {
			filter = FilterBuilders.andFilter(filter, FilterBuilders
					.termFilter(sceneID, conditionMap.get(sceneID).toString()));
		}

		/*
		 * avgCloudCover
		 */
		String avgCloudCover = ESearchField.AVGCLOUDCOVER.getField();
		if (conditionMap.get(avgCloudCover) != null) {
			filter = FilterBuilders.andFilter(
					filter,
					FilterBuilders.rangeFilter(avgCloudCover).lte(
							Double.parseDouble(conditionMap.get(avgCloudCover)
									.toString())));
		}
		/*
		 * 此处已经转换为string的形式，yyyy-MM-dd形式的日期
		 */
		String fromProductDate = ESearchField.FROMPRODUCTDATE.getField();
		String toProductDate = ESearchField.TOPRODUCTDATE.getField();
		if (conditionMap.get(fromProductDate) != null
				&& conditionMap.get(toProductDate) != null) {
			filter = FilterBuilders.andFilter(
					filter,
					FilterBuilders
							.rangeFilter(ESearchField.PRODUCTDATE.getField())
							.from((String) conditionMap.get(fromProductDate))
							.to((String) conditionMap.get(toProductDate)));
		}

		return filter;

	}
	
	public static void main(String[] args) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();

		conditionMap.put(ESearchField.SATELLITENAME.getField(), "GF1");
		 // conditionMap.put(ESearchField.SENSOR.getField(), "PMS1");
		 //conditionMap.put(ESearchField.NRESVAL.getField(), 8);
		//conditionMap.put(ESearchField.PRODUCTLEVEL.getField(), "LEVEL1A");
		// conditionMap.put(ESearchField.FROMSCENEPATH.getField(), 0);
		// conditionMap.put(ESearchField.TOSCENEPATH.getField(), 600);
		// conditionMap.put(ESearchField.FROMSCENEROW.getField(), 0);
		// conditionMap.put(ESearchField.TOSCENEROW.getField(), 600);
		// conditionMap.put(ESearchField.PRODUCTID.getField(), "58868");
		// conditionMap.put(ESearchField.SCENEID.getField(), 67397);
		//conditionMap.put(ESearchField.AVGCLOUDCOVER.getField(), 100);
		String date1 = "2012-12-01";
		String date2 = "2013-12-01";
		//conditionMap.put(ESearchField.FROMPRODUCTDATE.getField(), date1);
		//conditionMap.put(ESearchField.TOPRODUCTDATE.getField(), date2);

		ESQuery esQuery = new ESQuery();
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		ret = esQuery.queryByMultipleContidion(conditionMap);
		for (Map<String, Object> map : ret) {
			for (String s : map.keySet()) {
				System.out.println(s + ":" + map.get(s));
			}
			System.out.println("---------------");
		}
		System.out.println(ret.size());
	}
}

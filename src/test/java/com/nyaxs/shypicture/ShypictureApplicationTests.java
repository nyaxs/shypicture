package com.nyaxs.shypicture;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nyaxs.shypicture.bean.Picture;
import com.nyaxs.shypicture.bean.SearchCondition;
import com.nyaxs.shypicture.util.DownloadUtil;
import com.nyaxs.shypicture.util.JsonUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShypictureApplicationTests {


	@Test
	public void test() throws Exception {

		SearchCondition condition1 = new SearchCondition();
		SearchCondition condition2 = new SearchCondition();
		condition2.setDate("2020-05-10");
		condition2.setRankMode("monthly");

		System.out.println("###################" + condition1 + "###################" + condition2);
		System.out.println("###################" + JsonUtil.obj2String(condition1)
				+ "###################" + JsonUtil.obj2String(condition2));

		List<Picture> pictureList1 = getAndDownPictureList(condition1);
		List<Picture> pictureList2 = getAndDownPictureList(condition2);
		System.out.println("###################" + pictureList1 + "###################" + pictureList2);
		for (Picture picture :
				pictureList1) {
			System.out.println(picture.getId() + "##");
		}
		System.out.println("####################condition1和condition2的list id 分界线");
		for (Picture picture :
				pictureList2) {
			System.out.println(picture.getId() + "##");
		}

	}


	LocalDate today = null;
	LocalDate twoDaysAgo = null;

	public List<Picture> getAndDownPictureList(SearchCondition condition) throws Exception {
		today = LocalDate.now();
		twoDaysAgo = today.minusDays(2);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		String urlDaily = "https://api.imjad.cn/pixiv/v1/" +
				"?type=" + condition.getType() +
				"&mode=" + condition.getRankMode() +
				"&per_page=" + condition.getRankPerPage() +
				"&content=" + condition.getContent() +
				"&date=" + condition.getDate();
		JsonNode resultRootNode = objectMapper.readTree(new URL(urlDaily));
		//Json node tree , use node.at() to get target node
		JsonNode worksNode = resultRootNode.at("/response").get(0).at("/works");

		String worksString = objectMapper.writeValueAsString(worksNode);

		List<Object> worksList = objectMapper
				.readValue(worksString, new TypeReference<List<Object>>() {
				});

		List<Picture> pictureList = new ArrayList<Picture>();
		//保存pictureList的大图下载链接
		//List<String>  pictureImageLargeUrls = new ArrayList<String>();

		for (Object obj : worksList) {
			Map<String, Object> workMap = (Map<String, Object>) obj;
			String workStr = JsonUtil.obj2String(workMap.get("work"));
			Picture picture = objectMapper.readValue(workStr, Picture.class);
			pictureList.add(picture);
			//pictureImageLargeUrls.add(picture.getImage_urls().get("large"));
		}
		;

		System.out.println("###########pictureList: " + pictureList.get(0).getImage_urls().get("large"));

		DownloadUtil.get().downloadPicturesByList(pictureList);

		return pictureList;
	}
}

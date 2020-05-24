package com.nyaxs.shypicture;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nyaxs.shypicture.bean.Picture;
import com.nyaxs.shypicture.util.JsonUtil;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

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
	public void test() throws Exception{

		RestTemplate restTemplate = null;
		LocalDate today = null;
		LocalDate yesterday = null;

		today = LocalDate.now();
		yesterday = today.minusDays(1);
		restTemplate = new RestTemplate();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String url = "https://api.imjad.cn/pixiv/v1/?type=rank&mode=daily&per_page=2&content=illust&date="+yesterday;

		JsonNode resultRootNode = objectMapper.readTree(new URL(url));

		System.out.println(resultRootNode);

		JsonNode worksNode = resultRootNode.at("/response").get(0).at("/works");

		String worksString = objectMapper.writeValueAsString(worksNode);

		List<Object> worksList = objectMapper
				.readValue(worksString, new TypeReference<List<Object>>(){});

		List<Picture> pictureList = new ArrayList<Picture>();

		for (Object obj:worksList) {

			Map<String,Object> workMap = (Map<String, Object>) obj;

			String workStr = JsonUtil.obj2String(workMap.get("work")) ;
			pictureList.add(objectMapper.readValue(workStr,Picture.class));
		};


		/*
		Map<String,Object> resultMap = objectMapper
				.readValue(new URL(url) , new TypeReference<Map<String,Object>>(){});

		//List<Picture> pictureList = objectMapper
			//	.readValue(new URL(url) , new TypeReference<List<Picture>>(){});

		List<Object> responseList = (List<Object>) resultMap.get("response");


		Map<String,Object> responseMap = (Map<String, Object>) responseList.get(0);


		List<Object> worksList = (List<Object>) responseMap.get("works");


		List<Picture> pictureList = new ArrayList<Picture>();

		for (Object obj:worksList) {

			Map<String,Object> workMap = (Map<String, Object>) obj;

			String workStr = JsonUtil.obj2String(workMap.get("work")) ;
			System.out.println("----------------------------workStr-------------------------------");
			System.out.println(workStr);

			pictureList.add(objectMapper.readValue(workStr,Picture.class));
			System.out.println("----------------------------JsonUtil.string2Obj(workStr,Picture.class)-------------------------------");
			System.out.println(pictureList);
		};



		for (Picture p :
				pictureList) {
			System.out.println("picture实例属性"+p.getId()+p.getTitle()+p.getImage_urls().get("large"));
		}


		 */
















		/*
		String result = restTemplate.getForObject(url, String.class);
		//System.out.println("get获取的json字符串" + result);
		//objectMapper.readValue(new URL(url),Picture.class);
		Map<String,Object> resultMap = objectMapper.readValue(result,Map.class);
		List<Object> responseList = JsonUtil.string2Obj(resultMap.get("response").toString(),List.class);
		System.out.println("-----------------------------responseList------------------------------");
		System.out.println(responseList);
		Map<String,Object> responseMap = (Map<String, Object>) responseList.get(0);
		System.out.println("-----------------------------responseMap------------------------------");
		System.out.println(responseMap);
		List<Object> worksList = (List<Object>) responseMap.get("works");
		System.out.println("-----------------------------worksList------------------------------");
		System.out.println(worksList);
		String worksListStr = JsonUtil.obj2String(worksList);
		List<Map<String,Object>> workMapList = objectMapper
				.readValue(worksListStr,new TypeReference<List<Map<String,Object>>>(){});
		List<Picture> pictureList = new ArrayList<>();
		Picture p =  new Picture();
		for (Map<String, Object> map:workMapList){
			String pictureStr = map.get("work").toString();
			pictureList.add(JsonUtil.string2Obj(pictureStr,Picture.class));
		}

		//Map<String,Object> worksMap = (Map<String, Object>) worksList.get(0);
		String works = JsonUtil.obj2String(pictureList);
		//JsonNode jsonNode = objectMapper.readTree(result);
		//JsonNode response = jsonNode.get("response");
		System.out.println("-----------------------------------------------------------");
		//System.out.println(response);
		System.out.println("-----------------------------------------------------------");
		//JsonNode works = response.get("works");
		System.out.println(works);
		//List<Picture> pictureList = objectMapper.readValue(works.asText(),new TypeReference<List<Picture>>(){} );
		//model.addAttribute("picture",pictureList);
		System.out.println("-----------------------------------------------------------");
		System.out.println("pictureList:"+pictureList);

		 */
	}

}

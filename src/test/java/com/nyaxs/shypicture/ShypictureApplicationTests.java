package com.nyaxs.shypicture;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nyaxs.shypicture.bean.Picture;
import com.nyaxs.shypicture.bean.SearchCondition;
import com.nyaxs.shypicture.controller.PictureController;
import com.nyaxs.shypicture.service.PictureMapper;
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

	@Autowired
	private PictureMapper pictureMapper;

	@Test
	public void test1() throws Exception {

		SearchCondition condition2 = new SearchCondition();
		PictureController pictureController = new PictureController();
		condition2.setDate("2020-05-15");
		condition2.setRankPerPage(2);
		condition2.setRankMode("monthly");
		List<Picture> pictures = pictureController.getPictureList(condition2);

		for (Picture p :
				pictures) {
			pictureMapper.insertPicture(p.getId(), p.getImage_urls().get("large"), 0);
		}

		pictureMapper.deletePictureById(1233);

	}

	@Test
	public void test() throws Exception {

		SearchCondition condition1 = new SearchCondition();
		SearchCondition condition2 = new SearchCondition();
		condition2.setDate("2020-05-10");
		condition2.setRankMode("monthly");

		System.out.println("###################" + condition1 + "###################" + condition2);
		System.out.println("###################" + JsonUtil.obj2String(condition1)
				+ "###################" + JsonUtil.obj2String(condition2));

	}




}

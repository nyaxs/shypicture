package com.nyaxs.shypicture;

import com.nyaxs.shypicture.bean.Picture;
import com.nyaxs.shypicture.bean.SearchCondition;
import com.nyaxs.shypicture.controller.PictureController;
import com.nyaxs.shypicture.mapper.PictureMapper;
import com.nyaxs.shypicture.util.AsyncDownload;
import com.nyaxs.shypicture.util.JsonUtil;
import com.nyaxs.shypicture.util.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShypictureApplicationTests {

    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    private Task task;

    @Autowired
    private AsyncDownload asyncDownload;

    @Test
    public void testSyncTasks() throws Exception {
        asyncDownload.doTaskOne();
        asyncDownload.doTaskTwo();
        asyncDownload.doTaskThree();
    }

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

package com.nyaxs.shypicture.service;

import com.nyaxs.shypicture.bean.Picture;
import com.nyaxs.shypicture.bean.PictureData;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PictureMapper {
    //插入多条语句
    @Insert("<script>" +
            "INSERT IGNORE INTO picture(id,largeUrl,ageLimit) VALUES" +
            "<foreach collection=\"list\" item=\"item1\" index=\"index\"  separator=\",\">" +
            "(#{item1.id},#{item1.largeUrl},#{item1.ageLimit})" +
            "</foreach>" +
            "</script>")
    int insertManyPictures(@Param("list") List<Picture> pictureList);

    @Insert("<script>" +
            "INSERT IGNORE INTO picture(id,largeUrl,ageLimit) VALUES" +
            "<foreach collection=\"list\" item=\"item1\" index=\"index\"  separator=\",\">" +
            "(#{item1.pictureId},#{item1.largeUrl},#{item1.ageLimit})" +
            "</foreach>" +
            "</script>")
    int insertManyPicturesByPictureData(@Param("list") List<PictureData> pictureDataList);

    //获取指定数量随机图片
    @Select("select * from PICTURE order by rand() limit #{num}")
    List<Picture> findRandomPicture(@Param("num") int num);

    //查询picture总数
    @Select("select count(1) from PICTURE")
    int findPictureNumber();

    //根据id获取picture
    @Select("select * from PICTURE where id = #{id}")
    Picture findPictureById(@Param("id") int id);

    //插入单条数据
    @Insert("insert ignore into PICTURE(id,largeUrl,ageLimit) values(#{id},#{largeUrl},#{ageLimit})")
    int insertPicture(@Param("id") int id, @Param("largeUrl") String largeUrl, @Param("ageLimit") int ageLimit);

    //根据id删除
    @Delete("delete from PICTURE where id = #{id}")
    int deletePictureById(int id);

    //更新picture的限制年龄ageLimit字段
    @Update("update PICTURE set ageLimit = #{ageLimit} where id = #{id}")
    void updateAgeLimit(@Param("ageLimit") int ageLimit, @Param("id") int id);

}

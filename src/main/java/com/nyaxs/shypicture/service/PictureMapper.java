package com.nyaxs.shypicture.service;

import com.nyaxs.shypicture.bean.Picture;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PictureMapper {

    @Select("select * from picture where id = #{id}")
    Picture findPictureById(@Param("id") int id);

    @Insert("insert ignore into picture(id,largeUrl,ageLimit) values(#{id},#{largeUrl},#{ageLimit})")
    int insertPicture(@Param("id") int id, @Param("largeUrl") String largeUrl, @Param("ageLimit") int ageLimit);

    @Delete("delete from picture where id = #{id}")
    int deletePictureById(int id);

    @Update("update picture set ageLimit = #{ageLimit} where id = #{id}")
    void updateAgeLimit(@Param("ageLimit") int ageLimit, @Param("id") int id);

}

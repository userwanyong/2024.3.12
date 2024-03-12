package com.example.sp.mapper;

import com.example.sp.pojo.Shop;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShopMapper {
    //新增
    @Insert("insert into shop(shop_name,cover_img,state,content,category_id,create_user,create_time,update_time)" +
            "values (#{shopName},#{coverImg},#{state},#{content},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void add(Shop shop);

    List<Shop> list(Integer userId, Integer categoryId, String state);

    //根据id查询
    @Select("select * from shop where id = #{id}")
    Shop findById(Integer id);


    //更新
    @Update("update shop set shop_name=#{shopName},cover_img=#{coverImg},state=#{state},update_time=#{updateTime} where id=#{id}")
    void update(Shop shop);

    //删除
    @Delete("delete from shop where id=#{id}")
    void delete(Integer id);
}

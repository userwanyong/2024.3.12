package com.example.sp.service;

import com.example.sp.pojo.PageBean;
import com.example.sp.pojo.Shop;

public interface ShopService {
    //新增商品
    void add(Shop shop);

    //条件分页列表查询
    PageBean<Shop> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    Shop findById(Integer id);

    void update(Shop shop);

    void delete(Integer id);
}

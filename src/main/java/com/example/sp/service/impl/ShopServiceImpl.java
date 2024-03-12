package com.example.sp.service.impl;

import com.example.sp.mapper.ShopMapper;
import com.example.sp.pojo.PageBean;
import com.example.sp.pojo.Shop;
import com.example.sp.service.ShopService;
import com.example.sp.utils.ThreadLocalUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopMapper shopMapper;

    @Override
    public void add(Shop shop) {
        //补充属性值
        shop.setCreateTime(LocalDateTime.now());
        shop.setUpdateTime(LocalDateTime.now());
        Map<String,Object> map = ThreadLocalUtils.get();
        Integer userId = (Integer) map.get("id");
        shop.setCreateUser(userId);
        shopMapper.add(shop);
    }

    @Override
    public PageBean<Shop> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //1，创建PageBean对象
        PageBean<Shop> pb = new PageBean<>();
        //2，开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        //3，调用mapper
        Map<String,Object> map = ThreadLocalUtils.get();
        Integer userId = (Integer) map.get("id");
        List<Shop> as =shopMapper.list(userId,categoryId,state);
        //Page中提供了方法，可以获取PageHelper分页查询后，得到的总记录条数和当前页数据
        Page<Shop> p = (Page<Shop>) as;
        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public Shop findById(Integer id) {
        Shop c = shopMapper.findById(id);
        return c;
    }

    @Override
    public void update(Shop shop) {
        shop.setUpdateTime(LocalDateTime.now());
        shopMapper.update(shop);
    }

    @Override
    public void delete(Integer id) {
        shopMapper.delete(id);
    }
}

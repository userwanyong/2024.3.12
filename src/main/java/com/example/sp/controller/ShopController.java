package com.example.sp.controller;

import com.example.sp.pojo.Category;
import com.example.sp.pojo.PageBean;
import com.example.sp.pojo.Result;
import com.example.sp.pojo.Shop;
import com.example.sp.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;

    @PostMapping
    public Result add(@RequestBody @Validated Shop shop){
        shopService.add(shop);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Shop>> list(
           Integer pageNum,
           Integer pageSize,
           @RequestParam(required = false) Integer categoryId,
           @RequestParam(required = false) String state
    ){
        PageBean<Shop> pb = shopService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
    }

    @GetMapping("/detail")
    public Result<Shop> detail(Integer id){
        Shop c = shopService.findById(id);
        return Result.success(c);
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Shop shop){
        shopService.update(shop);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id){
        shopService.delete(id);
        return Result.success();
    }



}

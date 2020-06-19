package com.example.repair.controller;


import com.example.repair.Result.CodeMsg;
import com.example.repair.Result.Result;
import com.example.repair.Service.UserService;
import com.example.repair.entity.RepairMan;
import com.example.repair.entity.totalCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/total")
public class totalController {

    @Autowired
    private UserService userService;

    /**添加信息*/
    @PostMapping("/insert")
    public Result<totalCount> insert(@RequestBody totalCount totalCount){
        totalCount.setValid(1);
        totalCount totalCount1=userService.insert(totalCount);
        if(totalCount1!=null){
            return Result.success(CodeMsg.SUCCESS);
        }else{
            return  Result.error(CodeMsg.ERROR);
        }
    }

    /**查找有效记录*/
    @GetMapping("/find/{page}/{size}")
    public Result<Page<totalCount>> find(@PathVariable Integer page, @PathVariable Integer size){
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<totalCount> totalCounts=userService.findTrue(pageable);
        if(totalCounts!=null){
            return Result.success(totalCounts);
        }else {
            return  Result.error(CodeMsg.ERROR);
        }
    }

    /**失效某条记录*/
    @PostMapping("/lose")
    public Result<totalCount> lose(@RequestParam Long id){
       try{
        totalCount totalCount=userService.findTotalById(id);
        totalCount.setValid(0);
        userService.insert(totalCount);
        return Result.success(CodeMsg.SUCCESS);
        }catch (Exception e){
            return  Result.error(CodeMsg.ERROR);
        }
    }

    /**修改某条记录*/
    @PostMapping("/update/{id}")
    public Result<totalCount> update(@PathVariable Long id,@RequestBody totalCount totalCount){
        try{
            userService.update(id,totalCount);
            return Result.success(CodeMsg.SUCCESS);
        }catch (Exception e){
            return  Result.error(CodeMsg.ERROR);
        }
    }

    /**根据日期查询统计结果*/
    @GetMapping("/findByDate")
    public Result<List<totalCount>> findByDate(@RequestParam String date){
        try {
            List<totalCount> totalCounts=userService.findByDate(date);
            return Result.success(totalCounts);
        }catch (Exception e){
            return Result.error(CodeMsg.ERROR);
        }
    }


}

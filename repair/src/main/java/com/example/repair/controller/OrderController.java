package com.example.repair.controller;


import com.example.repair.Result.CodeMsg;
import com.example.repair.Result.Result;
import com.example.repair.Service.UserService;
import com.example.repair.Utils.DateUtil;
import com.example.repair.Utils.uuidUtil;
import com.example.repair.entity.Order;
import com.example.repair.entity.RepairMan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private UserService userService;


    /**用户下单*/
    @PostMapping("/place")
    public Result<Order> insert(@RequestParam String user,@RequestParam String phoneNumber,@RequestParam String address,@RequestParam String repairType,@RequestParam String text,@RequestParam(required = false) MultipartFile fileField) throws IOException {
        /*图片存储URL*/
        String staticPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath(); //存储到static目录下
        System.out.println(staticPath);
        String fileName = fileField.getOriginalFilename();  //获取文件名
        // 图片存储目录及图片名称
        String url_path =File.separator + fileName;
        //图片保存路径
        String savePath = staticPath + File.separator + url_path;
        System.out.println("图片保存地址："+savePath);
        // 访问路径=静态资源路径+文件目录路径
        String visitPath ="http://3090108ec6.zicp.vip"+url_path;

        System.out.println("图片访问uri："+visitPath);

        File saveFile = new File(savePath);
        if (!saveFile.exists()){
            saveFile.mkdirs();
        }
        try {
            fileField.transferTo(saveFile);  //将临时存储的文件移动到真实存储路径下
        } catch (IOException e) {
            e.printStackTrace();
        }



        Order order=new Order();
        order.setPic(visitPath);
        order.setUser(user);
        order.setPhoneNumber(phoneNumber);
        order.setAddress(address);
        order.setRepairType(repairType);
        order.setText(text);
        order.setOrderNumber(uuidUtil.getOrderIdByUUId());
        order.setOrderCreate(DateUtil.getTimeStamp());
        order.setCode("1");
        Order order1=userService.insert(order);
        if(order1!=null){
            return Result.success(CodeMsg.SUCCESS);
        }else{
            return Result.error(CodeMsg.ERROR);
        }
    }


    /**后台判断为无效单*/
    @PostMapping("/judgeFalse")
    public Result<Order> judgeFalse(@RequestParam Long id){
        try{
            userService.judgeFalse(id);
            return Result.success(CodeMsg.SUCCESS);
        }catch (Exception e){
            return Result.error(CodeMsg.ERROR);
        }
    }

    /**后台判断为有效单*/
    @PostMapping("/judgeTrue")
    public Result<Order> judgeTrue(@RequestParam Long id){
        try {
            userService.judgeTrue(id);
            return Result.success(CodeMsg.SUCCESS);
        }catch (Exception e){
            return Result.error(CodeMsg.ERROR);
        }
    }


    /**按状态码进行查询不同状态的单子*/
    @GetMapping("/findByCode/{code}/{page}/{size}")
    public Result<Page<Order>> findByCode(@PathVariable String code, @PathVariable Integer page, @PathVariable Integer size){
        try{
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Order> orders=userService.findByCode(code,pageable);
            return Result.success(orders);
        }catch (Exception e){
            return Result.error(CodeMsg.ERROR);
        }
    }

    /**修理工接单*/
    @PostMapping("/take")
    public Result<Order> take(@RequestParam Long repairId,@RequestParam Long orderId,@RequestParam Float probablyPrice,@RequestParam String serverTime){
      try{
        Order order=userService.findById(orderId);
        RepairMan repairMan=new RepairMan();
        repairMan.setId(repairId);
        order.setRepairMan(repairMan);
        order.setProbablyPrice(probablyPrice);
        order.setServerTime(serverTime);
        order.setCode("3");
        userService.insert(order);
        return Result.success(CodeMsg.SUCCESS);
        }catch (Exception e){
            return Result.error(CodeMsg.ERROR);
        }
    }

    /**用户撤单，并说明撤单理由*/
    @PostMapping("/undo")
    public Result<Order> undo(@RequestParam Long id,@RequestParam String undoOrder){
        try {
            Order order1=userService.judgeFalse(id); //返回状态0，即无效单
            order1.setUndoOder(undoOrder);
            userService.insert(order1);
            return Result.success(CodeMsg.SUCCESS);
        }catch (Exception e){
            return Result.error(CodeMsg.ERROR);
        }
    }

    /**用户退单，并说明退单理由，交由后台审核*/
    @PostMapping("/chargeBack")
    public Result<Order> chargeBack(@RequestParam Long id,@RequestParam String chargeBack){
        try {
            Order order1=userService.findById(id);
            order1.setCode("-1");
            order1.setChargeBack(chargeBack);
            userService.insert(order1);
            return Result.success(CodeMsg.SUCCESS);
        }catch (Exception e){
            return Result.error(CodeMsg.ERROR);
        }
    }


    /**修理工修理完后报价*/
    @PostMapping("/finalPrice")
    public Result<Order> finalPrice(@RequestParam Long id,@RequestParam Float finalPrice){
        try {
            Order order1=userService.findById(id);
            order1.setCode("4");
            order1.setFinalPrice(finalPrice);
            userService.insert(order1);
            return Result.success(CodeMsg.SUCCESS);
        }catch (Exception e){
            return Result.error(CodeMsg.ERROR);
        }
    }


    /**订单报价后，用户进行支付*/
    @PostMapping("/pay")
    public Result<Order> pay(@RequestParam Long repairId,@RequestParam Long orderId){
        try{
            RepairMan repairMan=userService.findRepairById(repairId);
            Order order=userService.findById(orderId);
            repairMan.setBalance(repairMan.getBalance()+order.getFinalPrice());  //修理工收钱
            repairMan.setSuccessNumber(repairMan.getSuccessNumber()+1);  //修理工成功单数+1
            order.setCode("5");
            userService.Register(repairMan);
            userService.insert(order);
            return Result.success(CodeMsg.SUCCESS);
        }catch (Exception e){
            return Result.error(CodeMsg.ERROR);
        }
    }


    /**订单结束，用户进行评价*/
    @PostMapping("/evaluation")
    public Result<Order> evaluation(@RequestParam Long id,@RequestParam String evaluation){
        try {
            Order order1=userService.findById(id);
            order1.setCode("6");
            order1.setEvaluation(evaluation);
            userService.insert(order1);
            return Result.success(CodeMsg.SUCCESS);
        }catch (Exception e){
            return Result.error(CodeMsg.ERROR);
        }
    }



}

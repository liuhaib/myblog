package com.liuhaibin.myblog.controller;


import com.liuhaibin.myblog.service.SysFileService;
import com.liuhaibin.myblog.uaits.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  文件控制器
 * </p>
 *
 * @author haibin
 * @since 2022-09-08
 */
@RestController
public class SysFileController {

    @Resource
    SysFileService sysFileService;

    //查看全部图片信息
    @GetMapping("/file/all")
    public Result GetAllImage(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize){
        return sysFileService.flieList(pageNum,pageSize);
    }

    //删除图片
    @DeleteMapping("/file/del/{id}")
    public Result deleteUser(@PathVariable Long id){
        boolean removeById = sysFileService.removeById(id);
        if(removeById){
            return Result.success();
        }else{
            return Result.error("删除失败");
        }
    }
    @PostMapping("/file/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        if(sysFileService.removeByIds(ids)){
            return Result.success();
        }else {
            return Result.error("批量删除失败");
        }
    }

    //图片上传
    @PostMapping("/file/uploadFile")
    public Result updateUserAvatar(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return Result.error("上传文件为空,请重试...");
        }
        return Result.success(sysFileService.updataFlie(file));
    }

    //图片下载
//    @GetMapping("/{filename}")
//    public Result  download(@PathVariable String filename, HttpServletResponse response) throws Exception {
//        if (filename.isEmpty()) {
//            return Result.error("文件名为空");
//        }
//        try {
//            String privateFile = qiniuUtils.getFile(filename);
//            return Result.ok(privateFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.fail("系统错误");
//        }
//    }


}


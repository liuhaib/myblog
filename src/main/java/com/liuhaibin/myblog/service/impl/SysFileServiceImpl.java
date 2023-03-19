package com.liuhaibin.myblog.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhaibin.myblog.mapper.SysFileMapper;
import com.liuhaibin.myblog.pojo.SysFile;
import com.liuhaibin.myblog.service.SysFileService;
import com.liuhaibin.myblog.uaits.QiniuUtils;
import com.liuhaibin.myblog.uaits.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haibin
 * @since 2022-09-08
 */
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {

    @Resource
    QiniuUtils qiniuUtils;


    @Override
    public Result flieList(Integer pageNum, Integer pageSize) {

        Page<SysFile> page = new Page<>(pageNum,pageSize);
        QueryWrapper<SysFile> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        Page<SysFile> filePage = page(page, wrapper);
        return Result.success(filePage);
    }

    @Override
    public String updataFlie(MultipartFile file) throws IOException {
        //获取上传 文件 名称
        String originalFilename = file.getOriginalFilename();
        //获取上传 文件 类型
        String type = FileUtil.extName(originalFilename);
        //获取上传 文件 的大小
        long size = file.getSize();


        // 定义一个文件唯一的标识码
        String ThisTime = LocalDate.now().toString();//获取当天日期
        String uuid = IdUtil.fastSimpleUUID();//唯一标识
        String fileName = ThisTime + StrUtil.UNDERLINE + uuid + StrUtil.DOT + type;

        String url;
        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        // 从数据库查询是否存在相同的记录
        SysFile dbFiles = getbyMD5(md5);

        try {
            if (dbFiles != null) {
                return dbFiles.getFileurl();
            }else {
                FileInputStream uploadFile = (FileInputStream) file.getInputStream();
                String path = qiniuUtils.upload(uploadFile, fileName);
                url = "http://" + path;
                //写入数据库
                SysFile userFile = new SysFile();
                userFile.setFilesize(size/1024);
                userFile.setFiletype(type);
                userFile.setFilename(fileName);
                userFile.setFileurl(url);
                userFile.setFilemd5(md5);
                save(userFile);
                return url;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "服务器内部错误...";
        }
    }

    private SysFile getbyMD5(String md5) {
        QueryWrapper<SysFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("filemd5",md5);
        List<SysFile> list = list(queryWrapper);
        return  list.size() == 0 ?  null:  list.get(0);
    }


}

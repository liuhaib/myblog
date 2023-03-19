package com.liuhaibin.myblog.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.liuhaibin.myblog.pojo.SysFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhaibin.myblog.uaits.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haibin
 * @since 2022-09-08
 */
public interface SysFileService extends IService<SysFile> {

    Result flieList(Integer pageNum, Integer pageSize);

    String updataFlie(MultipartFile file) throws IOException;
}

package com.liuhaibin.myblog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author haibin
 * @since 2022-09-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysFile对象", description="")
public class SysFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "文件名称")
    private String filename;

    @ApiModelProperty(value = "文件类型")
    private String filetype;

    @ApiModelProperty(value = "文件大小(kb)")
    private Long filesize;

    @ApiModelProperty(value = "下载链接")
    private String fileurl;

    @ApiModelProperty(value = "文件md5")
    private String filemd5;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDelete;


}

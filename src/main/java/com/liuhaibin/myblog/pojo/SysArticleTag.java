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
 * @since 2023-01-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysArticleTag对象", description="")
public class SysArticleTag implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "文章id")
    private Integer articleId;

    @ApiModelProperty(value = "标签id")
    private Integer tagId;


}

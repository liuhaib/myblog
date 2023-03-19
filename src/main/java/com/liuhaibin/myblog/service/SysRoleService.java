package com.liuhaibin.myblog.service;

import com.liuhaibin.myblog.dto.RoleDTO;
import com.liuhaibin.myblog.pojo.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhaibin.myblog.uaits.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haibin
 * @since 2022-05-03
 */
public interface SysRoleService extends IService<SysRole> {

   /**
    * 根据id 获取角色
    * @param id
    * @return
    */
   List<String> GetRoleByUserId(Long id);

    /**
     * 获取全部角色
     * @return
     */
   Result getallRole();

    /**
     * 分页查询数据
     * @param pageNum
     * @param pageSize
     * @param keywords
     * @return
     */
   Result listRoles(Integer pageNum, Integer pageSize, String keywords);

   @Transactional
   Result deleteRole(List<Long> roleIdList);

   @Transactional
   Result SaveOrUpdate(RoleDTO roleDTO);
}

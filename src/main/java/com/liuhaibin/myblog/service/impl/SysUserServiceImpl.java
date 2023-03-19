package com.liuhaibin.myblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhaibin.myblog.anth.MyUserDetails;
import com.liuhaibin.myblog.constant.RedisConstant;
import com.liuhaibin.myblog.dto.PasswordDto;
import com.liuhaibin.myblog.dto.SysUserDto;
import com.liuhaibin.myblog.mapper.SysUserMapper;
import com.liuhaibin.myblog.mapper.SysUserRoleMapper;
import com.liuhaibin.myblog.pojo.SysUser;
import com.liuhaibin.myblog.service.SysUserService;
import com.liuhaibin.myblog.uaits.JwtUait;
import com.liuhaibin.myblog.uaits.Result;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author haibin
 * @since 2022-04-27
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    SysUserRoleMapper sysUserRoleMapper;

    private static final String[] IGNORE_ISOLATOR_PROPERTIES = new String[]{"password"};

    /**
     * 登入
     * @param loginUser
     * @return
     */
    @Override
    public Result UserLogin(SysUserDto loginUser) {
        //获取登入的用户名和密码  封装进UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getUsername(),loginUser.getPassword());
        //通过AuthenticationManager的authenticate方法来进行用户认证  但是这个方法需要一个UsernamePasswordAuthenticationToken类型的参数
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取用户对象
        MyUserDetails myUserDetails = (MyUserDetails) authenticate.getPrincipal();
        SysUser user = myUserDetails.getSysUser();

        //拷贝 数据
        SysUserDto sysUserDto = new SysUserDto();
        BeanUtil.copyProperties(user, sysUserDto,IGNORE_ISOLATOR_PROPERTIES);

        //签发token
        String token = JwtUait.GetJwt(sysUserDto.getId());
        sysUserDto.setToken(token);

        // 用户数据储存 redis
        String jsonPrettyStr = JSONUtil.toJsonPrettyStr(myUserDetails);
        stringRedisTemplate.opsForValue()
                .set(RedisConstant.RED_LOGIN+sysUserDto.getId().toString(),
                        jsonPrettyStr,
                        RedisConstant.RED_LOGIN_TTL,TimeUnit.DAYS);//设置30分钟 删除 缓存

        return Result.success(sysUserDto);
    }

    /**
     * 分页 查询
     * @param pageNum
     * @param pageSize
     * @param keywords
     * @return
     */
    @Override
    public Result getAll(Integer pageNum, Integer pageSize, String keywords) {
        Page<SysUser> page = new Page<>(pageNum,pageSize);
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        if (!"".equals(keywords)) {
            wrapper.like("nickname", keywords);
        }
        wrapper.orderByDesc("id");
        Page<SysUser> userPage = page(page, wrapper);
        return Result.success(userPage);
    }


    /**
     * 用户信息 修改
     * @param user
     * @return
     */
    @Override
    public Result updateUser(SysUser user) {
        //更新用户表格 数据
        updateById(user);
        //更新用户角色关系表
        sysUserRoleMapper.updateUserRole(user);

        return Result.success();
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @Override
    public Result AddUser(SysUser user) {
        //密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //保存用户
        save(user);
        //获取 新用户的信息 用户id  用户角色id
        SysUser one = getOne(new QueryWrapper<SysUser>().eq("username", user.getUsername()));
        //新增用户角色关系
        sysUserRoleMapper.saveUserRole(one);
        return Result.success();
    }

    /**
     * 用户退出
     * @return
     */
    @Override
    public Result LoGOut() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails loginUser = (MyUserDetails) authentication.getPrincipal();
        String id = loginUser.getSysUser().getId().toString();
        stringRedisTemplate.delete(RedisConstant.RED_LOGIN +id);
        return Result.success();
    }

    /**
     * 用户删除
     * @param id
     * @return
     */
    @Override
    public Result deleteUser(Long id) {
        //删除用户
        removeById(id);
        //删除用户角色关系
        sysUserRoleMapper.DeleteUserRoleByID(id);
        return Result.success();
    }

    /**
     * 用户密码修改
     * @param passwordDto
     * @return
     */
    @Override
    public Result updatePassword(PasswordDto passwordDto) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",passwordDto.getId());
        SysUser one = getOne(queryWrapper);
        if (one == null) {
            return Result.error("用户不存在");
        }
        one.setPassword(passwordEncoder.encode(passwordDto.getConfirmPassword()));
        boolean b = updateById(one);
        if (b) {
            return Result.success("修改成功");
        }else {
            return Result.error("修改失败");
        }

    }


}

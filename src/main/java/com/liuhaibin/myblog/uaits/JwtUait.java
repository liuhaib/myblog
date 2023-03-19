package com.liuhaibin.myblog.uaits;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTUtil;

import java.util.HashMap;
import java.util.Map;

public class JwtUait {

    private static final String Hash_Yan = "java";

    /**
     * JWT 根据id 生成
     * @param id
     * @return
     */
    public static String GetJwt(Long id){
        Map<String, Object> map = new HashMap<String, Object>() {

            {
                put("id", id);
                put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24);
            }
        };
        return JWTUtil.createToken(map, Hash_Yan.getBytes());
    }

    /**
     * JWT 验证
     * @param token
     * @return
     */
    public static boolean JwtVerify(String token){
       return   JWTUtil.verify(token, Hash_Yan.getBytes());
    }

    /**
     * JWT 解析获取 id
     * @param token
     * @return
     */
    public static Long ParseTokenGetId(String token ){
        final JWT jwt = JWTUtil.parseToken(token);
        jwt.getHeader(JWTHeader.TYPE);
        Integer id = (Integer) jwt.getPayload("id");
        return id.longValue();
    }
}

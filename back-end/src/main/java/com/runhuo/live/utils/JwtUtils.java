package com.runhuo.live.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/8 16:33
 */
public class JwtUtils {
    // token时效：24小时
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    // 签名哈希的密钥，对于不同的加密算法来说含义不同
    public static final String APP_SECRET = "never-debug11903990702";

    /**
     * 生成token字符串
     *
     * @param id
     * @return
     */
    public static String getJwtToken(Long id) {
        long time = new Date().getTime();
        String jwtToken = Jwts.builder().setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("never-debug")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("userId", id)
                .claim("time", time)//通过时间戳决定唯一登录用户
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
        return jwtToken;
    }

    /**
     * 判断token字符串是否有效
     *
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if (StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取token串中的id
     *
     * @param token
     * @return
     */
    public static Long getIdByToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return Long.valueOf(claims.get("userId").toString());
    }
}
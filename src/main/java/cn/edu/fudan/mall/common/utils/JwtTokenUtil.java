package cn.edu.fudan.mall.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT token生成和解析工具类
 */
@Component
public class JwtTokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据负载生成token
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 解析token的负载
     * @param token
     * @return
     */
    private Claims parseTokenToClaims(String token){
        Claims claims = null;
        try{
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e){
            logger.info("JWT格式验证失败: {}", token);
        }
        return claims;
    }

    /**
     * 生成token过期时间
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 根据token获取用户名
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token){
        String username = null;
        try{
            Claims claims = parseTokenToClaims(token);
            username = claims.getSubject();
        } catch (Exception e){
            logger.info("解析token失败: {}", token);
        }
        return username;
    }

    /**
     * 检查token有效性
     * @param token
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails){
        String tokenUsername = getUsernameFromToken(token);
        boolean isExpired = isTokenExpired(token);
        if(isExpired || !tokenUsername.equals(userDetails.getUsername())){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 检查token是否过期
     * @param token
     * @return
     */
    public boolean isTokenExpired(String token){
        Date expireDate = getExpireDateFromToken(token);
        return expireDate.before(new Date());
    }

    /**
     * 根据Security的用户信息生成token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 获取token过期时间
     * @param token
     * @return
     */
    private Date getExpireDateFromToken(String token){
        Claims claims = parseTokenToClaims(token);
        return claims.getExpiration();
    }

    /**
     * 允许token刷新
     * @param token
     * @return
     */
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    /**
     * 更新token日期
     * @param token
     * @return
     */
    public String refreshToken(String token){
        Claims claims = parseTokenToClaims(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }


}

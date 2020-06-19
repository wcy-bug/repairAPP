package com.example.repair.Interceptor;


import com.example.repair.Utils.JedisUtil;
import com.example.repair.annotation.AuthToken;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


@Component
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录
        AuthToken methodAnnotation = method.getAnnotation(AuthToken.class);
        // 有 @AuthToken 注解，需要认证

        if (methodAnnotation == null) {
            System.out.println("没有AuthToken,进行token判断");
        String token=request.getHeader("token");
        System.out.println("取到的token令牌为："+token);
        Jedis jedis= JedisUtil.getJedis();
        Long OverTime=jedis.ttl(token);
        String jd=jedis.get(token);
        if(jd!=null){
            System.out.println("有token令牌，允许操作");
            return true;
        }else{
            System.out.println("token令牌不正确");
            return false;
        }
        }else{
            System.out.println("有AuthToken,跳过token判断");
            return true;
        }
      /*  if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //验证token
        if (method.getAnnotation(AuthToken.class) != null || handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null) {
            String token = request.getParameter(Authorization);
            log.info("获取到的token为: {} ", token);
            //此处主要设置你的redis的ip和端口号，我的redis是在本地
            Jedis jedis = new Jedis("127.0.0.1", 6379);
            String username = null;
            if (token != null && token.length() != 0) {
                //从redis中根据键token来获取绑定的username
                username = jedis.get(token);
                log.info("从redis中获取的用户名称为: {}", username);
            }
            //判断username不为空的时候
            if (username != null && !username.trim().equals("")) {
                String startBirthTime = jedis.get(token + username);
                log.info("生成token的时间为: {}", startBirthTime);
                Long time = System.currentTimeMillis() - Long.valueOf(startBirthTime);
                log.info("token存在时间为 : {} ms", time);
                //重新设置Redis中的token过期时间
                if (time > CommonUtil.TOKEN_RESET_TIME) {
                    jedis.expire(username, CommonUtil.TOKEN_EXPIRE_TIME);
                    jedis.expire(token, CommonUtil.TOKEN_EXPIRE_TIME);
                    log.info("重置成功!");
                    Long newBirthTime = System.currentTimeMillis();
                    jedis.set(token + username, newBirthTime.toString());
                }

                //关闭资源
                jedis.close();
                request.setAttribute(USER_KEY, username);
                return true;
            } else {
                JSONObject jsonObject = new JSONObject();

                PrintWriter out = null;
                try {
                    response.setStatus(unauthorizedErrorCode);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                    jsonObject.put("code", ((HttpServletResponse) response).getStatus());
                    //鉴权失败后返回的错误信息，默认为401 unauthorized
                    jsonObject.put("message", HttpStatus.UNAUTHORIZED);
                    out = response.getWriter();
                    out.println(jsonObject);

                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != out) {
                        out.flush();
                        out.close();
                    }
                }

            }

        }

        request.setAttribute(USER_KEY, null);

        return true;*/


    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

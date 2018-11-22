package com.secondhand.tradingplatformadmincontroller.config;

import com.secondhand.tradingplatformadmincontroller.shiro.DesCredentialsMatcher;
import com.secondhand.tradingplatformadmincontroller.shiro.MyShiroRealm;
import com.secondhand.tradingplatformadminentity.entity.shiro.Resources;
import com.secondhand.tradingplatformadminservice.service.shiro.ResourcesService;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 * @author 81079
 */

@Configuration
public class ShiroConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.password}")
    private String password;

    @Autowired(required = false)
    private ResourcesService resourcesService;

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
     * @return
     */
/*    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }*/
    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     Filter Chain定义说明
     1、一个URL可以配置多个Filter，使用逗号分隔
     2、当设置多个过滤器时，全部验证通过，才视为通过
     3、部分过滤器可指定参数，如perms，roles
     *
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager){
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/usersPage");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();

        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/img/**","anon");
        filterChainDefinitionMap.put("/font-awesome/**","anon");
        //<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        //自定义加载权限资源关系
        List<Resources> resourcesList = resourcesService.myQueryAll();
        for(Resources resources:resourcesList){

            if (ToolUtil.isNotEmpty(resources.getUrl())) {
                String permission = "perms[" + resources.getUrl()+ "]";
                filterChainDefinitionMap.put(resources.getUrl(),permission);
            }
        }
        filterChainDefinitionMap.put("/**", "authc");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //设置realm.
        securityManager.setRealm(myShiroRealm());
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
//        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        myShiroRealm.setCredentialsMatcher(desCredentialsMatcher());
        return myShiroRealm;
    }

//    /**
//     * 凭证匹配器
//     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
//     *  所以我们需要修改下doGetAuthenticationInfo中的代码;
//     * ）
//     * @return
//     */
//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher(){
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
//        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
//
//        return hashedCredentialsMatcher;
//    }


    @Bean
    public CredentialsMatcher desCredentialsMatcher(){
        return new DesCredentialsMatcher();
    }


    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setExpire(1800);// 配置缓存过期时间
        redisManager.setTimeout(timeout);
        redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }


    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

//    @Bean
//    public RedisSessionDao redisSessionDAO() {
//        RedisSessionDao redisSessionDao = new RedisSessionDao();
//        redisSessionDao.setRedisManager(redisManager());
//        return redisSessionDao;
//    }

    /**
     * shiro session的管理
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {

        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
//
//        //解决sessionID与运行项目容器ID冲突配置
//        sessionManager.setGlobalSessionTimeout(1800000);
//        sessionManager.setSessionValidationInterval(60000);
//        sessionManager.setSessionValidationSchedulerEnabled(true);
//        sessionManager.setSessionIdUrlRewritingEnabled(true);
//        sessionManager.setSessionIdCookie(setSessionIdCookie());
//        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;

//        //不走shiro验证，测试用
//        SimpleWebSessionManager sessionManager = new SimpleWebSessionManager();
//        sessionManager.setSessionDAO(redisSessionDAO());
//        return sessionManager;
    }

    @Bean
    public SimpleCookie setSessionIdCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("shiro.sesssion");
        simpleCookie.setPath("/");
        simpleCookie.setHttpOnly(true);
        return simpleCookie;
    }

    /**
     * 不走shiro验证，测试用
     * 解决sessionID与运行项目容器ID冲突配置
     */
    public class SimpleWebSessionManager extends DefaultWebSessionManager implements WebSessionManager {

        private CacheManager cacheManager;

        public SimpleWebSessionManager() {
            super();
        }

        @Override
        public void validateSessions() {
            if (logger.isInfoEnabled()) {
                logger.info("Validating all active sessions...");
            }
            int invalidCount = 0;
            Collection<?> activeSessions = getActiveSessions();
            if (activeSessions != null && !activeSessions.isEmpty()) {
                for (Iterator<?> i$ = activeSessions.iterator(); i$.hasNext();) {
                    Session session = (Session) i$.next();
                    try {
                        SessionKey key = new DefaultSessionKey(session.getId());
                        validate(session, key);
                    } catch (InvalidSessionException e) {
                        if (cacheManager != null) {
                            SimpleSession s = (SimpleSession) session;
                            if (s.getAttribute("user") != null) {
                                cacheManager.getCache(null).remove(
                                        s.getAttribute("user"));
                            }
                        }
                        if (logger.isDebugEnabled()) {
                            boolean expired = e instanceof ExpiredSessionException;
                            String msg = (new StringBuilder()).append(
                                    "Invalidated session with id [").append(
                                    session.getId()).append("]").append(
                                    expired ? " (expired)" : " (stopped)")
                                    .toString();
                            logger.debug(msg);
                        }
                        invalidCount++;
                    }
                }

            }
            if (logger.isInfoEnabled()) {
                String msg = "Finished session validation.";
                if (invalidCount > 0) {
                    msg = (new StringBuilder()).append(msg).append("  [").append(
                            invalidCount).append("] sessions were stopped.")
                            .toString();
                }
                else {
                    msg = (new StringBuilder()).append(msg).append(
                            "  No sessions were stopped.").toString();
                }
                logger.info(msg);
            }
        }

        @Override
        public void setCacheManager(CacheManager cacheManager) {
            this.cacheManager = cacheManager;
        }
    }
}

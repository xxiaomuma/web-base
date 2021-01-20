#  基础
##### 代码持续更新 未完善
##### web-base
  - ###### base-cache 缓存 (后续补充)
    - redis使用lettuce
    #
  - ###### base-db 数据库
    - 数据库多数据源的方式可做读写分离及一些应用
    ###### 配置使用方式:
    a) 引入jar包
    ````
        <dependency>
            <groupId>pers.xiaomuma.web</groupId>
            <artifactId>base-db</artifactId>
        </dependency> 
    ````
    b) yml配置
    ````
         spring:
           datasource:
             base-dome: #名称
               dome1:   #数据源名称
                 driver-class-name: com.mysql.cj.jdbc.Driver
                 url: jdbc:mysql://127.0.0.1:3306/dome1?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useUnicode=true&useSSL=false
                 username: root
                 password: 123456
               dome2:
                 driver-class-name: com.mysql.cj.jdbc.Driver
                 url: jdbc:mysql://127.0.0.1:3306/dome2?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useUnicode=true&useSSL=false
                 username: root
                 password: 123456
    ````
    c) 配置数据源 ( 数据源默认第一个)
    ````
      
     @Configuration
     @MapperScan(basePackages = "pers.xiaomuma.base.dome.dao")
     public class DataSourceConfiguration {
           
          @Bean
          public MultipleDataSource multipleDataSource() {
              return new MultipleDataSource(Lists.newArrayList(DataSourceName.DOME1, DataSourceName.DOME2));
          }
     }
     public interface DataSourceName {
    
        String DOME1 = "spring.datasource.base-dome.dome1";
        String DOME2 = "spring.datasource.base-dome.dome2";
     }
    ````
    ###### 提供两种方式:
    a) 注解方式:
    ````
        @DataSource(DataSourceName.DOME2)
        public List<Vehicle> findVehicle() {
            List<Vehicle> vehicles = vehicleService.list();
            return vehicles;
        }
    ````
    b) 代码方式:
    ````
        public void findAddress() {
             List<Vehicle>  vehicles = DynamicDataSourceContextHolder
                .callInDataSource(DataSourceName.DOME2, vehicleService::list);
             return vehicles;   
        }
    ````
    # 
  - ###### base-security ( security + jwt )
    ###### 提供两种登录模式：
    ###### 1.sms:
     - SmsAuthenticationFilter, SmsAuthenticationProvider, SmsAuthenticationToken
     - DefaultSmsUserLoginChecker, DefaultUserDetailsService
    ###### 2.username:
     - UsernameAuthenticationFilter, UsernameAuthenticationProvider, UsernameAuthenticationToken
     - DefaultUserDetailsService 
    ###### 代码示例
    ````
        @Component
        public class UserDetailsService implements DefaultUserDetailsService {
        
            
            @Override
            public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
                //手机号登录实现
            }
            
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                //账号登录实现
            }
        }
    ````
    ````
        @Component
        public class SmsUserLoginChecker implements DefaultSmsUserLoginChecker {
            
            @Override
            public boolean check(String mobile, String code) throws BadCredentialsException {
                //手机验证码效验实现
            }
        }
    ````
    ````
        @Configuration
        @EnableWebSecurity
        @Import({
                JwtAuthenticationConfiguration.class
        })
        @RequiredArgsConstructor
        public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
        
            private final JwtAuthenticationConfigurer jwtAuthenticationConfigurer;
            private final UserDetailsService userDetailsService;
            private final SmsUserLoginChecker smsUserLoginChecker;
            private final JwtTokenGenerator jwtTokenGenerator;
        
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http.csrf().disable();
                http.authorizeRequests()
                        .antMatchers("/login/**").permitAll()
                        .anyRequest().authenticated();
                http.apply(jwtAuthenticationConfigurer);
                http.exceptionHandling()
                        .accessDeniedHandler(new RestAccessDeniedHandler())
                        .authenticationEntryPoint(new RestForbiddenEntryPoint());
            }
        
            @Override
            public void configure(WebSecurity web) throws Exception {
                super.configure(web);
            }
        
            @Bean
            public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
            }
        
            @Bean
            public AuthenticationManager authenticationManagerBean() throws Exception {
                return super.authenticationManagerBean();
            }
        
            @Bean
            public SmsAuthenticationFilter smsAuthenticationFilter() throws Exception {
                SmsAuthenticationFilter filter = new SmsAuthenticationFilter();
                filter.setAuthenticationManager(super.authenticationManagerBean());
                filter.setAuthenticationFailureHandler(new DefaultAuthenticationFailureHandler());
                filter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler(jwtTokenGenerator));
                return filter;
            }
        
            @Bean
            public UsernameAuthenticationFilter usernameAuthenticationFilter() throws Exception {
                UsernameAuthenticationFilter filter = new UsernameAuthenticationFilter();
                filter.setAuthenticationManager(super.authenticationManagerBean());
                filter.setAuthenticationFailureHandler(new DefaultAuthenticationFailureHandler());
                filter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler(jwtTokenGenerator));
                return filter;
            }
        
            @Bean
            public SmsAuthenticationProvider smsAuthenticationProvider() {
                return new SmsAuthenticationProvider(userDetailsService, smsUserLoginChecker);
            }
        
            @Bean
            public UsernameAuthenticationProvider usernameAuthenticationProvider() {
                return new UsernameAuthenticationProvider(userDetailsService, passwordEncoder());
            }
        
            @Override
            protected void configure(AuthenticationManagerBuilder auth) {
                auth
                        .authenticationProvider(smsAuthenticationProvider())
                        .authenticationProvider(usernameAuthenticationProvider());
            }
        
        }
    ````
    可以通过new UsernameAuthenticationFilter("地址"); 或者 new SmsAuthenticationFilter("地址"); 来自定义请求登录路径
  - ###### base-web web基础
    - 基础
  - ###### base-support 支持
    - oss >> 文件
    - email >> 邮件
  - ###### base-dome 示例
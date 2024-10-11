# spring 基础架设
- example参照: [https://github.com/xxiaomuma/example](https://github.com/xxiaomuma/spring-cloud-example)
###### 1.项目结构
```
web-base
    |———— web-base-api
    |———— web-base-common
    |———— web-base-core
    |———— web-base-rpc
    |———— web-base-security
    |———— web-base-starter
    |———— web-base-support
    |———— web-base-thirdparty
    |———— web-base-web
```
###### 2.使用
```html
    <parent>
         <artifactId>web-base</artifactId>
         <groupId>pers.xiaomuma.web</groupId>
         <version>2.0-SNAPSHOT</version>
    </parent>
    <dependencies>
         <dependency>
              <groupId>pers.xiaomuma.web</groupId>
              <artifactId>web-base-starter</artifactId>
              <version>2.0-SNAPSHOT</version>
         </dependency>
    </dependencies>
```

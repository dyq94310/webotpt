# Otp web
通过web，在规定的有效期内可获取otp二次授权密码

## otp密码加解密
otp密码比较敏感，既然要上传GitHub，需做好加密
pom中引入``jasypt-maven-plugin``插件，方便加解密
```shell
# 加密
mvn jasypt:encrypt-value -Djasypt.encryptor.password="password" -Djasypt.plugin.value="value"
解密
mvn jasypt:decrypt-value -Djasypt.encryptor.password="password" -Djasypt.plugin.value="ENC(47GYBJezlwnAkSUsVpoJhN2PmUSxhy2+RzakDWkjUZyZFCyZc676w3znplmuf/0F)"
```
加密把密文写入配置文件中，密钥通过启动JVM参数传入

## 启动
密钥通过启动JVM参数传入：``-Djasypt.encryptor.password=``


## 接口列表
- http://localhost:8090/ 
> 可自动刷新otp的主页
- http://localhost:8090/test :获取一次otp
> otp=987728 ,expires later :7second!!
- http://localhost:8090/update/{minutes} :更新密码有效期

## 技术列表
1 使用otp-java生成otp密码
```xml
<dependency>
    <groupId>com.github.bastiaanjansen</groupId>
    <artifactId>otp-java</artifactId>
    <version>1.3.2</version>
</dependency>
```

2 使用jasypt加密otp密码
```xml
<dependency>
    <groupId>com.github.ulisesbocchio</groupId>
    <artifactId>jasypt-spring-boot-starter</artifactId>
    <version>3.0.5</version>
</dependency>
```

## use docker
```bash
cd [codepath]

# 构建image
docker build -t myotp:v1.1.19 .

# 启动 传入密钥
docker run -d -p 8090:8090 -e JAVA_ARGS='-Djasypt.encryptor.password=passwd'   --name otp myotp:v1.1.19 
```


## 项目地址

[jaspty](https://github.com/ulisesbocchio/jasypt-spring-boot)

[otp-java](https://github.com/BastiaanJansen/otp-java)

[extract_otp_secrets](https://github.com/scito/extract_otp_secrets)

[本项目](https://github.com/dyq94310/webotpt)
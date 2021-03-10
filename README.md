# IDEEAProject

IDEEA动态网页项目 - Java

## 二次开发  

进入到 vue 目录中，在命令行依次输入如下命令：  

```
# 安装依赖
npm install

# 在 localhost:8080 启动项目
npm run dev
```

由于我在 vue 项目中已经配置了端口转发，将数据转发到 SpringBoot 上，因此项目启动之后，在浏览器中输入 `http://localhost:8080` 就可以访问我们的前端项目了，所有的请求通过端口转发将数据传到 SpringBoot 中（注意此时不要关闭 SpringBoot 项目）。  

最后可以用 WebStorm 等工具打开 vue 项目，继续开发，开发完成后，当项目要上线时，依然进入到 vue 目录，然后执行如下命令：  

```
npm run build
```

该命令执行成功之后，vue 目录下生成一个 dist 文件夹，将该文件夹中的两个文件 static 和 index.html 拷贝到 SpringBoot 项目中 resources/static/ 目录下，然后就可以像之前那样直接访问了。 
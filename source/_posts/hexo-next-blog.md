---
title: Hexo博客系统+Next主题+CND加速建站备忘
date: 2017-01-15 21:35:47
categories: 
- experience
---
## Hexo搭建博客系统
> [Hexo API](https://hexo.io/zh-cn/docs/)

### 常用命令
初始化博客系统
```
hexo init <folder>
cd <folder>
npm install
```

创建新页面
```
hexo new page <page>
```

<!-- more -->

创建新文章
```
hexo new <title>
```

生成静态页
```
hexo g(generate)

-d 生成静态页后接着发布
-w 监视文件变动
```

发布
```
hexo d(deploy)

-g 发布前生成静态页
```

启动本地服务
```
hexo s(server)

--debug 调试模式
```

## 配置Next主题
> [Next帮助](http://theme-next.iissnan.com/getting-started.html)

下载主题
```
git clone https://github.com/iissnan/hexo-theme-next themes/next
```

## 推送Github Pages服务
在`_config.yml`里配置好github远程推送的仓库地址
```
deploy:
  type: git
  repo: <repository url>
  branch: [branch]
  message: [message]
```

按照`hexo-deployer-git`
```
$ npm install hexo-deployer-git --save
```

## 国内CDN加速
七牛云CDN和阿里云CDN都可以，设置过程大致相同。
1. 在`source`文件夹下新建`CNAME`文件，填写解析的域名`blog.swroom.com`。
2. 阿里云CDN控制台创建新的加速域名后，会给出一个新域名，需要将国内的解析到此域名，而海外的解析到github原域名。

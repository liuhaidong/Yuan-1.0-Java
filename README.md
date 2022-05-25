# Yuan-1.0-Java

本项目是浪潮人工智能巨量模型“源1.0”API的Java实现。
原项目地址： https://github.com/Shawn-Inspur/Yuan-1.0.

# Introduction
“源1.0”，是浪潮人工智能研究院9月28日在京发布全球最大规模人工智能巨量模型。“源”的单体模型参数量达2457亿，超越美国OpenAI组织研发的GPT-3，成为全球最大规模的单体AI巨量模型。 本文将介绍如何进行“源1.0”API的调用。该API接口主要是针对外网开放，用于第三方用户根据自身需求获取推理结果
关于Yuan-1.0的论文请访问. https://arxiv.org/abs/2110.04725

## 1. 申请测试账号

请访问官网 (http://air.inspur.com) for details to get access of the corpus and APIs of Yuan model.

## 2. 下载代码

git clone https://github.com/liuhaidong/Yuan-1.0-Java.git

## 3. 设置参数访问Yuan API
在配置文件application.properties, 设置(http://air.inspur.com)注册所用的 用户名 和 手机 :
username =
mobile = 

## 4. Compile and Build
安装 maven 和 Java 1.8
mvn clean compile package -DskipTests 



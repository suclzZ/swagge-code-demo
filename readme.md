接口生成原理:
	1)使用Springfox-swagger2生成swagger。json文件
	2)使用Swagger2markup将swagger。json文件转换成asciidoc文档片段
	3)编写asciidoc的文档(主要是组装步骤2中生成的asciidoc文档片段)
	4)使用Asciidoctor将asciidoc转换成HTML或pdf
	
1)在pom文件中引入对应jar，同时配置相关文件
2)创建SwaggerConfig文件，在application.properties中配置对应属性
3)在业务代码(C)添加Api相关的注
4)在test模块中引入队以你代码
5)mvn test命令，生成对应index.html文件
6)启动项目后，通过http://<ip>:<port>/<app>/swagger-ui.html访问所有接口，并测试

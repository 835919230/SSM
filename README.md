###### 首语：Spring实战（第四版）里的上传文件Demo真的是好坑啊！
## 实现一个不需要写Web.xml的Web App
这时候需要实现一个抽象类==AbstractAnnotationConfigDispatcherServletInitializer==，
并覆盖它的三个方法：
- getRootConfigClasses()
- getServletConfigClasses()
- getServletMappings()

具体如下所示

```java
public class Bootstrap extends AbstractAnnotationConfigDispatcherServletInitializer{

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{ServletConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}

```
这个时候我们就需要去创建两个Class（至少我是这样的）,分别是==RootConfig==跟==ServletConfig==。
在RootConfig里边，我们可以去定义一些跟Spring Web模块关系不是太紧密的Bean，就比如DataSource或者是HibernateTemplate这种与数据库相关的Bean。
在ServletConfig里边我们可以去定义我们需要的bean,就比如ViewResolver跟MultipartResolver。
在这里晒出ViewResolver的源码。
```java
@EnableWebMvc
@Configuration
@ComponentScan("com.hc.web")
public class ServletConfig extends WebMvcConfigurerAdapter{
    Logger log = Logger.getLogger(this.getClass());
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setExposeContextBeansAsAttributes(true);
        log.debug("ViewResolver启动");
        return viewResolver;
    }

    @Bean
    public MultipartResolver multipartResolver() throws IOException {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setUploadTempDir(new FileSystemResource("/tmp/hc/uploads"));
        multipartResolver.setMaxUploadSize(2097152L);
        multipartResolver.setMaxInMemorySize(0);
        return multipartResolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
```
configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)这个方法就相当于我们在.xml文件里面写的

<mvc:enableHandlerMapping>

我们还可以看到，ViewResolver和MuiltiPartResolver都有自己的实现类，在上面我选择的是==InternalResourceViewResolver==，和==CommonsMultipartResolver==
```java
@Configuration
@ComponentScan(basePackages = {"com.hc"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)})
public class RootConfig {
}
```

### 上传文件小记
在这里，我实现的是上传多个文件

先上代码

这是一个Controller

```java
    @PostMapping("/upload")
    public String l(@RequestParam("file")MultipartFile[] files) throws IOException {
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String filename = file.getOriginalFilename();
            File f = new File("/data/"+filename);
            if (f.exists())continue;
            FileUtils.writeByteArrayToFile(f,file.getBytes());
        }
        return "upload";
    }
```
这是对应的网页代码
```java
<form action="/upload" method="post" enctype="multipart/form-data">
        <input type="file" name="file" multiple/>
        <input type="submit" value="上传"/>
    </form>
```
上传的参数是“file”，然后我启用了multiple属性，我们就可以去选择多个图片上传，在Controller处理上传的方法上我标注了上传的参数名，对应的Java实体却是一个Multiipart**数组**，我还选用了Apache commons包下的FileUtils，简单便捷。

#### 上传多个文件遇到的“坑”
- 不要用byte[] 作入参
    用了之后，选择多个文件，最后只保留了第一个选中了。
-或者这样
```java
@PostMapping("/upload")
    public String upload(@RequestPart("file")Part[] parts) throws IOException {
        for (int i = 0; i < parts.length; i++) {
            Part part = parts[i];
            String fileName = part.getSubmittedFileName();
            part.write("/data/"+fileName);
        }
        return "upload";
    }
```
这也是会报错的，500

为什么呢，注意这里是500，那么就是我自己的问题了。找了好久，才发现是MultipartResolver选择上的错误。

刚刚我选择的是CommonsMultipartResolver，要想成功，就需要使用==StandardMultipartResolver==了

要使用StandardMultipartResolver，我们需要在刚刚的Bootstrap类上重写一个方法

```java
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(new MultipartConfigElement("/",2097152L,4194304L,0));
    }
```

并修改ServletConfig类中得到MultipartResolver的方法

```java
@Bean
    public MultipartResolver multipartResolver() throws IOException {
        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
//        multipartResolver.setUploadTempDir(new FileSystemResource("/tmp/hc/uploads"));
//        multipartResolver.setMaxUploadSize(2097152L);
//        multipartResolver.setMaxInMemorySize(4096);
        return multipartResolver;
    }
```

这样原来报错的那段代码就可以用了呢！^_^

但是，现在上传中文名文件会**出现乱码**，如果不希望出现乱码（肯定不希望啊），就在实现了AbstractAnnotationConfigDispatcherServletInitializer 这个类的Bootstrap类上重写如下方法。

```java
@Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter("utf-8");
        return new Filter[]{encodingFilter};
    }
```
就是增加一个CharacterEncodingFilter，这个Filter会把字符集修改为我指定的utf-8

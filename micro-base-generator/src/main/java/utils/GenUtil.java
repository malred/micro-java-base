package utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 生成代码的工具类
 */
public class GenUtil {

    /**
     * 生成java代码
     *
     * @param data      填充到模板中的数据
     * @param templates 模板名称
     * @param zip       输出流,输出为zip
     */
    public static void generateCode(Map<String, Object> data, List<String> templates, ZipOutputStream zip) throws Exception {
        // 1,设置velocity的资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        // 2,初始化velocity引擎
        Velocity.init(prop);
        VelocityContext context = new VelocityContext(data);
        // 5,合并数据到模板
        for (String template : templates) {
            // 4,加载velocity模板文件
            Template tpl = Velocity.getTemplate(template, "utf-8");
            StringWriter sw = new StringWriter();
            // 合并+写入
            tpl.merge(context, sw);
            // 输出到zip文件
            zip.putNextEntry(new ZipEntry(
                            getFileName(
                                    template,
                                    data.get("upperEntityName").toString(),
                                    data.get("package").toString())
                    )
            );
            // 写入
            IOUtils.write(sw.toString(), zip, "UTF-8");
            IOUtils.closeQuietly(sw);
        }
    }

    /**
     * 根据模板名,类名称,包名称,拼接一个完整的文件路径和名称
     *
     * @param template    模板名称 Controller.vm
     * @param className   实体类名称   User
     * @param packageName 包名称   org.malred
     * @return org/malred/controller/UserController.java
     */
    public static String getFileName(String template, String className, String packageName) {
        // main/java/
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNoneEmpty(packageName)) {
            // 拼接上包名(.都换成斜杠)
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }
        if (template.contains("controller.java.vm") || template.contains("feignController.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }
        if (template.contains("service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }
        if (template.contains("serviceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }
        if (template.contains("dao.java.vm")) {
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }
        if (template.contains("pojo.java.vm")) {
            return packagePath + "pojo" + File.separator + className + ".java";
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(
                getFileName("controller.java.vm", "User", "org.malred")
        );
        System.out.println(
                getFileName("feignController.java.vm", "User", "org.malred")
        );
        System.out.println(
                getFileName("dao.java.vm", "User", "org.malred")
        );
        System.out.println(
                getFileName("service.java.vm", "User", "org.malred")
        );
    }
}

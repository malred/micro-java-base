
import utils.GenUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * @author malguy-wang sir
 * @create ---
 */
public class test {
    public static void main(String[] args) {
        // 构建数据
        Map<String, Object> data = new HashMap();
        data.put("package", "org.malred.service");
        data.put("upperEntityName", "Good");
        data.put("entityName", "good");
        // 构建模板列表
        List<String> templates = new ArrayList<String>();
        templates.add("vms/controller.java.vm");
        templates.add("vms/dao.java.vm");
        templates.add("vms/service.java.vm");
        templates.add("vms/serviceImpl.java.vm");
        templates.add("vms/pojo.java.vm");

        // 构建输出流对象
        File file = new File("C:\\Users\\13695\\Desktop\\code.zip");
        ZipOutputStream zip = null;
        try {
            FileOutputStream out = new FileOutputStream(file);
            zip = new ZipOutputStream(out);
            GenUtil.generateCode(data, templates, zip);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                zip.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

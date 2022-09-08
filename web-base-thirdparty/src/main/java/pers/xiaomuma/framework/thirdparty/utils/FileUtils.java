package pers.xiaomuma.framework.thirdparty.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class FileUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    public static String getFileContent(String filePath) {
        InputStream inputStream = FileUtils.class.getClassLoader()
                .getResourceAsStream(filePath);
        if (Objects.isNull(inputStream)) {
            LOGGER.info("获取{}流失败", filePath);
            return "";
        }
        StringBuilder sbf = new StringBuilder();
        try {
            int length;
            byte[] bytes = new byte[1024];
            while((length = inputStream.read(bytes)) != -1){
                sbf.append(new String(bytes, 0, length));
            }
        } catch (IOException e) {
            throw new RuntimeException("读取文件失败", e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e1) {
                LOGGER.error("关闭 inputStream 失败, {}", e1.getMessage());
            }
        }
        return sbf.toString();
    }
}

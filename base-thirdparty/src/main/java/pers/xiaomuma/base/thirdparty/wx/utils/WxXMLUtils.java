package pers.xiaomuma.base.thirdparty.wx.utils;


import cn.hutool.core.util.StrUtil;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WxXMLUtils {

    public static String transferMapToXml(SortedMap<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");

        for (String key : map.keySet()) {
            sb.append("<").append(key).append(">").append(map.get(key)).append("</").append(key).append(">");
        }
        return sb.append("</xml>").toString();
    }

    public static Map<String, Object> transferXmlToMap(String xml) {
        String utf8xml = xml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        if (StrUtil.isBlank(utf8xml)) {
            return null;
        } else {
            HashMap<String, Object> xmlEntryMap = new HashMap<>();
            try {
                InputStream in = new ByteArrayInputStream(utf8xml.getBytes(StandardCharsets.UTF_8));
                Throwable var4 = null;

                try {
                    SAXBuilder builder = new SAXBuilder();
                    Document doc;
                    try {
                        doc = builder.build(in);
                    } catch (JDOMException var23) {
                        throw new IllegalArgumentException(var23);
                    }

                    Element root = doc.getRootElement();
                    List<Element> list = root.getChildren();

                    String k;
                    String v;
                    for (Iterator<Element> var9 = list.iterator(); var9.hasNext(); xmlEntryMap.put(k, v)) {
                        Element e = var9.next();
                        k = e.getName();
                        List<Element> children = e.getChildren();
                        if (children.isEmpty()) {
                            v = e.getTextNormalize();
                        } else {
                            v = getChildrenText(children);
                        }
                    }
                } catch (Throwable var24) {
                    var4 = var24;
                    throw var24;
                } finally {
                    if (in != null) {
                        if (var4 != null) {
                            try {
                                in.close();
                            } catch (Throwable var22) {
                                var4.addSuppressed(var22);
                            }
                        } else {
                            in.close();
                        }
                    }

                }
                return xmlEntryMap;
            } catch (IOException var26) {
                throw new RuntimeException(var26);
            }
        }
    }

    private static String getChildrenText(List<Element> children) {
        StringBuilder sb = new StringBuilder();
        if (!children.isEmpty()) {

            for (Element e : children) {
                String name = e.getName();
                String value = e.getTextNormalize();
                List<Element> list = e.getChildren();
                sb.append("<").append(name).append(">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }

                sb.append(value);
                sb.append("</").append(name).append(">");
            }
        }

        return sb.toString();
    }
}

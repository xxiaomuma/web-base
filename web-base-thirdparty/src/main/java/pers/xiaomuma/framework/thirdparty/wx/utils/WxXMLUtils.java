package pers.xiaomuma.framework.thirdparty.wx.utils;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class WxXMLUtils {

    public static String transferMapToXml(SortedMap<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (String key : map.keySet()) {
            sb.append("<").append(key).append(">")
                    .append(map.get(key))
                    .append("</").append(key).append(">");
        }
        return sb.append("</xml>").toString();
    }

    public static Map<String, Object> transferXmlToMap(String xml) {
        String utf8xml = xml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        if (StringUtils.isBlank(utf8xml)) {
            return null;
        }
        Map<String, Object> xmlEntryMap = new HashMap<>();
        try (InputStream in = new ByteArrayInputStream(utf8xml.getBytes(StandardCharsets.UTF_8))) {
            SAXBuilder builder = new SAXBuilder();
            Document doc;
            try {
                doc = builder.build(in);
            } catch (JDOMException e) {
                throw new IllegalArgumentException(e);
            }
            // 解析 DOM
            Element root = doc.getRootElement();
            List<Element> list = root.getChildren();
            for (Element e : list) {
                String k = e.getName();
                String v;
                List<Element> children = e.getChildren();
                if (children.isEmpty()) {
                    v = e.getTextNormalize();
                } else {
                    v = getChildrenText(children);
                }
                xmlEntryMap.put(k, v);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return xmlEntryMap;
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

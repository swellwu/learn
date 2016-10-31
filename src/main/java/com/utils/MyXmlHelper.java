package com.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;

/**
 * Created by Administrator on 2016/10/31.
 */
public class MyXmlHelper {

    private MyXmlHelper(){

    }

    /**
     * 解析一般规整格式的xml，输出根节点下所有属性对列表
     * @param xml xml文件
     * @param keys 属性名列表
     * @return
     */
    public static List<Map<String,String>> parseXml(File xml, List<String> keys) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(xml);
        // 获取根元素
        Element root = document.getRootElement();
        System.out.println("Root: " + root.getName());
        List<Map<String, String>> result = new ArrayList<>();
        // 迭代
        for (Iterator iter = root.elementIterator(); iter.hasNext(); ) {
            Map<String, String> one = new HashMap<>();
            Element e = (Element) iter.next();
            List<Element> attributes = e.attributes();
            for (String key : keys) {
                for(int i=0;i<attributes.size();++i){
                    if(e.attribute(i).getName().equals(key)){
                        one.put(key, e.attributeValue(key));
                    }
                }
            }
            result.add(one);
        }
        return result;
    }
}
//         + firstWorldElement.attribute(0).getName() + "="
//                 + firstWorldElement.attributeValue("name"));

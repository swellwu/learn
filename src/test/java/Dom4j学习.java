import com.google.gson.Gson;
import com.utils.MyXmlHelper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/31.
 */
public class Dom4j学习 {

    private static Gson gson = new Gson();

    @Test
    public void createXmlTest() throws IOException {
        // 第一种方式：创建文档，并创建根元素
        // 创建文档:使用了一个Helper类
        Document document = DocumentHelper.createDocument();

        // 创建根节点并添加进文档
        Element root = DocumentHelper.createElement("student");
        document.setRootElement(root);

        // 第二种方式:创建文档并设置文档的根元素节点
        Element root2 = DocumentHelper.createElement("student");
        Document document2 = DocumentHelper.createDocument(root2);

        // 添加属性
        root2.addAttribute("name", "zhangsan");
        // 添加子节点:add之后就返回这个元素
        Element helloElement = root2.addElement("hello");
        Element worldElement = root2.addElement("world");

        helloElement.setText("hello Text");
        worldElement.setText("world text");

        // 输出
        // 输出到控制台
        XMLWriter xmlWriter = new XMLWriter();
        xmlWriter.write(document);


        // 输出到文件
        // 格式
        OutputFormat format = new OutputFormat("    ", true);// 设置缩进为4个空格，并且另起一行为true
        XMLWriter xmlWriter2 = new XMLWriter(
                new FileOutputStream("student.xml"), format);
        xmlWriter2.write(document2);

        // 另一种输出方式，记得要调用flush()方法,否则输出的文件中显示空白
        XMLWriter xmlWriter3 = new XMLWriter(new FileWriter("student2.xml"),
                format);
        xmlWriter3.write(document2);
        xmlWriter3.flush();
        // close()方法也可以
    }

    @Test
    public void parseXmlTest() throws DocumentException, IOException, SAXException, ParserConfigurationException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File("students.xml"));
        // 获取根元素
        Element root = document.getRootElement();
        System.out.println("Root: " + root.getName());
        // 获取所有子元素
        List<Element> childList = root.elements();
        System.out.println("total child count: " + childList.size());
        // 获取特定名称的子元素
        List<Element> childList2 = root.elements("hello");
        System.out.println("hello child: " + childList2.size());
        // 获取名字为指定名称的第一个子元素
        Element firstWorldElement = root.element("world");
        // 输出其属性
        System.out.println("first World Attr: "
                + firstWorldElement.attribute(0).getName() + "="
                + firstWorldElement.attributeValue("name"));
        System.out.println("迭代输出-----------------------");
        // 迭代输出
        for (Iterator iter = root.elementIterator(); iter.hasNext();)
        {
            Element e = (Element) iter.next();
            System.out.println(e.attributeValue("name"));
        }
        System.out.println("用DOMReader-----------------------");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        // 注意要用完整类名
        org.w3c.dom.Document document2 = db.parse(new File("students.xml "));
        DOMReader domReader = new DOMReader();
        // 将JAXP的Document转换为dom4j的Document
        Document document3 = domReader.read(document2);
        Element rootElement = document3.getRootElement();
        System.out.println("Root: " + rootElement.getName());
    }

    @Test
    public void parseTest() throws DocumentException {
        File cityxml=new File("Cities.xml");
        List<String> keys=new ArrayList<>();
        keys.add("ID");
        keys.add("CityName");
        keys.add("PID");
        keys.add("ZipCode");
        List<Map<String,String>> citys = MyXmlHelper.parseXml(cityxml,keys);
        System.out.println(gson.toJson(citys));

        File provinceXml=new File("Provinces.xml");
        List<String> keys2=new ArrayList<>();
        keys2.add("ID");
        keys2.add("ProvinceName");
        List<Map<String,String>> provinces = MyXmlHelper.parseXml(provinceXml,keys2);
        System.out.println(gson.toJson(provinces));

        File districtXml=new File("Districts.xml");
        List<String> keys3=new ArrayList<>();
        keys3.add("ID");
        keys3.add("DistrictName");
        keys3.add("CID");
        List<Map<String,String>> districts=MyXmlHelper.parseXml(districtXml,keys3);
        System.out.println(gson.toJson(districts));
    }
}

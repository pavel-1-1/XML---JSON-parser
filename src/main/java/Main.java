import com.google.gson.Gson;
import org.w3c.dom.NodeList;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        String file = "data.xml";

        List<Employee> list = parseXMl(file);
        list.forEach(System.out::println);

        String json = listToJson(list);
        System.out.println(json);

        writeString(json);

    }

    private static List<Employee> parseXMl(String file) throws ParserConfigurationException, IOException, SAXException {
        List<Employee> employees = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        NodeList employeeList = document.getDocumentElement().getElementsByTagName("employee");
        for (int i = 0; i < employeeList.getLength(); i++) {
            Node node = employeeList.item(i);
            Element elementNode = (Element) node;

            employees.add(new Employee(Long.parseLong(elementNode.getElementsByTagName("id").item(0).getTextContent()),
                    elementNode.getElementsByTagName("firstName").item(0).getTextContent(),
                    elementNode.getElementsByTagName("lastName").item(0).getTextContent(),
                    elementNode.getElementsByTagName("country").item(0).getTextContent(),
                    Integer.parseInt(elementNode.getElementsByTagName("age").item(0).getTextContent())));
        }
        return employees;
    }

    private static String listToJson(List<Employee> list) {
        return new Gson().toJson(list);
    }

    private static void writeString(String json) {
        try (FileWriter fileWriter = new FileWriter("data2.json")) {
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

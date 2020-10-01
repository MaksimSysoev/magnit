package ru.magnit.app;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.*;

public class OperationWithXml {

    private int field;
    private DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();


    public OperationWithXml(int field) {
        this.field = field;
    }

    private int sumEntryAttributes() throws Exception {
        int sum = 0;
        try {
                File fXmlFile = new File("2.xml");
                DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);
                //optional, but recommended
                //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("entry");
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        sum = sum + Integer.parseInt(eElement.getAttribute("field").replaceAll("[^0-9]", ""));
                    }
                }
            } catch (Exception e) {
            e.printStackTrace();
        }
       return sum;
    }


    private void transfromXmlFile() throws Exception {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(new File("template.xsl"));
            Transformer transformer = factory.newTransformer(xslt);
            Source xml = new StreamSource(new File("1.xml"));
            transformer.transform(xml, new StreamResult(new File("2.xml")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void createXml() throws Exception {
        try {
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            //root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("entries");
            doc.appendChild(rootElement);

            for (int i = 0; i < this.field; i++) {
                //entry elements
                Element entry = doc.createElement("entry");
                rootElement.appendChild(entry);

                Element field = doc.createElement("field");
                field.appendChild(doc.createTextNode("Значение поля " + i));
                entry.appendChild(field);
            }

            //write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("1.xml"));
            transformer.transform(source, result);


            this.transfromXmlFile();

            System.out.println("Сумма значений атрибута field в файле 2.xml = " + this.sumEntryAttributes());

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

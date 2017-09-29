package Model;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.util.ArrayList;

public class Model {
    private ArrayList<Person> persons = new ArrayList<>();
    private ArrayList<Person> searchList = new ArrayList<>();

    public void setSearchList(ArrayList<Person> arrayList) {
        searchList = arrayList;
    }

    public void clearSearchList() {
        searchList.clear();
    }

    public void addData(ArrayList<String> arrayList) {
        arrayList = refactorData(arrayList);

        Person person = new Person();
        person.setData(arrayList);

        persons.add(person);
    }

    private ArrayList<String> refactorData(ArrayList<String> arrayList) {
        ArrayList<String> newList = new ArrayList<>();

        for (String obj : arrayList) {
            if (!checkString(obj)) {
                String string = obj;

                string = string.toUpperCase();
                string = string.charAt(0) + string.substring(1).toLowerCase();

                newList.add(string);
            }

            else {
                newList.add(obj);
            }
        }

        return newList;
    }

    public void saveFile(String fileName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();

            Document doc = documentBuilder.newDocument();
            Element rootElement = doc.createElement("students");
            doc.appendChild(rootElement);

            for (Person person : persons) {
                Element student = doc.createElement("student");
                rootElement.appendChild(student);

                Element surname = doc.createElement("surname");
                surname.appendChild(doc.createTextNode(person.getSurname()));
                student.appendChild(surname);

                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(person.getName()));
                student.appendChild(name);

                Element fathername = doc.createElement("fathername");
                fathername.appendChild(doc.createTextNode(person.getFathername()));
                student.appendChild(fathername);

                Element country = doc.createElement("country");
                country.appendChild(doc.createTextNode(person.getCountry()));
                student.appendChild(country);

                Element region = doc.createElement("region");
                region.appendChild(doc.createTextNode(person.getRegion()));
                student.appendChild(region);

                Element city = doc.createElement("city");
                city.appendChild(doc.createTextNode(person.getCity()));
                student.appendChild(city);

                Element street = doc.createElement("street");
                street.appendChild(doc.createTextNode(person.getStreet()));
                student.appendChild(street);

                Element house = doc.createElement("house");
                house.appendChild(doc.createTextNode(Integer.toString(person.getHouse())));
                student.appendChild(house);

                Element housing = doc.createElement("housing");
                housing.appendChild(doc.createTextNode(Integer.toString(person.getHousing())));
                student.appendChild(housing);

                Element apartment = doc.createElement("apartment");
                apartment.appendChild(doc.createTextNode(Integer.toString(person.getApartment())));
                student.appendChild(apartment);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource domSource = new DOMSource(doc);
                StreamResult streamResult = new StreamResult(new File(new File(".").getAbsolutePath() + "\\files\\" + fileName + ".xml"));

                transformer.transform(domSource, streamResult);
            }

        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        } catch (TransformerConfigurationException e1) {
            e1.printStackTrace();
        } catch (TransformerException e1) {
            e1.printStackTrace();
        }
    }

    public void openFile(String fileName) {
        File file = new File(new File(".").getAbsolutePath() + "\\files\\" + fileName + ".xml");

        if (!file.exists()) {
            persons.clear();
            return;
        }

        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();

            persons.clear();

            DefaultHandler handler = new DefaultHandler() {
                String string;
                ArrayList<String> arrayList = new ArrayList();

                @Override
                public void startElement(String namespace, String localName, String qName, Attributes attribute) throws SAXException {
                    string = qName;
                }

                @Override
                public void endElement(String namespace, String localName, String qName) throws SAXException {
                    if (qName.equals("student")) {
                        addData(arrayList);
                        arrayList.clear();
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    if (!string.equals("student") && !string.equals("students")) {
                        arrayList.add(new String(ch, start, length));
                        string = "students";
                    }
                }
            };

            saxParser.parse(new File(file.getPath()), handler);

        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        } catch (org.xml.sax.SAXException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private boolean checkString(String string) {
        try {
            Integer.parseInt(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void eraseData(Object obj) {
        persons.remove(obj);
    }
    public ArrayList<Person> getPersons() { return persons;}
    public ArrayList<Person> getSearchList() {
        return searchList;
    }
}
package softuni.workshop.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {
    <O> O importXml (String path, Class<O> objectClass) throws JAXBException;
}

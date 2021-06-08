package config;

import generated.MessageManagerConfig;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class ConfigHelper {
    public static MessageManagerConfig getConfig() throws JAXBException {
        File file = new File("AppConfiguration/MessageManagerConfig.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(MessageManagerConfig.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (MessageManagerConfig) jaxbUnmarshaller.unmarshal(file);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_final_client;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import javax.xml.bind.*;
import javax.xml.namespace.QName;
import rest.FileClient;
import rest.UserClient;
import xsd.files.FileType;
import xsd.files.FilesType;
import xsd.users.UserType;
import xsd.users.UsersType;


/**
 *
 * @author souf
 */
public class TestUserlClient {

    
     public static void main(String[] args) throws JAXBException {
        
        FileClient file = new FileClient();        
            // GET
        String response = file.findAll_XML(String.class);
        System.out.println(response);                  
       
//            // JAXB context 
        JAXBContext jcIn = JAXBContext.newInstance("xsd.files");;
        Unmarshaller u = jcIn.createUnmarshaller();
        
//        // from String (reponse) to xml element
        JAXBElement element = (JAXBElement) u.unmarshal(new StringReader(response));
        
            // userinfoes : list of userinfo
        FilesType filesinfoes = (FilesType) element.getValue();
        System.out.println(filesinfoes.getFile());
        List<FileType> fileinfoes = filesinfoes.getFile();
        for (int i = 0; i < fileinfoes.size(); i++) {
            FileType monfile = (FileType) fileinfoes.get(i);
            System.out.println("id " + monfile.getId());
            System.out.println("taille   : " + monfile.getTaille());
            System.out.println("nom   : " + monfile.getNom());
            System.out.println();
        }
   /*         
        // The code of the Post Operation
        
//            // JAXB context 
        JAXBContext jcOut = JAXBContext.newInstance("xsd.users");
        Marshaller m = jcOut.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
//            // new userinfo
        final UserType userOut = new UserType();
        userOut.setId(Integer.SIZE);
        userOut.setPrenom("soufiane");
        userOut.setNom("AICHANE");
        userOut.setMotDePasse("pass");
        userOut.setEmail("email@");
        
            // a new xml element 
        JAXBElement elementOut = new JAXBElement(new QName("","user"),UserType.class,userOut);
        
            // from xml element to string
        final StringWriter stringWriter = new StringWriter();
        m.marshal(elementOut,stringWriter);
        System.out.println(stringWriter.toString());
      
            // POST the new userinfo
        client.create_XML(stringWriter.toString());
        */
        file.close();
    }
}

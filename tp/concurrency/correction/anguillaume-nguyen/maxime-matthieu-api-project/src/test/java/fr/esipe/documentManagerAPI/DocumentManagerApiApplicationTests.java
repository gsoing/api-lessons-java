package fr.esipe.documentManagerAPI;

import fr.esipe.documentManagerAPI.model.DocumentModel;
import fr.esipe.documentManagerAPI.service.DocumentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DocumentManagerApiApplicationTests {

	@Autowired
	private DocumentService serv;

	@Test
	void contextLoads() {
	}

	/*@Test
	public void testOptimistic() {
		Date currentDate = new Date();
		DocumentModel updatedDoc = new DocumentModel();
		updatedDoc = serv.findDocumentById(1).get();
		updatedDoc.setUpdated(currentDate);
		updatedDoc.setBody("ola");
		serv.updateDocument(updatedDoc);

		DocumentModel doc2 = new DocumentModel();
		doc2 = serv.findDocumentById(1).get();
		DocumentModel doc3 = new DocumentModel();
		doc3 = serv.findDocumentById(1).get();

		doc2.setBody("bla");
		serv.updateDocument(doc2);


		doc3.setBody("flu");
		try {
			serv.updateDocument(doc3);
		} catch (Exception e) {
			e.printStackTrace();
		}

		DocumentModel Model = serv.findDocumentById(1).get();
		assertEquals("bla", Model.getBody());


	}*/

}

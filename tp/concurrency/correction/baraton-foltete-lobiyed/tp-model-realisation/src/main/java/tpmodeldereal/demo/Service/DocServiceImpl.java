package tpmodeldereal.demo.Service;
import org.springframework.beans.factory.annotation.Autowired;
import tpmodeldereal.demo.Model.document;
import tpmodeldereal.demo.Repository.DocRepository;
import java.util.Collection;


public class DocServiceImpl implements  DocService {

    @Autowired
    DocRepository repo ;
    document doc;


    @Override
    public void createdoc(document doc) {
        repo.save(doc) ;
    }

    @Override
    public Collection<document> getAllDocs() {
        return repo.findDocs();
    }

    @Override
    public document findDocById(String id) {
        return repo.findByDocumentId( id );
    }

    @Override
    public void deleteDocById(String id) {
      repo.deleteById( id );
    }

    @Override
    public void updateDoc(document doc) {
        repo.save( doc ) ;
    }

    @Override
    public void deleteAllDocs() {
        repo.deleteAll();
    }
}

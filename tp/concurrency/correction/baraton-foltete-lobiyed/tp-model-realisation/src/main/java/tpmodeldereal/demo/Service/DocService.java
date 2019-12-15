package tpmodeldereal.demo.Service;


import tpmodeldereal.demo.Model.document;

import java.util.Collection;
import java.util.List;

public interface DocService {

    public void createdoc (document doc) ;

    public Collection<document> getAllDocs() ;

    public document findDocById (String id) ;

    public void deleteDocById (String id) ;

    public void updateDoc (document doc) ;

    public void deleteAllDocs() ;



}

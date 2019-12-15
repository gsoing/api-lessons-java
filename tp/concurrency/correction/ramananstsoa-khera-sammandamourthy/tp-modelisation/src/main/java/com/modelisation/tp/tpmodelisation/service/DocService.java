package com.modelisation.tp.tpmodelisation.service;

import com.github.rutledgepaulv.qbuilders.builders.GeneralQueryBuilder;
import com.github.rutledgepaulv.qbuilders.conditions.Condition;
import com.github.rutledgepaulv.rqe.pipes.QueryConversionPipeline;
import com.modelisation.tp.tpmodelisation.exception.NotFoundException;
import com.modelisation.tp.tpmodelisation.model.CustomLock;
import com.modelisation.tp.tpmodelisation.model.Doc;
import com.modelisation.tp.tpmodelisation.repository.CustomDocRepository;
import com.modelisation.tp.tpmodelisation.repository.CustomLockRepository;
import com.modelisation.tp.tpmodelisation.repository.DocRepository;
import lombok.extern.slf4j.Slf4j;
import com.github.rutledgepaulv.qbuilders.visitors.MongoVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Doc Service
 */
@Slf4j
@Service
public class DocService {

    @Autowired
    private DocRepository docRepository;
    @Autowired
    private CustomLockRepository lockRepository;


    @Autowired
    private CustomDocRepository customDocRepository;

    private QueryConversionPipeline pipeline = QueryConversionPipeline.defaultPipeline();

    /**
     * Get all documents
     *
     * @param stringQuery
     * @param pageable
     * @return all documents
     */
    public Page<Doc> getDocuments(String stringQuery, Pageable pageable) {
        Criteria criteria = convertQuery(stringQuery);
        Page<Doc> results = customDocRepository.findDocs(criteria, pageable);
        return results;
    }

    /**
     * Get a document by id
     *
     * @param id
     * @return document
     */
    public Doc getDocument(String id) {
        Doc document = docRepository.findById(id).orElseThrow(() -> NotFoundException.DEFAULT);
        return document;
    }

    /**
     * Create a document
     *
     * @param doc
     * @return document inserted
     */
    public Doc createDoc(Doc doc) {
        Doc insertedDoc = docRepository.save(doc);
        return insertedDoc;
    }

    /**
     * Convertit une requête RSQL en un objet Criteria compréhensible par la base
     *
     * @param stringQuery
     * @return
     */
    private Criteria convertQuery(String stringQuery) {
        Criteria criteria;
        if (!StringUtils.isEmpty(stringQuery)) {
            Condition<GeneralQueryBuilder> condition = pipeline.apply(stringQuery, Doc.class);
            criteria = condition.query(new MongoVisitor());
        } else {
            criteria = new Criteria();
        }
        return criteria;
    }

    /**
     * Update a document
     *
     * @param doc
     * @return doc updated
     */
    public Doc updateDoc(Doc doc) {
        return docRepository.save(doc);
    }

    public Doc pessimisticUpdateDoc(Doc updateDoc, CustomLock lock) throws InterruptedException {
        Doc doc = docRepository.findById(updateDoc.getDocumentId()).orElseThrow(() -> NotFoundException.DEFAULT);
        doc.setCreated(updateDoc.getCreated());
        doc.setUpdated(updateDoc.getUpdated());
        doc.setTitle(updateDoc.getTitle());
        doc.setCreator(updateDoc.getCreator());
        doc.setEditor(updateDoc.getEditor());
        doc.setBody(updateDoc.getBody());
        return docRepository.save(doc);
    }

    /**
     * Add a lock on document
     * @param doc
     * @param owner
     * @return document saved
     */
    public Doc addLock(Doc doc, String owner) {
        Doc docWithLock = docRepository.findById(doc.getDocumentId()).orElseThrow(() -> NotFoundException.DEFAULT);
        CustomLock lock = new CustomLock(owner);
        lockRepository.save(lock);
        docWithLock.setCustomLock(lock.getId());
        return docRepository.save(docWithLock);
    }
}

package documentedition.demo.repository;

import documentedition.demo.model.Doc;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.util.DigestUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Slf4j
public class EtagEventListener extends AbstractMongoEventListener<Doc> {

    @Override
    public void onAfterConvert(AfterConvertEvent<Doc> event) {
        event.getSource().setEtag(calculateEtag(event.getDocument()));
    }

    @Override
    public void onAfterSave(AfterSaveEvent<Doc> event) {
        event.getSource().setEtag(calculateEtag(event.getDocument()));
    }

    /**
     * On calcule l'etag en calculant une MD5 sur le string json
     * @param document
     * @return
     */
    private String calculateEtag(Document document) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            DigestUtils.appendMd5DigestAsHex(new ByteArrayInputStream(document.toJson().getBytes()), stringBuilder);
        } catch (IOException e) {
            throw new DataRetrievalFailureException("Unable to create etag", e);
        }
        return stringBuilder.toString();
    }
}
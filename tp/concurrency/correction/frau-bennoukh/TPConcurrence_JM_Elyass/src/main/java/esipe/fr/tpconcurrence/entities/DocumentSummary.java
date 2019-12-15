package esipe.fr.tpconcurrence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ApiModel(description = "résumé d'un document")
public class DocumentSummary   {
  @Id
  @JsonIgnore
  private String id;

  @Version
  @NotNull
  @JsonIgnore
  private String version;

  private String documentId = null;

  private LocalDateTime created = null;

  private LocalDateTime updated = null;

  private String title = null;

  public DocumentSummary(String documentId, LocalDateTime created, LocalDateTime updated, String title) {
    this.documentId = documentId;
    this.created = created;
    this.updated = updated;
    this.title = title;
  }

  public DocumentSummary() {
  }

  public String getDocumentId() {
    return documentId;
  }

  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }


  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public LocalDateTime getUpdated() {
    return updated;
  }

  public void setUpdated(LocalDateTime updated) {
    this.updated = updated;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }
}


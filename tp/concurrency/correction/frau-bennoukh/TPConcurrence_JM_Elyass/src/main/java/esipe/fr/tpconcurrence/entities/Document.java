package esipe.fr.tpconcurrence.entities;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "a document")
@org.springframework.data.mongodb.core.mapping.Document("Document")
public class Document extends DocumentSummary {

  private String creator = null;
  private String editor = null;
  private String body = null;

  public Document() {
  }

  public Document creator(String creator) {
    this.creator = creator;
    return this;
  }


  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }


  public String getEditor() {
    return editor;
  }

  public void setEditor(String editor) {
    this.editor = editor;
  }


  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

}


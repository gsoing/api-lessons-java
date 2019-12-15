package esipe.fr.tpconcurrence.entities;


import io.swagger.annotations.ApiModel;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "la liste des résultats d'une recherche de document")
public class DocumentsList extends PageData  {

  private List<DocumentSummary> data = null;


  public DocumentsList addDataItem(DocumentSummary dataItem) {
    if (this.data == null) {
      this.data = new ArrayList<DocumentSummary>();
    }
    this.data.add(dataItem);
    return this;
  }

  public List<DocumentSummary> getData() {
    return data;
  }

  public void setData(List<DocumentSummary> data) {
    this.data = data;
  }

}


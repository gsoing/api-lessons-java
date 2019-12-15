package com.tplock.locking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DocumentsList extends PageData  {
    @JsonProperty("data")
    private List<DocumentSummary> data = null;

    public DocumentsList data(List<DocumentSummary> data) {
        this.data = data;
        return this;
    }

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



package com.tplock.locking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

public class PageData   {
    @JsonProperty("page")
    private Integer page = null;

    @JsonProperty("nbElements")
    private Integer nbElements = null;

    public PageData page(Integer page) {
        this.page = page;
        return this;
    }


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public PageData nbElements(Integer nbElements) {
        this.nbElements = nbElements;
        return this;
    }


    public Integer getNbElements() {
        return nbElements;
    }

    public void setNbElements(Integer nbElements) {
        this.nbElements = nbElements;
    }


}

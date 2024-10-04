package org.gso.samples.tweets.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.net.URI;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageData<T> {

    private URI next;
    private long totalElements;
    private int page;
    @NotEmpty
    private List<T> data;


    @Builder
    @SuppressWarnings("unused")
    private PageData(long totalElements, int page, List<T> data){
        this.totalElements = totalElements;
        this.page = page;
        this.data = data;
    }

    @Builder
    @SuppressWarnings("unused")
    private PageData(long totalElements, int page, List<T> data, URI next){
        this.totalElements = totalElements;
        this.page = page;
        this.data = data;
        this.next = next;
    }

    public static <T> PageData<T> fromPage(Page<T> page) {
        var pageData = new PageData<T>(page.getTotalElements(), page.getNumber(), page.getContent());
        return pageData;
    }
}

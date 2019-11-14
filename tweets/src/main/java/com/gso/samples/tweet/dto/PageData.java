package com.gso.samples.tweet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageData<T> {

    private URI next;
    private long totalElements;
    private int page;
    @NotNull
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
     return PageData.<T>builder()
             .data(page.getContent())
             .page(page.getNumber())
             .totalElements(page.getTotalElements())
             .build();
    }
}

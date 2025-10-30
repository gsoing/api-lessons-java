package org.gso.samples.tweets.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.domain.Page;

import java.net.URI;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PageData<T>(long totalElements, int page,List<T> data, URI next) {


    public static <T> PageData<T> fromPage(Page<T> page, URI next) {
        return new PageData<T>(page.getTotalElements(), page.getNumber(), page.getContent(), next);
    }
}

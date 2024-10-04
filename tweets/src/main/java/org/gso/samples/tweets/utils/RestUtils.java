package org.gso.samples.tweets.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@UtilityClass
public class RestUtils {

    public static boolean hasNext(Page results, Pageable pageable) {
        long totalPage = results.getTotalElements() / pageable.getPageSize();
        return pageable.getPageNumber() < Math.ceil(totalPage) - 1;
    }

    public static LocalDateTime convertToLocalDateTime(ZonedDateTime zonedDateTime) {
        return zonedDateTime == null ? null : zonedDateTime.toLocalDateTime();
    }

    public static ZonedDateTime convertToZoneDateTime(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atZone(ZoneId.systemDefault());
    }

    public static final URI buildNextUri(UriComponentsBuilder uriComponentsBuilder, Pageable pageable) {
        return uriComponentsBuilder
                .replaceQueryParam("page", pageable.getPageNumber() + 1)
                .replaceQueryParam("size",pageable.getPageSize()).encode().build().toUri();
    }

    public static URI buildLocation(String path, String id) {
        return UriComponentsBuilder.fromPath(path).pathSegment(id).build().toUri();
    }
}

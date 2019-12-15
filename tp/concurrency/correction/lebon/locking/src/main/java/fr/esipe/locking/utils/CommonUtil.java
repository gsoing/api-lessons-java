package fr.esipe.locking.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.DigestUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class CommonUtil {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static boolean hasNext(Page<?> results, Pageable pageable) {
        long totalPage = results.getTotalElements() / pageable.getPageSize();
        return pageable.getPageNumber() < Math.ceil(totalPage) - 1;
    }

    public static URI buildNextUri(UriComponentsBuilder uriComponentsBuilder, Pageable pageable) {
        return uriComponentsBuilder
                .replaceQueryParam("page", pageable.getPageNumber() + 1)
                .replaceQueryParam("size",pageable.getPageSize()).encode().build().toUri();
    }

    public static LocalDateTime convertToLocalDateTime(ZonedDateTime zonedDateTime) {
        return zonedDateTime == null ? null : zonedDateTime.toLocalDateTime();
    }

    public static ZonedDateTime convertToZoneDateTime(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atZone(ZoneId.systemDefault());
    }

    public static String buildEtag(String source) throws IOException {
        return DigestUtils.md5DigestAsHex(new ByteArrayInputStream(source.getBytes()));
    }

}

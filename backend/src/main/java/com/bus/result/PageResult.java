package com.bus.result;

import lombok.Data;

import java.util.List;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

@Data
public class PageResult<T> {

    private long total;

    private List<T> records;

    private int page;

    private int pageSize;

    public PageResult() {
    }

    public PageResult(long total, List<T> records) {
        this.total = total;
        this.records = records;
    }

    public PageResult(long total, List<T> records, int page, int pageSize) {
        this.total = total;
        this.records = records;
        this.page = page;
        this.pageSize = pageSize;
    }

    public static <T> PageResult<T> of(long total, List<T> records) {
        return new PageResult<>(total, records);
    }

    public static <T> PageResult<T> of(long total, List<T> records, int page, int pageSize) {
        return new PageResult<>(total, records, page, pageSize);
    }
}

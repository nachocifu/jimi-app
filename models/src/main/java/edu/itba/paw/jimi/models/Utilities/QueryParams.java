package edu.itba.paw.jimi.models.Utilities;

public class QueryParams {

    public static final int NO_VALUE = -1;


    private int startAt;
    private int pageSize;
    private int total;
    private int pageCount;
    private int currentPage;
    private String orderBy;
    private boolean ascending;

    public QueryParams(int startAt, int pageSize) {
        this.pageSize = pageSize;
        this.startAt = startAt;
    }

    public QueryParams(int startAt, int pageSize, int total) {
        this.startAt = startAt;
        this.pageSize = pageSize;
        setTotal(total);
    }

    public QueryParams(String orderBy, boolean ascending) {
        this.orderBy = orderBy;
        this.ascending = ascending;
        this.startAt = NO_VALUE;
        this.pageSize = NO_VALUE;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartAt() {
        return startAt;
    }

    public void setStartAt(int startAt) {
        this.startAt = startAt;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        this.pageCount = (int) Math.ceil(total / (double) pageSize);
        this.currentPage = startAt / pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}

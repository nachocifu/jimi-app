package edu.itba.paw.jimi.models.Utilities;

public class QueryParams {
    private int startAt;
    private int pageSize;
    private int total;
    private int pageCount;
    private int currentPage;

    public QueryParams(int startAt, int pageSize) {
        this.pageSize = pageSize;
        this.startAt = startAt;
    }

    public QueryParams(int startAt, int pageSize, int total) {
        this.startAt = startAt;
        this.pageSize = pageSize;
        setTotal(total);
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
        this.pageCount = (int) Math.ceil(total / (double)pageSize);
        this.currentPage = startAt / pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}

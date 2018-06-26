package edu.itba.paw.jimi.interfaces.exceptions;

public class HttpError extends RuntimeException {

    protected String title;
    protected String body;
    protected Integer status;

    HttpError(String title, String body, Integer status) {
        super(body);
        this.title = title;
        this.body = body;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Integer getStatus() {
        return status;
    }
}

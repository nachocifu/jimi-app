package edu.itba.paw.jimi.interfaces.exceptions;

public class Http400Error extends HttpError {

    public Http400Error(String title, String body) {
        super(title, body, 400);
    }

}

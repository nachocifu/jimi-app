package edu.itba.paw.jimi.interfaces.exceptions;

public class Http404Error extends HttpError {

    public Http404Error(String title, String body) {
        super(title, body, 404);
    }

}

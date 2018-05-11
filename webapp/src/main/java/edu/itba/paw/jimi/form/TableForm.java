package edu.itba.paw.jimi.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class TableForm {

    @Size(min = 4, max = 10)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String tablename;

    public String getName() {
        return tablename;
    }

    public void setName(String name) { this.tablename = name; }

}

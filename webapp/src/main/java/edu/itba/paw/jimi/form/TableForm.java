package edu.itba.paw.jimi.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class TableForm {

    @Size(min = 4, max = 10)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

}

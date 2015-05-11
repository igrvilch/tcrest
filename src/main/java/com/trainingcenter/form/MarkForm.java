package com.trainingcenter.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MarkForm {

    @NotNull
    @Min(1)
    @Max(5)
    private Integer mark;

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }
}

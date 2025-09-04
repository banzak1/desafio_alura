package br.com.alura.AluraFake.task;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public class OpenTextTaskDTO {

    @NotNull
    private Long courseId;
    @NotBlank
    @Length(min = 4, max = 255)
    private String statement;
    @NotNull
    @Positive
    private Long order;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }
}

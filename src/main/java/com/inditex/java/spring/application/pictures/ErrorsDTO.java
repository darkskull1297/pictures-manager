package com.inditex.java.spring.application.pictures;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ErrorsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<ErrorDTO> errors = new ArrayList<ErrorDTO>();

    public ErrorsDTO errors(List<ErrorDTO> errors) {
        this.errors = errors;
        return this;
    }

    public ErrorsDTO addErrorsItem(ErrorDTO errorsItem) {
        this.errors.add(errorsItem);
        return this;
    }

    /**
     * List of errors.
     * @return errors
     **/
    @NotNull
    @Valid
    public List<ErrorDTO> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDTO> errors) {
        this.errors = errors;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorsDTO errors = (ErrorsDTO) o;
        return Objects.equals(this.errors, errors.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errors);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ErrorsDTO {\n");

        sb.append("    errors: ").append(toIndentedString(errors)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

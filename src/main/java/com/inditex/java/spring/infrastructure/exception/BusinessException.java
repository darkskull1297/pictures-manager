package com.inditex.java.spring.infrastructure.exception;

public class BusinessException extends RuntimeException {
    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -6788661443546955762L;

    /**
     * The error code.
     */
    protected String errorCode;

    /**
     * The error description.
     */
    protected String exceptionDescription;

    /**
     * <p>
     * Constructor for BusinessException.
     * </p>
     *
     * @param errorCode a {@link String} object.
     * @param description a {@link String} object.
     */
    public BusinessException(String errorCode, String description) {
        super("", null, false, false);
        this.errorCode = errorCode;
        this.exceptionDescription = description;
    }

    /**
     * <p>
     * Constructor for BusinessException (errorCode, message)
     * </p>.
     *
     * @param errorCode a {@link String} string.
     * @param description a {@link String} string.
     * @param message   a {@link String} message.
     */
    public BusinessException(String errorCode, String description, String message) {
        super(message, null, false, false);
        this.exceptionDescription = description;
        this.errorCode = errorCode;
    }

    /**
     * <p>
     * Getter for the field <code>errorCode</code>.
     * </p>
     *
     * @return a {@link String} string.
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * <p>
     * Getter for the field <code>description</code>.
     * </p>
     *
     * @return a {@link String} string.
     */
    public String getExceptionDescription() {
        return exceptionDescription;
    }

}

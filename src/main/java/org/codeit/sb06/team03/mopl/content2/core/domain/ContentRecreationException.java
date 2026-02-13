package org.codeit.sb06.team03.mopl.content2.core.domain;

public class ContentRecreationException extends ContentException {

    protected ContentRecreationException(String message) {
        super(message);
    }

    public static ContentRecreationException emptyHistory() {
        return new ContentRecreationException("History is empty.");
    }

    public static ContentRecreationException alreadyInitialized() {
        return new ContentRecreationException("Content already initialized.");
    }

    public static ContentRecreationException notInitialized() {
        return new ContentRecreationException("Content not initialized.");
    }
}

package org.codeit.sb06.team03.mopl.content2.core.domain;

import java.util.UUID;

public class ContentAccessDeniedException extends ContentException {

    public ContentAccessDeniedException(UUID requesterId) {
        super("Access Denied: Requester[%s] does not have sufficient permissions to create or access content."
                .formatted(requesterId));
    }

    public ContentAccessDeniedException(UUID contentId, UUID requesterId) {
        super("Access Denied: Requester[%s] cannot perform this action on Content[%s]"
                .formatted(requesterId, contentId));
    }
}

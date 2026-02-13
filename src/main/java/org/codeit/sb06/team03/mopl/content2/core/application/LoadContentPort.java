package org.codeit.sb06.team03.mopl.content2.core.application;

import org.codeit.sb06.team03.mopl.content2.core.domain.Content;

import java.util.UUID;

public interface LoadContentPort {

    Content load(UUID id);
}

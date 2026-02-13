package org.codeit.sb06.team03.mopl.content2.core.application;

import org.codeit.sb06.team03.mopl.content2.core.domain.Content;

public interface CreateContentUseCase {

    Content create(CreateContentCommand command);
}

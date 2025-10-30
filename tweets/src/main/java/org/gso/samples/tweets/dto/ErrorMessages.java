package org.gso.samples.tweets.dto;

import java.util.List;

public record ErrorMessages (ErrorMessageType type, List<ErrorMessage> messages) {}

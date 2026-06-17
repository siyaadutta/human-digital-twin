package com.humandigitaltwin.api;

import java.util.List;

public record ApiErrorResponse(
        String timestamp,
        int status,
        String message,
        List<String> details) {
}

package miquido.recruitment.enums;

import lombok.Getter;

@Getter
public enum SwApiEndpoint {
    PEOPLE("people");

    SwApiEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    final String endpoint;
}

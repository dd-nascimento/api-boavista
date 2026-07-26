package com.david.api_boavista.enums;

public enum Role {
    
    //Centralizar as informações de cada perfil, facilitanto futuras expansões sem precisar alterar o código em diversos lugares.
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }
    public String getAuthority() {
        return authority;
    }
}

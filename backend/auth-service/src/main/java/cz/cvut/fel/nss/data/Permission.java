package cz.cvut.fel.nss.data;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ,
    ADMIN_CREATE,
    ADMIN_UPDATE,
    ADMIN_DELETE,
    USER_READ,
    USER_UPDATE


}

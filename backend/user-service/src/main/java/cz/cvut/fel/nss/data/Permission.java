package cz.cvut.fel.nss.data;

import lombok.RequiredArgsConstructor;

/**
 * Permission enum defines various permissions for roles in the application.
 */
@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ,
    ADMIN_CREATE,
    ADMIN_UPDATE,
    ADMIN_DELETE,
    USER_READ,
    USER_UPDATE


}

package cz.cvut.fel.nss.data;

import lombok.RequiredArgsConstructor;

/**
 * Enum representing various permissions in the system.
 * Each permission corresponds to a specific action that can be performed by users.
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

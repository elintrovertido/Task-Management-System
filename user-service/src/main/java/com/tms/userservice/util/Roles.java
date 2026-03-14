package com.tms.userservice.util;

import java.util.Set;

public enum Roles {
    ADMIN(Set.of(
            Permissions.PROJECT_READ,
            Permissions.PROJECT_WRITE,
            Permissions.USER_WRITE,
            Permissions.USER_READ,
            Permissions.TASK_READ,
            Permissions.TASK_WRITE
    )),
    USER(Set.of(
            Permissions.PROJECT_READ,
            Permissions.PROJECT_WRITE,
            Permissions.TASK_READ,
            Permissions.TASK_WRITE
    ));

   private final Set<Permissions> permissions;

   Roles(Set<Permissions> permissions){
       this.permissions = permissions;
   }

   public Set<Permissions> getPermissions(){
       return this.permissions;
   }
}

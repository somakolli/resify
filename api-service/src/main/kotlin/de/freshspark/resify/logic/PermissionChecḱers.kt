package de.freshspark.resify.logic

import de.freshspark.resify.models.Permission
import de.freshspark.resify.models.PermissionScope
import de.freshspark.resify.models.PermissionType
import de.freshspark.resify.models.ResifyUser
import java.util.*

fun hasPermission(
  userPermissions: MutableCollection<Permission>,
  permission: Permission
): Boolean {
  return userPermissions.any {
    it == permission
  }
}
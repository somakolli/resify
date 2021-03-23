package de.freshspark.resify.logic

import de.freshspark.resify.models.Permission
import de.freshspark.resify.models.PermissionScope
import de.freshspark.resify.models.PermissionType
import de.freshspark.resify.models.ResifyUser
import java.util.*

fun hasPermission(
  user: ResifyUser,
  permission: Permission
): Boolean {
  return user.permissions.any {
    it == permission
  }
}
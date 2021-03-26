## What do we need?
 - create, modify and delete groups containing a set of permissions and a prefix for their members
 - add users to groups, remove users from groups
 - give users specific permissions
 - exclude users from specific group-inherited permissions
 - list groups
 - info on a user -> their groups, specific permissions and their active prefix
 - info on a group

## Command structure
/novaperms /nperms /novap /nope /np
 - groups / g
 - inspect / i
   - player / p [player]
   - group / g [group]
 - add / a
   - [player] [group]
 - permit / p
   - player / p [player] [permission]
   - group / g [group] [permission]
 - create / c [group]
 - prefix [group] [prefix]
## ListUtilCheck

### ListUtil.fromArray

Use `ListUtil.fromArray` to simplify code, when possible:

```java
addUsers(ListUtil.fromArray(UserLocalServiceUtil.getUser(userId)));
```

Instead of

```java
List<User> users = new ArrayList<>();

users.add(UserLocalServiceUtil.getUser(userId));

addUsers(users);
```

---

Pass arguments directly to `ListUtil.fromArray` instead of passing new array:

```java
addUsers(
    ListUtil.fromArray(
        _getUser("Anthony"), _getUser("Belinda"), _getUser("Emily"),
        _getUser("Lucy")));
```

Instead of

```java
addUsers(
    ListUtil.fromArray(
        new User[] {
            _getUser("Anthony"), _getUser("Belinda"), _getUser("Emily"),
            _getUser("Lucy")
        }));
```

### ListUtil.isEmpty

Use `ListUtil.isEmpty` to simplify code, when possible:

```java
List<BundleCapability> bundleCapabilities =
	bundleWiring.getCapabilities("liferay.resource.bundle");

if (ListUtil.isEmpty(bundleCapabilities)) {

...

}
```

Instead of

```java
List<BundleCapability> bundleCapabilities =
	bundleWiring.getCapabilities("liferay.resource.bundle");

if ((bundleCapabilities == null) || bundleCapabilities.isEmpty()) {

...

}
```
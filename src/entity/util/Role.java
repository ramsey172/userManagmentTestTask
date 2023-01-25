package entity.util;

public class Role {

    private final RoleName name;
    private RoleLevel level;

    public Role(RoleName name) {
        this.name = name;
        if (name.equals(RoleName.USER) || name.equals(RoleName.CUSTOMER)) {
            level = RoleLevel.LEVEL_1;
        }
        if (name.equals(RoleName.ADMIN) || name.equals(RoleName.PROVIDER)) {
            level = RoleLevel.LEVEL_2;
        }
        if (name.equals(RoleName.SUPER_ADMIN)) {
            level = RoleLevel.LEVEL_3;
        }
    }

    public RoleName getName() {
        return name;
    }

    public RoleLevel getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name=" + name +
                ", level=" + level +
                '}';
    }
}

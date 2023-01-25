package validators;

import entity.User;
import entity.util.Role;
import entity.util.RoleName;

import java.util.List;

public class UserValidator extends AbstractUserValidator {

    private final int MIN_PHONES_COUNT = 1;
    private final int MAX_PHONES_COUNT = 3;

    public boolean isValid(User user) {
        return isFirstNameValid(user.getFirstName()) &&
                isSecondNameValid(user.getSecondName()) &&
                isEmailValid(user.getEmail()) &&
                isPhonesValid(user.getPhones()) &&
                isRolesValid(user.getRoles());
    }

    private boolean isFirstNameValid(String firstName) {
        if (!firstName.matches("^[A-Za-z][A-Za-z0-9_]{2,16}$")) {
            errorMessages.add("Incorrect firstname");
            return false;
        }
        return true;
    }

    private boolean isSecondNameValid(String secondName) {
        if (!secondName.matches("^[A-Za-z][A-Za-z0-9_]{2,16}$")) {
            errorMessages.add("Incorrect secondname");
            return false;
        }
        return true;
    }

    private boolean isEmailValid(String email) {
        if (!email.matches("^[\\w-]{2,4}@([\\w-]{2,4}\\.)+[\\w-]{2,4}$")) {
            errorMessages.add("Incorrect email");
            return false;
        }
        return true;
    }

    private boolean isPhonesValid(List<String> phones) {
        if (phones.size() < MIN_PHONES_COUNT || phones.size() > MAX_PHONES_COUNT) {
            errorMessages.add("Incorrect phones count");
            return false;
        }
        if (!phones.stream().allMatch(phone -> phone.matches("^375(\\d){9}$"))) {
            errorMessages.add("Incorrect phone(s)");
            return false;
        }
        return true;
    }

    private boolean isRolesValid(List<Role> roles) {
        if (roles.isEmpty() || roles.size() > 2) {
            errorMessages.add("Roles are empty or roles count exceeds 2");
            return false;
        }

        if (roles.size() == 2) {
            if (roles.stream().anyMatch(role -> role.getName().equals(RoleName.SUPER_ADMIN))) {
                errorMessages.add("User with role SUPER_ADMIN must have only 1 role");
                return false;
            }
            if (roles.get(0).getLevel().equals(roles.get(1).getLevel())) {
                errorMessages.add("Levels of 2 roles are the same");
                return false;
            }
        }

        return true;
    }


}

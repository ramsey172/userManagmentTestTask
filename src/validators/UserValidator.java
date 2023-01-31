package validators;

import entity.User;
import entity.util.Role;

import java.util.List;

public class UserValidator extends AbstractValidator<User> {

    private final int MIN_PHONES_COUNT = 1;
    private final int MAX_PHONES_COUNT = 3;
    private final String FIRST_NAME_REGEX = "^[A-Za-z][A-Za-z0-9_]{2,16}$";
    private final String SECOND_NAME_REGEX = "^[A-Za-z][A-Za-z0-9_]{2,16}$";
    private final String EMAIL_REGEX = "^[\\w-]{2,4}@([\\w-]{2,4}\\.)+[\\w-]{2,4}$";
    private final String PHONE_REGEX = "^375(\\d){9}$";

    public boolean isValid(User user) {
        return isFirstNameValid(user.getFirstName()) &&
                isSecondNameValid(user.getSecondName()) &&
                isEmailValid(user.getEmail()) &&
                isPhonesValid(user.getPhones()) &&
                isRolesValid(user.getRoles());
    }

    private boolean isFirstNameValid(String firstName) {
        if (!firstName.matches(FIRST_NAME_REGEX)) {
            errorMessages.add("Incorrect firstname");
            return false;
        }
        return true;
    }

    private boolean isSecondNameValid(String secondName) {
        if (!secondName.matches(SECOND_NAME_REGEX)) {
            errorMessages.add("Incorrect secondname");
            return false;
        }
        return true;
    }

    private boolean isEmailValid(String email) {
        if (!email.matches(EMAIL_REGEX)) {
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
        if (!phones.stream().allMatch(phone -> phone.matches(PHONE_REGEX))) {
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
            if (roles.stream().anyMatch(role -> role.getName().equals(Role.RoleName.SUPER_ADMIN))) {
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

package console;

import entity.User;
import entity.util.Role;
//import entity.util.RoleName;
import services.UserManagerService;
import validators.UserValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static console.util.ConsoleReader.readInt;
import static console.util.ConsoleReader.readString;
import static console.util.ConsoleReader.readLong;
import static console.util.ConsoleWriter.write;
import static console.util.ConsoleWriter.writeError;

public class ConsoleApplication {
    private final UserManagerService userService = new UserManagerService();
    private final UserValidator userValidator = new UserValidator();

    public void run() {
        while (true) {
            write("1. View users list, 2. Add, 3. Remove 4. Edit, 5. Exit ");
            int i = readInt();

            switch (i) {
                case 1 -> {
                    List<User> usersList = userService.findAll();
                    usersList.forEach(user -> write(user.asString()));
                }
                case 2 -> {
                    while (true) {
                        User user = getUserFromDialog();
                        if (userValidator.isValid(user)) {
                            userService.create(user);
                            break;
                        } else {
                            for (String errorMessage : userValidator.popErrorMessages()) {
                                writeError(errorMessage);
                            }
                        }
                    }
                }
                case 3 -> {
                    write("Enter id");
                    long id = readLong();
                    userService.removeById(id);
                }
                case 4 -> {
                    write("Enter user id you want to edit");
                    long id = readLong();
                    Optional<User> user = userService.findById(id);
                    if (user.isPresent()) {
                        User updatedUser = getUpdatedUserFromDialog(user.get());
                        if (userValidator.isValid(updatedUser)) {
                            userService.update(updatedUser);
                        } else {
                            for (String errorMessage : userValidator.popErrorMessages()) {
                                writeError(errorMessage);
                            }
                        }

                    } else {
                        write("User not found");
                    }
                }
                case 5 -> {
                    return;
                }
            }
        }
    }

    private User getUserFromDialog() {
        write("Enter firstName");
        String firstName = readString();
        write("Enter secondName");
        String secondName = readString();
        write("Enter email");
        String email = readString();
        boolean flagPhonesContinue = true;
        String tmpPhone;
        String tmpPhoneContinueAnswer;
        List<String> phones = new ArrayList<>();
        write("Enter phone number");
        while (flagPhonesContinue) {
            tmpPhone = readString();
            phones.add(tmpPhone);
            write("Want to add another phone number? (y/n)");
            tmpPhoneContinueAnswer = readString();
            flagPhonesContinue = tmpPhoneContinueAnswer.equals("y");
        }
        boolean flagRolesContinue = true;
        int tmpRoleIntAnswer;
        Role.RoleName tmpRoleName;
        Role tmpRole;
        String tmpRoleContinueAnswer;
        List<Role> roles = new ArrayList<>();
        write("Choose user role");
        for (int i = 0; i < Role.RoleName.values().length; i++) {
            write((i + 1) + ". " + Role.RoleName.values()[i]);
        }
        while (flagRolesContinue) {
            tmpRoleIntAnswer = readInt();
            tmpRoleName = Role.RoleName.values()[--tmpRoleIntAnswer];
            tmpRole = new Role(tmpRoleName);
            roles.add(tmpRole);
            write("Want to add another role? (y/n)");
            tmpRoleContinueAnswer = readString();
            flagRolesContinue = tmpRoleContinueAnswer.equals("y");
        }
        return new User(firstName, secondName, email, phones, roles);
    }

    private User getUpdatedUserFromDialog(User user) {
        User updatedUser = getUserFromDialog();
        updatedUser.setId(user.getId());
        return updatedUser;
    }
}

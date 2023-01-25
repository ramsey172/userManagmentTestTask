package validators;

import entity.User;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractUserValidator {
    protected List<String> errorMessages = new ArrayList<>();

    public abstract boolean isValid(User user);

    public List<String> popErrorMessages() {
        List<String> errors = new ArrayList<>(errorMessages);
        errorMessages.clear();
        return errors;
    }
}

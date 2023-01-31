package validators;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractValidator<T> {
    protected List<String> errorMessages = new ArrayList<>();

    public abstract boolean isValid(T object);

    public List<String> popErrorMessages() {
        List<String> errors = new ArrayList<>(errorMessages);
        errorMessages.clear();
        return errors;
    }
}

package storage;

import entity.User;
import entity.util.Role;
import entity.util.RoleName;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileUserStorage implements UserStorage {
    private long ids;
    private final String filePath;
    private final FileWriter fileWriter;
    private BufferedReader bufferedReader;

    public FileUserStorage(String filePath) {
        this.filePath = filePath;
        try {
            fileWriter = new FileWriter(this.filePath, true);
            bufferedReader = new BufferedReader(new FileReader(this.filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        checkFilePath();
    }

    private void defineId() {
        List<User> usersList = findAll();
        ids = usersList.isEmpty() ? 1 : usersList.size();
    }


    @Override
    public void create(User user) {
        if (user.getId() == 0) {
            user.setId(ids++);
        }
        String userAsFileStorageString = getStringFromUser(user);
        try {
            fileWriter.write(userAsFileStorageString);
            fileWriter.write("\n");
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(User user) {
        List<User> usersList = findAll();
        usersList = usersList.stream().filter(u -> u.getId() != user.getId()).collect(Collectors.toList());
        usersList.add(user);
        usersList = usersList.stream().sorted(Comparator.comparingLong(User::getId)).collect(Collectors.toList());
        try {
            try (FileWriter tmpFileWriter = new FileWriter(filePath)) {
                tmpFileWriter.write("");
            }
            for (User u : usersList) {
                create(u);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> usersList = new ArrayList<>();
        try {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                usersList.add(getUserFromString(line));
            }
            bufferedReader.close();
            bufferedReader = new BufferedReader(new FileReader(this.filePath));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }

    @Override
    public void removeById(long id) {
        List<User> usersList = findAll();
        Optional<User> optionalUserToRemove = findById(id);
        if (optionalUserToRemove.isPresent()) {
            usersList = usersList.stream().filter(user -> user.getId() != id).collect(Collectors.toList());
            try {
                try (FileWriter tmpFileWriter = new FileWriter(filePath)) {
                    tmpFileWriter.write("");
                }
                for (User user : usersList) {
                    create(user);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public Optional<User> findById(long id) {
        List<User> usersList = findAll();
        return usersList.stream().filter(user -> user.getId() == id).findFirst();
    }

    private void checkFilePath() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            defineId();
        }
    }

    private User getUserFromString(String str) {
        String[] arrUserParts = str.split("\\|");
        String[] arrPhones = arrUserParts[4].split(",");
        String[] arrRoles = arrUserParts[5].split(",");

        ArrayList<Role> roles = new ArrayList<>();
        ArrayList<String> phones = new ArrayList<>(Arrays.asList(arrPhones));
        for (String r : arrRoles) {
            Role role = new Role(RoleName.valueOf(r));
            roles.add(role);
        }
        return new User(Long.parseLong(arrUserParts[0]), arrUserParts[1], arrUserParts[2], arrUserParts[3], phones, roles);
    }

    private String getStringFromUser(User user) {
        String phonesAsString = String.join(",", user.getPhones());
        List<String> arrStringRoles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            arrStringRoles.add(String.valueOf(role.getName()));
        }
        return user.getId() + "|" + user.getFirstName() + "|" + user.getSecondName() + "|" + user.getEmail() + "|" + phonesAsString + "|" + String.join(",", arrStringRoles);
    }
}

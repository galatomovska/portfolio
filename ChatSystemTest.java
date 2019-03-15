import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ChatSystemTest {

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) {
            ChatRoom cr = new ChatRoom(jin.next());
            int n = jin.nextInt();
            for (int i = 0; i < n; ++i) {
                k = jin.nextInt();
                if (k == 0) cr.addUser(jin.next());
                if (k == 1) cr.removeUser(jin.next());
                if (k == 2) System.out.println(cr.hasUser(jin.next()));
            }
            System.out.println("");
            System.out.println(cr.toString());
            n = jin.nextInt();
            if (n == 0) return;
            ChatRoom cr2 = new ChatRoom(jin.next());
            for (int i = 0; i < n; ++i) {
                k = jin.nextInt();
                if (k == 0) cr2.addUser(jin.next());
                if (k == 1) cr2.removeUser(jin.next());
                if (k == 2) cr2.hasUser(jin.next());
            }
            System.out.println(cr2.toString());
        }
        if (k == 1) {
            ChatSystem cs = new ChatSystem();
            Method mts[] = cs.getClass().getMethods();
            while (true) {
                String cmd = jin.next();
                if (cmd.equals("stop")) break;
                if (cmd.equals("print")) {
                    System.out.println(cs.getRoom(jin.next()) + "\n");
                    continue;
                }
                for (Method m : mts) {
                    if (m.getName().equals(cmd)) {
                        String params[] = new String[m.getParameterTypes().length];
                        for (int i = 0; i < params.length; ++i) params[i] = jin.next();
                        m.invoke(cs, params);
                    }
                }
            }
        }
    }

}

class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

//    public static void main(String[] args) {
//        User user1 = new User("a");
//        User user2 = new User("b");
//        System.out.println(user1.equals(user2));
//    }
}

class ChatRoom {
    private Set<User> users;
    private String name;

    public ChatRoom(String name) {
        this.users = new TreeSet<>(Comparator.comparing(User::getName));
        this.name = name;
    }

    public void addUser(String username) {
        users.add(new User(username));
    }

    public void removeUser(String username) {
        users.remove(new User(username));
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(name + "\n");
        for (User user : users) {
            sBuilder.append(user.toString() + "\n");
        }
        if (users.size() == 0)
            sBuilder.append("EMPTY\n");
        return sBuilder.toString();
    }

    public boolean hasUser(String username) {
        return users.contains(new User(username));
    }

    public int numUser() {
        return users.size();
    }

    public String getName() {
        return name;
    }

    public Set<User> getUsers() {
        return users;
    }
}

class ChatSystem {
    private Map<String, ChatRoom> rooms;
    private List<User> registeredUsers;

    public ChatSystem() {
        this.rooms = new TreeMap<>();
        this.registeredUsers = new ArrayList<>();
    }

    public void addRoom(String roomName) {
        rooms.put(roomName, new ChatRoom(roomName));
    }

    public void removeRoom(String roomName) {
        rooms.remove(roomName);
    }

    public ChatRoom getRoom(String roomName) throws NoSuchRoomException {
        if (!rooms.containsKey(roomName))
            throw new NoSuchRoomException(roomName);
        return rooms.get(roomName);
    }

    public void register(String userName) {
        ChatRoom room = rooms.values().stream().sorted(Comparator.comparing(ChatRoom::numUser)
                .thenComparing(ChatRoom::getName)).findFirst().orElse(null);
        if(room != null){
            room.addUser(userName);

        }
        registeredUsers.add(new User(userName));

    }

    public void registerAndJoin(String userName, String roomName) {
        //register(userName);
        registeredUsers.add(new User(userName));
        rooms.get(roomName).addUser(userName);
    }

    public void joinRoom(String userName, String roomName) throws NoSuchUserException, NoSuchRoomException {
        if (!rooms.containsKey(roomName))
            throw new NoSuchRoomException(roomName);
//        if (!registeredUsers.contains(new User(userName)))
//            throw new NoSuchUserException(userName);
        rooms.get(roomName).addUser(userName);
    }

    public void leaveRoom(String userName, String roomName) throws NoSuchUserException, NoSuchRoomException {
        if (!rooms.containsKey(roomName))
            throw new NoSuchRoomException(roomName);
//        if (!registeredUsers.contains(new User(userName)))
//            throw new NoSuchUserException(userName);
        rooms.get(roomName).removeUser(userName);

    }

    public void followFriend(String userName, String friend_username) throws NoSuchUserException {
        registeredUsers.add(new User(userName));
        if (!registeredUsers.contains(new User(friend_username)))
            throw new NoSuchUserException(friend_username);
        rooms.values().stream().filter(r ->
                r.getUsers().contains(new User(friend_username))).forEach(r ->
                r.addUser(userName));

    }


}

class NoSuchRoomExcеption extends Exception {
    public NoSuchRoomExcеption(String roomName) {
        super(roomName);
    }
}

class NoSuchUserException extends Exception {
    public NoSuchUserException(String userName) {
        super(userName);
    }
}

class NoSuchRoomException extends Exception {
    public NoSuchRoomException(String roomName) {
        super(roomName);
    }
}








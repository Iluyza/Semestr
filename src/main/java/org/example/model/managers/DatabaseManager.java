package org.example.model.managers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.model.database.PostgresConnectionProvider;
import org.example.model.entities.Message;
import org.example.model.entities.Post;
import org.example.model.entities.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DatabaseManager {

    public static boolean createUser(String username, String password, String email) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("insert into users (username, password," +
                    " email) values (?, ?, ?)");
            statement.setString(1, username);
            statement.setInt(2, password.hashCode());
            statement.setString(3, email);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean getUser(String username, String password, HttpServletRequest req) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            HttpSession session = req.getSession(true);
            PreparedStatement statement = connection.prepareStatement("select id, username, password," +
                    " email from users where username = ? and password = ?;");
            statement.setString(1, username);
            statement.setInt(2, password.hashCode());
            ResultSet set = statement.executeQuery();
            set.next();
            session.setAttribute("User", User.builder()
                    .id(set.getInt(1))
                    .username(set.getString(2))
                    .password(set.getInt(3))
                    .email(set.getString(4))
                    .build());
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static List<Post> getFeed(int id, int myId) {
        List<Post> list = new LinkedList<>();
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select posts.id, username, postname, post from users right join posts on owner_id = users.id where owner_id = ?;");
            getList(id,myId, list, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static List<Post> getAllFeed(int id, int myId) {
        List<Post> list = new LinkedList<>();
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select posts.id, username, postname, post from (select id_of_author from follower_list where id_of_follower = ?) as table1 left join (users left join posts on owner_id = users.id) on id_of_author = owner_id;");
            getList(id, myId, list, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private static void getList(int id, int myId, List<Post> list, PreparedStatement statement) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                PreparedStatement st = connection.prepareStatement("select count(*) from likes where id_of_pos = ?;");
                PreparedStatement checkOwner = connection.prepareStatement("select exists(select * from posts where owner_id = ? and id = ?)");
                checkOwner.setInt(1, myId);
                st.setInt(1, set.getInt(1));
                checkOwner.setInt(2,set.getInt(1));
                ResultSet s = st.executeQuery();
                s.next();
                list.add(
                        Post.builder()
                                .id(set.getInt(1))
                                .owner(set.getString(2))
                                .name(set.getString(3))
                                .text(set.getString(4))
                                .likes(s.getLong(1))
                                .isEditable((exists(checkOwner)||PermissionManager.checkPermission(id))+"")
                                .build()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean exists(PreparedStatement statement) throws SQLException {
        ResultSet set = statement.executeQuery();
        set.next();
        return set.getBoolean(1);
    }

    public static void createMessage(String AuthorName, String ReceiverName, String text) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("insert into messages (name_of_author, name_of_receiver, message_text) VALUES (?,?,?)");
            statement.setString(1, AuthorName);
            statement.setString(2, ReceiverName);
            statement.setString(3, text);
            statement.execute();
        } catch (SQLException ignored) {
        }
    }

    public static List<Message> getMessages(String AuthorName, String ReceiverName) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select name_of_author, message_text from messages where" +
                    "                     (name_of_author = ? AND name_of_receiver = ?) or (name_of_receiver = ? and name_of_author = ?) order by id;");
            statement.setString(1, AuthorName);
            statement.setString(2, ReceiverName);
            statement.setString(3, AuthorName);
            statement.setString(4, ReceiverName);
            ResultSet set = statement.executeQuery();
            LinkedList<Message> list = new LinkedList<>();
            while (set.next()) {
                list.add(
                        Message.builder()
                                .author(set.getString(1))
                                .text(set.getString(2))
                                .build()
                );
            }
            return list;
        } catch (SQLException e) {
            return null;
        }
    }

    public static void deletePost(int id){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("delete from posts where id=?;");
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException ignored) {
        }
    }

    public static void postUpdate(Post post){
        try(Connection connection = PostgresConnectionProvider.getConnection()){
            PreparedStatement statement = connection.prepareStatement("update posts set postname = ?, post = ? where id = ?;");
            statement.setString(1,post.getName());
            statement.setString(2,post.getText());
            statement.setLong(3, post.getId());
            statement.execute();
        } catch (SQLException ignored) {
        }
    }

    public static void toFollow(User user, int id){
        try(Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("insert into follower_list (id_of_follower, id_of_author) VALUES (?, ?)");
            statement.setInt(1, user.getId());
            statement.setInt(2, id);
            statement.execute();
        } catch (SQLException ignored) {
        }
    }

    public static void unfollow(User user, int id){
        try(Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("delete from follower_list where id_of_follower = ? and id_of_author = ?;");
            statement.setInt(1, user.getId());
            statement.setInt(2, id);
            statement.execute();
        } catch (SQLException ignored) {
        }
    }

    public static Post getPost(int id){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select posts.id, username, postname, post from users right join posts on owner_id = users.id where posts.id = ?");
            statement.setInt(1,id);
            ResultSet set = statement.executeQuery();
            set.next();
            return Post.builder()
                    .id(set.getInt(1))
                    .owner(set.getString(2))
                    .name(set.getString(3))
                    .text(set.getString(4))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean createPost(int id, String name, String text){
        try(Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("insert into posts (owner_id, postname, post) VALUES (?,?,?)");
            statement.setInt(1,id);
            statement.setString(2,name);
            statement.setString(3,text);
            statement.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static String getUsername(int id){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select username from users where id = ?");
            statement.setInt(1,id);
            ResultSet set = statement.executeQuery();
            set.next();
            return set.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isFollower(int follower_id, int author_id){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select exists(select * from follower_list where id_of_author = ? and id_of_follower = ?)");
            statement.setInt(1,author_id);
            statement.setInt(2, follower_id);
            return exists(statement);
        } catch (SQLException e) {
            return false;
        }
    }

    private static void toLike(long user_id, long post_id){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("insert into likes (id_of_user, id_of_pos) VALUES (?,?)");
            statement.setLong(1,user_id);
            statement.setLong(2,post_id);
            statement.execute();
        } catch (SQLException ignored) {
        }
    }
    private static void unLike(long user_id, long post_id){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("delete from likes where id_of_user=? and id_of_pos=?");
            statement.setLong(1,user_id);
            statement.setLong(2,post_id);
            statement.execute();
        } catch (SQLException ignored) {
        }
    }

    public static void onLike(long user, long post){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select exists(select * from likes where id_of_user=? and id_of_pos=?)");
            statement.setLong(1,user);
            statement.setLong(2,post);
            if(exists(statement)){
               unLike(user,post);
            }
            else {
                toLike(user,post);
            }
        } catch (SQLException ignored) {
        }
    }

    public static void getUsers(List<User> list){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select id, username from users;");
            ResultSet set = statement.executeQuery();
            while (set.next()){
                list.add(
                        User.builder()
                                .id(set.getInt(1))
                                .username(set.getString(2))
                                .build()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void getUsers(List<User> list, String name){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select id, username from users where username like ?;");
            statement.setString(1,"%"+name+"%");
            ResultSet set = statement.executeQuery();
            while (set.next()){
                list.add(
                        User.builder()
                                .id(set.getInt(1))
                                .username(set.getString(2))
                                .build()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteUser(int id){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("delete from users where id=?");
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

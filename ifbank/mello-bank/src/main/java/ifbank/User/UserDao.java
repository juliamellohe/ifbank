package ifbank.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ifbank.config.Database;

public class UserDao {

    public List<User> getUsers() {

        ArrayList<User> users = new ArrayList<User>();

        try {

            Connection con = Database.getConnection();

            String sql = "SELECT * FROM users;";
            ResultSet rs = con.prepareStatement(sql).executeQuery();

            while (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("email"), rs.getString("password"));
                user.setId(rs.getInt("id"));
                LocalDateTime when = rs.getTimestamp("data_cadastro").toLocalDateTime();
                user.setData_cadastro(when);
                users.add(user);
            }

        } catch (Exception error) {
            System.out.println("Erro ao tentar vizualisar os usuários");
            error.printStackTrace();
        }
        return users;
    }

    public void add(User user) {
        
        try {
            
            Connection con = Database.getConnection();

            String sql = "INSERT INTO users(name, email, password) values (?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();

        } catch (Exception error) {
            System.out.println("Erro ao fazer cadastro! ;(");
            error.printStackTrace();
        }
    }

    public User getByEmailAndPass(String email, String password) {
        
        User user = null;
        
        Connection con = Database.getConnection();
        
        try {
          String sql = "SELECT * FROM users where email = ? and password = ?;";
          PreparedStatement ps = con.prepareStatement(sql);
          ps.setString(1, email);
          ps.setString(2, password);
          ResultSet rs = ps.executeQuery();
    
          if (rs.next()) {
            user = new User(rs.getString("name"), rs.getString("email"), rs.getString("password"));
            user.setId(rs.getInt("id"));
            LocalDateTime when = rs.getTimestamp("data_cadastro").toLocalDateTime();
            user.setData_cadastro(when);

          }
          
        } catch (SQLException e) {
          System.out.println("Erro ao buscar o usuário pelo email e senha");
          e.printStackTrace();
        }
    
        return user;
      }

}

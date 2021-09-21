package ifbank.Registro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ifbank.config.Database;

public class RegistroDao {
    
    public List<Registro> listRegistrosById(int user_id) {

        Connection con = Database.getConnection();

        ArrayList<Registro> registros = new ArrayList<Registro>();

        try {
            String sql = "SELECT * FROM registros WHERE user_id = ?;";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user_id); // primeiro executa o ps e dps o rs para que o ? seja preenchido
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Registro registro = new Registro(rs.getInt("user_id"), rs.getString("description"), rs.getFloat("valor"), rs.getString("tipo"));
                registro.setId(rs.getInt("id"));
                LocalDateTime when = rs.getTimestamp("created_at").toLocalDateTime();
                registro.setCreated_at(when);
                registros.add(registro);
            }

        } catch (Exception error) {
            System.out.println("Erro ao tentar vizualisar os registros! ;(");
            error.printStackTrace();
        }
        return registros;

    }

    public Registro getById(int id) {

        Connection con = Database.getConnection();

        Registro registro = null;

        try {
            
            String sql = "SELECT * FROM registros where id = ? ;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            // primeiro executa o ps e dps o rs para que o ? seja preenchido

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                registro = new Registro(rs.getInt("user_id"), rs.getString("description"), rs.getFloat("valor"), rs.getString("tipo"));
                registro.setId(rs.getInt("id"));
                LocalDateTime when = rs.getTimestamp("created_at").toLocalDateTime();
                registro.setCreated_at(when);
            }

        } catch (Exception error) {
            System.out.println("Erro ao tentar buscar registro pelo id");
            error.printStackTrace();
        }
        return registro;

    }

    public void update(Registro registro) {

        try {
            Connection con = Database.getConnection();

            String sql = "UPDATE registros SET description=?, valor=?, tipo=? WHERE id=?;";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, registro.getDescription());
            ps.setFloat(2, registro.getValor());
            ps.setString(3, registro.getTipo());
            ps.setInt(4, registro.getId());
            ps.execute();

        } catch (SQLException error) {
            System.out.println("Erro na tentativa de editar registro! ;(");
            error.printStackTrace();
        }
        System.out.println(registro);
    }

    public void delete(int id) {

        try {
            Connection con = Database.getConnection();

            PreparedStatement prepared = con.prepareStatement("DELETE FROM registros WHERE id = ?;");

            prepared.setInt(1, id);
            prepared.execute();

        } catch (SQLException e) {
            System.out.println("Erro na tentativa de deletar seu registro ;(");
            e.printStackTrace();
        }

    }

    public List<Registro> getByType(int user_id, String filtro) {

        Connection con = Database.getConnection();

        ArrayList<Registro> registros = new ArrayList<Registro>();

        try {
            
            String sql = "select * FROM registros WHERE user_id = ? and tipo LIKE '?%';";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);
            ps.setString(2, filtro);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Registro registro = new Registro(rs.getInt("user_id"), rs.getString("description"), rs.getFloat("valor"), rs.getString("tipo"));
                registro.setId(rs.getInt("id"));
                LocalDateTime when = rs.getTimestamp("created_at").toLocalDateTime();
                registro.setCreated_at(when);
                registros.add(registro);
            }

        } catch (Exception error) {
            System.out.println("Erro ao tentar buscar registro pelo tipo");
            error.printStackTrace();
        }
        return registros;

    }

    public List<Registro> getByDate(int user_id, String date, String date2) {

        Connection con = Database.getConnection();

        ArrayList<Registro> registros = new ArrayList<Registro>();

        try {
            
            String sql = "SELECT * FROM registros WHERE user_id = ? and created_at BETWEEN '?' and '?' ORDER BY created_at ASC;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);
            ps.setString(2, date);
            ps.setString(3, date2);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Registro registro = new Registro(rs.getInt("user_id"), rs.getString("description"), rs.getFloat("valor"), rs.getString("tipo"));
                registro.setId(rs.getInt("id"));
                LocalDateTime when = rs.getTimestamp("created_at").toLocalDateTime();
                registro.setCreated_at(when);
                registros.add(registro);
            }

        } catch (Exception error) {
            System.out.println("Erro ao tentar buscar registros pelas datas");
            error.printStackTrace();
        }
        return registros;

    }
    public void add(Registro registro) {

        Connection con = Database.getConnection();

        try {

            String sql = "INSERT INTO registros(user_id, description, valor, tipo) values (?, ?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, registro.getUser_id());
            ps.setString(2, registro.getDescription());
            ps.setFloat(3, registro.getValor());
            ps.setString(4, registro.getTipo());
            ps.execute();

        } catch (Exception error) {
            System.out.println("Erro ao inserir um registro");
            error.printStackTrace();
        }
    }

}

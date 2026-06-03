package dao;

import model.Curso;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CursoDAO {

    public int buscarId(String nome, String campus, String periodo) {
        String sql = "SELECT id FROM curso WHERE nome = ? AND campus = ? AND periodo = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, campus);
            stmt.setString(3, periodo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return -1;
    }

    public Curso buscarPorId(int id) {
        String sql = "SELECT * FROM curso WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Curso c = new Curso();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setCampus(rs.getString("campus"));
                c.setPeriodo(rs.getString("periodo"));
                return c;
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return null;
    }
}
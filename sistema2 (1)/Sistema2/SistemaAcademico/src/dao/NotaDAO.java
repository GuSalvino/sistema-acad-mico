package dao;

import model.Nota;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class NotaDAO {

	/**
	 * Insere uma nova nota no banco de dados com validações.
	 */
	public boolean inserir(Nota n) {
		// Validações básicas
		if (n.getRgm() == null || n.getRgm().trim().isEmpty()) {
			System.out.println("Erro: RGM é obrigatório!");
			return false;
		}

		// Validar se o aluno existe
		Aluno aluno = new AlunoDAO().buscar(n.getRgm().trim());
		if (aluno == null) {
			System.out.println("Erro: Aluno com RGM " + n.getRgm() + " não encontrado!");
			return false;
		}

		// Validar nota (0 a 10)
		if (n.getNota() < 0 || n.getNota() > 10) {
			System.out.println("Erro: Nota deve estar entre 0 e 10!");
			return false;
		}

		// Validar faltas (>= 0)
		if (n.getFaltas() < 0) {
			System.out.println("Erro: Faltas não podem ser negativas!");
			return false;
		}

		// Validar duplicidade
		if (existeNota(n.getRgm().trim(), n.getDisciplina(), n.getSemestre())) {
			System.out.println("Erro: Já existe uma nota registrada para este aluno nesta disciplina e semestre!");
			return false;
		}

		String sql = "INSERT INTO nota (rgm, disciplina, semestre, nota, faltas) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = Conexao.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, n.getRgm().trim());
			stmt.setString(2, n.getDisciplina());
			stmt.setString(3, n.getSemestre());
			stmt.setDouble(4, n.getNota());
			stmt.setInt(5, n.getFaltas());
			stmt.execute();
			System.out.println("Nota salva com sucesso!");
			return true;
		} catch (Exception e) {
			System.out.println("Erro ao inserir nota: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Verifica se já existe uma nota para o aluno na disciplina e semestre especificados.
	 */
	private boolean existeNota(String rgm, String disciplina, String semestre) {
		String sql = "SELECT COUNT(*) FROM nota WHERE rgm = ? AND disciplina = ? AND semestre = ?";
		try (Connection conn = Conexao.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, rgm);
			stmt.setString(2, disciplina);
			stmt.setString(3, semestre);
			var rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (Exception e) {
			System.out.println("Erro ao verificar duplicidade: " + e.getMessage());
		}
		return false;
	}

	/**
	 * Lista todas as notas de um aluno.
	 */
	public java.util.List<Nota> listarPorAluno(String rgm) {
		java.util.List<Nota> lista = new java.util.ArrayList<>();
		
		if (rgm == null || rgm.trim().isEmpty()) {
			System.out.println("Erro: RGM é obrigatório!");
			return lista;
		}

		String sql = "SELECT * FROM nota WHERE rgm = ? ORDER BY semestre, disciplina";
		try (Connection conn = Conexao.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, rgm.trim());
			var rs = stmt.executeQuery();
			while (rs.next()) {
				Nota n = new Nota();
				n.setRgm(rs.getString("rgm"));
				n.setDisciplina(rs.getString("disciplina"));
				n.setSemestre(rs.getString("semestre"));
				n.setNota(rs.getDouble("nota"));
				n.setFaltas(rs.getInt("faltas"));
				lista.add(n);
			}
		} catch (Exception e) {
			System.out.println("Erro ao listar notas: " + e.getMessage());
		}
		return lista;
	}

	/**
	 * Exclui uma nota do banco de dados.
	 */
	public boolean excluir(String rgm, String disciplina, String semestre) {
		if (rgm == null || rgm.trim().isEmpty()) {
			System.out.println("Erro: RGM é obrigatório!");
			return false;
		}

		String sql = "DELETE FROM nota WHERE rgm = ? AND disciplina = ? AND semestre = ?";
		try (Connection conn = Conexao.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, rgm.trim());
			stmt.setString(2, disciplina);
			stmt.setString(3, semestre);
			int linhasAfetadas = stmt.executeUpdate();
			if (linhasAfetadas > 0) {
				System.out.println("Nota excluída com sucesso!");
				return true;
			} else {
				System.out.println("Erro: Nota não encontrada!");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Erro ao excluir nota: " + e.getMessage());
			return false;
		}
	}
}

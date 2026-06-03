package dao;

import model.Aluno;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AlunoDAO {

	public boolean inserir(Aluno aluno) {
		// Validações de campos obrigatórios
		if (aluno.getRgm() == null || aluno.getRgm().trim().isEmpty()) {
			System.out.println("Erro: RGM é obrigatório!");
			return false;
		}
		if (aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
			System.out.println("Erro: Nome é obrigatório!");
			return false;
		}
		if (aluno.getCpf() == null || aluno.getCpf().trim().isEmpty()) {
			System.out.println("Erro: CPF é obrigatório!");
			return false;
		}
		if (aluno.getEmail() == null || aluno.getEmail().trim().isEmpty()) {
			System.out.println("Erro: Email é obrigatório!");
			return false;
		}

		String sql = "INSERT INTO aluno (rgm, nome, cpf, celular, data_nascimento, email, endereco, municipio, uf) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, aluno.getRgm().trim());
			stmt.setString(2, aluno.getNome().trim());
			stmt.setString(3, aluno.getCpf().trim());
			stmt.setString(4, aluno.getCelular() != null ? aluno.getCelular().trim() : "");
			stmt.setString(5, aluno.getDataNascimento() != null ? aluno.getDataNascimento().trim() : "");
			stmt.setString(6, aluno.getEmail().trim());
			stmt.setString(7, aluno.getEndereco() != null ? aluno.getEndereco().trim() : "");
			stmt.setString(8, aluno.getMunicipio() != null ? aluno.getMunicipio().trim() : "");
			stmt.setString(9, aluno.getUf() != null ? aluno.getUf().trim() : "");
			stmt.execute();
			System.out.println("Aluno cadastrado com sucesso!");
			return true;
		} catch (Exception e) {
			System.out.println("Erro ao inserir aluno: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Exclui um aluno do banco de dados.

	 */
	public boolean excluir(String rgm) {
		if (rgm == null || rgm.trim().isEmpty()) {
			System.out.println("Erro: RGM é obrigatório!");
			return false;
		}

		String sql = "DELETE FROM aluno WHERE rgm = ?";
		try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, rgm.trim());
			int linhasAfetadas = stmt.executeUpdate();
			if (linhasAfetadas > 0) {
				System.out.println("Aluno excluído com sucesso!");
				return true;
			} else {
				System.out.println("Erro: Aluno não encontrado!");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Erro ao excluir aluno: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Busca um aluno pelo RGM.
	 */
	public Aluno buscar(String rgm) {
		if (rgm == null || rgm.trim().isEmpty()) {
			System.out.println("Erro: RGM é obrigatório!");
			return null;
		}

		String sql = "SELECT a.*, c.nome AS nome_curso FROM aluno a " +
		             "LEFT JOIN curso c ON a.curso_id = c.id WHERE a.rgm = ?";
		try (Connection conn = Conexao.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, rgm.trim());
			var rs = stmt.executeQuery();
			if (rs.next()) {
				Aluno a = new Aluno();
				a.setRgm(rs.getString("rgm"));
				a.setNome(rs.getString("nome"));
				a.setCpf(rs.getString("cpf"));
				a.setCelular(rs.getString("celular"));
				a.setDataNascimento(rs.getString("data_nascimento"));
				a.setEmail(rs.getString("email"));
				a.setEndereco(rs.getString("endereco"));
				a.setMunicipio(rs.getString("municipio"));
				a.setUf(rs.getString("uf"));
				a.setNomeCurso(rs.getString("nome_curso"));
				return a;
			}
		} catch (Exception e) {
			System.out.println("Erro ao buscar aluno: " + e.getMessage());
		}
		return null;
	}

	/**
	 * Atualiza o curso de um aluno.
	 * Valida se o aluno existe e se o curso existe antes de atualizar.
	 */
	public boolean atualizarCurso(String rgm, String nomeCurso, String campus, String periodo) {
		// Validar se o aluno existe
		if (buscar(rgm) == null) {
			System.out.println("Erro: Aluno não encontrado!");
			return false;
		}

		// Validar se o curso existe
		int cursoId = new CursoDAO().buscarId(nomeCurso, campus, periodo);
		if (cursoId == -1) {
			System.out.println("Erro: Curso não encontrado no banco!");
			return false;
		}

		String sql = "UPDATE aluno SET curso_id = ? WHERE rgm = ?";
		try (Connection conn = Conexao.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, cursoId);
			stmt.setString(2, rgm.trim());
			int linhasAfetadas = stmt.executeUpdate();
			if (linhasAfetadas > 0) {
				System.out.println("Curso atualizado com sucesso!");
				return true;
			} else {
				System.out.println("Erro: Falha ao atualizar curso!");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Erro ao atualizar curso: " + e.getMessage());
			return false;
		}
	}
}

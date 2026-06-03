package model;

public class Aluno {

	private String rgm;
	private String nome;
	private String cpf;
	private String celular;
	private String dataNascimento;
	private String email;
	private String endereco;
	private String municipio;
	private String uf;
	private int cursoId;
	private String nomeCurso;

	public int getCursoId() {
		return cursoId;
	}

	public void setCursoId(int cursoId) {
		this.cursoId = cursoId;
	}

	// GETTERS E SETTERS
	public String getRgm() {
		return rgm;
	}

	public void setRgm(String rgm) {
		this.rgm = rgm;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public String getNomeCurso() {
	    return nomeCurso;
	}

	public void setNomeCurso(String nomeCurso) {
	    this.nomeCurso = nomeCurso;
	}
	
	
}
package model;

public class Nota {
	private String rgm;
	private String disciplina;
	private String semestre;
	private double nota;
	private int faltas;

	public Nota() {
	}
	
	public Nota(String rgm, String disciplina, String semestre, double nota, int faltas) {
		super();
		this.rgm = rgm;
		this.disciplina = disciplina;
		this.semestre = semestre;
		this.nota = nota;
		this.faltas = faltas;
	}

	// getters/setters
	public String getRgm() {
		return rgm;
	}

	public void setRgm(String rgm) {
		this.rgm = rgm;
	}

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public int getFaltas() {
		return faltas;
	}

	public void setFaltas(int faltas) {
		this.faltas = faltas;
	}

}
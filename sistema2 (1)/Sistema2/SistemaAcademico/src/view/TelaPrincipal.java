package view;

import dao.AlunoDAO;
import dao.NotaDAO;
import model.Aluno;
import model.Nota;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class TelaPrincipal extends JFrame {

	private JTextField txtRgm, txtNome, txtEmail;
	private JFormattedTextField txtCpf, txtCelular, txtData;
	private JButton btnSalvar, btnBuscar, btnExcluir, btnLimpar;
	private JComboBox<String> cmbUf;

	public TelaPrincipal() {

		setTitle("Sistema Acadêmico");
		setSize(700, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JTabbedPane abas = new JTabbedPane();

		// ================= DADOS PESSOAIS =================
		JPanel dados = new JPanel();
		dados.setLayout(null);

		try {
			MaskFormatter fmtCpf = new MaskFormatter("###.###.###-##");
			fmtCpf.setPlaceholderCharacter('_');
			fmtCpf.setValueContainsLiteralCharacters(false);
			txtCpf = new JFormattedTextField(fmtCpf);

			MaskFormatter fmtCelular = new MaskFormatter("(##)#####-####");
			fmtCelular.setPlaceholderCharacter('_');
			txtCelular = new JFormattedTextField(fmtCelular);

			MaskFormatter fmtData = new MaskFormatter("##/##/####");
			fmtData.setPlaceholderCharacter('_');
			txtData = new JFormattedTextField(fmtData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		txtRgm = new JTextField();
		txtNome = new JTextField();
		txtEmail = new JTextField();
		JTextField txtEndereco = new JTextField();
		JTextField txtMunicipio = new JTextField();

		String[] ufs = { "SP", "RJ", "MG", "RS", "BA", "PR", "PE", "CE", "PA", "MA", "AM", "SC", "GO", "ES", "PB", "RN",
				"AL", "PI", "MS", "MT", "DF", "SE", "RO", "TO", "AC", "AP", "RR" };
		cmbUf = new JComboBox<>(ufs);

		txtRgm.setBounds(120, 20, 120, 25);
		txtNome.setBounds(310, 20, 200, 25);
		txtData.setBounds(120, 55, 120, 25);
		txtCpf.setBounds(310, 55, 150, 25);
		txtEmail.setBounds(120, 90, 200, 25);
		txtEndereco.setBounds(120, 125, 390, 25);
		txtMunicipio.setBounds(120, 160, 180, 25);
		cmbUf.setBounds(320, 160, 70, 25);
		txtCelular.setBounds(450, 160, 120, 25);

		dados.add(new JLabel("RGM")).setBounds(20, 20, 90, 25);
		dados.add(txtRgm);
		dados.add(new JLabel("Nome")).setBounds(250, 20, 50, 25);
		dados.add(txtNome);

		dados.add(new JLabel("Nascimento")).setBounds(20, 55, 90, 25);
		dados.add(txtData);
		dados.add(new JLabel("CPF")).setBounds(250, 55, 50, 25);
		dados.add(txtCpf);

		dados.add(new JLabel("Email")).setBounds(20, 90, 90, 25);
		dados.add(txtEmail);

		dados.add(new JLabel("End.")).setBounds(20, 125, 90, 25);
		dados.add(txtEndereco);

		dados.add(new JLabel("Município")).setBounds(20, 160, 90, 25);
		dados.add(txtMunicipio);
		dados.add(new JLabel("UF")).setBounds(300, 160, 20, 25);
		dados.add(cmbUf);
		dados.add(new JLabel("Celular")).setBounds(400, 160, 55, 25);
		dados.add(txtCelular);

		btnSalvar = new JButton("Salvar");
		btnBuscar = new JButton("Buscar");
		btnLimpar = new JButton("Limpar");
		btnExcluir = new JButton("Excluir");
		btnSalvar.setBounds(80, 210, 100, 30);
		btnBuscar.setBounds(190, 210, 100, 30);
		btnLimpar.setBounds(300, 210, 100, 30);
		btnExcluir.setBounds(410, 210, 100, 30);

		dados.add(btnSalvar);
		dados.add(btnBuscar);
		dados.add(btnLimpar);
		dados.add(btnExcluir);

		// EVENTOS DADOS PESSOAIS
		btnSalvar.addActionListener(e -> {
			// Validações de campos obrigatórios
			if (txtRgm.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Erro: RGM é obrigatório!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (txtNome.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Erro: Nome é obrigatório!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (txtCpf.getText().trim().isEmpty() || txtCpf.getText().trim().equals("___.___.___-__")) {
				JOptionPane.showMessageDialog(null, "Erro: CPF é obrigatório!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (txtEmail.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Erro: Email é obrigatório!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
				return;
			}

			Aluno a = new Aluno();
			a.setRgm(txtRgm.getText());
			a.setNome(txtNome.getText());
			a.setCpf(txtCpf.getText());
			a.setCelular(txtCelular.getText());
			a.setDataNascimento(txtData.getText());
			a.setEmail(txtEmail.getText());
			a.setEndereco(txtEndereco.getText());
			a.setMunicipio(txtMunicipio.getText());
			a.setUf((String) cmbUf.getSelectedItem());
			
			// Só exibe mensagem de sucesso se a inserção foi bem-sucedida
			if (new AlunoDAO().inserir(a)) {
				JOptionPane.showMessageDialog(null, "Aluno salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
				btnLimpar.doClick(); // Limpar campos após sucesso
			} else {
				JOptionPane.showMessageDialog(null, "Erro ao salvar aluno. Verifique os dados e tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnBuscar.addActionListener(e -> {
			if (txtRgm.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Erro: Digite um RGM para buscar!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Aluno a = new AlunoDAO().buscar(txtRgm.getText().trim());
			if (a != null) {
				txtNome.setText(a.getNome() != null ? a.getNome() : "");
				txtCpf.setText(a.getCpf() != null ? a.getCpf() : "");
				txtCelular.setText(a.getCelular() != null ? a.getCelular() : "");
				txtData.setText(a.getDataNascimento() != null ? a.getDataNascimento() : "");
				txtEmail.setText(a.getEmail() != null ? a.getEmail() : "");
				txtEndereco.setText(a.getEndereco() != null ? a.getEndereco() : "");
				txtMunicipio.setText(a.getMunicipio() != null ? a.getMunicipio() : "");
				cmbUf.setSelectedItem(a.getUf() != null ? a.getUf() : "SP");
			} else {
				JOptionPane.showMessageDialog(null, "Aluno não encontrado!", "Aviso", JOptionPane.WARNING_MESSAGE);
			}
		});

		btnLimpar.addActionListener(ev -> {
			txtRgm.setText("");
			txtNome.setText("");
			txtEmail.setText("");
			txtEndereco.setText("");
			txtMunicipio.setText("");
			txtCpf.setValue(null);
			txtCelular.setValue(null);
			txtData.setValue(null);
			cmbUf.setSelectedIndex(0);
		});

		btnExcluir.addActionListener(e -> {
			if (txtRgm.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Erro: RGM é obrigatório!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int confirm = JOptionPane.showConfirmDialog(null,
					"Tem certeza que deseja excluir o aluno " + txtRgm.getText() + "?", "Confirmar exclusão",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				if (new AlunoDAO().excluir(txtRgm.getText())) {
					JOptionPane.showMessageDialog(null, "Aluno excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					btnLimpar.doClick();
				} else {
					JOptionPane.showMessageDialog(null, "Erro ao excluir aluno!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// ================= CURSO =================
		JPanel curso = new JPanel();
		curso.setLayout(null);

		String[] cursos = { "Análise e Desenvolvimento de Sistemas", "Ciência da Computação", "Engenharia de Software",
				"Sistemas de Informação" };
		String[] campi = { "Tatuapé", "Centro", "Santo André", "Campinas" };

		JComboBox<String> cmbCurso = new JComboBox<>(cursos);
		JComboBox<String> cmbCampus = new JComboBox<>(campi);

		JRadioButton rdMatutino = new JRadioButton("Matutino");
		JRadioButton rdVespertino = new JRadioButton("Vespertino");
		JRadioButton rdNoturno = new JRadioButton("Noturno");
		rdNoturno.setSelected(true);

		ButtonGroup grupoPeriodo = new ButtonGroup();
		grupoPeriodo.add(rdMatutino);
		grupoPeriodo.add(rdVespertino);
		grupoPeriodo.add(rdNoturno);

		cmbCurso.setBounds(120, 20, 300, 25);
		cmbCampus.setBounds(120, 55, 200, 25);
		rdMatutino.setBounds(120, 90, 100, 25);
		rdVespertino.setBounds(230, 90, 110, 25);
		rdNoturno.setBounds(350, 90, 100, 25);

		JTextField txtRgmCurso = new JTextField();
		txtRgmCurso.setBounds(120, 130, 120, 25);
		curso.add(new JLabel("RGM")).setBounds(20, 130, 90, 25);
		curso.add(txtRgmCurso);

		JButton btnSalvarCurso = new JButton("Salvar");
		btnSalvarCurso.setBounds(120, 170, 100, 30);
		curso.add(btnSalvarCurso);

		btnSalvarCurso.addActionListener(e -> {
			if (txtRgmCurso.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Erro: RGM é obrigatório!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String periodo = rdMatutino.isSelected() ? "Matutino"
					: rdVespertino.isSelected() ? "Vespertino" : "Noturno";
			
			// Só exibe mensagem de sucesso se a atualização foi bem-sucedida
			if (new AlunoDAO().atualizarCurso(txtRgmCurso.getText().trim(), (String) cmbCurso.getSelectedItem(),
					(String) cmbCampus.getSelectedItem(), periodo)) {
				JOptionPane.showMessageDialog(null, "Curso salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
				txtRgmCurso.setText("");
			} else {
				JOptionPane.showMessageDialog(null, "Erro ao salvar curso. Verifique se o aluno e o curso existem.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});

		curso.add(new JLabel("Curso")).setBounds(20, 20, 90, 25);
		curso.add(cmbCurso);
		curso.add(new JLabel("Campus")).setBounds(20, 55, 90, 25);
		curso.add(cmbCampus);
		curso.add(new JLabel("Período")).setBounds(20, 90, 90, 25);
		curso.add(rdMatutino);
		curso.add(rdVespertino);
		curso.add(rdNoturno);

		// ================= NOTAS =================
		JPanel notas = new JPanel();
		notas.setLayout(null);

		JTextField txtRgmNota = new JTextField();
		JLabel lblNomeAluno = new JLabel("");
		lblNomeAluno.setForeground(java.awt.Color.GRAY);

		String[] disciplinas = { "Programação Orientada a Objetos", "Banco de Dados", "Engenharia de Software",
				"Estrutura de Dados", "Redes de Computadores" };
		String[] semestres = { "2024-1", "2024-2", "2025-1", "2025-2" };

		JComboBox<String> cmbDisciplina = new JComboBox<>(disciplinas);
		JComboBox<String> cmbSemestre = new JComboBox<>(semestres);
		JTextField txtNota = new JTextField();
		JTextField txtFaltas = new JTextField();

		txtRgmNota.setBounds(120, 20, 120, 25);
		lblNomeAluno.setBounds(260, 20, 250, 25);
		cmbDisciplina.setBounds(120, 55, 280, 25);
		cmbSemestre.setBounds(120, 90, 120, 25);
		txtNota.setBounds(280, 90, 80, 25);
		txtFaltas.setBounds(430, 90, 80, 25);

		notas.add(new JLabel("RGM")).setBounds(20, 20, 90, 25);
		notas.add(txtRgmNota);
		notas.add(lblNomeAluno);

		notas.add(new JLabel("Disciplina")).setBounds(20, 55, 90, 25);
		notas.add(cmbDisciplina);

		notas.add(new JLabel("Semestre")).setBounds(20, 90, 90, 25);
		notas.add(cmbSemestre);
		notas.add(new JLabel("Nota")).setBounds(250, 90, 40, 25);
		notas.add(txtNota);
		notas.add(new JLabel("Faltas")).setBounds(390, 90, 50, 25);
		notas.add(txtFaltas);

		JButton btnSalvarNota = new JButton(UIManager.getIcon("FileView.floppyDriveIcon"));
		JButton btnExcluirNota = new JButton(UIManager.getIcon("FileChooser.detailsViewIcon"));
		JButton btnConsultarNota = new JButton(UIManager.getIcon("FileChooser.homeFolderIcon"));

		btnSalvarNota.setToolTipText("Salvar nota");
		btnExcluirNota.setToolTipText("Excluir nota");
		btnConsultarNota.setToolTipText("Consultar notas");

		btnSalvarNota.setBounds(120, 135, 50, 40);
		btnExcluirNota.setBounds(180, 135, 50, 40);
		btnConsultarNota.setBounds(240, 135, 50, 40);

		notas.add(btnSalvarNota);
		notas.add(btnExcluirNota);
		notas.add(btnConsultarNota);

		txtRgmNota.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent e) {
				if (!txtRgmNota.getText().trim().isEmpty()) {
					Aluno a = new AlunoDAO().buscar(txtRgmNota.getText().trim());
					if (a != null) {
						lblNomeAluno.setText(a.getNome());
						lblNomeAluno.setForeground(java.awt.Color.BLACK);
					} else {
						lblNomeAluno.setText("Aluno não encontrado");
						lblNomeAluno.setForeground(java.awt.Color.RED);
					}
				} else {
					lblNomeAluno.setText("");
					lblNomeAluno.setForeground(java.awt.Color.GRAY);
				}
			}
		});

		btnSalvarNota.addActionListener(e -> {
			// Validações
			if (txtRgmNota.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Erro: RGM é obrigatório!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
				return;
			}

			int faltas;
			try {
				faltas = Integer.parseInt(txtFaltas.getText().trim());
				if (faltas < 0) {
					JOptionPane.showMessageDialog(null, "Erro: Faltas não podem ser negativas!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Erro: Faltas deve ser um número inteiro!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
				return;
			}

			double nota;
			try {
				nota = Double.parseDouble(txtNota.getText().trim());
				if (nota < 0 || nota > 10) {
					JOptionPane.showMessageDialog(null, "Erro: Nota deve estar entre 0 e 10!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Erro: Nota deve ser um número entre 0 e 10!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
				return;
			}

			Nota n = new Nota();
			n.setRgm(txtRgmNota.getText().trim());
			n.setDisciplina((String) cmbDisciplina.getSelectedItem());
			n.setSemestre((String) cmbSemestre.getSelectedItem());
			n.setNota(nota);
			n.setFaltas(faltas);
			
			// Só exibe mensagem de sucesso se a inserção foi bem-sucedida
			if (new NotaDAO().inserir(n)) {
				JOptionPane.showMessageDialog(null, "Nota salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
				txtNota.setText("");
				txtFaltas.setText("");
			} else {
				JOptionPane.showMessageDialog(null, "Erro ao salvar nota. Verifique os dados e tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnExcluirNota.addActionListener(e -> {
			String rgm = JOptionPane.showInputDialog(null, "Digite o RGM do aluno:");
			if (rgm == null || rgm.trim().isEmpty())
				return;

			java.util.List<Nota> lista = new NotaDAO().listarPorAluno(rgm.trim());
			if (lista.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Nenhuma nota encontrada para esse aluno!", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}

			String[] opcoes = lista.stream().map(n -> n.getDisciplina() + " - " + n.getSemestre())
					.toArray(String[]::new);

			String escolha = (String) JOptionPane.showInputDialog(null, "Selecione a nota para excluir:",
					"Excluir Nota", JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

			if (escolha == null)
				return;

			int confirm = JOptionPane.showConfirmDialog(null, "Excluir nota de " + escolha + "?", "Confirmar",
					JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				String[] partes = escolha.split(" - ");
				if (new NotaDAO().excluir(rgm.trim(), partes[0], partes[1])) {
					JOptionPane.showMessageDialog(null, "Nota excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Erro ao excluir nota!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnConsultarNota.addActionListener(e -> {
			String rgm = JOptionPane.showInputDialog(null, "Digite o RGM do aluno:");
			if (rgm != null && !rgm.trim().isEmpty()) {
				java.util.List<Nota> lista = new NotaDAO().listarPorAluno(rgm.trim());
				if (lista.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Nenhuma nota encontrada!", "Aviso", JOptionPane.WARNING_MESSAGE);
				} else {
					StringBuilder sb = new StringBuilder();
					sb.append(String.format("%-30s %-10s %-6s %-6s%n", "Disciplina", "Semestre", "Nota", "Faltas"));
					sb.append("-".repeat(55)).append("\n");
					for (Nota n : lista) {
						sb.append(String.format("%-30s %-10s %-6.1f %-6d%n", n.getDisciplina(), n.getSemestre(),
								n.getNota(), n.getFaltas()));
					}
					JOptionPane.showMessageDialog(null, sb.toString(), "Notas do aluno " + rgm,
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		// ================= BOLETIM =================
		JPanel boletim = new JPanel();
		boletim.setLayout(null);

		JTextField txtRgmBoletim = new JTextField();
		JLabel lblNomeBoletim = new JLabel("");
		JLabel lblCursoBoletim = new JLabel("");

		JButton btnBuscarBoletim = new JButton("Buscar");

		txtRgmBoletim.setBounds(120, 20, 120, 25);
		lblNomeBoletim.setBounds(120, 50, 300, 25);
		lblCursoBoletim.setBounds(120, 75, 300, 25);
		btnBuscarBoletim.setBounds(250, 20, 80, 25);

		boletim.add(new JLabel("RGM")).setBounds(20, 20, 90, 25);
		boletim.add(txtRgmBoletim);
		boletim.add(btnBuscarBoletim);
		boletim.add(new JLabel("Nome:")).setBounds(20, 50, 90, 25);
		boletim.add(lblNomeBoletim);
		boletim.add(new JLabel("Curso:")).setBounds(20, 75, 90, 25);
		boletim.add(lblCursoBoletim);

		String[] colunas = { "Disciplina", "Semestre", "Nota", "Faltas" };
		javax.swing.table.DefaultTableModel modeloTabela = new javax.swing.table.DefaultTableModel(colunas, 0);
		JTable tabela = new JTable(modeloTabela);
		JScrollPane scroll = new JScrollPane(tabela);
		scroll.setBounds(20, 110, 520, 150);
		boletim.add(scroll);

		btnBuscarBoletim.addActionListener(e -> {
			if (txtRgmBoletim.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Erro: Digite um RGM para buscar!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Aluno a = new AlunoDAO().buscar(txtRgmBoletim.getText().trim());
			if (a != null) {
				lblNomeBoletim.setText(a.getNome());
				// Deixar vazio se não houver curso, em vez de exibir "Sem curso"
				lblCursoBoletim.setText(a.getNomeCurso() != null ? a.getNomeCurso() : "");
				modeloTabela.setRowCount(0);
				java.util.List<Nota> notas2 = new NotaDAO().listarPorAluno(txtRgmBoletim.getText().trim());
				for (Nota n : notas2) {
					modeloTabela
							.addRow(new Object[] { n.getDisciplina(), n.getSemestre(), n.getNota(), n.getFaltas() });
				}
			} else {
				JOptionPane.showMessageDialog(null, "Aluno não encontrado!", "Aviso", JOptionPane.WARNING_MESSAGE);
				lblNomeBoletim.setText("");
				lblCursoBoletim.setText("");
				modeloTabela.setRowCount(0);
			}
		});

		// ================= MENU BAR =================
		JMenuBar menuBar = new JMenuBar();

		JMenu menuAluno = new JMenu("Aluno");
		JMenuItem itemSalvar = new JMenuItem("Salvar");
		JMenuItem itemAlterar = new JMenuItem("Alterar");
		JMenuItem itemConsultar = new JMenuItem("Consultar");
		JMenuItem itemExcluir = new JMenuItem("Excluir");
		JMenuItem itemSair = new JMenuItem("Sair");

		itemSalvar.setAccelerator(javax.swing.KeyStroke.getKeyStroke("ctrl S"));
		itemSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke("shift F4"));

		menuAluno.add(itemSalvar);
		menuAluno.add(itemAlterar);
		menuAluno.add(itemConsultar);
		menuAluno.add(itemExcluir);
		menuAluno.addSeparator();
		menuAluno.add(itemSair);

		JMenu menuNotas = new JMenu("Notas e Faltas");
		JMenuItem itemSalvarNota = new JMenuItem("Salvar");
		JMenuItem itemAlterarNota = new JMenuItem("Alterar");
		JMenuItem itemExcluirNota = new JMenuItem("Excluir");
		JMenuItem itemConsultarNota = new JMenuItem("Consultar");

		menuNotas.add(itemSalvarNota);
		menuNotas.add(itemAlterarNota);
		menuNotas.add(itemExcluirNota);
		menuNotas.add(itemConsultarNota);

		JMenu menuAjuda = new JMenu("Ajuda");
		JMenuItem itemSobre = new JMenuItem("Sobre");
		menuAjuda.add(itemSobre);

		itemSalvar.addActionListener(e -> btnSalvar.doClick());
		itemExcluir.addActionListener(e -> btnExcluir.doClick());
		itemConsultar.addActionListener(e -> btnBuscar.doClick());
		itemSair.addActionListener(e -> System.exit(0));

		itemSobre.addActionListener(e -> JOptionPane.showMessageDialog(null,
				"Sistema Acadêmico\nDesenvolvido para POO - Quarta-Manhã", "Sobre", JOptionPane.INFORMATION_MESSAGE));

		itemConsultarNota.addActionListener(e -> {
			String rgm = JOptionPane.showInputDialog(null, "Digite o RGM do aluno:");
			if (rgm != null && !rgm.trim().isEmpty()) {
				java.util.List<Nota> lista = new NotaDAO().listarPorAluno(rgm.trim());
				if (lista.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Nenhuma nota encontrada!", "Aviso", JOptionPane.WARNING_MESSAGE);
				} else {
					StringBuilder sb = new StringBuilder();
					sb.append(String.format("%-30s %-10s %-6s %-6s%n", "Disciplina", "Semestre", "Nota", "Faltas"));
					sb.append("-".repeat(55)).append("\n");
					for (Nota n : lista) {
						sb.append(String.format("%-30s %-10s %-6.1f %-6d%n", n.getDisciplina(), n.getSemestre(),
								n.getNota(), n.getFaltas()));
					}
					JOptionPane.showMessageDialog(null, sb.toString(), "Notas do aluno " + rgm,
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		itemExcluirNota.addActionListener(e -> {
			String rgm = JOptionPane.showInputDialog(null, "Digite o RGM do aluno:");
			if (rgm == null || rgm.trim().isEmpty())
				return;
			String disciplina = JOptionPane.showInputDialog(null, "Digite a disciplina:");
			if (disciplina == null || disciplina.trim().isEmpty())
				return;
			String semestre = JOptionPane.showInputDialog(null, "Digite o semestre (ex: 2025-1):");
			if (semestre == null || semestre.trim().isEmpty())
				return;
			int confirm = JOptionPane.showConfirmDialog(null, "Excluir nota de " + disciplina + " - " + semestre + "?",
					"Confirmar", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				if (new NotaDAO().excluir(rgm.trim(), disciplina.trim(), semestre.trim())) {
					JOptionPane.showMessageDialog(null, "Nota excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Erro ao excluir nota!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		menuBar.add(menuAluno);
		menuBar.add(menuNotas);
		menuBar.add(menuAjuda);

		setJMenuBar(menuBar);

		abas.add("Dados Pessoais", dados);
		abas.add("Curso", curso);
		abas.add("Notas e Faltas", notas);
		abas.add("Boletim", boletim);

		add(abas);

	} // fecha o construtor TelaPrincipal()

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
	}
}

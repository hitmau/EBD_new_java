
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class Aluno {

	private int codaluno = 0;
	private String nome;
	private String ddtnasc;
	private String telefone;
	private String email;
	private String sexo;
	private String codclasse;
	private String codclassesugestao;
	private String obs;

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	private boolean professor;
	private boolean inativo;
	private boolean especial;
	private boolean batismo;
	
	public boolean isBatismo() {
		return batismo;
	}

	public void setBatismo(boolean batismo) {
		this.batismo = batismo;
	}

	public boolean isInativo() {
		return inativo;
	}

	public void setInativo(boolean inativo) {
		this.inativo = inativo;
	}

	public boolean isEspecial() {
		return especial;
	}

	public void setEspecial(boolean especial) {
		this.especial = especial;
	}

	private static Connection con = Conexao.getConexao();

	public int getCodaluno() {
		return codaluno;
	}

	public void setCodaluno(int codaluno) {
		this.codaluno = codaluno;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDdtnasc() {
		return ddtnasc;
	}

	public void setDdtnasc(String ddtnasc) {
		this.ddtnasc = ddtnasc;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getCodclasse() {
		return codclasse;
	}

	public void setCodclasse(String codclasse) {
		this.codclasse = codclasse;
	}

	public String getCodclassesugestao() {
		return codclassesugestao;
	}

	public void setCodclassesugestao(String codclassesugestao) {
		this.codclassesugestao = codclassesugestao;
	}

	public boolean getProfessor() {
		return professor;
	}

	public void setProfessor(boolean professor) {
		this.professor = professor;
	}

	public static String selectNomeAluno(String nome) throws SQLException {
		nome = Consulta.select(nome, "aluno", "nome", "nome");
		return nome;
	}


	
	public static Aluno getLista(int id) throws SQLException {
		// System.out.println(id);

		PreparedStatement stmt = con.prepareStatement("SELECT * FROM aluno WHERE CODALUNO = ?");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		Aluno aluno = new Aluno();
		while (rs.next()) {
			aluno.setNome(rs.getString("nome"));
			aluno.setDdtnasc(rs.getString("dtnasc"));
			aluno.setTelefone(rs.getString("telefone"));
			aluno.setSexo(rs.getString("sexo"));
			aluno.setCodclasse(rs.getString("codclasse"));
			aluno.setCodclassesugestao(rs.getString("codclassesugestao"));
			aluno.setProfessor(rs.getBoolean("professor"));
			aluno.setEspecial(rs.getBoolean("especial"));
			aluno.setBatismo(rs.getBoolean("batismo"));
			aluno.setEmail(rs.getString("email"));
			aluno.setInativo(rs.getBoolean("inativo"));
			aluno.setObs(rs.getString("obs"));
		}
		rs.close();
		stmt.close();
		return aluno;
	}

	public static void insertAluno(String nome, String dtnasc, String telefone, String sexo, int codclasse,
			int codclassesugestao, String professor, String alunoespecial, String batismo, String email, String inativo,
			String obs) throws SQLException, ParseException {
		// Connection con = Conexao.getConexao();
		try {

			String query = " INSERT INTO aluno (CODALUNO, NOME, DTNASC, TELEFONE, SEXO, CODCLASSE, CODCLASSESUGESTAO, PROFESSOR, ESPECIAL, BATISMO, EMAIL, INATIVO, OBS)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, Consulta.pk("aluno", "codaluno"));
			preparedStmt.setString(2, nome);
			preparedStmt.setString(3, Consulta.ConverteData(dtnasc));
			preparedStmt.setString(4, telefone);
			preparedStmt.setString(5, sexo);
			preparedStmt.setInt(6, codclasse);
			preparedStmt.setInt(7, codclassesugestao);
			preparedStmt.setString(8, professor);
			preparedStmt.setString(9, alunoespecial);
			preparedStmt.setString(10, batismo);
			preparedStmt.setString(11, email);
			preparedStmt.setString(12, inativo);
			preparedStmt.setString(13, obs);

			// execute the preparedstatement
			preparedStmt.execute();
			System.out.println("Novo aluno " + nome + " inserido!");
			Conexao.closeConexao();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	public static void updateAluno(String nome, String dtnasc, String telefone, String sexo, String codclasse,
			String codclassesugestao, String professor, String especial, String batismo, String email, String inativo,
			String obs, String codaluno) {
		Connection con = Conexao.getConexao();

		String sql = "update aluno set nome = ?, dtnasc = ?, telefone = ?, sexo = ?, codclasse = ?, codclassesugestao = ?, professor = ?, especial = ?, batismo = ?, email = ?, inativo = ?, obs = ? where codaluno = ?";
		try {

			PreparedStatement stmt = con.prepareStatement(sql);

			if (nome == null) {
				nome = Consulta.select(codaluno, "aluno", "nome", "nome");
			}
			stmt.setString(1, nome);
			if (dtnasc == null) {
				dtnasc = Consulta.select(codaluno, "aluno", "dtnasc", "dtnasc");
			}
			stmt.setString(2, Consulta.ConverteData(dtnasc));
			if (telefone == null) {
				telefone = Consulta.select(codaluno, "aluno", "telefone", "telefone");
			}
			stmt.setString(3, telefone);
			if (sexo == null) {
				sexo = Consulta.select(codaluno, "aluno", "sexo", "sexo");
			}
			stmt.setString(4, sexo);
			if (codclasse == null) {
				codclasse = Consulta.select(codaluno, "aluno", "codclasse", "codclasse");
			}
			stmt.setString(5, codclasse);
			if (codclassesugestao == null) {
				codclassesugestao = Consulta.select(codaluno, "aluno", "codclassesugestao", "codclassesugestao");
			}
			stmt.setString(6, codclassesugestao);
			if (professor == null) {
				professor = Consulta.select(codaluno, "aluno", "professor", "professor");
				if (professor == null) {
					professor = "N";
				}
			}
			stmt.setString(7, professor);
			if (especial == null) {
				especial = Consulta.select(codaluno, "aluno", "especial", "especial");
				if (especial == null) {
					especial = "N";
				}
			}
			stmt.setString(8, especial);
			if (batismo == null) {
				batismo = Consulta.select(codaluno, "aluno", "batismo", "batismo");
				if (batismo == null) {
					batismo = "N";
				}
			}
			stmt.setString(9, batismo);
			if (email == null) {
				email = Consulta.select(codaluno, "aluno", "email", "email");
			}
			stmt.setString(10, email);

			if (inativo == null) {
				inativo = Consulta.select(codaluno, "aluno", "inativo", "inativo");
				if (inativo == null) {
					inativo = "N";
				}
			}
			stmt.setString(11, inativo);
			if (obs == null) {
				obs = Consulta.select(codaluno, "aluno", "obs", "obs");
			}
			stmt.setString(12, obs);
			stmt.setString(13, codaluno);
			// excutar
			stmt.execute();
			// fechar a conexao
			// Conexao.closeConexao();
			System.out.println("Update realizado!");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws SQLException, ParseException {
		// buscaAluno("Mauricio");
		// System.out.println(selectAluno("teste"));
		// System.out.println(Consulta.pk("classes", "codclasses"));
		// PK da tabela
		// System.out.println(Consulta.pk("aluno", "codaluno"));
		// insere
		// Aluno.insertAluno("Jos� Barreto","28/05/2018", "21 980734617", "M", 1, 1,
		// "N", "N", "S", "jose@totalcontrol.com.br", "N","obs");
		// Aluno.insertAluno("Mauricio testes","28/03/1987", "21 980734617", "M", 4, 4,
		// "N", "N", "S", "mauricio@totalcontrol.com.br", "N","kjasdf lkajsd f�laksjdlf
		// kasdklfjas�ldfk asdl fj kasjd flasd kfla sjdf");
		// System.out.println(selectAluno("teste"));
		// System.out.println(selectAluno("Ma"));
		// System.out.println(Consulta.select("Mauricio Rodrigues", "aluno", "nome",
		// "codaluno"));
		// System.out.println(Consulta.select("441", "aluno", "codaluno", "nome"));
		// Aluno.insertAluno("Bruno Xavier", "24/01/1987", "21934875478", "M", 3, 3,
		// "S", "N", "S", "bruno@gmail.com", "N", "sem obs");
		// updateAluno("597", "Mauricinho Rodrigues", "26/02/2014", null, null, "2",
		// "2", "S", null, null, "S", "S", "agora tem obs");
		// updateAluno("Maumau", "28/03/1987", "21980317641", "M", "2", "1", "n", "n",
		// "n", "n", "n", "n", "441");
		// System.out.println(Consulta.select("441", "aluno", "codaluno", "nome"));
		// System.out.println(Consulta.select("597", "aluno", "codaluno", "obs"));

		// List<Aluno> teste = getLista(1);
		// List<Aluno> teste = getLista(1);
		// Aluno a1 = new Aluno();
		// System.out.println(getLista(441).toString());
		Aluno a = getLista(1);
		System.out.println(a.getNome());
		// System.out.println(teste.);

	}

	// private transient JornadaVO jornadaVO = null;
	// private transient MensagemVO mensagemVO = null;
	// public int getCodigoJornada() {
	// if (jornadaVO == null) {
	// return codigoJornada;
	// } else {
	// return jornadaVO.getCodigo();
	// }
	// }
	/*
	 * public void setCodigoJornada(int codigoJornada) { if (jornadaVO == null) {
	 * this.codigoJornada = codigoJornada; } else { throw new
	 * RuntimeException("Objeto Jornada existente"); } }
	 */
	/*
	 * public int getCodigoMensagem() { if (mensagemVO == null) { return
	 * codigoMensagem; } else { return mensagemVO.getCodigo(); } }
	 */
	/*
	 * public void setCodigoMensagem(int codigoMensagem) { if (mensagemVO == null) {
	 * this.codigoMensagem = codigoMensagem; } else { throw new
	 * RuntimeException("Objeto Mensagem existente"); } }
	 */
	/*
	 * public int getEmpresa() { return empresa; }
	 */

}
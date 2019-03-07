package App;

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
	private static int conf;
	private String dtcadastro;
	
	public String getDtcadastro() {
		return dtcadastro;
	}

	public void setDtcadastro(String dtcadastro) {
		this.dtcadastro = dtcadastro;
	}

	public int getConf() {
		return conf;
	}

	public void setConf(int conf) {
		this.conf = conf;
	}
	
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

	public static void selectNomeAlunol(String nome, Integer onde) throws SQLException {
		Consulta.selectlike(nome, onde, "aluno", "nome", "nome");
		//return nome;
	}
	
	public static String selectNomeAluno(String nome) throws SQLException {
		nome = Consulta.select(nome, "aluno", "nome", "nome");
		return nome;
	}

//--------------------------------------------------//
	
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
			aluno.setDtcadastro(rs.getString("dtcadastro"));
		}
		rs.close();
		stmt.close();
		return aluno;
	}

	public static Integer insertAluno(String nome, String dtnasc, String telefone, String sexo, int codclasse,
			int codclassesugestao, String professor, String alunoespecial, String batismo, String email, String inativo,
			String obs) throws SQLException, ParseException {
		// Connection con = Conexao.getConexao();
		try {

			String query = " INSERT INTO aluno (CODALUNO, NOME, DTNASC, TELEFONE, SEXO, CODCLASSE, CODCLASSESUGESTAO, PROFESSOR, ESPECIAL, BATISMO, EMAIL, INATIVO, OBS, DTCADASTRO)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, Consulta.PkMax("aluno", "codaluno"));
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
			preparedStmt.setString(14, Consulta.datahora());
			//Verifica se já existe
			//Critério Nome e email iguais
			//Ou data e email iguais
			String n1 = Consulta.select(nome, "aluno", "nome", "nome");
			String e1 = Consulta.select(email, "aluno", "email", "email");
			String d1 = Consulta.select(email, "aluno", "email", "dtnasc");
			String cod = Consulta.select(email, "aluno", "email", "codaluno");
			String dh = Consulta.select(email, "aluno", "email", "dtcadastro");
			
			if (n1.equals(nome) && e1.equals(email)) {
				System.out.println("Provavelmete o Aluno já exista! Código: " + cod + " cadastrado em " + dh + "!");
			} else if (Consulta.ExibeData(d1).equals(dtnasc) && e1.equals(email)) {
				System.out.println("Data de nascimento e email existentes! Código: " + cod + " cadastrado em " + dh + "!");
			} else {
				// execute the preparedstatement
				preparedStmt.execute();
				System.out.println("Novo aluno " + nome + " inserido!");
				if (preparedStmt.getMoreResults() == false) {
					conf = 1;
				} else {
					conf = 0;
				}
			}
			
			Conexao.closeConexao();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return conf;

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

	public static void Delete(int pk) throws SQLException {
        /* (Consulta.select(pk, "aluno", "codaluno", "codaluno").isEmpty()) {
        
        }*/
		Consulta.DeletePk("aluno", pk);
	}
	
	public static void main(String[] args) throws SQLException, ParseException {
		//inserir novo aluno----------------------
		insertAluno("Luiz viannas", "01/01/1900", "900000000", "M", 1, 2, "N", "N", "N", "luiz.vianna@integrajca.com.br", "N","");
		//-----------------------------------------
		//Consulta usando nome com parametro de 0 like %% 1 %- e 2 -%
		//selectNomeAlunol("Vian", 0);
		//------------------------------------------------------------
		//Lista um retorno pelo pk do aluno escolhendo sua coluna como no exemplo.
		System.out.println(getLista(603).nome + " - " + getLista(603).email+ " - " + getLista(603).dtcadastro);
		//------------------------------------------------------------
		//Deleta registro de aluno
		//Delete(603);
		// System.out.println(selectAluno("teste"));
		// System.out.println(Consulta.pk("classes", "codclasses"));
		// PK da tabela
		// System.out.println(Consulta.pk("aluno", "codaluno"));
		// insere
		// Aluno.insertAluno("José Barreto","28/05/2018", "21 980734617", "M", 1, 1,
		// "N", "N", "S", "jose@totalcontrol.com.br", "N","obs");
		// Aluno.insertAluno("Mauricio testes","28/03/1987", "21 980734617", "M", 4, 4,
		// "N", "N", "S", "mauricio@totalcontrol.com.br", "N","kjasdf lkajsd fçlaksjdlf
		// kasdklfjasçldfk asdl fj kasjd flasd kfla sjdf");
		// System.out.println(selectAluno("teste"));
		// System.out.println(selectAluno("Ma"));
		 //System.out.println(Consulta.select("Mauricio Rodrigues", "aluno", "nome",
		 //"codaluno"));
		 //System.out.println(Consulta.select("441", "aluno", "codaluno", "nome"));
		 //Aluno.insertAluno("Bruno Xavier", "24/01/1987", "21934875478", "M", 3, 3,
		 //"S", "N", "S", "bruno@gmail.com", "N", "sem obs");
		 //updateAluno("597", "Mauricinho Rodrigues", "26/02/2014", null, null, "2",
		 //"2", "S", null, null, "S", "S", "agora tem obs");
		 //updateAluno("Maumau", "28/03/1987", "21980317641", "M", "2", "1", "n", "n",
		 //"n", "n", "n", "n", "441");
		 //System.out.println(Consulta.select("441", "aluno", "codaluno", "nome"));
		 //System.out.println(Consulta.select("597", "aluno", "codaluno", "obs"));

		 //List<Aluno> teste = getLista(1);
		 //List<Aluno> teste = getLista(1);
		 //Aluno a1 = new Aluno();
		 //System.out.println(getLista(441).toString());
		
		 //System.out.println(teste.);

	}

}

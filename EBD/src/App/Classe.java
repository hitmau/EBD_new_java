package App;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class Classe {
	
	private static Connection con = Conexao.getConexao();
	private static int conf;
	private int codclasse = 0;
	private String descricao;
	private int idadeIni = 0;
	private int idadeFim = 0;
	private boolean especial;
	private boolean inativo;
	public int getCodclasse() {
		return codclasse;
	}
	public void setCodclasse(int codclasse) {
		this.codclasse = codclasse;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getIdadeIni() {
		return idadeIni;
	}
	public void setIdadeIni(int idadeIni) {
		this.idadeIni = idadeIni;
	}
	public int getIdadeFim() {
		return idadeFim;
	}
	public void setIdadeFim(int idadeFim) {
		this.idadeFim = idadeFim;
	}
	public boolean isEspecial() {
		return especial;
	}
	public void setEspecial(boolean especial) {
		this.especial = especial;
	}
	public boolean isInativo() {
		return inativo;
	}
	public void setInativo(boolean inativo) {
		this.inativo = inativo;
	}
	public int getCodCategoria() {
		return codCategoria;
	}
	public void setCodCategoria(int codCategoria) {
		this.codCategoria = codCategoria;
	}
	public String getDhcadastro() {
		return dhcadastro;
	}
	public void setDhcadastro(String dhcadastro) {
		this.dhcadastro = dhcadastro;
	}
	private int codCategoria = 0;
	private String dhcadastro;
	
	public static Classe getLista(int id) throws SQLException {
		// System.out.println(id);

		PreparedStatement stmt = con.prepareStatement("SELECT * FROM classes WHERE codclasse = ?");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		Classe classe = new Classe();
		while (rs.next()) {
			classe.setDescricao(rs.getString("descricao"));
			classe.setIdadeIni(rs.getInt("idadeini"));
			classe.setIdadeFim(rs.getInt("idadefim"));
			classe.setEspecial(rs.getBoolean("especial"));
			classe.setInativo(rs.getBoolean("inativo"));
			classe.setCodCategoria(rs.getInt("codcategoria"));
			classe.setDhcadastro(rs.getString("dhcadastro"));
		}
		rs.close();
		stmt.close();
		return classe;
	}
	
	public static void getListaTudo() throws SQLException {
		// System.out.println(id);

		PreparedStatement stmt = con.prepareStatement("SELECT * FROM classes order by 1");
		//stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString("codclasse") + " - " + rs.getString("descricao") + " - " + rs.getInt("codcategoria") + " - " + rs.getInt("idadeini") + " - " + rs.getInt("idadefim") + " - " + rs.getBoolean("especial") + " - " + rs.getBoolean("inativo"));
			/*
			classe.setIdadeIni(rs.getInt("idadeini"));
			classe.setIdadeFim(rs.getInt("idadefim"));
			classe.setEspecial(rs.getBoolean("especial"));
			classe.setInativo(rs.getBoolean("inativo"));
			classe.setCodCategoria(rs.getInt("codcategoria"));
			classe.setDhcadastro(rs.getString("dtcadastro"));
			*/
		}
		rs.close();
		stmt.close();
		//return classe;
	}

	public static Integer insertClasse(String Descricao, int IdadeIni, int IdadeFim, boolean especial, boolean inativo,
			int codCategoria) throws SQLException, ParseException {
		// Connection con = Conexao.getConexao();
		try {

			String query = " INSERT INTO classes (CODCLASSE, DESCRICAO, IDADEINI, IDADEFIM, ESPECIAL, INATIVO, CODCATEGORIA, DHCADASTRO)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?)";

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, Consulta.PkMax("classes", "codclasse"));
			preparedStmt.setString(2, Descricao);
			preparedStmt.setInt(3, IdadeIni);
			preparedStmt.setInt(4, IdadeFim);
			preparedStmt.setBoolean(5, especial);
			preparedStmt.setBoolean(6, inativo);
			preparedStmt.setInt(7, codCategoria);
			preparedStmt.setString(8, Consulta.datahora());
			// Verifica se já existe
			// Critério Nome e email iguais
			// Ou data e email iguais
			String n1 = Consulta.select(Descricao, "classes", "descricao", "descricao");
			int e1 = Consulta.select(IdadeIni, "classes", "IdadeIni", "IdadeIni");
			int d1 = Consulta.select(IdadeFim, "classes", "IdadeFim", "IdadeFim");
			int cod = Consulta.select(codCategoria, "classes", "codclasse", "codclasse");
			String dh = Consulta.select(Descricao, "classes", "descricao", "descricao");
			 
			if (n1.equals(Descricao)) {
				System.out.println("Provavelmete a Classe já exista! Código: " + cod + " cadastrado em " + dh + "!");
			} else if (e1 == IdadeIni && d1 == IdadeFim) {
				System.out.println(
						"Data de nascimento e email existentes! Código: " + cod + " cadastrado em " + dh + "!");
			} else {
				// execute the preparedstatement
				preparedStmt.execute();
				System.out.println("Nova Classe " + Descricao + " inserida com sucesso!");
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

	public static void updateClasse(String descricao, int idadeIni, int idadeFim, boolean especial, boolean inativo,
			int codCategoria, int codClasse) {
		Connection con = Conexao.getConexao();
		String codclasse = String.valueOf(codClasse);
		
		String sql = "update classes set descricao = ?, idadeini = ?, idadefim = ?, especial = ?, inativo = ?, codcategoria = ? where codclasse = ?;";
		try {

			PreparedStatement stmt = con.prepareStatement(sql);

			if (descricao == null) {
				descricao = Consulta.select(codclasse, "classe", "descricao", "descricao");
			}
			stmt.setString(1, descricao);
			stmt.setInt(2, idadeIni);
			stmt.setInt(3, idadeFim);
			stmt.setBoolean(4, especial);
			stmt.setBoolean(5, inativo);
			stmt.setInt(6, codCategoria);
			stmt.setInt(7, codClasse);
			// excutar
			stmt.execute();
			// fechar a conexao
			// Conexao.closeConexao();
			System.out.println("Update realizado!");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void updateClasse(String descricao, int codClasse) {
		Connection con = Conexao.getConexao();
		String codclasse = String.valueOf(codClasse);
		String sql = "update classes set descricao = ? where codclasse = ?;";
		try {

			PreparedStatement stmt = con.prepareStatement(sql);

			if (descricao == null) {
				descricao = Consulta.select(codclasse, "classes", "descricao", "descricao");
			}
			stmt.setString(1, descricao);
			stmt.setInt(2, codClasse);
			String n1 = Consulta.select(codclasse, "classes", "descricao", "descricao");
			// excutar
			stmt.execute();
			// fechar a conexao
			// Conexao.closeConexao();
			System.out.println("Update realizado! Nome antigo: " + n1 + ", nome novo: " + descricao + ".");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static int Delete(int pk) throws SQLException {
		String ret, restor;
		int statusDel = 0;
		if (Consulta.select(pk, "classe", "codclasse", "codclasse") == 0) {
			ret = "Chave não encontrada!";
			System.out.println("Chave não encontrada!");
		} else {
			ret = getLista(pk).descricao;
			//Deleta o registro da tabela e retorna 0 ou 1.
			restor = Consulta.DeletePk("classe", pk);
			if (restor.equals("ok")) {
				statusDel = 1;
			} else {
				statusDel = 0;
			}
				System.out.println("Classe " + ret + " excluído(a) com sucesso!");
			
		}
		return statusDel;
	}
	
	public static void main(String[] args) throws SQLException, ParseException {
		
		//Classe.getListaTudo();
		//System.out.println(Classe.getLista(9).getDescricao() + Classe.getLista(99).getIdadeIni());
		//Classe.insertClasse("Jovens do Senhor", 18, 26, false, false, 17);
		//System.out.println(Consulta.PkMax("classes", "codclasse"));
		Classe.updateClasse("jovens de Jesuasasas", 18);
	}

}

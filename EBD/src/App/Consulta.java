package App;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Consulta {

	public static String status = Conexao.status;
	//public static int Istatus;
	public static int pk = 0;

	public static void main(String[] args) throws SQLException {
		// Testes efetuados!!!
		// conecta no banco.
		// Conexao.getConexao();
		// Retorna 1 para conexão ok e 0 para sem conexão.
		///// Conexao.RetornaStatus();
		// Função PkMax retorna o pk + 1.
		// System.out.println(PkMax("aluno", "codaluno") + " PkMax");
		// select com like
		// selectlike("Luiz", 0, "aluno", "nome", "nome");
		// select "exato"
		///// System.out.println(select("isac", "aluno", "nome", "dtnasc"));
		// Mostra data bonitinho!
		///// System.out.println(ExibeData(select("isac", "aluno", "nome", "dtnasc")));
		////// System.out.println(ExibeData("1987-03-28"));
		// Retorna data e hora atual do sistema
		////System.out.println(datahora());
		System.out.println(select(0, "aluno", "codaluno", "codaluno"));
		System.out.println(select("Mauricio", "aluno", "nome", "nome"));
	}

	public static String datahora() {
		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
		String dh = data + " " + hora;
		return dh;
	}

	public static String ConverteData(String data) {

		String dia = data.substring(0, 2);
		String mes = data.substring(3, 5);
		String ano = data.substring(6);
		String dtnovo = ano + "-" + mes + "-" + dia;
		// return (java.sql.Date) new SimpleDateFormat("yyyy-MM-dd").parse(data);
		return dtnovo;
	}

	public static String ExibeData(String data) {

		String dia = data.substring(8);
		String mes = data.substring(5, 7);
		String ano = data.substring(0, 4);
		String dtnovo = dia + "/" + mes + "/" + ano;
		// return (java.sql.Date) new SimpleDateFormat("yyyy-MM-dd").parse(data);
		return dtnovo;
	}

	public static String select(String busca, String tabela, String coluna, String retorno) throws SQLException {

		Connection con = Conexao.getConexao();
		java.sql.Statement acesso = con.createStatement();

		try {
			Conexao.getConexao();
			String sql = "SELECT * FROM " + tabela + " where " + coluna + " = '" + busca + "';";
			ResultSet res = acesso.executeQuery(sql);
			while (res != null && res.next()) {
				status = res.getString(retorno);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		if (status == "1") {
			System.out.println(status = "Não encontrato ou campo em branco!");
		} else if (status == "0") {
			status = "Conexão não estabelecida!";
		} else if (status == "") {
			status = "Nenhum resultado!";
		} else if (status == "Não conectou...") {
			status = "Nenhum resultado encontrado!";
		}
		// Conexao.closeConexao();
		return status;
	}

	public static int select(int busca, String tabela, String coluna, String retorno) throws SQLException {

		Connection con = Conexao.getConexao();
		java.sql.Statement acesso = con.createStatement();

		try {
			Conexao.getConexao();
			String sql = "SELECT * FROM " + tabela + " where " + coluna + " = '" + busca + "';";
			ResultSet res = acesso.executeQuery(sql);
			while (res != null && res.next()) {
				status = res.getString(retorno);
			}
			if (status == "Não conectou...") {
				status = "0";
			} else {
				status = "1";
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		int i = Integer.parseInt(status);
		return i;
	}
	
	public static void selectlike(String busca, Integer onde, String tabela, String coluna, String retorno)
			throws SQLException {
		Connection con = Conexao.getConexao();
		java.sql.Statement acesso = con.createStatement();
		String sql = "";
		try {
			Conexao.getConexao();
			if (onde == 0) {
				sql = "SELECT * FROM " + tabela + " where " + coluna + " like '%" + busca + "%';";
			} else if (onde == 1) {
				sql = "SELECT * FROM " + tabela + " where " + coluna + " like '" + busca + "%';";
			} else if (onde == 2) {
				sql = "SELECT * FROM " + tabela + " where " + coluna + " like '%" + busca + "';";
			}

			ResultSet res = acesso.executeQuery(sql);
			while (res != null && res.next()) {
				status = res.getString(retorno);
				System.out.println(status);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		if (status == "1") {
			status = "Não encontrato ou campo em branco!";
		} else if (status == "0") {
			status = "Conexão não estabelecida!";
		} else if (status == "") {
			status = "Nenhum resultado!";
		}
		// Conexao.closeConexao();
		// return status;
	}

	public static int PkMax(String tabela, String campo) throws SQLException {
		Connection con = Conexao.getConexao();
		java.sql.Statement acesso = con.createStatement();

		try {
			Conexao.getConexao();
			String sql = "SELECT MAX(" + campo + ") + 1 as " + campo + " FROM " + tabela + ";";
			ResultSet res = acesso.executeQuery(sql);
			while (res != null && res.next()) {
				pk = res.getInt(campo);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		if (status == "1") {
			status = "Não encontrato!";
		} else if (status == "0") {
			status = "Conexão não estabelecida!";
		}
		return pk;
	}

	public static String DeletePk(String tabela, int pk) throws SQLException {
		Connection con = Conexao.getConexao();
		java.sql.Statement acesso = con.createStatement();
		String status = "";
		
		try {
			Conexao.getConexao();
			String sql = "DELETE FROM " + tabela + " WHERE CODALUNO = " + pk;
			PreparedStatement preparedSql = con.prepareStatement(sql);
			preparedSql.setString(1, tabela);
			preparedSql.setInt(2, pk);
			preparedSql.execute();
			status = "Código " + pk + " da tabela " + tabela + " deletado com sucesso!";
			Conexao.closeConexao();
		} catch (SQLException ex) {
			ex.printStackTrace();
			status = "Erro ao teletar o registro!" + ex.getMessage();
		}
		return status;
	}
	/*
	 * O updade assim como insert deve ser na classe de sua tabela public static
	 * String update(String tabela, String coluna, String origem, String destino)
	 * throws SQLException { Connection con = Conexao.getConexao();
	 * java.sql.Statement acesso = con.createStatement(); try { String sql =
	 * "update " + tabela + " set " + coluna + " = " + destino + " where " + coluna
	 * + " = " + destino +";"; } catch (Exception e) { // TODO: handle exception }
	 * return status; }
	 */


}

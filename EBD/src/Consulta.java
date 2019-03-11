import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Consulta {

	public static String status = Conexao.status;
	public static int pk = 0;
/*
	public static void main(String[] args) throws SQLException {

		// Conexao.getConexao();
		// System.out.println(Conexao.getStatus());

		System.out.println(select("Mauricio", "aluno", "nome", "nome"));
		System.out.println(pk("aluno", "codaluno"));
		System.out.println(pk("classes", "codclasse"));
	}
*/
	public static String ConverteData(String data) {
		
		String dia = data.substring(0, 2);
		String mes = data.substring(3, 5);
		String ano = data.substring(6);
		String dtnovo = ano + "-" + mes + "-" + dia;
		//return (java.sql.Date) new SimpleDateFormat("yyyy-MM-dd").parse(data);
		
		return dtnovo;
		
	}
	
	public static String select(String busca, String tabela, String coluna, String retorno)
			throws SQLException {
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
			status = "Não encontrato ou campo em branco!";
		} else if (status == "0") {
			status = "Conexão não estabelecida!";
		} else if (status == "") {
			status = "Nenhum resultado!";
		}
		//Conexao.closeConexao();
		return status;
	}

	public static int pk(String tabela, String campo) throws SQLException {
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

	public static String update(String tabela, String coluna, String origem, String destino)
			throws SQLException {
		Connection con = Conexao.getConexao();
		java.sql.Statement acesso = con.createStatement();
		try {
			String sql = "update " + tabela + " set " + coluna + " = " + destino + " where ";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return status;
	}
	/*
	 * public static void altera(FuncionarioVO funcionarioVO) throws SQLException {
	 * PreparedStatement stmt = connection
	 * .prepareStatement("update LIS_FUN_RPT  set CD_EMPGCB=?, CD_FIL=?," +
	 * "CD_LFRPT_RLG=?,CD_EMPGCB_FUN=?,CD_FUN=?,CD_JORRPT=?," +
	 * "CD_MSGRPT=?, ST_LFRPT=?, CD_LFRPT_VIA_CCH, ST_LFRPT_ICL " + "where "DAÍ PRA
	 * FRENTE NÃO SEI DE MAIS NADA..." stmt.setInt(1, funcionarioVO.getEmpresa());
	 * stmt.setInt(2, funcionarioVO.getFilial()); stmt.setInt(3,
	 * funcionarioVO.getNumeroRelogio()); stmt.setInt(4,
	 * funcionarioVO.getCodigoEmpresaFunc()); stmt.setInt(5,
	 * funcionarioVO.getMatricula()); stmt.setInt(6,
	 * funcionarioVO.getCodigoJornada()); stmt.setInt(7,
	 * funcionarioVO.getCodigoMensagem()); stmt.setString(8,
	 * funcionarioVO.getSituacaoRegistro()); stmt.setInt(9,
	 * funcionarioVO.getViaCracha()); stmt.setString(10,
	 * funcionarioVO.getSituacaoAutenticaRegistro()); stmt.execute(); stmt.close();
	 * }
	 */
}

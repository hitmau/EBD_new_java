import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import br.com.ConexaoBanco.Conexao;

//import br.com.ConexaoBanco.Conexao;

public class Conexao {

	public static String status = "N�o conectou...";
/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection a = Conexao.getConexao();
		System.out.println(getStatus());

	}
*/
	//Abre conex�o com o banco
	public static java.sql.Connection getConexao() {
		Connection connection = null; // atributo do tipo Connection
		try {
			// Carregando o JDBC Driver padr�o
			String driverName = "com.mysql.jdbc.Driver";
			Class.forName(driverName);
			// Configurando a nossa conex�o com um banco de dados//
			String serverName = "sql9.freemysqlhosting.net";// "186.202.152.25"; //caminho do servidor do BD
			String mydatabase = "sql9260135";// "alanmake_pw"; //nome do seu banco de dados
			String url = "jdbc:mysql://" + serverName + ":3306/" + mydatabase + "?useSSL=true";
			String username = "sql9260135";// "alanm_pw"; //nome de um usu�rio de seu BD
			String password = "GJCVaqdCZy";// "7k6}P@JY"; //sua senha de acesso

			connection = DriverManager.getConnection(url, username, password);

			// Testa sua conex�o//
			if (connection != null) {
				status = ("1");
			} else {
				status = ("0");
			}

			return connection;
		} catch (ClassNotFoundException e) { // Driver n�o encontrado
			System.out.println("O driver expecificado nao foi encontrado.");
			return null;
		} catch (SQLException e) {
//N�o conseguindo se conectar ao banco	 
			System.out.println("Nao foi possivel conectar ao Banco de Dados.");
			return null;
		}

	}

//M�todo que retorna o status da sua conex�o//
	public static String statusConection() {
		return status;
	}

	public static void RetornaStatus() {
		System.out.println(status);
	}

	public static String getStatus() {
		return status;
	}

//M�todo que fecha conex�o
	public static boolean closeConexao() {
		try {
			Conexao.getConexao().close();
			status = ("STATUS--->Conex�o fechada");
			return true;
		} catch (SQLException e) {
			status = ("STATUS--->Erro ao exibir status");
			return false;
		}
	}

	// M�todo que reinicia conex�o
	public static java.sql.Connection refreshConexao() {
		closeConexao();
		return Conexao.getConexao();
	}

}

package onlineQuiz.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import onlineQuiz.connection.ConnectionPool;
 
public class Security {
	
	ConnectionPool pool = ConnectionPool.getInstance("mysql");
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet result = null;
	
	public Security() {
		conn = pool.getConnection();
		System.out.println("Connection to mySql database established (Security)");
	}
     
    public String encryptPassword(String userId, String unencryptedPassword){
    	
    	String salt = userId.substring(0,4) + unencryptedPassword.substring(unencryptedPassword.length() - 4);
        MessageDigest messageDigest=null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update((unencryptedPassword+salt).getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return (new BigInteger(messageDigest.digest())).toString(16);
         
    }
     
    public String retrieveEncryptedPassword(String userID){
    	String storedEncryptedPwd = null;
    	try {
    		String query = "SELECT password FROM Users WHERE email = ?;";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userID);
			result = pstmt.executeQuery();
			while( result.next() ) {
				storedEncryptedPwd = result.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (result != null) {
					result.close();
					result = null;
				}
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
				if (pool != null)
					pool.freeConnection(conn);
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
        return storedEncryptedPwd;
    }
     
    public boolean verifyPassword(String userId, String unecryptedPassword){
        String encryptedLoginPagePassword = encryptPassword(userId, unecryptedPassword);
        String encryptedPasswordFromDatabase = retrieveEncryptedPassword(userId);
        if (encryptedLoginPagePassword.equals(encryptedPasswordFromDatabase))
            return true;
        return false;
    }
 
}
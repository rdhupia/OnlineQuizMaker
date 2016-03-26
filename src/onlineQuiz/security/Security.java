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

	ResultSet result = null;
	 
    public static final String encryptPassword(String userId, String unencryptedPassword){
    	
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
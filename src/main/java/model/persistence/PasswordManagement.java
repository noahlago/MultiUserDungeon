package model.persistence;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordManagement {
    /**Stores the name of the hashing algorithm to use when hashing all passwords. Final so that all password hashing is consistent once instantiated. */
    private final String hashAlgorithm;

    /**
     * Allows for a hashing algorithm to be specified, if one other than SHA-256 is to be used
     * @param hashAlgorithm //name of the algorithm to be used for all password hashing
     */
    public PasswordManagement(String hashAlgorithm){
        this.hashAlgorithm = hashAlgorithm;
    }

    /**
     * Defaults the hashing method for passwords to use the SHA-256 algorithm
     */
    public PasswordManagement(){
        this.hashAlgorithm = "SHA-256";
    }

    /**
     * This method hashes the given password using the specified hashing algorithm (default: SHA-256), and returns the hashed password as a String, to be stored in the profile save file
     * Hashing is done by breaking the password into Bytes, which are then each hashed, and appended into a String to be returned. 
     * @param password the password to be hashed
     * @return the hashed bytes of the password as a String
     * @throws NoSuchAlgorithmException if the previously specified hashing algorithm is not found within the security library
     */
    public String hashPassword(String password) throws NoSuchAlgorithmException{
        MessageDigest messageDigest = MessageDigest.getInstance(hashAlgorithm);
        
        byte[] passwordBytes = password.getBytes();
        messageDigest.update(passwordBytes);
        passwordBytes = messageDigest.digest();

        String hashed = "";

        for(byte hashedByte : passwordBytes){
            hashed += hashedByte;
        }

        return hashed;
    }
}

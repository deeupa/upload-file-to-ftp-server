package com.javaartifacts.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FTPFileUpload {

	public static void main(String... args) {

		String server = "127.0.0.1";
		int port = 21;
		String username = "your_username";
		String password = "your_password";
		String remoteFilePath = "/ftp";
		String localFilePath = "c:\\test.txt";

		FTPClient ftpClient = new FTPClient();
		ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));

		try {

			ftpClient.connect(server, port);
			ftpClient.login(username, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			if (!ftpClient.changeWorkingDirectory(remoteFilePath)) {
				System.out.println("Remote directory does not exist.");
				return;
			}

			File localFile = new File(localFilePath);
			FileInputStream inputStream = new FileInputStream(localFile);

			boolean uploadFile = ftpClient.storeFile(localFile.getName(), inputStream);
			inputStream.close();

			if (uploadFile) {
				System.out.println("File uploaded successfully.");
			} else {
				System.out.println("File upload failed.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
package com.ZLHW.base.nio;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost",8899);
			
			OutputStream os = socket.getOutputStream(); 
			InputStream is = socket.getInputStream(); 

			int len; 
			byte[] buf = new byte[100]; 

			os.write("luzhecheng".getBytes()); 
			os.flush();
			while(is.read(buf)>-1){
				System.out.println(new String(buf));
			}
			is.close(); 
			os.close(); 
			socket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

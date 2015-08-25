package com.ZLHW.base.nio;
import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;



/**
 * ��Mina Serverʾ��
 * @author ����
 *
 */
public class MainServer {

	public static void main(String []args)throws Exception{
		
		//����һ���������Server��Socket����NIO
		SocketAcceptor acceptor = new NioSocketAcceptor();
		
		//����������ݵĹ�����
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
	
		//�趨�����������һ��һ�У�/r/n���Ķ�ȡ���
		chain.addLast("myChain", new ProtocolCodecFilter(new TextLineCodecFactory()));
		
		//�趨�������˵���Ϣ������:һ��SamplMinaServerHandler����
		acceptor.setHandler(new SamplMinaServerHandler());
		
		//�������˰󶨵Ķ˿�
		int bindPort = 8899;
		
		//�󶨶˿ڣ�����������
		try {
			acceptor.bind(new InetSocketAddress(bindPort));
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		System.out.println("Mina Server is Listing on:=" + bindPort);
		
	}
	
}
package com.ZLHW.base.nio;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * �����ͻ���
 * @author ����
 *
 */
public class MainClient {

	public static void main(String []args)throws Exception{
		
		//Create TCP/IP connection
		NioSocketConnector connector = new NioSocketConnector();
		
		//����������ݵĹ�����
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();
		
		//�趨�����������һ��һ��(/r/n)�Ķ�ȡ���
		chain.addLast("myChin", new ProtocolCodecFilter(new TextLineCodecFactory()));
		
		//����������Ϣ��������һ��SamplMinaServerHander����
		connector.setHandler(new SamplMinaClientHander());
		
		//set connect timeout
		connector.setConnectTimeout(30);
		
		//���ӵ���������
		ConnectFuture cf = connector.connect(new InetSocketAddress("localhost",8899));
		
		//Wait for the connection attempt to be finished.
		cf.awaitUninterruptibly();
		
		cf.getSession().getCloseFuture().awaitUninterruptibly();
		
		connector.dispose();
	}
	
}
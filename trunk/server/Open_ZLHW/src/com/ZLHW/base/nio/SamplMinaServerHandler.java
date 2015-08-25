package com.ZLHW.base.nio;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * �Զ������Ϣ������������ʵ��IoHandlerAdapter��
 * @author ����
 *
 */
public class SamplMinaServerHandler extends IoHandlerAdapter{

	private int count = 0;
	
	/**
	 * ��һ���ͻ������ӽ���ʱ
	 */
	@Override
	public void sessionOpened(IoSession session) throws Exception {

		System.out.println("incoming client: " + session.getRemoteAddress());

	}

	/**
	 * ��һ���ͻ��˹ر�ʱ
	 */
	@Override
	public void sessionClosed(IoSession session) throws Exception {

		System.out.println(session.getRemoteAddress() + "is Disconnection");

	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		//�������趨�˷����������Ĺ�����һ��һ�ж�ȡ������Ϳ���תΪString:
		String str = (String)message;
		
		//Write the received data back to remote perr
		System.out.println("�յ��ͻ��˷�������ϢΪ" + "  " + str);
		
		//��������Ϣ���͸�ͻ���
		session.write(str + count);
		
		++count;
		
	}
	
	

	
}

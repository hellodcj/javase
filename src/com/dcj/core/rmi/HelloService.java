package com.dcj.core.rmi;

import java.io.IOException;
import java.rmi.Remote;
import java.util.Date;

public interface HelloService extends Remote {
	public String echo(String msg) throws IOException;
	public Date getTime() throws Exception;
}

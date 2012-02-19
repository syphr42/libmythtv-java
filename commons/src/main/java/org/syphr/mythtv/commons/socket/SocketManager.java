/*
 * Copyright 2011-2012 Gregory P. Moyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.syphr.mythtv.commons.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ByteChannel;
import java.util.concurrent.TimeUnit;

public interface SocketManager {

	/**
	 * Provide a means for intercepting messages. This allows clients of this class to
	 * override normal behavior upon the receipt of data over the socket.
	 *
	 * @param interceptor
	 *            the interceptor to set
	 */
	public void setInterceptor(Interceptor interceptor);

	/**
	 * Set the default timeout when waiting for a response from the server. A
	 * value of <code>0</code> indicates no timeout (default).
	 *
	 * @param time
	 *            the new default timeout value
	 * @param unit
	 *            the units of the given value
	 */
	public void setDefaultTimeout(long time, TimeUnit unit);

	/**
	 * Retrieve the current default timeout for waiting for a response from the
	 * server. A value of <code>0</code> indicates no timeout (default).
	 *
	 * @param unit
	 *            the units to provide
	 * @return the current default timeout in the given units
	 */
	public long getDefaultTimeout(TimeUnit unit);

	/**
	 * Connect to a server. This method will block until the connection completes.
	 * If a connection is already active, this method will do nothing.
	 *
	 * @see #connect(InetSocketAddress, long)
	 *
	 * @param host
	 *            the hostname (or IP address) of the server
	 * @param port
	 *            the port on the server
	 * @param timeout
	 *            number of milliseconds to wait before assuming the connection failed
	 *            (values < 1 indicate no timeout)
	 * @throws IOException
	 *             if the connection could not be completed
	 */
	public void connect(String host, int port, final long timeout)
			throws IOException;

	/**
	 * Connect to a server. This method will block until the connection completes.
	 * If a connection is already active, this method will do nothing.
	 *
	 * @see #connect(String, int, long)
	 *
	 * @param addr
	 *            the server location
	 * @param timeout
	 *            number of milliseconds to wait before assuming the connection failed
	 *            (values < 1 indicate no timeout)
	 * @throws IOException
	 *             if the connection could not be completed
	 */
	public void connect(InetSocketAddress addr, final long timeout)
			throws IOException;

	/**
	 * Determine whether or not this manager has an active connection to a server.
	 *
	 * @return <code>true</code> if the manager is connected; <code>false</code> otherwise
	 */
	public boolean isConnected();

	/**
	 * Retrieve the address to which this instance is currently connected.
	 *
	 * @see #isConnected()
	 *
	 * @return the connected address or <code>null</code> if this manager is not connected
	 */
	public InetSocketAddress getConnectedAddress();

	/**
	 * Create a copy of this manager that is connected to the same server (if this
	 * instance is connected).
	 *
	 * @see #isConnected()
	 *
	 * @return the new manager instance
	 * @throws IOException
	 *             if the connection could not be completed
	 */
	public SocketManager newConnection() throws IOException;

	/**
	 * Close the active connection and clean up any resources associated with it. If there
	 * is no active connection, this method will make sure that resources are cleaned up
	 * and return.
	 */
	public void disconnect();

	/**
	 * Send a message over the active connection. This method will not wait for a response
	 * and should not be used with messages that will trigger a response from the server.
	 *
	 * @param message
	 *            the message to send
	 * @throws IOException
	 *             if this manager is not connected to a server, is interrupted while
	 *             sending the message, or some other communication error occurs
	 */
	public void send(String message) throws IOException;

	/**
	 * Send a message to the server and wait for a response. This method will
	 * wait according to the {@link #getDefaultTimeout(TimeUnit) default
	 * timeout}, which may be indefinitely (the default) and should not be used
	 * with messages that do not or may not cause the server to respond.
	 *
	 * @param message
	 *            the message to send
	 * @return the response from the server or an empty string if the thread is
	 *         interrupted or the timeout is reached
	 * @throws IOException
	 *             if this manager is not connected to a server or some other
	 *             communication error occurs
	 */
	public String sendAndWait(String message) throws IOException;

	/**
	 * Send a message to the server and wait for a response up to the given
	 * timeout value.
	 *
	 * @param message
	 *            the message to send
	 * @param timeout
	 *            the length of time in units of <code>unit</code> to wait for a
	 *            response
	 * @param unit
	 *            units used to interpret the <code>timeout</code> parameter
	 * @return the response from the server or an empty string if the thread
	 *         is interrupted or the timeout is reached
	 * @throws IOException
	 *             if this manager is not connected to a server or some other
	 *             communication error occurs
	 */
	public String sendAndWait(String message, long timeout, TimeUnit unit)
			throws IOException;

	/**
	 * Redirect the data channel used by this socket manager. While the channel is redirected, normal
	 * communication is suspended. To release the redirected channel, simply close
	 * it.
	 *
	 * @return the redirected channel stream
	 */
	public ByteChannel redirectChannel();

}
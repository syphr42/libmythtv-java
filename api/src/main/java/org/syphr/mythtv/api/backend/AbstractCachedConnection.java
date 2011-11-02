/*
 * Copyright 2011 Gregory P. Moyer
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
package org.syphr.mythtv.api.backend;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This class provides base functionality for a connection that automatically
 * disconnects after a period of being idle.
 * 
 * @author Gregory P. Moyer
 */
public abstract class AbstractCachedConnection
{
    /**
     * A thread controller for maintaining the idle timeout.
     */
    private final ExecutorService executor;

    /**
     * The idle timeout in milliseconds.
     */
    private volatile long timeout;

    /**
     * A flag to determine whether or not the cached connection has been used
     * since the last check.
     */
    private volatile boolean used;

    /**
     * Construct a new instance and start a monitor that will watch the any open
     * connection for idling.
     * 
     * @param timeout
     *            the timeout value
     * @param unit
     *            the timeout units
     */
    public AbstractCachedConnection(long timeout, TimeUnit unit)
    {
        setTimeout(timeout, unit);

        executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    try
                    {
                        Thread.sleep(getTimeout());
                    }
                    catch (InterruptedException e)
                    {
                        if (isConnected())
                        {
                            disconnect();
                        }

                        return;
                    }

                    if (!used && isConnected())
                    {
                        disconnect();
                    }
                    else
                    {
                        used = false;
                    }

                    if (Thread.interrupted())
                    {
                        if (isConnected())
                        {
                            disconnect();
                        }

                        return;
                    }
                }
            }
        });
    }

    /**
     * Retrieve the current idle timeout value in milliseconds.
     * 
     * @return the current timeout
     */
    public long getTimeout()
    {
        return timeout;
    }

    /**
     * Change the idle timeout.
     * 
     * @param timeout
     *            the timeout value
     * @param unit
     *            the timeout units
     */
    public void setTimeout(long timeout, TimeUnit unit)
    {
        this.timeout = TimeUnit.MILLISECONDS.convert(timeout, unit);
    }

    /**
     * Permanently shutdown the connection and the monitor thread. After this
     * call, if any code initiates a connection, there will be no automatic
     * cleanup.
     */
    public void shutdownConnection()
    {
        executor.shutdownNow();
    }

    /**
     * Determine whether or not this instance has been permanently
     * {@link #shutdownConnection() shutdown}.
     * 
     * @return <code>true</code> if the instance is permanently shutdown;
     *         <code>false</code>
     */
    public boolean isConnectionShutdown()
    {
        return executor.isShutdown();
    }

    /**
     * Mark the connection as in use to prevent it from being closed due to
     * inactivity.
     */
    protected void markConnectionUsed()
    {
        used = true;
    }

    /**
     * Determine whether or not a connection is currently open.
     * 
     * @return <code>true</code> if an open connection exists;
     *         <code>false</code> otherwise
     */
    protected abstract boolean isConnected();

    /**
     * Disconnect the open connection. If no connection is open, this method
     * should do nothing.
     */
    protected abstract void disconnect();
}

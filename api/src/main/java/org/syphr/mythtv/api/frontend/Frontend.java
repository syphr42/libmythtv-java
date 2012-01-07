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
package org.syphr.mythtv.api.frontend;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.api.MythVersion;
import org.syphr.mythtv.control.ControlFactory;
import org.syphr.mythtv.control.ControlVersion;

public class Frontend
{
    private static Logger LOGGER = LoggerFactory.getLogger(Frontend.class);

    private static final int DEFAULT_CONTROL_PORT = 6546;
    private static final int DEFAULT_HTTP_PORT = 6544;

    private final CachedControl control;

    private String host;

    public Frontend(MythVersion version)
    {
        this(version.getControl());
    }

    public Frontend(ControlVersion controlVersion)
    {
        control = new CachedControl(ControlFactory.createInstance(controlVersion),
                                    1L,
                                    TimeUnit.MINUTES);
    }

    public void setConnectionTimeout(long timeout, TimeUnit unit)
    {
        control.setTimeout(timeout, unit);
    }

    public void autoConfigure() throws IOException
    {
        String localHost = InetAddress.getLocalHost().getHostName();

        setFrontendConnectionParameters(localHost, DEFAULT_CONTROL_PORT, DEFAULT_HTTP_PORT);
    }

    public void setFrontendConnectionParameters(String frontendHost, int controlPort, int httpPort)
    {
        this.host = frontendHost;

        control.setConnectionParameters(frontendHost, controlPort == 0
                ? DEFAULT_CONTROL_PORT
                : controlPort);
    }

    public void destroy()
    {
        control.shutdownConnection();
    }

    public String getHost()
    {
        return host;
    }

    public ServerInfo getInfo() throws IOException
    {
        return new ServerInfo(control);
    }

    public void sendMessage(String message) throws IOException
    {
        control.message(message);
    }
}

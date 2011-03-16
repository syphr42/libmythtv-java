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
package org.syphr.mythtv.http.backend;

import java.net.URI;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

public class ConnectionManager
{
    private final URI base;

    private final Client client;

    public ConnectionManager(String host, int port)
    {
        this(URI.create("http://" + host + ":" + port));
    }

    public ConnectionManager(URI base)
    {
        this(base, Client.create());
    }

    private ConnectionManager(URI base, Client client)
    {
        this.base = base;
        this.client = client;
    }

    public ConnectionManager extend(URI newBase)
    {
        return new ConnectionManager(base.resolve(newBase), client);
    }

    public String getXml(String... paths) throws ContentException
    {
        try
        {
            return getWebResource(paths).accept(MediaType.APPLICATION_XML).get(String.class);
        }
        catch (UniformInterfaceException e)
        {
            throw new ContentException(e.getMessage(), e);
        }
    }

    public String getJson(String... paths) throws ContentException
    {
        try
        {
            return getWebResource(paths).accept(MediaType.APPLICATION_JSON).get(String.class);
        }
        catch (UniformInterfaceException e)
        {
            throw new ContentException(e.getMessage(), e);
        }
    }

    private WebResource getWebResource(String... paths)
    {
        WebResource wr = client.resource(base);
        for (String path : paths)
        {
            wr = wr.path(path);
        }

        return wr;
    }
}

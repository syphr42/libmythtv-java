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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

/**
 * This class manages HTTP requests to the backend with a convenient API to retrieve data
 * in multiple formats.
 *
 * @author Gregory P. Moyer
 */
public class ConnectionManager
{
    private final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

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

    /**
     * Create a new connection manager with an extended base URI. This is useful for
     * passing a connection manager instance to a more specific class (such as a subset of
     * REST methods) while still re-using the same client.<br>
     * <br>
     * For example:
     *
     * <pre>
     * ConnectionManager connMan = new ConnectionManager("mythbackend", 6544);
     * ...
     *
     * ConnectionManager guideConnMan = connMan.extend(URI.create("/Guide"));
     * String guideXml = guideConnMan.getXml("GetProgramGuide");
     * ...
     * </pre>
     *
     * @param newBase
     *            the URI to resolve against the current base that will be the base of the
     *            new connection manager
     * @return a new connection manager
     */
    public ConnectionManager extend(URI newBase)
    {
        return new ConnectionManager(base.resolve(newBase), client);
    }

    // TODO need to take arguments
    public String getXml(String... paths) throws ContentException
    {
        WebResource wr = getWebResource(paths);
        logger.trace("Requesting XML from URI: {}", wr.getURI());

        return getData(MediaType.APPLICATION_XML_TYPE, wr);
    }

    // TODO need to take arguments
    public String getJson(String... paths) throws ContentException
    {
        WebResource wr = getWebResource(paths);
        logger.trace("Requesting JSON from URI: {}", wr.getURI());

        return getData(MediaType.APPLICATION_JSON_TYPE, wr);
    }

    public void put(String data, String... paths) throws ContentException
    {
        WebResource wr = getWebResource(paths);

        // TODO encode data into uri or as payload

        logger.trace("Posting to URI: {} :: {}", wr.getURI(), data);

        try
        {
            wr.post();
        }
        catch (UniformInterfaceException e)
        {
            throw new ContentException(e.getMessage(), e);
        }
        catch (ClientHandlerException e)
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

    private String getData(MediaType mediaType, WebResource wr) throws ContentException
    {
        try
        {
            String data = wr.accept(mediaType).get(String.class);

            logger.trace("Received response: {}", data);

            return data;
        }
        catch (UniformInterfaceException e)
        {
            throw new ContentException(e.getMessage(), e);
        }
        catch (ClientHandlerException e)
        {
            throw new ContentException(e.getMessage(), e);
        }
    }
}

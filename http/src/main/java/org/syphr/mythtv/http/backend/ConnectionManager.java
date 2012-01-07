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
package org.syphr.mythtv.http.backend;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.wink.client.ClientRuntimeException;
import org.apache.wink.client.ClientWebException;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final RestClient client;

    public ConnectionManager(String host, int port)
    {
        this(URI.create("http://" + host + ":" + port));
    }

    public ConnectionManager(URI base)
    {
        this(base, new RestClient());
    }

    private ConnectionManager(URI base, RestClient client)
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
        Resource resource = getResource(paths);
        logger.trace("Requesting XML from URI: {}", resource.getUriBuilder().build());

        return getData(MediaType.APPLICATION_XML_TYPE, resource);
    }

    // TODO need to take arguments
    public String getJson(String... paths) throws ContentException
    {
        Resource resource = getResource(paths);
        logger.trace("Requesting JSON from URI: {}", resource.getUriBuilder().build());

        return getData(MediaType.APPLICATION_JSON_TYPE, resource);
    }

    public void put(String data, String... paths) throws ContentException
    {
//        Resource wr = getWebResource(paths);
//
//        // TODO encode data into uri or as payload
//
//        logger.trace("Posting to URI: {} :: {}", wr.getUriBuilder().build(), data);
//
//        try
//        {
//            wr.post();
//        }
//        catch (ClientWebException e)
//        {
//            throw new ContentException(e.getMessage(), e);
//        }
//        catch (ClientRuntimeException e)
//        {
//            throw new ContentException(e.getMessage(), e);
//        }
    }

    private Resource getResource(String... paths)
    {
        Resource resource = client.resource(base);
        UriBuilder uriBuilder = resource.getUriBuilder();

        for (String path : paths)
        {
            uriBuilder.path(path);
        }

        return resource;
    }

    private String getData(MediaType mediaType, Resource resource) throws ContentException
    {
        try
        {
            String data = resource.accept(mediaType).get(String.class);

            logger.trace("Received response: {}", data);

            return data;
        }
        catch (ClientWebException e)
        {
            throw new ContentException(e.getMessage(), e);
        }
        catch (ClientRuntimeException e)
        {
            throw new ContentException(e.getMessage(), e);
        }
    }
}

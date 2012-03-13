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
package org.syphr.mythtv.http.impl;

import java.io.IOException;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.http.ServiceVersionException;

public class ServiceUtils
{
    /**
     * Standard logging facility.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceUtils.class);

    private static final String VERSION_URI_PATH = "version";

    private static final Pattern VERSION_PATTERN = Pattern.compile("<\\?xml version=\".+?\" encoding=\".+?\"\\?>\\s*?<String>(.+?)</String>\\s");

    public static boolean toPrimitive(Boolean b)
    {
        if (b == null)
        {
            return false;
        }

        return b.booleanValue();
    }

    public static void verifyVersion(URI serviceBaseUri, String expectedVersion) throws ServiceVersionException,
                                                                                IOException
    {
        String serviceVersion = getVersion(serviceBaseUri);

        if (!serviceVersion.equals(expectedVersion))
        {
            throw new ServiceVersionException("Service supports version "
                    + serviceVersion
                    + "; this client supports version "
                    + expectedVersion);
        }
    }

    public static String getVersion(URI serviceBaseUri) throws IOException
    {
        URI uri = URI.create(serviceBaseUri.toString() + "/" + VERSION_URI_PATH);

        HttpClient httpclient = new DefaultHttpClient();
        try
        {
            HttpGet httpget = new HttpGet(uri);
            LOGGER.debug("Retrieving service version from {}", httpget.getURI());

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpget, responseHandler);
            LOGGER.trace("Version response: {}", responseBody);

            Matcher matcher = VERSION_PATTERN.matcher(responseBody);
            if (matcher.matches())
            {
                return matcher.group(1);
            }

            throw new IOException("Failed to retrieve version information");
        }
        finally
        {
            httpclient.getConnectionManager().shutdown();
        }
    }

    private ServiceUtils()
    {
        /*
         * Static utilities
         */
    }
}

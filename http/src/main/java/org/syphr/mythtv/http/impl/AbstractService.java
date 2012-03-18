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

import javax.xml.ws.BindingProvider;

import org.syphr.mythtv.commons.unsupported.UnsupportedHandler;
import org.syphr.mythtv.commons.unsupported.UnsupportedHandlerLog;
import org.syphr.mythtv.http.Service;
import org.syphr.mythtv.http.ServiceVersionException;

public abstract class AbstractService implements Service
{
    private UnsupportedHandler unsupported;

    public AbstractService()
    {
        unsupported = new UnsupportedHandlerLog();
    }

    protected void configureAndVerify(String host, int port, BindingProvider provider) throws ServiceVersionException,
                                                                                      IOException
    {
        URI uri = URI.create("http://" + host + ":" + port + "/" + getName());

        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, uri.toString());
        ServiceUtils.verifyVersion(uri, getVersion(), getName());
    }

    @Override
    public void setUnsupportedHandler(UnsupportedHandler unsupported)
    {
        this.unsupported = unsupported;
    }

    protected UnsupportedHandler getUnsupportedHandler()
    {
        return unsupported;
    }

    protected void handleUnsupported(String opDescription)
    {
        unsupported.handle(opDescription);
    }

    protected abstract String getName();

    protected abstract String getVersion();
}

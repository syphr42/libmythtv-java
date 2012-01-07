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

import java.lang.reflect.InvocationTargetException;
import java.net.URI;

import javax.xml.stream.XMLStreamException;

import org.syphr.mythtv.http.backend.impl.Version;

public class BackendFactory
{
    public static Content getContent(ConnectionManager connMan) throws ContentException
    {
        return getInstance(Content.class, connMan);
    }

    public static Dvr getDvr(ConnectionManager connMan) throws ContentException
    {
        return getInstance(Dvr.class, connMan);
    }

    public static Guide getGuide(ConnectionManager connMan) throws ContentException
    {
        return getInstance(Guide.class, connMan);
    }

    public static Myth getMyth(ConnectionManager connMan) throws ContentException
    {
        return getInstance(Myth.class, connMan);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static <T> T getInstance(Class<T> type, ConnectionManager connMan) throws ContentException
    {
        Version version = null;

        try
        {
            String mainAppName = type.getSimpleName();
            version = Version.parse(connMan.getXml(mainAppName, "version"));
            Class<T> clazz = (Class) Class.forName("org.syphr.mythtv.http.backend.impl."
                                                   + type.getSimpleName()
                                                   + version.getValue()
                                                            .replace('.', '_'));

            return clazz.getConstructor(ConnectionManager.class).newInstance(connMan.extend(URI.create("/" + mainAppName)));
        }
        catch (IllegalArgumentException e)
        {
            invalid(version);
        }
        catch (SecurityException e)
        {
            invalid(version);
        }
        catch (ClassNotFoundException e)
        {
            invalid(version);
        }
        catch (InstantiationException e)
        {
            invalid(version);
        }
        catch (IllegalAccessException e)
        {
            invalid(version);
        }
        catch (InvocationTargetException e)
        {
            invalid(version);
        }
        catch (NoSuchMethodException e)
        {
            invalid(version);
        }
        catch (XMLStreamException e)
        {
            invalid(version);
        }

        /*
         * Appease javac with a return value that cannot be reached.
         */
        return null;
    }

    private static void invalid(Version version) throws ContentException
    {
        String message = version == null
                ? "Backend did not respond properly"
                : "Version " + version.getValue() + " is not supported";
        throw new ContentException(message);
    }
}

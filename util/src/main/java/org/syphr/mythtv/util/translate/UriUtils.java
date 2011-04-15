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
package org.syphr.mythtv.util.translate;

import java.net.URI;
import java.net.URISyntaxException;

public class UriUtils
{
    public static URI toUri(String uri) throws URISyntaxException
    {
        return new URI(uri.replace(" ", "%20"));
    }

    public static String toString(URI uri)
    {
        return uri.toString().replace("%20", " ");
    }

    public static String toPathString(URI uri)
    {
        return uri.getPath().replace("%20", " ");
    }

    private UriUtils()
    {
        /*
         * Static utility class
         */
    }
}

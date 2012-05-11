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
package org.syphr.mythtv.control.data;

import org.syphr.mythtv.types.FrontendLocation;

public class UIPathElement
{
    private final FrontendLocation location;
    private final String customElement;

    public UIPathElement(FrontendLocation location)
    {
        this(location, null);
    }

    public UIPathElement(String customElement)
    {
        this(null, customElement);
    }

    private UIPathElement(FrontendLocation location, String customElement)
    {
        this.location = location;
        this.customElement = customElement;
    }

    public boolean isLocation()
    {
        return location != null;
    }

    public FrontendLocation getLocation()
    {
        return location;
    }

    public boolean isCustomElement()
    {
        return customElement != null;
    }

    public String getCustomElement()
    {
        return customElement;
    }

    @Override
    public String toString()
    {
        if (isLocation())
        {
            return location.toString();
        }

        return customElement;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customElement == null) ? 0 : customElement.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        UIPathElement other = (UIPathElement)obj;
        if (customElement == null)
        {
            if (other.customElement != null)
            {
                return false;
            }
        }
        else if (!customElement.equals(other.customElement))
        {
            return false;
        }
        if (location != other.location)
        {
            return false;
        }
        return true;
    }
}

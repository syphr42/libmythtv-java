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
package org.syphr.mythtv.ws.frontend;

import java.util.Date;
import java.util.List;

import org.syphr.mythtv.ws.data.Action;
import org.syphr.mythtv.ws.data.FrontendStatus;

public interface FrontendService
{
    public List<Action> getActionList(String context);

    public List<String> getContextList();

    public FrontendStatus getStatus();

    public boolean playRecording(long chanId, Date startTime);

    public boolean playVideo(String id, Boolean useBookmark);

    public boolean sendAction(String action, String value, Long width, Long height);

    public boolean sendMessage(String message, Long timeout);
}
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
package org.syphr.mythtv.protocol.impl;

import java.util.List;

import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.DriveInfo;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.data.UpcomingRecordings;
import org.syphr.mythtv.util.exception.ProtocolException;

public interface Parser
{
    public List<String> splitArguments(String value);

    public String combineArguments(String... args);

    public String combineArguments(List<String> args);

    public List<DriveInfo> parseDriveInfo(String value) throws ProtocolException;

    public UpcomingRecordings parseUpcomingRecordings(String value) throws ProtocolException;

    public List<Program> parseProgramInfos(List<String> args) throws ProtocolException;

    public Program parseProgramInfo(List<String> args) throws ProtocolException;

    public Channel parseChannel(String value) throws ProtocolException;

    public List<String> extractProgramInfo(Program program) throws ProtocolException;

    public List<String> extractChannel(Channel channel);
}

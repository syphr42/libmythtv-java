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
package org.syphr.mythtv.protocol.impl;

import java.io.IOException;

import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;

public class QueryRecorder66 extends QueryRecorder63
{
    public QueryRecorder66(Translator translator,
                           Parser parser,
                           int recorderId,
                           SocketManager socketManager)
    {
        super(translator, parser, recorderId, socketManager);
    }

    @Override
    public long getFilePosition() throws IOException, CommandException
    {
        return new Command66QueryRecorderGetFilePosition(getTranslator(),
                                                         getParser(),
                                                         getRecorderId()).send(getSocketManager());
    }

    @Override
    public long getFramesWritten() throws IOException, CommandException
    {
        return new Command66QueryRecorderGetFramesWritten(getTranslator(),
                                                          getParser(),
                                                          getRecorderId()).send(getSocketManager());
    }

    @Override
    public long getKeyframePos(long desiredPosition) throws IOException,
                                                    CommandException
    {
        return new Command66QueryRecorderGetKeyframePos(getTranslator(),
                                                        getParser(),
                                                        getRecorderId(),
                                                        desiredPosition).send(getSocketManager());
    }

    @Override
    public long getMaxBitrate() throws IOException, CommandException
    {
        return new Command66QueryRecorderGetMaxBitrate(getTranslator(),
                                                       getParser(),
                                                       getRecorderId()).send(getSocketManager());
    }
}

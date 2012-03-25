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
package org.syphr.mythtv.protocol.events.impl.sender;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.data.TunerStatus;
import org.syphr.mythtv.data.TunerStatus.TunerData;
import org.syphr.mythtv.protocol.events.BackendEventListener63;
import org.syphr.mythtv.protocol.events.impl.BackendMessage;
import org.syphr.mythtv.protocol.events.impl.EventSender;
import org.syphr.mythtv.types.TunerStatusCategory;

public class EventSender63Signal implements EventSender<BackendEventListener63>
{
    private final Translator translator;

    private SignalEventType type;
    private int recorder;
    private String statusMessage;
    private List<Pair<TunerStatusCategory, TunerData>> dataPairs;

    public EventSender63Signal(Translator translator)
    {
        this.translator = translator;
    }

    @Override
    public void processMessage(BackendMessage message) throws ProtocolException
    {
        recorder = Integer.parseInt(message.getArgs().get(0));

        List<String> data = message.getData();

        if ("message".equals(data.get(0)))
        {
            statusMessage = data.get(1);
            return;
        }

        dataPairs = new ArrayList<Pair<TunerStatusCategory, TunerData>>();

        int i = 0;
        while (i < data.size())
        {
            String name = data.get(i++);

            String[] split = data.get(i++).split(" ");
            TunerStatusCategory category = translator.toEnum(split[0], TunerStatusCategory.class);

            TunerData tunerData = new TunerData(name,
                                                Integer.parseInt(split[1]),
                                                Integer.parseInt(split[2]),
                                                Integer.parseInt(split[3]),
                                                Integer.parseInt(split[4]),
                                                Integer.parseInt(split[5]),
                                                Integer.parseInt(split[6]),
                                                Integer.parseInt(split[7]));

            dataPairs.add(Pair.of(category, tunerData));
        }
    }

    @Override
    public void sendEvent(BackendEventListener63 l)
    {
        switch (type)
        {
            case MESSAGE:
                l.signalMessage(recorder, statusMessage);
                break;

            case TUNER_STATUS:
                l.signalTunerStatus(recorder,
                                    new TunerStatus(dataPairs.toArray(new Pair[dataPairs.size()])));
                break;
        }
    }

    protected enum SignalEventType
    {
        MESSAGE, TUNER_STATUS
    }
}

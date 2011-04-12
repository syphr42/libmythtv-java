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
package org.syphr.mythtv.protocol.events.impl;

import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.data.TunerStatus;
import org.syphr.mythtv.data.TunerStatus.TunerData;
import org.syphr.mythtv.protocol.events.BackendEventListener63;
import org.syphr.mythtv.protocol.events.SystemEvent;
import org.syphr.mythtv.protocol.events.SystemEventData;
import org.syphr.mythtv.protocol.impl.Protocol63Utils;
import org.syphr.mythtv.types.TunerStatusCategory;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.translate.DateUtils;

public class EventProtocol63 extends AbstractEventProtocol<BackendEventListener63>
{
    public EventProtocol63()
    {
        super(BackendEventListener63.class);
    }

    @Override
    protected EventSender<BackendEventListener63> createSender(List<String> fragments) throws ProtocolException
    {
        BackendMessage63 message = new BackendMessage63(fragments);

        try
        {
            String command = message.getCommand();

            if ("CLEAR_SETTINGS_CACHE".equals(command))
            {
                return new EventSender<BackendEventListener63>()
                {
                    @Override
                    public void sendEvent(BackendEventListener63 l)
                    {
                        l.clearSettingsCache();
                    }
                };
            }
            else if ("COMMFLAG_REQUEST".equals(command))
            {
                final String[] chanIdTimestamp = message.getArgs().get(0).split("_");
                final Channel channel = new Channel(Integer.parseInt(chanIdTimestamp[0]));
                final Date startTime = DateUtils.getIsoDateFormat().parse(chanIdTimestamp[1]);

                return new EventSender<BackendEventListener63>()
                {
                    @Override
                    public void sendEvent(BackendEventListener63 l)
                    {
                        l.commFlagRequest(channel, startTime);
                    }
                };
            }
            else if ("DONE_RECORDING".equals(command))
            {
                List<String> args = message.getArgs();

                final int recorder = Integer.parseInt(args.get(0));
                final long seconds = Long.parseLong(args.get(1));
                final long frames = Long.parseLong(args.get(2));

                return new EventSender<BackendEventListener63>()
                {
                    @Override
                    public void sendEvent(BackendEventListener63 l)
                    {
                        l.doneRecording(recorder, seconds, frames);
                    }
                };
            }
            else if ("DOWNLOAD_FILE".equals(command))
            {
                String type = message.getArgs().get(0);

                if ("UPDATE".equals(type))
                {
                    final URL remoteUrl = new URL(message.getData().get(0));
                    final URI localUri = new URI(message.getData().get(1));
                    final long bytesReceived = Long.parseLong(message.getData().get(2));
                    final long bytesTotal = Long.parseLong(message.getData().get(3));

                    return new EventSender<BackendEventListener63>()
                    {
                        @Override
                        public void sendEvent(BackendEventListener63 l)
                        {
                            l.downloadFileUpdate(remoteUrl,
                                                 localUri,
                                                 bytesReceived,
                                                 bytesTotal);
                        }
                    };
                }
                else if ("FINISHED".equals(type))
                {
                    final URL remoteUrl = new URL(message.getData().get(0));
                    final URI localUri = new URI(message.getData().get(1));
                    final long bytesTotal = Long.parseLong(message.getData().get(2));
                    final String errorText = message.getData().get(3);
                    final int errorCode = Integer.parseInt(message.getData().get(4));

                    return new EventSender<BackendEventListener63>()
                    {
                        @Override
                        public void sendEvent(BackendEventListener63 l)
                        {
                            l.downloadFileFinshed(remoteUrl,
                                                  localUri,
                                                  bytesTotal,
                                                  errorText,
                                                  errorCode);
                        }
                    };
                }
            }
            else if ("GENERATED_PIXMAP".equals(command))
            {
                if (!"OK".equals(message.getData().get(0)))
                {
                    throw new ProtocolException(message.toString(), Direction.RECEIVE);
                }

                DateFormat isoFormat = DateUtils.getIsoDateFormat();

                final String[] chanIdTimestamp = message.getData().get(1).split("_");
                final Channel channel = new Channel(Integer.parseInt(chanIdTimestamp[0]));
                final Date timestamp = isoFormat.parse(chanIdTimestamp[1]);

                final String comment = message.getData().get(2);
                final Date timestamp2 = isoFormat.parse(message.getData().get(3));
                final long num1 = Long.parseLong(message.getData().get(4));
                final long num2 = Long.parseLong(message.getData().get(5));
                final byte[] bytes = message.getData().get(6).getBytes();

                return new EventSender<BackendEventListener63>()
                {
                    @Override
                    public void sendEvent(BackendEventListener63 l)
                    {
                        l.generatedPixmap(channel,
                                          timestamp,
                                          comment,
                                          timestamp2,
                                          num1,
                                          num2,
                                          bytes);
                    }
                };
            }
            else if ("LIVETV_CHAIN".equals(command))
            {
                if ("UPDATE".equals(message.getArgs().get(0)))
                {
                    final String chainId = message.getArgs().get(1);

                    return new EventSender<BackendEventListener63>()
                    {
                        @Override
                        public void sendEvent(BackendEventListener63 l)
                        {
                            l.liveTvChainUpdate(chainId);
                        }
                    };
                }
            }
            else if ("LIVETV_WATCH".equals(command))
            {
                final int recorder = Integer.parseInt(message.getArgs().get(0));
                final boolean recordingIsActive = "1".equals(message.getArgs().get(1));

                return new EventSender<BackendEventListener63>()
                {
                    @Override
                    public void sendEvent(BackendEventListener63 l)
                    {
                        l.liveTvWatch(recorder, recordingIsActive);
                    }
                };
            }
            else if ("MASTER_UPDATE_PROG_INFO".equals(command))
            {
                final Channel channel = new Channel(Integer.parseInt(message.getArgs().get(0)));
                final Date startTime = DateUtils.getIsoDateFormat().parse(message.getArgs().get(1));

                return new EventSender<BackendEventListener63>()
                {
                    @Override
                    public void sendEvent(BackendEventListener63 l)
                    {
                        l.masterUpdateProgInfo(channel, startTime);
                    }
                };
            }
            else if ("RECORDING_LIST_CHANGE".equals(command))
            {
                List<String> args = message.getArgs();

                if ("ADD".equals(args.get(0)))
                {
                    final Channel channel = new Channel(Integer.parseInt(args.get(1)));
                    final Date startTime = DateUtils.getIsoDateFormat().parse(args.get(2));

                    return new EventSender<BackendEventListener63>()
                    {
                        @Override
                        public void sendEvent(BackendEventListener63 l)
                        {
                            l.recordingListChangeAdd(channel, startTime);
                        }
                    };
                }
                else if ("UPDATE".equals(args.get(0)))
                {
                    final Program program = Protocol63Utils.parseProgramInfo(message.getData());

                    return new EventSender<BackendEventListener63>()
                    {
                        @Override
                        public void sendEvent(BackendEventListener63 l)
                        {
                            l.recordingListChangeUpdate(program);
                        }
                    };
                }
                else if ("DELETE".equals(args.get(0)))
                {
                    final Channel channel = new Channel(Integer.parseInt(args.get(1)));
                    final Date startTime = DateUtils.getIsoDateFormat().parse(args.get(2));

                    return new EventSender<BackendEventListener63>()
                    {
                        @Override
                        public void sendEvent(BackendEventListener63 l)
                        {
                            l.recordingListChangeDelete(channel, startTime);
                        }
                    };
                }
            }
            else if ("SCHEDULE_CHANGE".equals(command))
            {
                return new EventSender<BackendEventListener63>()
                {
                    @Override
                    public void sendEvent(BackendEventListener63 l)
                    {
                        l.scheduleChange();
                    }
                };
            }
            else if ("SIGNAL".equals(command))
            {
                final int recorder = Integer.parseInt(message.getArgs().get(0));

                List<String> data = message.getData();

                if ("message".equals(data.get(0)))
                {
                    final String statusMessage = data.get(1);

                    return new EventSender<BackendEventListener63>()
                    {
                        @Override
                        public void sendEvent(BackendEventListener63 l)
                        {
                            l.signalMessage(recorder, statusMessage);
                        }
                    };
                }

                final List<Pair<TunerStatusCategory, TunerData>> dataPairs = new ArrayList<Pair<TunerStatusCategory, TunerData>>();

                int i = 0;
                while (i < data.size())
                {
                    TunerStatusCategory category = Protocol63Utils.getTranslator().toEnum(data.get(i++), TunerStatusCategory.class);

                    String[] split = data.get(i++).split(" ");
                    TunerData tunerData = new TunerData(split[0],
                                                        Integer.parseInt(split[1]),
                                                        Integer.parseInt(split[2]),
                                                        Integer.parseInt(split[3]),
                                                        Integer.parseInt(split[4]),
                                                        Integer.parseInt(split[5]),
                                                        Integer.parseInt(split[6]),
                                                        Integer.parseInt(split[7]));

                    dataPairs.add(Pair.of(category, tunerData));
                }

                return new EventSender<BackendEventListener63>()
                {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void sendEvent(BackendEventListener63 l)
                    {
                        l.signalTunerStatus(recorder,
                                            new TunerStatus(dataPairs.toArray(new Pair[dataPairs.size()])));
                    }
                };
            }
            else if ("SYSTEM_EVENT".equals(command))
            {
                final List<String> args = message.getArgs();
                final SystemEvent event = SystemEvent.valueOf(args.get(0));
                final Map<SystemEventData, String> dataMap = new HashMap<SystemEventData, String>();

                DateFormat startTimeFormat = DateUtils.getIsoDateFormat();

                for (int i = 1; i < args.size(); i += 2)
                {
                    String dataType = args.get(i);
                    String dataValue = args.get(i + 1);

                    if ("SENDER".equals(dataType))
                    {
                        dataMap.put(SystemEventData.SENDER, dataValue);
                    }
                    else if ("HOSTNAME".equals(dataType))
                    {
                        dataMap.put(SystemEventData.HOSTNAME, dataValue);
                    }
                    else if ("CARDID".equals(dataType))
                    {
                        dataMap.put(SystemEventData.CARD_ID, dataValue);
                    }
                    else if ("CHANID".equals(dataType))
                    {
                        dataMap.put(SystemEventData.CHAN_ID, dataValue);
                    }
                    else if ("STARTTIME".equals(dataType))
                    {
                        dataMap.put(SystemEventData.START_TIME,
                                    String.valueOf(startTimeFormat.parse(dataValue)
                                                                  .getTime()));
                    }
                    else if ("SECS".equals(dataType))
                    {
                        dataMap.put(SystemEventData.SECS, dataValue);
                    }
                }

                return new EventSender<BackendEventListener63>()
                {
                    @Override
                    public void sendEvent(BackendEventListener63 l)
                    {
                        l.systemEvent(event, dataMap);
                    }
                };
            }
            else if ("UPDATE_FILE_SIZE".equals(command))
            {
                List<String> args = message.getArgs();
                if (args.size() != 3)
                {
                    throw new ProtocolException(message.toString(), Direction.RECEIVE);
                }

                final Channel channel = new Channel(Integer.parseInt(args.get(0)));
                final Date startTime = DateUtils.getIsoDateFormat().parse(args.get(1));
                final long size = Long.parseLong(args.get(2));

                return new EventSender<BackendEventListener63>()
                {
                    @Override
                    public void sendEvent(BackendEventListener63 l)
                    {
                        l.updateFileSize(channel, startTime, size);
                    }
                };
            }
            else if ("VIDEO_LIST_CHANGE".equals(command))
            {
                return new EventSender<BackendEventListener63>()
                {
                    @Override
                    public void sendEvent(BackendEventListener63 l)
                    {
                        l.videoListChange();
                    }
                };
            }
        }
        catch (Exception e)
        {
            throw new ProtocolException(message.toString(), Direction.RECEIVE, e);
        }

        throw new ProtocolException("Unknown backend message: " + message.toString(), Direction.RECEIVE);
    }
}

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
package org.syphr.mythtv.http.backend.impl;

import java.io.IOException;
import java.util.List;

import javax.xml.ws.BindingProvider;

import org.syphr.mythtv.http.ServiceVersionException;
import org.syphr.mythtv.http.backend.CaptureService;
import org.syphr.mythtv.http.backend.impl._0_25.capture.Capture;
import org.syphr.mythtv.http.backend.impl._0_25.capture.CaptureCard;
import org.syphr.mythtv.http.backend.impl._0_25.capture.CaptureServices;
import org.syphr.mythtv.http.impl.AbstractService;
import org.syphr.mythtv.http.impl.ServiceUtils;

public class CaptureService0_25 extends AbstractService implements CaptureService
{
    private static final String NAME = "Capture";

    private static final String VERSION = "1.4";

    private final Capture service;

    public CaptureService0_25(String host, int port) throws ServiceVersionException, IOException
    {
        CaptureServices locator = new CaptureServices();
        service = locator.getBasicHttpBindingCapture();

        configureAndVerify(host, port, (BindingProvider)service);
    }

    @Override
    protected String getName()
    {
        return NAME;
    }

    @Override
    protected String getVersion()
    {
        return VERSION;
    }

    @Override
    public Integer addCaptureCard(String videoDevice,
                                  String audioDevice,
                                  String vbiDevice,
                                  String cardType,
                                  Long audioRateLimit,
                                  String hostName,
                                  Long dvbswFilter,
                                  Long dvbSatType,
                                  Boolean dvbWaitForSeqStart,
                                  Boolean skipBTAudio,
                                  Boolean dvbOnDemand,
                                  Long dvbDiSEqCType,
                                  Long firewireSpeed,
                                  String firewireModel,
                                  Long firewireConnection,
                                  Long signalTimeout,
                                  Long channelTimeout,
                                  Long dvbTuningDelay,
                                  Long contrast,
                                  Long brightness,
                                  Long colour,
                                  Long hue,
                                  Long diSEqCId,
                                  Boolean dvbeitScan)
    {
        return service.addCaptureCard(videoDevice,
                                      audioDevice,
                                      vbiDevice,
                                      cardType,
                                      audioRateLimit,
                                      hostName,
                                      dvbswFilter,
                                      dvbSatType,
                                      dvbWaitForSeqStart,
                                      skipBTAudio,
                                      dvbOnDemand,
                                      dvbDiSEqCType,
                                      firewireSpeed,
                                      firewireModel,
                                      firewireConnection,
                                      signalTimeout,
                                      channelTimeout,
                                      dvbTuningDelay,
                                      contrast,
                                      brightness,
                                      colour,
                                      hue,
                                      diSEqCId,
                                      dvbeitScan);
    }

    @Override
    public Integer addCardInput(Long cardId,
                                Long sourceId,
                                String inputName,
                                String externalCommand,
                                String changerDevice,
                                String changerModel,
                                String hostName,
                                String tuneChan,
                                String startChan,
                                String displayName,
                                Boolean dishnetEIT,
                                Long recPriority,
                                Long quicktune,
                                Long schedOrder,
                                Long liveTVOrder)
    {
        return service.addCardInput(cardId,
                                    sourceId,
                                    inputName,
                                    externalCommand,
                                    changerDevice,
                                    changerModel,
                                    hostName,
                                    tuneChan,
                                    startChan,
                                    displayName,
                                    dishnetEIT,
                                    recPriority,
                                    quicktune,
                                    schedOrder,
                                    liveTVOrder);
    }

    @Override
    public CaptureCard getCaptureCard(Integer cardId)
    {
        return service.getCaptureCard(cardId);
    }

    @Override
    public List<CaptureCard> getCaptureCardList(String hostName, String cardType)
    {
        // TODO
        return null; //service.getCaptureCardList(hostName, cardType);
    }

    @Override
    public boolean removeCaptureCard(Integer cardId)
    {
        return ServiceUtils.toPrimitive(service.removeCaptureCard(cardId));
    }

    @Override
    public boolean removeCardInput(Integer cardInputId)
    {
        return ServiceUtils.toPrimitive(service.removeCardInput(cardInputId));
    }

    @Override
    public boolean updateCaptureCard(Integer cardId, String setting, String value)
    {
        return ServiceUtils.toPrimitive(service.updateCaptureCard(cardId, setting, value));
    }

    @Override
    public boolean updateCardInput(Integer cardInputId, String setting, String value)
    {
        return ServiceUtils.toPrimitive(service.updateCardInput(cardInputId, setting, value));
    }
}

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
package org.syphr.mythtv.ws.backend.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.BindingProvider;

import org.syphr.mythtv.data.CaptureCard;
import org.syphr.mythtv.http.backend.impl._0_25.capture.ArrayOfCaptureCard;
import org.syphr.mythtv.http.backend.impl._0_25.capture.Capture;
import org.syphr.mythtv.http.backend.impl._0_25.capture.CaptureCardList;
import org.syphr.mythtv.http.backend.impl._0_25.capture.CaptureServices;
import org.syphr.mythtv.ws.ServiceVersionException;
import org.syphr.mythtv.ws.impl.ServiceUtils;

public class CaptureService0_25 extends AbstractCaptureService
{
    private static final String VERSION = "1.4";

    private final Capture service;

    public CaptureService0_25(String host, int port) throws ServiceVersionException, IOException
    {
        CaptureServices locator = new CaptureServices();
        service = locator.getBasicHttpBindingCapture();

        configureAndVerify(host, port, (BindingProvider)service);
    }

    @Override
    protected String getVersion()
    {
        return VERSION;
    }

    @Override
    public Integer addCaptureCard(CaptureCard card)
    {
        return service.addCaptureCard(card.getVideoDevice(),
                                      card.getAudioDevice(),
                                      card.getVbiDevice(),
                                      card.getCardType(),
                                      card.getAudioRateLimit(),
                                      card.getHostName(),
                                      card.getDvbswFilter(),
                                      card.getDvbSatType(),
                                      card.getDvbWaitForSeqStart(),
                                      card.getSkipBTAudio(),
                                      card.getDvbOnDemand(),
                                      card.getDvbDiSEqCType(),
                                      card.getFirewireSpeed(),
                                      card.getFirewireModel(),
                                      card.getFirewireConnection(),
                                      card.getSignalTimeout(),
                                      card.getChannelTimeout(),
                                      card.getDvbTuningDelay(),
                                      card.getContrast(),
                                      card.getBrightness(),
                                      card.getColour(),
                                      card.getHue(),
                                      card.getDiSEqCId(),
                                      card.getDvbeitScan());
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
    public CaptureCard getCaptureCard(int cardId)
    {
        return convert(service.getCaptureCard(cardId));
    }

    @Override
    public List<CaptureCard> getCaptureCardList(String hostName, String cardType)
    {
        List<CaptureCard> cards = new ArrayList<CaptureCard>();

        CaptureCardList result = service.getCaptureCardList(hostName, cardType);
        if (result == null)
        {
            return cards;
        }

        ArrayOfCaptureCard array = result.getCaptureCards();
        if (array == null)
        {
            return cards;
        }

        List<org.syphr.mythtv.http.backend.impl._0_25.capture.CaptureCard> list = array.getCaptureCards();
        if (list == null)
        {
            return cards;
        }

        for (org.syphr.mythtv.http.backend.impl._0_25.capture.CaptureCard rCard : list)
        {
            CaptureCard card = convert(rCard);

            if (card != null)
            {
                cards.add(card);
            }
        }

        return cards;
    }

    @Override
    public boolean removeCaptureCard(int cardId)
    {
        return ServiceUtils.toPrimitive(service.removeCaptureCard(cardId));
    }

    @Override
    public boolean removeCardInput(int cardInputId)
    {
        return ServiceUtils.toPrimitive(service.removeCardInput(cardInputId));
    }

    @Override
    public boolean updateCaptureCard(int cardId, String setting, String value)
    {
        return ServiceUtils.toPrimitive(service.updateCaptureCard(cardId, setting, value));
    }

    @Override
    public boolean updateCardInput(int cardInputId, String setting, String value)
    {
        return ServiceUtils.toPrimitive(service.updateCardInput(cardInputId, setting, value));
    }

    private CaptureCard convert(org.syphr.mythtv.http.backend.impl._0_25.capture.CaptureCard rCard)
    {
        if (rCard == null)
        {
            return null;
        }

        CaptureCard card = new CaptureCard();
        card.setId(rCard.getCardId());
        card.setVideoDevice(rCard.getVideoDevice());
        card.setAudioDevice(rCard.getAudioDevice());
        card.setVbiDevice(rCard.getVBIDevice());
        card.setCardType(rCard.getCardType());
        card.setAudioRateLimit(rCard.getAudioRateLimit());
        card.setHostName(rCard.getHostName());
        card.setDvbswFilter(rCard.getDVBSWFilter());
        card.setDvbSatType(rCard.getDVBSatType());
        card.setDvbWaitForSeqStart(rCard.isDVBWaitForSeqStart());
        card.setSkipBTAudio(rCard.isSkipBTAudio());
        card.setDvbOnDemand(rCard.isDVBOnDemand());
        card.setDvbDiSEqCType(rCard.getDVBDiSEqCType());
        card.setFirewireSpeed(rCard.getFirewireSpeed());
        card.setFirewireModel(rCard.getFirewireModel());
        card.setFirewireConnection(rCard.getFirewireConnection());
        card.setSignalTimeout(rCard.getSignalTimeout());
        card.setChannelTimeout(rCard.getChannelTimeout());
        card.setDvbTuningDelay(rCard.getDVBTuningDelay());
        card.setContrast(rCard.getContrast());
        card.setBrightness(rCard.getBrightness());
        card.setColour(rCard.getColour());
        card.setHue(rCard.getHue());
        card.setDiSEqCId(rCard.getDiSEqCId());
        card.setDvbeitScan(ServiceUtils.toPrimitive(rCard.isDVBEITScan()));

        return card;
    }
}

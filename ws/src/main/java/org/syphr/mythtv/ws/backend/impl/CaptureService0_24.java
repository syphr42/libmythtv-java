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

import java.util.List;

import org.syphr.mythtv.data.CaptureCard;

public class CaptureService0_24 extends AbstractCaptureService
{
    @Override
    public Integer addCaptureCard(CaptureCard card)
    {
        handleUnsupported("add capture card");
        return null;
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
        handleUnsupported("add card input");
        return null;
    }

    @Override
    public CaptureCard getCaptureCard(int cardId)
    {
        handleUnsupported("get capture card");
        return null;
    }

    @Override
    public List<CaptureCard> getCaptureCardList(String hostName, String cardType)
    {
        handleUnsupported("get capture card list");
        return null;
    }

    @Override
    public boolean removeCaptureCard(int cardId)
    {
        handleUnsupported("remove capture card");
        return false;
    }

    @Override
    public boolean removeCardInput(int cardInputId)
    {
        handleUnsupported("remove card input");
        return false;
    }

    @Override
    public boolean updateCaptureCard(int cardId, String setting, String value)
    {
        handleUnsupported("update capture card");
        return false;
    }

    @Override
    public boolean updateCardInput(int cardInputId, String setting, String value)
    {
        handleUnsupported("udpate card input");
        return false;
    }

    @Override
    protected String getVersion()
    {
        return null;
    }
}
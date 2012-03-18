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
package org.syphr.mythtv.ws.backend;

import java.util.List;

import org.syphr.mythtv.data.CaptureCard;

public interface CaptureService
{
    public Integer addCaptureCard(CaptureCard card);

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
                                Long liveTVOrder);

    public CaptureCard getCaptureCard(int cardId);

    public List<CaptureCard> getCaptureCardList(String hostName, String cardType);

    public boolean removeCaptureCard(int cardId);

    public boolean removeCardInput(int cardInputId);

    public boolean updateCaptureCard(int cardId, String setting, String value);

    public boolean updateCardInput(int cardInputId, String setting, String value);
}
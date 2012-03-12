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
package org.syphr.mythtv.http.backend;

import java.util.List;

import org.syphr.mythtv.http.backend.impl._0_25.capture.CaptureCard;

public interface CaptureService
{
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
                                  Boolean dvbeitScan);

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

    public CaptureCard getCaptureCard(Integer cardId);

    public List<CaptureCard> getCaptureCardList(String hostName, String cardType);

    public boolean removeCaptureCard(Integer cardId);

    public boolean removeCardInput(Integer cardInputId);

    public boolean updateCaptureCard(Integer cardId, String setting, String value);

    public boolean updateCardInput(Integer cardInputId, String setting, String value);
}
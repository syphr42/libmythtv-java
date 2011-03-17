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
package org.syphr.mythtv.protocol.types;

public enum VideoEditMark
{
    ALL,
    UNSET,
    TMP_CUT_END,
    TMP_CUT_START,
    UPDATED_CUT,
    PLACEHOLDER,
    CUT_END,
    CUT_START,
    BOOKMARK,
    BLANK_FRAME,
    COMM_START,
    COMM_END,
    GOP_START,
    KEYFRAME,
    SCENE_CHANGE,
    GOP_BYFRAME,
    ASPECT_1_1,
    ASPECT_4_3,
    ASPECT_16_9,
    ASPECT_2_21_1,
    ASPECT_CUSTOM,
    VIDEO_WIDTH,
    VIDEO_HEIGHT,
    VIDEO_RATE,
    DURATION_MS;
}

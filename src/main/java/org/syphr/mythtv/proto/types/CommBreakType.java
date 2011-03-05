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
package org.syphr.mythtv.proto.types;

public enum CommBreakType
{
    MARK_ALL,
    MARK_UNSET,
    MARK_TMP_CUT_END,
    MARK_TMP_CUT_START,
    MARK_UPDATED_CUT,
    MARK_PLACEHOLDER,
    MARK_CUT_END,
    MARK_CUT_START,
    MARK_BOOKMARK,
    MARK_BLANK_FRAME,
    MARK_COMM_START,
    MARK_COMM_END,
    MARK_GOP_START,
    MARK_KEYFRAME,
    MARK_SCENE_CHANGE,
    MARK_GOP_BYFRAME,
    MARK_ASPECT_1_1,
    MARK_ASPECT_4_3,
    MARK_ASPECT_16_9,
    MARK_ASPECT_2_21_1,
    MARK_ASPECT_CUSTOM,
    MARK_VIDEO_WIDTH,
    MARK_VIDEO_HEIGHT,
    MARK_VIDEO_RATE,
    MARK_DURATION_MS;
}

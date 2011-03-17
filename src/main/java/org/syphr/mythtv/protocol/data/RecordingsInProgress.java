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
package org.syphr.mythtv.protocol.data;

public class RecordingsInProgress
{
    private final int total;
    private final int liveTv;

    public RecordingsInProgress(int total, int liveTv)
    {
        this.total = total;
        this.liveTv = liveTv;
    }

    public int getTotal()
    {
        return total;
    }

    public int getLiveTv()
    {
        return liveTv;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("RecordingsInProgress [total=");
        builder.append(total);
        builder.append(", liveTv=");
        builder.append(liveTv);
        builder.append("]");
        return builder.toString();
    }
}

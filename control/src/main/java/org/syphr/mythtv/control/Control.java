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
package org.syphr.mythtv.control;

import java.io.IOException;
import java.util.List;

import org.syphr.mythtv.control.types.JumpPoint;
import org.syphr.mythtv.util.socket.SocketManager;

/**
 * This interface represents the combined frontend control API of all MythTV versions that
 * are supported. However, any functionality that is not part of the most current stable
 * release of MythTV will be marked as deprecated.
 *
 * @author Gregory P. Moyer
 */
public interface Control
{
    public void connect(String host, int port, final long timeout) throws IOException;

    /**
     * Retrieve the low-level communications manager. In most cases, it should not be
     * necessary to access this object directly.
     *
     * @return the socket manager that controls communication with the frontend
     */
    public SocketManager getSocketManager();

    /**
     * TODO
     *
     * Retrieve a list of constants of the given type that are valid for this
     * control version.<br>
     * <br>
     * This is useful to know before trying a command that a certain option is
     * valid. For example, to use {@link #queryRecordings(RecordingCategory)}
     * without the risk of sending an invalid category, first make sure it is in
     * the list returned from
     * <code>getAvailableTypes(RecordingCategory.class)</code>
     *
     * @param <E>
     *            the generic enum type
     * @param type
     *            the enum class
     * @return a list of constants that are valid for this protocol
     */
    public <E extends Enum<E>> List<E> getAvailableTypes(Class<E> type);

    public void jump(JumpPoint jumpPoint) throws IOException;

    public void play() throws IOException;

    public void query() throws IOException;

    public void set() throws IOException;

    public void screenshot() throws IOException;

    public void exit() throws IOException;
}

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

import java.util.Calendar;
import java.util.List;

import org.syphr.mythtv.data.TimeInfo;
import org.syphr.mythtv.http.backend.impl._0_25.myth.ConnectionInfo;
import org.syphr.mythtv.http.backend.impl._0_25.myth.LogMessage;
import org.syphr.mythtv.http.backend.impl._0_25.myth.SettingList;
import org.syphr.mythtv.http.backend.impl._0_25.myth.StorageGroupDir;

public class MythService0_24 extends AbstractMythService
{
    @Override
    public boolean addStorageGroupDir(String groupName, String dirName, String hostName)
    {
        handleUnsupported("add storage group dir");
        return false;
    }

    @Override
    public boolean backupDatabase()
    {
        handleUnsupported("backup database");
        return false;
    }

    @Override
    public boolean changePassword(String userName, String oldPassword, String newPassword)
    {
        handleUnsupported("change password");
        return false;
    }

    @Override
    public boolean checkDatabase(boolean repair)
    {
        handleUnsupported("check database");
        return false;
    }

    @Override
    public ConnectionInfo getConnectionInfo(String pin)
    {
        handleUnsupported("get connection info");
        return null;
    }

    @Override
    public String getHostName()
    {
        handleUnsupported("get hostname");
        return null;
    }

    @Override
    public List<String> getHosts()
    {
        handleUnsupported("get hosts");
        return null;
    }

    @Override
    public List<String> getKeys()
    {
        handleUnsupported("get keys");
        return null;
    }

    @Override
    public List<LogMessage> getLogs(String hostName,
                                    String application,
                                    Integer pid,
                                    Integer tid,
                                    String thread,
                                    String filename,
                                    Integer line,
                                    String function,
                                    Calendar fromTime,
                                    Calendar toTime,
                                    String level,
                                    String msgContains)
    {
        handleUnsupported("get logs");
        return null;
    }

    @Override
    public SettingList getSetting(String hostName, String key, String _default)
    {
        handleUnsupported("get setting");
        return null;
    }

    @Override
    public List<StorageGroupDir> getStorageGroupDirs(String groupName, String hostName)
    {
        handleUnsupported("get storage group dirs");
        return null;
    }

    @Override
    public TimeInfo getTimeZone()
    {
        handleUnsupported("get time zone");
        return null;
    }

    @Override
    public boolean profileDelete()
    {
        handleUnsupported("profile delete");
        return false;
    }

    @Override
    public boolean profileSubmit()
    {
        handleUnsupported("profile submit");
        return false;
    }

    @Override
    public String profileText()
    {
        handleUnsupported("profile text");
        return null;
    }

    @Override
    public String profileURL()
    {
        handleUnsupported("profile URL");
        return null;
    }

    @Override
    public String profileUpdated()
    {
        handleUnsupported("profile updated");
        return null;
    }

    @Override
    public boolean putSetting(String hostName, String key, String value)
    {
        handleUnsupported("put setting");
        return false;
    }

    @Override
    public boolean removeStorageGroupDir(String groupName, String dirName, String hostName)
    {
        handleUnsupported("remove storage group dir");
        return false;
    }

    @Override
    public boolean sendMessage(String message, String address, Integer udpPort, Integer timeout)
    {
        handleUnsupported("send message");
        return false;
    }

    @Override
    public boolean testDBSettings(String hostName,
                                  String userName,
                                  String password,
                                  String dbName,
                                  Integer dbPort)
    {
        handleUnsupported("test DB settings");
        return false;
    }

    @Override
    protected String getVersion()
    {
        return null;
    }
}